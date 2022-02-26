package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.ADMIN_ID;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID;
// класс типа DAO должен предоставлять методы взаимодействия с Meal-ДБ

// @Repository указывает на то, что в контексте Спринга будет создан Бин этого класса для дальнейшей DI этого бина
@Repository
public class InMemoryMealRepository implements MealRepository {

    // Map  userId -> mealRepository
    // Мапа в кот под ключами хранятся id юзеров,
    // а под значениями- объекты InMemoryBaseRepository<Meal>,
    // кот содержат в себе ConcurrentHashMap<> в которой: ключи-id еды, значения-Meal
    // то каждому юзеру в этой мапе соотв. другая мапа в кот каждому id приема пищи соотв этот прием пищи
    //и эту мапу надо заполнить(в блоке инициализации)
    private Map<Integer, InMemoryBaseRepository<Meal>> usersMealsMap = new ConcurrentHashMap<>();

    //блок инициализации.
    {
        //берем лист MEALS еды юзера, кот инициализировался в блоке класса MealsUtil
        //и отправляем его в метод save этого класса, где выполнится computeIfAbsent,
        //те вычислить, если отсутствует такой ключ(а usersMealsMap сейчас пустая, те нет ключей никаких)

        // тогда мы заполняем мапу usersMealsMap: под ключем 1 кладем объект new InMemoryBaseRepository<>()
        // кот содержат в себе ConcurrentHashMap<> в которой по идее должна быть вся еда юзера1
        MealsUtil.MEALS.forEach(meal -> save(meal, USER_ID));

        //далее под ключем 2, добавим в мапу объект new InMemoryBaseRepository<>()
        // кот содержат в себе ConcurrentHashMap<> в которой по идее должна быть вся еда админа с id 2
        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500), ADMIN_ID);
    }


    @Override
    public Meal save(Meal meal, int userId) {
        //создаем сначала пустой объект InMemoryBaseRepository<>() кот содержат в себе ConcurrentHashMap<> в которой по идее должна быть вся еда юзера1
        InMemoryBaseRepository<Meal> meals = usersMealsMap.computeIfAbsent(userId, uid -> new InMemoryBaseRepository<>());
        //и в этом пустом объекте заполняем его внутреннюю мапу приемами пищи с пом его метода save
        return meals.save(meal);
    }

    //удаляем определенную еду у определенного пользователя
    @Override
    public boolean delete(int id, int userId) {
        //как удаляем: по ключу userId достаем из usersMealsMap объект, внутри которого мапа со всей едой пользователя
        InMemoryBaseRepository<Meal> meals = usersMealsMap.get(userId);
        //и на этом объекте вызываем метод его класса, кот из его внутренней мапы удалит пару ключ-значение по ключу
        return meals != null && meals.delete(id);
    }

    //берем определенную еду у определенного пользователя
    @Override
    public Meal get(int id, int userId) {
        //из usersMealsMap получаем объект InMemoryBaseRepository<Meal>, внутри которого мапа со всей едой определенного пользователя
        InMemoryBaseRepository<Meal> meals = usersMealsMap.get(userId);
        //достаем из этой мапы определенный прием пищи по его id
        return meals == null ? null : meals.get(id);
    }

//    метод, по запросу из MealService возвращает список всей еды List<Meal>, отправляя туда userId
    @Override
    public List<Meal> getAll(int userId) {
//        обращаемся к своему private методу:
        return getAllFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAllFiltered(userId, meal -> Util.isBetweenInclusive(meal.getDateTime(), startDateTime, endDateTime));
    }

//    метод возвращает лист со всей едой пользователя(предикат пока всегда true)
    private List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
//  получаем объект, внутри которого мапа со всей едой юзера(userId)
        InMemoryBaseRepository<Meal> meals = usersMealsMap.get(userId);
        //если null, возвращаем пустую коллекцию
        return meals == null ? Collections.emptyList() :
                //иначе у объекта получаем из его внутренней мапы  лист с ее значениями (map.values()) и индуцируем стрим на ней
                meals.getCollection().stream()
                        //пропускаем чз фильтр, кот всегда пока true
                        .filter(filter)
                        //сортируем с пом компаратора из класса Comparator
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        //собираем лист List<Meal>
                        .collect(Collectors.toList());
    }
}