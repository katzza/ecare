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
    <%--    <script type="text/javascript">
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
        </script>--%>
    <title>Contract options</title>
</head>
<body>
<form:form modelAttribute="contract" method="post">
<div class="container">
    <h1></h1>
    <h4>Choose options to contract</h4>
    <h1></h1>

    <c:if test="${not empty message}">
        <div id="error">${message}</div>
    </c:if>

    <h5 class=" "></h5>
    Your tafiff <form:input readonly="true" path="tariffId.tariffName"/>
    <br> <br>
    Description <form:input readonly="true" path="tariffId.tariffDescription"/>
    <br> <br>
    Price <form:input readonly="true" path="tariffId.price"/>
    <br> <br>
    <th>Current calls-option:</th>
    <td>${contract.tariffId.callsOption!=null?contract.tariffId.callsOption.optionName:" ---"} </td>
    <br>
    <th>Current internet-option:</th>
    <td>${contract.tariffId.internetOption!=null?contract.tariffId.internetOption.optionName:" ---"} </td>
    <br>
    <th>Current travel-option:</th>
    <td>${contract.tariffId.travelOption!=null?contract.tariffId.travelOption.optionName:" ---"} </td>
    <br>
    <br>
    <c:if test="${contract.contractAddedOptions.size()!=0}">
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
                <c:forEach var="multioption" items="${contract.tariffId.multipleOptionDtos}">
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
    <br> <br>
    <label for="contractoptions">Set more options to tariff</label>
    <form:select path="selectedContractOptionIds" multiple="multiple" id="contractoptions">
        <c:forEach items="${contract.contractOptions}" var="item">
            <form:option label="${item.key}" value="${item.value}"/>
        </c:forEach>
    </form:select>
    <c:if test="${not empty message}">
        <div id="error">${message}</div>
    </c:if>

    <br><br>
    <br><br>
    <form:hidden path="contractId" value="${contract.contractId}"/>
    <input type="submit" value="Next">
    </form:form>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
            crossorigin="anonymous"></script>
</div>
</body>
</html>