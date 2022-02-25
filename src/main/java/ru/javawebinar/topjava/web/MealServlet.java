package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

//сервлета, управляющая едой юзеров (принимает запросы, связанные с  едой пользователей)
public class MealServlet extends HttpServlet {

//    в поля сервлеты перенесли некоторый функционал из тестового класса SpringMain.

//   это поле с самим контекстом из которого можно доставать созданные Спрингом Бины
    private ConfigurableApplicationContext springContext;
//    поле с DAO контроллером еды пользователя
    private MealRestController mealController;

    @Override
    public void init(ServletConfig config) throws ServletException {
//        воспользуемся тем, что TomCat сам вызывает этот метод при инициализации этой сервлеты,
//        переопределим его таким образом, чтобы еще и инициализировать поля этого класса.
//        ТО ЗДЕСЬ ПРОИСХОДИТ НАПОЛНЕНИЕ СПРИНГ-КОНТЕКСТА БИНАМИ И DI.
        super.init(config);
//       инициализируем контекст, указывая spring-context-у на  xml с его конфигурацией, в которой сейчас
//       указанны директории проекта, которые надо просканировать на предмет аннотаций
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
//      выбираем- какой контроллер будем исспользовать- и это -  MealRestController(он перенаправляет в MealService логику).
        mealController = springContext.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
//        переопределим destroy() метод spring-context-а, чтобы при закрытии контекста каким-то образом срабатывал логгер
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

//    Здесь IF()-проверка: надо создать и сохранить или взять и обновить
//    StringUtils - служебный класс Спринга с его утильными методами
        if (StringUtils.isEmpty(request.getParameter("id"))) {
            mealController.create(meal);
        } else {
            mealController.update(meal, getId(request));
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//       первоначально сюда попадаем из другой сервлеты-users с action == null.
//       те после того как определились- с каким юзером или администратором работаем (это запоминается в статик поле id класса SecurityUtil)

//        В String action берем параметр action из  request-а (action будет null или конкретный id юзера или админа)
        String action = request.getParameter("action");

//        в блоке switch обрабатываем пришедший action.
        switch (action == null ? "all" : action) {

            //если пришедший параметр action == "delete"
            case "delete":
                int id = getId(request);
                mealController.delete(id);
                response.sendRedirect("meals");
                break;

            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;

//                из формы фильтрации в meal.jsp сюда придет запрос о фильтрации еды по дате и времени приема пищи
            case "filter":
//                из реквеста получаем следующие объекты
                LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
                LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
                LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
                LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
//                по атрибуту "meals" кладем лист MealTo, кот вернет утильный метод getBetween()
                request.setAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
//                форвардимся на "/meals.jsp" чтобы там перерисовалась табличка с приемами пищи с ущетом фильта
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            //        если пришедший параметр action == null, тогда присваиваем action == all, идем в case "all" в котором
            //        под атрибутом "meals" отсылаем List<MealTo> на отрисовку в meals.jsp то, что вернет наш контроллер mealController.getAll()
            case "all":
            default:
                request.setAttribute("meals", mealController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
