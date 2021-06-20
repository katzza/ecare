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
    <title>Multioptions to tariff</title>
</head>
<body>
<br>

<form:form modelAttribute="tariff" method="post">
<h1></h1>
<div class="container">
    <h4>Multioptions to tariff ${tariff.tariffName}</h4>
    <h1></h1>
    <c:if test="${not empty message}">
        <div id="error">${message}</div>
    </c:if>

    <h5 class=" "></h5>
    Your tariff has basic options selected:
    <br>
    <table>
        <tr>
            <td> Calls:</td>
            <td>${tariff.callsOption.optionName!=null?tariff.callsOption.optionName:" ---"} </td>
        </tr>
        <tr>
            <td> Internet:</td>
            <td>${tariff.internetOption.optionName!=null?tariff.internetOption.optionName:" ---"} </td>
        </tr>
        <tr>
            <td> Travel:</td>
            <td>${tariff.travelOption.optionName!=null?tariff.travelOption.optionName:" ---"} </td>
        </tr>
        <tr>
            <c:if test="${tariff.multipleOptionDtos.size()!=0}">
                <div>
                    <th>Current multiple options in tariff:</th>
                    <table class="table">
                        <tr>
                            <th> Name</th>
                            <th>Description</th>
                            <th>Month price</th>
                            <th>Connection price</th>
                            <th>Base option</th>
                        </tr>
                        <c:forEach var="multioption" items="${tariff.multipleOptionDtos}">
                            <tr>
                                <td>${multioption.optionName}</td>
                                <td>${multioption.description}</td>
                                <td>${multioption.monthPrice}</td>
                                <td>${multioption.connectionPrice}</td>
                                <td>${multioption.baseOptionName}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
        </tr>
    </table>
    <br>
          If you want to choose more additional options, add the rest of the basic options to the tariff.
    <br><br>
    <label for="multipleoptions">Set base-depending options to tariff</label>
    <form:select path="multipleOptionIds" multiple="multiple" id="multipleoptions">
        <c:forEach items="${tariff.multipleOptions}" var="item">
            <form:option label="${item.key}" value="${item.value}"/>
        </c:forEach>
    </form:select>
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
