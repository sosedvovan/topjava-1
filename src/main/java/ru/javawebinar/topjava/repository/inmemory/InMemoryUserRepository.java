package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

// класс типа DAO должен предоставлять методы взаимодействия с User-ДБ(в вирт. памяти пока)

// @Repository указывает на то, что в контексте Спринга будет создан Бин этого класса для дальнейшей DI этого бина
@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {
//    У класса есть Мапа, в кот хранятся все пользователи. в ключах этой мапы - id Юзеров, в значениях - сами Юзеры
//    так же реализованны различные CRUD методы, для работы с этой мапой.

    /**
     * private static AtomicInteger counter = new AtomicInteger(0);
     *
     * private Map<Integer, T> map = new ConcurrentHashMap<>();
     *
     * public T save(T entry) {...}
     *
     * public boolean delete(int id) {
     *         return map.remove(id) != null;
     *     }
     *
     * public T get(int id) {
     *         return map.get(id);
     *     }
     *
     *  Collection<T> getCollection() {
     *         return map.values();
     *     }
     */


//    пока тестовые USER и ADMIN
    static final int USER_ID = 1;
    static final int ADMIN_ID = 2;


    //Дореализовываем абстракты интерфейса UserRepository(кот не реализованны в супере InMemoryBaseRepository<User>)

    //возвращает список всех пользователей из Collection<T>
    //на возврате от метода getCollection() делаем стрим
    // .в котором делаем сортировку по имени Юзера с пом. метода Comparator.comparing()
    // подавая в его аргумены реализацию Function<User, String> кот. возвращает Стрингу-имя Юзера...  и далее сортируем по емейлу
    // . И собираем результат в List<User>
    @Override
    public List<User> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    //возвращаем Юзера из Collection<T>
    //на возврате от метода getCollection() делаем стрим
    //.фильтр- в потоке остаются только те Юзеры, емейлы которых equals с поданным в аргументы метода
    //.возвращаем первого найденного
    //.иначе возвращаем null
    @Override
    public User getByEmail(String email) {
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}