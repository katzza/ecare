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
    <title>Contract</title>
    <script type="text/javascript">
        function validate() {
            if (document.getElementById("phoneNumber").value === "" || document.getElementById("tariff").value === "") {
                alert("Phone number and tariff are required");
                return false;
            }
        }
    </script>
    <jsp:include page="../header.jsp"/>
    <title>New contract</title>
</head>
<body>
<div class="container">
    <br>

    <form:form modelAttribute="contract" method="post">
    <h1></h1>
    <h4>Client</h4>
    <td><b>${contract.clientEmail}</b></td>
    <br><br>
    <h1></h1>
    <h5 class=" "></h5>

    Choose a phone number:
    <div class="form-group">
        <label for="tr" class="form-label">Numbers <span class="text-muted"></span></label>
        <form:select path="phoneNumber.id" id="tr" class="selectpicker"
                     data-live-search="true" data-size="5" data-actions-box="true" data-width="90%">
            <c:forEach items="${contract.numbers}" var="item">
                <form:option label="${item.key}" value="${item.value}"/>
            </c:forEach>
        </form:select>
    </div>
    <br><br><br>
    Choose a tariff:
    <div class="form-group">
        <label for="tr" class="form-label">Tariffs <span class="text-muted"></span></label>
        <form:select path="tariffId.tariffId" id="tr" class="selectpicker"
                     data-live-search="true" data-size="5" data-actions-box="true" data-width="90%">
            <c:forEach items="${contract.tariffs}" var="item">
                <form:option label="${item.key}" value="${item.value}"/>
            </c:forEach>
        </form:select>
    </div>

    <br><br>
    <input type="hidden" name="contractId" value=${contract.contractId}>
    <input type="hidden" name="clientId" value=${contract.clientId}>
    <input type="submit" onclick="validate()" value="Save">
</div>


</form:form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>
</body>
</html>
