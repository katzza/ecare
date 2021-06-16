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
    <title>Update Tariff</title>
</head>
<body>
<form:form onsubmit="return validate();" modelAttribute="tariff" method="post">
<div class="container">
    <h1></h1>
    <h4>Update tariff</h4>
    <h1></h1>

    <c:if test="${not empty message}">
        <div id="error">${message}</div>
    </c:if>

    <h5 class=" "></h5>
    Name <form:input maxlength="40" required="required" path="tariffName"/>
    <br> <br>
    Description <form:input maxlength="100" required="required" path="tariffDescription"/>
    <br> <br>
    Price <form:input pattern="[0-9]{0,7}" maxlength="7" required="required" path="price"/>
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
                        <td>
                         <span class="pull-right">
                             <a href="/tariff/deletefreeoption?tariffId=${tariff.tariffId}&optionId=${multioption.optionId}"
                                class="btn btn-outline-primary" role="button">Delete</a></span>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
    <label for="callsoption">Set a new calls-option to tariff</label>
    <form:select path="callsOptionId" id="callsoption">
        <c:forEach items="${tariff.callsOptions}" var="item">
            <form:option label="${item.key}" value="${item.value}"/>
        </c:forEach>
    </form:select>
    <br> <br>
    <label for="internetoption">Set a new internet-option to tariff</label>
    <form:select path="internetOptionId" id="internetoption">
        <c:forEach items="${tariff.internetOptions}" var="item">
            <form:option label="${item.key}" value="${item.value}"/>
        </c:forEach>
    </form:select>
    <br> <br>
    <label for="traveloption">Set a new travel-option to tariff</label>
    <form:select path="travelOptionId" id="traveloption">
        <c:forEach items="${tariff.travelOptions}" var="item">
            <form:option label="${item.key}" value="${item.value}"/>
        </c:forEach>
    </form:select>
    <br> <br>
    <label for="freeoptions">Set new free options to tariff</label>
    <form:select path="freeOptionIds" multiple="multiple" id="freeoptions">
        <c:forEach items="${tariff.freeOptions}" var="item">
            <form:option label="${item.key}" value="${item.value}"/>
        </c:forEach>
    </form:select>
    <c:if test="${not empty message}">
        <div id="error">${message}</div>
    </c:if>

    <br><br>
    <br><br>
    <form:hidden path="tariffId" value="${tariff.tariffId}"/>
    <input type="submit" value="Next">
    </form:form>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
            crossorigin="anonymous"></script>
</div>
</body>
</html>

