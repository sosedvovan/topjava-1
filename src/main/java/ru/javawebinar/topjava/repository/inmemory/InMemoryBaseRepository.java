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

    /**
     * public abstract class AbstractBaseEntity {
     *
     *     protected Integer id;
     *
     *     protected AbstractBaseEntity(Integer id) {
     *         this.id = id;
     *     }
     *
     *     public void setId(Integer id) {
     *         this.id = id;
     *     }
     *
     *     public Integer getId() {
     *         return id;
     *     }
     *
     *     public boolean isNew() {
     *         return this.id == null;
     *     }
     *
     *     @Override
     *     public String toString() {
     *         return getClass().getSimpleName() + ":" + id;
     *     }
     * }
     */

//   объявляем сейф-треад счетчик:
    private static AtomicInteger counter = new AtomicInteger(0);

//    сейф-треад ConcurrentHashMap, где ключ- это Id(Atomic counter), а значение- это то, чем параметризирован класс. те User
    private Map<Integer, T> map = new ConcurrentHashMap<>();

//   метод кладет в мапу Usera под ключем counter.incrementAndGet(работает только в ConcurrentHashMap?)
//    принимает объект юзера
    public T save(T entry) {
//     isNew() вернет true, если поле id ( унаследованное от AbstractBaseEntity ) id == null
        if (entry.isNew()) {
//            вызываем сеттер для id AbstractBaseEntity (для юзера и его еды)
            entry.setId(counter.incrementAndGet());
//            кладем в мапу
            map.put(entry.getId(), entry);
//            возвращаем юзера
            return entry;
        }
//      Если значение для указанного ключа присутствует и не равно нулю, предпринимаются попытки
//     * вычислить новое сопоставление с учетом ключа и его текущего сопоставленного значения.
        return map.computeIfPresent(entry.getId(), (id, oldT) -> entry);
    }

//    обращается к методу мапы map.remove(id). возвращает true? если удачно удалили
    public boolean delete(int id) {
        return map.remove(id) != null;
    }

//    по id возвращаем юзера из мапы
    public T get(int id) {
        return map.get(id);
    }

//    все значения мапы кладет в коллекцию и возвращает эту коллекцию
    Collection<T> getCollection() {
        return map.values();
    }
}