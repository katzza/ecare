<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <title>Options</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/checkout/">

    <!-- Bootstrap core CSS -->
    <link href="../resources/static/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="form-validation.css" rel="stylesheet">
    <jsp:include page="../header.jsp"/>
    <style>
        .letter {
            color: #d63384;
            font-size: 100%;
        }

        p {
            color: rgb(49, 151, 116);
        }
    </style>
</head>
<body>
<div class="container">
<h2>Users</h2>
<br>
<table class="table table-striped">
    <tr>
        <th>Email</th>
        <th>Client surname</th>
    </tr>
    <h3></h3>
    <c:forEach var="client" items="${clients}">
        <tr>
            <td>${client.user.email}</td>
            <td>${client.surname}</td>
        </tr>
    </c:forEach>
</table>
</div>
</body>

</html>
