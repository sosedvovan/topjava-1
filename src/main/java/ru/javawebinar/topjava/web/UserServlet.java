package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

//сервлета, управляющая пользователями (принимает запросы, связзанные с пользователями и может перенаправлять на еду этих пользователей)
public class UserServlet extends HttpServlet {
//    получаем логгер:
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        из index.html в эту  сервлету "users" в метод doPost приходит пара ключ"userId"-значение User-"1" или Admin-"2"
//        переменной int userId присваиваем значение пришедшее в request-Е
        int userId = Integer.parseInt(request.getParameter("userId"));
//      переменную userId передаем в утильный метод-сеттер setAuthUserId(), класса SecurityUtil,
//      где ею инициализируем единственное статическое поле id класса SecurityUtil... гет-метод для поля id в SecurityUtil называется authUserId().
//      логически- мы  Id юзера(userId) конвертировали в Id еды (и теперь статическое поле id класса SecurityUtil хранит в себе Id еды?)
        SecurityUtil.setAuthUserId(userId);
//        редирект в сервлету еды - "meals" (в которой попадаем в метод doGet с action == null) и инициализированным статик-полем Id в классе SecurityUtil
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
//        форвардимся на jsp-вьюху со списком всех юзеров
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
