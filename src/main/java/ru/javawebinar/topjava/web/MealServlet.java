package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //подключаем логирование
        log.info("getAll");
        //кладем в атрибуты под ключем "meals" список еды (метод getTos() запустит цепочку методов кот вернут List<MealTo>)
        //в модели MVC контроллером является сервлет, вьюхой -отрисовка jsp, моделью-то, что кладем в атрибуты
        request.setAttribute("meals", MealsUtil.getTos(MealsUtil.MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY));
        //форвардимся на "/meals.jsp". в нем из атрибутов достанем List<MealTo>
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
