<%@ page contentType="text/html;charset=UTF-8" language="java" %><%--настройка jsp--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--подключаем jstl для forEach чтобы не делать java вставки--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%--jstl форматинг даты добавили--%>    <%--тк томкат не поддерживает jstl, то добавляем его в POM--%>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %><%--свой форматинг даты добавили--%>
<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
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
    <h3><a href="index.html">Home</a></h3><%--в хедаре ссылка на index.html--%>
    <hr/><%--подчеркиваем--%>
    <h2>Meals</h2><%--напишем название таблички--%>
    <table border="1" cellpadding="8" cellspacing="0"><%--ТАБЛИЦА. Без cellpadding текст в таблице «налипает» на рамку. cellspacing- расстояние му ячейками таблицы--%>
        <thead><%--заголовочная строка таблицы--%>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        </thead>
        <%--items="${meals}" сам берет из атрибута "meals" сервлеты список List<MealTo>--%>
        <%--говорим, что переменная "meal", кот исп для итерации, является бином MealTo--%>
        <c:forEach items="${meals}" var="meal"><%--далее таблицу заполняем построчно. <td>2015-05-30 10:00</td> - 1-ая ячейка, <td>Завтрак</td> - 2-ая ячейка, <td>500</td> - 3-ая ячейка,--%>
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">  <%--meal.excess это обращение к методу boolean isExcess() чтобы выбрать стиль строки в таблице(зеленый или красный)--%>
                <td>
                        ${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}<%--обращ. к геттеру getDateTime() и полученную dateTime перегоняем в дату и время--%>
                                <%--<%=DateTimeUtil.toString(meal.getDateTime())%>--%><%--дату перегоняем в строку: 2015-05-30 10:00--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                                <%--${fn:formatDateTime(meal.dateTime)}<%--форматируем в вид: 2015-05-30 10:00 с пом functions.tdl Там вызов метода toString() из DateTimeUtil--%>
                </td>
                <td>${meal.description}</td><%--в ява вставке прыгаем на геттер в MealTo: Завтрак Обед Ужин--%>
                <td>${meal.calories}</td><%--в ява вставке прыгаем на геттер в MealTo: 1000 500 700--%>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>