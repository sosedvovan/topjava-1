package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
// класс-контроллер-репозиторий-DAO для еды пользователя

// @Controller указывает на то, что в контексте Спринга будет создан Бин этого класса для дальнейшей DI этого бина или в него
// К ЭТОМУ КЛАССУ (И ТОЛЬКО К ЭТОМУ) КОНТРОЛЛЕРУ МЫ БУДЕМ ОБРАЩАТЬСЯ ИЗ НАШИХ СЕРВЛЕТ по поводу еды
@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

//  поле инициализируется MealService-ом Спрингом тк здесь над конструктором стоит @Autowired
//  а над MealService стоит @Service

//  в каждом методе этого класса идет обращение к методам MealService service из которых идет обращение
//  к repository- к одной из реализаций: MealRepository(InMemoryMealRepository)
    private final MealService service;

//    Спринг автоматом достанет из своего контекста Бин MealService(@Service) и инициализирует это поле этого контроллера
    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);
    }

//по запросу из сервлеты MealServlet возвращает список List<MealTo> для отправки на отрисовку meals.jsp
    public List<MealTo> getAll() {
//      получаем userId у утильного класса: SecurityUtil.authUserId()
        int userId = SecurityUtil.authUserId();
//      печатаем в лог-файл
        log.info("getAll for user {}", userId);

        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        checkNew(meal);
        log.info("create {} for user {}", meal, userId);
        return service.create(meal, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        log.info("update {} for user {}", meal, userId);
        service.update(meal, userId);
    }

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */
    public List<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Meal> mealsDateFiltered = service.getBetweenDates(startDate, endDate, userId);
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }
}