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

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        @media (min-width: 768px) {
        }
    </style>
    <jsp:include page="../header.jsp"/>
    <script type="text/javascript">
        function validate() {
            if (document.getElementById("callsoption").value === "0"
                && document.getElementById("internetoption").value === "0") {
                if (document.getElementById("traveloption").value === "0") {
                    alert("Please check out the option! At least one of the options must be selected for the tariff.");
                    document.getElementById("callsoption").focus();
                    return false;
                }
            }
            return true;
        }
    </script>
    <title>New Tariff</title>
</head>
<body>
<br>

<form:form onsubmit="return validate();" modelAttribute="tariff" method="post">
<h1></h1>
<div class="container">
    <h2>Tariff</h2>
    <h1></h1>

    <c:if test="${not empty message}">
        <div id="error">${message}</div>
    </c:if>

        <%--    <h5 class=" "></h5>
            Name <form:input maxlength="40" required="required" path="tariffName"/>
            <br><br>
            Description <form:input maxlength="100" required="required" path="tariffDescription"/>
            <br><br>
            Price <form:input pattern="[0-9]{0,7}" maxlength="7" required="required" path="price"/>
            <br>--%>
    <div class="form-group">
        Name
        <form:input type="text" class="form-control" maxlength="40" required="required" path="tariffName"/>
    </div>
    <br>
    <div class="form-group">
        Description
        <form:input type="text" class="form-control" maxlength="100" required="required" path="tariffDescription"/>
    </div>
    <br>
    <div class="form-group">
        Price <form:input type="text" class="form-control" pattern="[0-9]{0,7}" maxlength="7" required="required"
                          path="price"/>
        <br>
    </div>

    <br>
    <label for="callsoption">Set calls-option to tariff</label>
    <form:select path="callsOptionId" id="callsoption">
        <c:forEach items="${tariff.callsOptions}" var="item">
            <form:option label="${item.key}" value="${item.value}"/>
        </c:forEach>
    </form:select>
    <br>
    <br>
    <label for="internetoption">Set internet-option to tariff</label>
    <form:select path="internetOptionId" id="internetoption">
        <c:forEach items="${tariff.internetOptions}" var="item">
            <form:option label="${item.key}" value="${item.value}"/>
        </c:forEach>
    </form:select>
    <br>
    <br>
    <label for="traveloption">Set travel-option to tariff</label>
    <form:select path="travelOptionId" id="traveloption">
        <c:forEach items="${tariff.travelOptions}" var="item">
            <form:option label="${item.key}" value="${item.value}"/>
        </c:forEach>
    </form:select>
    <br>
    <br>
    <br>
    <label for="freeoptions">Set free options to tariff</label>
    <form:select path="freeOptionIds" multiple="multiple" id="freeoptions">
        <c:forEach items="${tariff.freeOptions}" var="item">
            <form:option label="${item.key}" value="${item.value}"/>
        </c:forEach>
    </form:select>
    <c:if test="${not empty message}">
        <div id="error">${message}</div>
    </c:if>

    <br><br>
    <form:hidden path="tariffId" value="${tariff.tariffId}"/>
    <input type="submit" value="Save">
    </form:form>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
            crossorigin="anonymous"></script>
</div>
</body>
</html>
