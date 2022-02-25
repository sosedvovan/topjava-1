<%--В этой вьюхе отрисовываем форму ФИЛЬТРА и табличку со всей едой юзера--%>

<%--настройка jsp--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--настройка jstl доп тегов--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--настройка нашего tld файла с пом кот форматируем дату для показа--%>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<head>
    <title>Meals</title>
<%--    подключаем style.css для таблички со всей едой юзера--%>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
<section>
<%--    в хедере страницы ссылка на index.html, где мы выбираем Юзера или Админа--%>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
<%--    название формы для фильтрации еды--%>
    <h2>Meals</h2>
<%--    создаем форму, кот посылает ГЕТ запрос в "meals" сервлету--%>
    <form method="get" action="meals">
<%--    в ГЕТ запросе также передаем скрытое поле с параметром action=filter--%>
        <input type="hidden" name="action" value="filter">
<%--    ПЕРВОЕ ПОЛЕ ДЛЯ ВВОДА СТАРТОВОЙ ДАТЫ ДЛЯ ФИЛЬТРАЦИИ MealTo--%>
        <dl>
            <dt>From Date:</dt>
<%--            под ключем "startDate" кладем в ГЕТ запрос то, что ввели в форме--%>
<%--            пока не пойму где объявленна переменная param - это какая то мапа--%>
<%--            если удалить value="${param.startDate}", то поведение программы не меняется--%>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
    <%--    ВТОРОЕ ПОЛЕ ДЛЯ ВВОДА КОНЕЧНОЙ ДАТЫ ДЛЯ ФИЛЬТРАЦИИ MealTo--%>
        <dl>
            <dt>To Date:</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
    <%--    ТРЕТЬЕ ПОЛЕ ДЛЯ ВВОДА СТАРТОВОГО ВРЕМЕНИ ДЛЯ ФИЛЬТРАЦИИ MealTo--%>
        <dl>
            <dt>From Time:</dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
    <%--    ЧЕТВЕРТОЕ ПОЛЕ ДЛЯ ВВОДА КОНЕЧНОЙ ДАТЫ ДЛЯ ФИЛЬТРАЦИИ MealTo--%>
        <dl>
            <dt>To Time:</dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
<%--    КНОПКА ДЛЯ ОТПРАВКИ ГЕТ ЗАПРОСА В СЕРВЛЕТУ "meals"  с action=filter--%>
        <button type="submit">Filter</button>
    </form>
    <hr/>
<%--    ссылка с гет запросом в сервлету meals для дальнейшего отображения вьюхи с созданием еды--%>
    <a href="meals?action=create">Add Meal</a>
    <br><br>

<%--    ОТОБРАЖАЕМ НИЖЕ ТАБЛИЧКУ СО ВСЕЙ ЕДОЙ ЮЗЕРА ИЛИ С ОТФИЛЬТРОВАННОЙ ЕДОЙ, ЕСЛИ ПРИМЕНИЛИ ФИЛЬТР--%>
    <table border="1" cellpadding="8" cellspacing="0">
<%--        создаем заголовки полей таблицы--%>
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
<%--    настраиваем jstl цикл по List<MealTo> пришедшей с атрибутом в запросе к этой вьюхе--%>
<%--    items="${meals}"::: по тому, что лежит в пришедшем атрибуте meals будем итерироваться с пом переменной::: var="meal"--%>
        <c:forEach items="${meals}" var="meal">
<%--    также укажем, что перемененая meal, это бин класса MealTo--%>
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
<%--            тегу <tr> дадим в атрибут id-из style.css, которому присвоим то что вернет геттер isExcess для выбора красного или зеленного цвета--%>
            <tr data-mealExcess="${meal.excess}">
<%--                в первой ячейке переформатированная дата-время--%>
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
<%--    во второй ячейке- название приема пищи- завтрак, обед, ужин--%>
                <td>${meal.description}</td>
<%--    в третьей ячейке- калорийность приема пищи--%>
                <td>${meal.calories}</td>
<%--    в четвертой ячейке гет запрос к сервлете meals с предложением редактировать прием пищи с заданным id--%>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
        <%--в пятой  ячейке гет запрос к сервлете meals с предложением удалить прием пищи с заданным id--%>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>