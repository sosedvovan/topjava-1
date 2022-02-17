package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    //дневное ограничние калорий
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    //список еды сделали статическим членом класса
    public static final List<Meal> MEALS =
            Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    //метод вызывает getFiltered() посылая туда List<Meal>, кол-во дневных калорий и предикат который всегда true тк мы хотим вывести все записи без фильтрации
    public static List<MealTo> getTos(List<Meal> meals, int caloriesPerDay) {
        return getFiltered(meals, caloriesPerDay, new Predicate<Meal>() {
            @Override
            public boolean test(Meal meal) {
                return true;
            }
        });
    }

    public static List<MealTo> getFilteredTos(List<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return getFiltered(meals, caloriesPerDay, meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime));
    }

    //получаем List<Meal>, кол-во дневных калорий и предикат который всегда true тк мы хотим вывести все записи без фильтрации
    private static List<MealTo> getFiltered(List<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        //получаем сгруппированную по дате мапу в ключах которых неповторяющиеся даты а в значениях сумма калорий по этой дате за завтрак обед и ужин
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );
        //из метода возвращаем List<MealTo> полученный с помощью фильтрации и map
        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    //из Meal делает MealTo
    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}