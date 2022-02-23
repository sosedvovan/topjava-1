package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

//бизнес логика для Meal. если ей нужен доступ к ДБ, то она обращается к repository
//здесь вызываются проверки возвратов-из-методов-репозитория
@Service
public class MealService {

//    поле- интерфейс MealRepository получает свою реализацию в виде InMemoryMealRepository
//    это репозиторий к которому будет обращаться этот сервис, если ему что-то понадобится в ДБ
    private final MealRepository repository;

//    конструктор. Спринг уже автоматически инициализирует поле repository бином MealRepository кот берет из своего контекста, и без анотации @Autowired
//    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

//    возвращает еду Meal принимая id еды и userId и запускает метод из репозитория. производится проверка на существование
    public Meal get(int id, int userId) {

        return checkNotFoundWithId(repository.get(id, userId), id);
    }

//    удаляет прием пищи с пом метода из репозитория? + проверка на существование
    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

//   возвращает лист с приемами пищи му 2-я датами с пом. метода из репозитория
    public List<Meal> getBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return repository.getBetween(
                DateTimeUtil.createDateTime(startDate, LocalDate.MIN, LocalTime.MIN),
                DateTimeUtil.createDateTime(endDate, LocalDate.MAX, LocalTime.MAX), userId);
    }

//    метод по запросу из MealRestController возвращает ему список всех юзеров
    public List<Meal> getAll(int userId) {
//    обращаемся к методу getAll() класса-репозитория приемов пищи: InMemoryMealRepository
        return repository.getAll(userId);
    }

//    обновляет прием пищи у конкретного юзера с пом метода из репозитория + проверка
    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    //    создает прием пищи у конкретного юзера с пом метода из репозитория + проверка
    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }
}