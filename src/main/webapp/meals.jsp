<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %><%--
  Created by IntelliJ IDEA.
  User: Мейир
  Date: 22.12.2016
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meal List</title>
    <style>
        .normal{color:green;}
        .exceeded{color:red;}
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a> </h2>
    <h3>Meal List</h3>
    <hr>
    <tabel border = "1" cellpadding = "8" cellspacing = "0">
        <thead>
            <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Colaries</th>
            </tr>
        </thead>
        <c:forEach items = "${meals}" var = "meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr class="${meal.exceed?'exceeded':'normal'}">
                <td>
                <%=TimeUtil.toString(meal.getDateTime())%>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>

            </tr>
        </c:forEach>
</tabel>
</section>

</body>
</html>
