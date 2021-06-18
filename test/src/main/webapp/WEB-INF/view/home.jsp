<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.82.0">
    <title>Welcome</title>

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
    <img class="d-block mx-auto mb-4" src="../../resources/static/images/logo.jpg" alt="">
    <h1 class="display-5 fw-bold">Happy telecom</h1>
    <br> <br>
    <div class="col-lg-6 mx-auto">
        <p class="lead mb-4">LIFE IS FOR SHARING.</p>
        <br> <br>
        <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
            <form action="/login" method="get">
                <input type="submit" value=Login class="btn btn-outline-primary btn-lg px-4 me-sm-3">
            </form>
            <form action="/registration" method="get">
                <input type="submit" value=Registration class="btn btn-outline-primary btn-lg px-4 me-sm-3">
            </form>
        </div>
    </div>
</div>


<div class="b-example-divider mb-0"></div>


<script src="../../resources/js/bootstrap.bundle.min.js"></script>


</body>
</html>

