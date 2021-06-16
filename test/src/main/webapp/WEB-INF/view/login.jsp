<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">

    <script type="text/javascript">
        function validate() {
            if (document.getElementById("username").value === ""
                && document.getElementById("password").value === "") {
                alert("Username and password are required");
                document.getElementById("username").focus();
                return false;
            }
            if (document.getElementById("username").value === "") {
                alert("Username is required");
                document.getElementById("username").focus();
                return false;
            }
            if (document.getElementById("password").value === "") {
                alert("Password is required");
                document.getElementById("password").focus();
                return false;
            }
        }
    </script>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/sign-in/">


    <!-- Bootstrap core CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>

        @media (min-width: 768px) {
        }
    </style>


    <!-- Custom styles for this template
    <link href="<c:url value="../../resources/static/styles/mystyle.css"/>" rel="stylesheet">-->
    <link rel="stylesheet" href="<c:url value="../../resources/static/styles/mystyle.css" />"/>
    <!-- Custom styles for this template -->
    <link href="../../resources/static/images/logo.jpg" rel="stylesheet">
</head>
<body>
<div class="px-4 py-5 my-5 text-center">
    <img class="d-block mx-auto mb-4" src="../../resources/static/images/logo.jpg" alt="" width="150" height="150">
    <h3 class="display-5 fw-bold">Happy telecom</h3>
    <br>
    <div class="col-lg-6 mx-auto">


        <main class="form-login" style="width:100%; max-width: 330px; padding: 15px; margin: auto">
            <form role="form" action="<c:url value='/login' />" method='POST'>
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
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input class="btn btn-outline-primary" name="submit" type="submit"
                           onclick="validate()" value="Sign in"/>
                </fieldset>
            </form>
        </main>

    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
            crossorigin="anonymous"></script>
</body>

</html>

