package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
//с версии 2.3 должен быть среад-сейф тк все запросы идут чз него(до этой версии исп пулл потоков)
public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRepository repository;

    @Override//показал, что есть метод init() и можно размещать логику
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepository();
    }

    @Override//принимаем данные из формы
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        //если id пустой- создаем новую еду иначе берем все проперти еды из запроса и делаем save и редирект на список
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        repository.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {//если action нет, тогда делаем action=all и обрабатываем в case "all": отдаем весь список
            case "delete"://если в табличке всех приемов meals.jsp выбрали delete
                int id = getId(request);//берем Id из запроса с пом служебного метода
                log.info("Delete {}", id);
                repository.delete(id);
                response.sendRedirect("meals");//редирект на список (вызываем сервлету гет-запросом без параметров-
                // те сюда придет с action == null и получится action == case "all") так жа как и из index.html сюда попадаем
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?//если action "create" создаем Meal по умолчанию
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        repository.get(getId(request));//иначе берем из репозитория
                request.setAttribute("meal", meal);//в запрос кладем еду кот. создали или редактируем
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);//и отправляемся на "/mealForm.jsp" в кот форма для редактирования
                break;
            case "all":
            default:
                log.info("getAll");
                //здесь надо отобразить весь список. создаем атрибут "meals" в который кладем список всех приемов пищи List<MealTo>(это кот с полем boolean excess)
                // В MealsUtil.getTos отправляем все значения из Мапы(repository.getAll()) и константу макс калорийности и получаем List<MealTo>
                request.setAttribute("meals",
                        MealsUtil.getTos(repository.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                //форвардимся на "/meals.jsp" передавая туда request, response. там из request возьмем атрибут в цикле: <c:forEach items="${meals}" var="meal">
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        //бросаем NullPointerException если в параметрах в id ничего нет, если есть- присваиваем строке
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        //строку в число переводим
        return Integer.parseInt(paramId);
    }
}
