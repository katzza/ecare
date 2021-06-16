<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/sign-in/">


    <!-- Bootstrap core CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        @media (min-width: 768px) {
        }
    </style>
    <title>New client</title>
    <script type="text/javascript">
        function validate() {
            if (document.getElementById("name").value === "" || document.getElementById("surname").value === ""
                || document.getElementById("birthdate").value === "" || document.getElementById("passport").value === "") {
                alert("Name, surname, birthdate and passport are required");
                document.getElementById("name").focus();
                return false;
            }
        }
    </script>
    <jsp:include page="../header.jsp"/>
    <title>Edit client</title>
</head>
<body>

<div class="container">
    <br>
    <form:form action="/employee/editclient" modelAttribute="client" method="post">
        <h1></h1>
        <h2>Client</h2>
        <h1></h1>

        <h5 class=" "></h5>
        Name <form:input type="text" required="required" minlength="1" maxlength="100" id="name"
                         name="username" path="name"/>
        <br><br>
        Surname <form:input type="text" required="required" minlength="1" maxlength="100" id="surname"
                            name="surname" path="surname"/>
        <br><br>
        Birthdate <form:input type="date" min="1900-01-01" max="2003-01-01" path="birthdate"/>
        <br><br>
        Address <form:input type="text" maxlength="100" path="address"/>
        <br><br>
        Passport <form:input required="required" pattern="\d{7,10}" path="passport"/>
        <br><br>
        <input type="hidden" name="clientId" value=${client.clientId}>
        <%--  <input type="hidden" name="user" value=${client.user}>--%>
        <%--    <input type="hidden" name="clientContracts" value=${client.clientContracts}>--%>
        <input type="submit" onclick="validate()" value="Save">
    </form:form>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
            crossorigin="anonymous"></script>
</div>
</body>
</html>
