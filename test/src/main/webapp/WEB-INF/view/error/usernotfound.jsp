<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Errorpage - user not found</title>
</head>
<body>
<h1> User not found, please check the entered data</h1>
<h1></h1>
<sec:authorize access="hasRole('EMPLOYEE')">
    <td>
        <form action="/employee/employeehomepage" method="get">
            <input type="submit" value="Employee homepage" class="btn btn-link"></form>
    </td>
</sec:authorize>
<sec:authorize access="!hasRole('CLIENT')">
    <td>
        <a href="../">startpage</a>
    </td>
</sec:authorize>

</body>
</html>
