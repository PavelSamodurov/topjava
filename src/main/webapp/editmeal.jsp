<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Meal</title>
</head>
<body>
<form method="POST" action='meals' name="frmAddMeal">
    <input
    type="hidden" name="mealId" value="${meal.id}"/>
    Date Time : <input
        type="datetime-local" name="dateTime"
        value = "${meal.dateTime}"/> <br />
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br />
    <input
        type="submit" value="Submit" />
</form>
</body>
</html>