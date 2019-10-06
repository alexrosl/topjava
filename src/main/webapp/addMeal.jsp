<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="meals">Back</a></h3>
<hr>
<h2>Meal</h2>
<form method="post" action="meals">
    <input type="hidden" name="mealId" value="0"/>
    <input type="datetime-local" name="dateTime" required/>
    <hr>
    <input type="text" name="description" required/>
    <hr>
    <input type="number" name="calories" required/>
    <hr>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
