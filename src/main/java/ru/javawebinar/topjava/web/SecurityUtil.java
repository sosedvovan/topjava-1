package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

//какой-то утильный класс. все поля и методы в нем статические.
//При выборе пользователя на странице index.html, попадаем в UserServlet, откуда инициализируем
// поле этого класса (private static int id = 1 или 2 или 3...;)  -это id выбранного юзера или админа
//
public class SecurityUtil {

//    поле инициализируется из UserServlet-а, и содержит поле с аутинфицированным юзером
    private static int id = 1;

//    Типа ГЕТТЕРА,только называется authUserId()
    public static int authUserId() {
        return id;
    }

//    SETTER
    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }

//    GETTER для константы DEFAULT_CALORIES_PER_DAY
    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}