<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://topjava.javawebinar.ru/functions" prefix="f" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1">
    <tr>
        <th>Date Time</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach var="meal" items="${mealToList}">
        <tr
                <c:choose>
                    <c:when test="${meal.excess}">
                        style='color:red;'
                    </c:when>
                    <c:otherwise>
                        style='color:green;'
                    </c:otherwise>
                </c:choose>
        >
            <td><p>${f:formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy HH:mm')}</p></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
