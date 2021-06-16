<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <title>Clientpage</title>
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
    <br>
    <h3><%= "Welcome to client homepage"%>
    </h3>
    <br>
    <h4><%= "Client :"   %>
        <td>${client.user.email} </td>
    </h4>
    <br>

    <sec:authorize access="hasRole('EMPLOYEE')">
        <table class="table">
            <thead>
            <th style="width:20%"></th>
            <th style="width:80%"></th>
            </thead>
            <tbody>
            <tr>
                <td>Email</td>
                <td>${client.user.email} </td>

            </tr>
            <tr>
                <td>Name</td>
                <td>${client.name} </td>
            </tr>
            <tr>
                <td>Surname</td>
                <td>${client.surname} </td>
            </tr>
            <tr>
                <td>Birthday</td>
                <td>${client.birthdate} </td>
            </tr>
            <tr>
                <td>Address</td>
                <td>${client.address} </td>
            </tr>
            <tr>
                <td>Passport</td>
                <td>${client.passport} </td>
            </tr>
            </tbody>
        </table>
        <form action="/employee/editclient" method="get">
            <input type="hidden" name="clientId" value=${client.clientId}>
            <input type="submit" value="Edit client data" class="btn btn-primary">
        </form>
        <br>
        <form action="/contract/newcontract" method="get">
            <input type="hidden" name="clientId" value=${client.clientId}>
            <input type="submit" value="New contract" class="btn btn-primary"></form>

    </sec:authorize>

    <br>
    <h4>Client contracts</h4>
    <c:if test="${not empty message}">
        <div id="error" class="letter">${message}</div>
    </c:if>
    <td></td>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Phone number</th>
            <th>Tariff</th>
            <th>Blocked by user</th>
            <th>Blocked by Happy Telecom</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${client.clientContracts}" var="contract">
            <tr>
                <td>${contract.phoneNumber.phoneNumber} </td>
                <td>${contract.tariffId.tariffName}</td>
                <td>${contract.blockedByUser}</td>
                <td>${contract.blockedByCompany}</td>

                <td>
                    <c:if test="${!contract.blockedByUser || !contract.blockedByCompany}">
                        <form action="/contract/editcontract" method="get">
                            <input type="hidden" name="contractId" value=${contract.contractId}>
                            <input type="submit" value="Edit contract" class="btn btn-info"></form>
                    </c:if>
                </td>
                    <%--    BLOCK/UNBLOCK BY CLIENT--%>
                <sec:authorize access="!hasRole('EMPLOYEE')">
                    <td>
                        <c:if test="${contract.blockedByUser && !contract.blockedByCompany}">
                            <form action="/client/unblock" method="get">
                                <input type="hidden" name="contractId" value=${contract.contractId}>
                                <input type="submit" value="Unblock number" class="btn btn-info">
                            </form>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${!contract.blockedByUser && !contract.blockedByCompany}">
                            <form action="/client/block" method="get">
                                <input type="hidden" name="contractId" value=${contract.contractId}>
                                <input type="submit" value="Block number" class="btn btn-info">
                            </form>
                        </c:if>
                    </td>
                </sec:authorize>
                    <%--    BLOCK/UNBLOCK BY EMPLOYEE--%>
                <sec:authorize access="!hasRole('CLIENT')">
                    <td>
                        <c:if test="${contract.blockedByCompany}">
                            <form action="/employee/unblock" method="get">
                                <input type="hidden" name="contractId" value=${contract.contractId}>
                                <input type="submit" value="Unblock number" class="btn btn-primary">
                            </form>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${!contract.blockedByCompany}">
                            <form action="/employee/block" method="get">
                                <input type="hidden" name="contractId" value=${contract.contractId}>
                                <input type="submit" value="Block number" class="btn btn-primary">
                            </form>

                        </c:if>
                    </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <br/>
</div>
</body>
</html>
