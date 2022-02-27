package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractBaseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

//от него наследуется только InMemoryUserRepository
//класс типа DAO, параметризирован наследниками класса AbstractBaseEntity, относящемуся к model, а это Meal или User

// @Repository указывает на то, что в контексте Спринга будет создан Бин этого класса для дальнейшей DI этого бина
@Repository
public class InMemoryBaseRepository<T extends AbstractBaseEntity> {
//КЛАС ПАРАМЕТРИЗУЕТСЯ ТОЛЬКО НАСЛЕДНИКАМИ AbstractBaseEntity, ТЕ ЭТО  User  И  Meal
    //имеет только дефолтный конструктор. Содержит в поле Мапу и методы crud по работе с этой Мапой

//    Класс поставляет объекты, кот содержат в себе ConcurrentHashMap<> в которой:
            //если<Meal>: ключи-id еды, значения-Meal
            //если<User>: ключи-id юзеров, значения-User

//   объявляем сейф-треад счетчик- поставщик значений id еды для ключей Мапы:
    private static AtomicInteger counter = new AtomicInteger(0);

//    сейф-треад ConcurrentHashMap, где ключ- это Id(Atomic counter), а значение- это то, чем параметризирован класс. те User
    private Map<Integer, T> map = new ConcurrentHashMap<>();

//   метод для заполнения этой мапы. кладет в мапу Usera или Meal под ключем counter.incrementAndGet(работает только в ConcurrentHashMap?)
//    принимает объект юзера
    public T save(T entry) {
//     isNew() вернет true, если внутри объекта Meal поле id еще не инициализированно(те объект Meal создан чз низкоур. констр., кот не принимает id)
        if (entry.isNew()) {
//            вызываем сеттер для id AbstractBaseEntity (для юзера и его еды)
            entry.setId(counter.incrementAndGet());//если в объекте Meal поле id не инициализированно, инициализируем с пом counter
//            кладем в мапу
            map.put(entry.getId(), entry);
//            возвращаем юзера
            return entry;
        }
//      Если в объекте Meal поле id  уже инициализированно, делаем map.computeIfPresent
//      те в этом методе идет проверка- существует ли такой ключ в Мапе. Если такого ключа находится-
//      предпринимаются попытки вычислить новое значение для такого ключа с учетом этого ключа в лямбде с пом Functional
//      В данном случае  ключ присутствует, и при вычислении нового значения получится такой же объект, что и был
//      Если такой ключ отсутствует, то в мапе ничего не изменется
        return map.computeIfPresent(entry.getId(), (id, oldT) -> entry);
    }

//    обращается к методу мапы map.remove(id). возвращает true? если удачно удалили
    public boolean delete(int id) {
        return map.remove(id) != null;
    }

//    по id возвращаем Meal из мапы
    public T get(int id) {
        return map.get(id);
    }

//    все значения мапы кладет в коллекцию и возвращает эту коллекцию
    Collection<T> getCollection() {
        return map.values();
    }
}