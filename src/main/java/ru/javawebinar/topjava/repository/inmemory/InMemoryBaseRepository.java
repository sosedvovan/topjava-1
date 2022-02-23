package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractBaseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

//класс типа DAO, параметризирован наследниками класса AbstractBaseEntity, относящемуся к model, а это Meal или User
//от него наследуется InMemoryBaseRepository<User>
@Repository
public class InMemoryBaseRepository<T extends AbstractBaseEntity> {

//    сейф-треад счетчик:
    private static AtomicInteger counter = new AtomicInteger(0);

//    сейф-треад ConcurrentHashMap, где ключ- это Id, а значение- это
    private Map<Integer, T> map = new ConcurrentHashMap<>();

    public T save(T entry) {
        if (entry.isNew()) {
            entry.setId(counter.incrementAndGet());
            map.put(entry.getId(), entry);
            return entry;
        }
        return map.computeIfPresent(entry.getId(), (id, oldT) -> entry);
    }

    public boolean delete(int id) {
        return map.remove(id) != null;
    }

    public T get(int id) {
        return map.get(id);
    }

    Collection<T> getCollection() {
        return map.values();
    }
}