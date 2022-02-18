package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public class InMemoryMealRepository implements MealRepository {
    //в Meal и MealTo добавили поле id, чтобы пользоваться Map- ConcurrentHashMap(треад-сейф Мап):
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    //уникальный счетчик(атомик чтобы треад-сейф) используем как ключ для мапы:
    private AtomicInteger counter = new AtomicInteger(0);

    {//инициализируем лист MEALS, те перегоняем в Мапу Meal-сы кот создали в MealsUtil
        //тк они без поля id созданны, то в методе save добавляем к ним AtomicInteger counter и кладем в мапу
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        //isNew() проверяет id на null(в Meal 2-а конструктора- с id и без него)
        if (meal.isNew()) {//если в Meal нет id тогда
            meal.setId(counter.incrementAndGet());//получаем id
            repository.put(meal.getId(), meal);//и кладем в Мапу
            return meal;
        }
        // treat case: update, but not present in storage
        //если в Meal есть id кладем в мапу с уже имеющимся id
        /*
        return repository.computeIfPresent(meal.getId(), new BiFunction<Integer, Meal, Meal>() {
            @Override
            public Meal apply(Integer id, Meal oldMeal) {
                return meal;
                //так у меня):
               // return new Meal(counter.incrementAndGet(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
            }
        });
         */
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }//берем все значения из Мапы
}

