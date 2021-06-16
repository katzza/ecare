<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/sign-in/">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        @media (min-width: 768px) {
        }
    </style>
    <title>Errorpage</title>
</head>
<body>
<div class="container">
    <h3>Something went wrong - go back to the home page:</h3>

    <sec:authorize access="hasRole('EMPLOYEE')">
        <td>
            <form action="/employee/employeehomepage" method="get">
                <input type="submit" value="Employee homepage" class="btn btn-outline-primary"></form>
        </td>
    </sec:authorize>
    <sec:authorize access="hasRole('CLIENT')">
        <td>
            <form action="/client/homepage" method="get">
                <input type="submit" value="Client homepage" class="btn btn-outline-primary"></form>
        </td>
    </sec:authorize>
    <sec:authorize access="!hasRole('EMPLOYEE')&&!hasRole('CLIENT')">
        <a href="../" style="font-size: 20px">startpage</a>
    </sec:authorize>
    <h3>${errorMsg}</h3>
    <h1></h1>

    <img src="../../resources/static/images/errorph.jpg"/>
</div>
</body>
</html>
