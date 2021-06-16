<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.82.0">
    <title>Heroes · Bootstrap v5.0</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/heroes/">

    <!-- Bootstrap core CSS -->
    <link href="../../resources/static/css/bootstrap.min.css" rel="stylesheet">

    <style>

        @media (min-width: 768px) {
        }
    </style>


    <!-- Custom styles for this template -->
    <link href="../../resources/static/images/logo.jpg" rel="stylesheet">
</head>
<body>
<div class="px-4 py-5 my-5 text-center">
    <img class="d-block mx-auto mb-4" src="../../resources/static/images/logo.jpg" alt="" width="150" height="150">
    <h3 class="display-5 fw-bold">Happy telecom</h3>
    <br>
    <div class="col-lg-6 mx-auto">
        <form:form action="saveuser" modelAttribute="userDto">
        <h1></h1>
        <div class="container">
            <h2></h2>

            <fieldset>
                <div class="form-group">
                    <input type="email" maxlength="254" required="required" id="login" class="form-control"
                           placeholder="your@mail.com"
                           name="username"
                           autofocus>
                </div>
                <br>
                <div class="form-group">
                    <input required="required" minlength="3" maxlength="10" id="password" class="form-control"
                           placeholder="password" name="password"
                           type="password"
                           value="">
                </div>
                <h1></h1>
                <h1></h1>
                <input class="btn btn-outline-primary" name="submit" type="submit"
                     value="Save new user"/>
            </fieldset>


        </div>
    </div>


    <div class="b-example-divider mb-0"></div>
    <script src="../../resources/js/bootstrap.bundle.min.js"></script>
    </form:form>
</body>
</html>