<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <title>Clients</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/checkout/">

    <!-- Bootstrap core CSS -->
    <link href="../resources/static/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="form-validation.css" rel="stylesheet">
    <jsp:include page="../header.jsp"/>
</head>
<body>
<div class="container">
    <h2>Clients</h2>
    <br>
    <table class="table">
        <tr>
            <th>Name</th>
            <th>Surname</th>
            <th>BirthDate</th>
            <th>Passport</th>
            <th>Address</th>
        </tr>
        <c:forEach var="client" items="${clients}">
            <tr>
            <c:if test="${client.name.length()>1}">
                <td>${client.name}</td>
                <td>${client.surname}</td>
                <td>${client.birthdate}</td>
                <td>${client.passport}</td>
                <td>${client.address}</td>
                <td>
                    <form action="/employee/findclientbyemail" method="get">
                        <input type="hidden" name="email" value=${client.user.email}>
                        <input type="submit" value="Details/Edit" class="btn btn-primary">
                    </form>
                </td>
                </tr>
            </c:if>
        </c:forEach>
    </table>
</div>
</body>

</html>
