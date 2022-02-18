<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <%--в ссылке формируем Get запрос на сервлету meals в метод doGet(передаем и там принимаем параметр action=)--%>
    <a href="meals?action=create">Add Meal</a><%--метод для добавления еды чз параметр action кот принимает свои собственные значения--%>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>${meal.id}</td>
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                    <%--в ссылке формируем Get запрос на сервлету meals в метод doGet(передаем и там принимаем параметры action= и id=)--%>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td><%--метод Update чз action в кот передаем id--%>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td><%--Delete чз action--%>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>