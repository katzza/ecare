<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <title>Tariffs</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/checkout/">

    <!-- Bootstrap core CSS -->
    <link href="../resources/static/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="form-validation.css" rel="stylesheet">
    <jsp:include page="../header.jsp"/>
    <style>
        .letter {
            color: #d63384;
            font-size: 100%;
        }

        p {
            color: rgb(49, 151, 116);
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Tariffs</h2>
    <br>

    <c:if test="${not empty message}">
        <div id="error" class="letter">${message}</div>
    </c:if>
    <br>
    <sec:authorize access="hasRole('EMPLOYEE')">
        <form action="/tariff/newtariff" method="get">
            <button class="btn btn-warning" type="submit"> New tariff</button>
        </form>
    </sec:authorize>
    <br>
    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Multioptions</th>
        </tr>
        <c:forEach var="tariff" items="${tariffs}">
            <tr>
                <c:if test="${tariff.baseTariff}">
                    <td><b>${tariff.tariffName}</b></td>
                    <td><b>${tariff.tariffDescription}</b></td>
                    <td><b>${tariff.price}</b></td>
               <%--     <c:if test="${tariff.multipleOptionDtos.size()>0}">
                        <td><b>Yes</b></td>
                    </c:if>
                    <c:if test="${tariff.multipleOptionDtos.size()==0}">
                        <td>---</td>
                    </c:if>--%>
                    <td><b>${tariff.multipleOptionDtos.size()==0?"---":"YES"}</b></td>
                </c:if>
                <c:if test="${!tariff.baseTariff}">
                    <td>${tariff.tariffName}</td>
                    <td>${tariff.tariffDescription}</td>
                    <td>${tariff.price}</td>
                    <td>${tariff.multipleOptionDtos.size()==0?"---":"YES"}</td>
                </c:if>
                <sec:authorize access="hasRole('EMPLOYEE')">
                    <td>
                        <form action="/tariff/updatetariff" method="get">
                            <input type="hidden" name="tariffId" value=${tariff.tariffId}>
                            <input type="submit" value="Details/Edit" class="btn btn-outline-primary">
                        </form>
                    </td>
                    <td>
                        <form action="/tariff/basetariff" method="get">
                            <input type="hidden" name="tariffId" value=${tariff.tariffId}>
                            <input type="submit" value="Base tariff" class="btn btn-primary">
                        </form>
                    </td>
                    <td>
                        <form action="/tariff/delete" method="get">
                            <input type="hidden" name="tariffId" value=${tariff.tariffId}>
                            <input type="submit" value="Delete tariff" class="btn btn-warning">
                        </form>
                    </td>
                </sec:authorize>
            </tr>
        </c:forEach>
    </table>
</div>
</body>

</html>
