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
    <title>Tariffinfo</title>
</head>
<body>
<form:form modelAttribute="tariff" >
<div class="container">
    <h1></h1>
    <h4>Tariffinfo</h4>
    <h1></h1>

    <h5 class=" "></h5>
    Name <form:input maxlength="40" readonly="true" path="tariffName"/>
    <br> <br>
    Description <form:input  readonly="true" path="tariffDescription"/>
    <br> <br>
    Price <form:input   readonly="true" path="price"/>
    <br> <br>
    <th>Current calls-option:</th>
    <td>${tariff.callsOption!=null?tariff.callsOption.optionName:" ---"} </td>
    <br>
    <th>Current internet-option:</th>
    <td>${tariff.internetOption!=null?tariff.internetOption.optionName:" ---"} </td>
    <br>
    <th>Current travel-option:</th>
    <td>${tariff.travelOption!=null?tariff.travelOption.optionName:" ---"} </td>
    <br>
    <br>
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
    </form:form>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
            crossorigin="anonymous"></script>
</div>
</body>
</html>

