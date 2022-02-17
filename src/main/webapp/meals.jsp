<%@ page contentType="text/html;charset=UTF-8" language="java" %><%--настройка jsp--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--подключаем jstl для forEach чтобы не делать java вставки--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%--jstl форматинг добавили--%>    <%--тк томкат не поддерживает jstl, то добавляем его в POM--%>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style><%--стили для отрисовки нормальной и превышенной калорийности--%>
        .normal {color: green;}
        .excess {color: red;}
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        </thead>
        <%--говорим, что переменная "meal", кот исп для итерации, является бином MealTo--%>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">  <%--meal.excess это обращение к методу boolean isExcess()--%>
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%><%--dateTime перегоняем в дату--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%><%--дату перегоняем в строку: 2015-05-30 10:00--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}<%--форматируем в вид: 2015-05-30 10:00 с пом functions.tdl--%>
                </td>
                <td>${meal.description}</td><%--в ява вставке прыгаем на геттер в MealTo: Завтрак Обед Ужин--%>
                <td>${meal.calories}</td><%--в ява вставке прыгаем на геттер в MealTo: 1000 500 700--%>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>