<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <title>Options</title>
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
    <h2>Options</h2>
    <br>
    <c:if test="${not empty message}">
        <div id="error" class="letter">${message}</div>
    </c:if>
    <form action="/option/newoption" method="get">
        <button class="btn btn-warning" type="submit"> New option</button>
    </form>
    <br>
    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Month price</th>
            <th>Connection price</th>
            <th>Base option</th>
        </tr>
        <c:forEach var="option" items="${options}">
            <tr>
                <td>${option.optionName}</td>
                <td>${option.description}</td>
                <td>${option.monthPrice}</td>
                <td>${option.connectionPrice}</td>
                <td>${option.baseOptionName}</td>

                <td>
                    <form action="/option/updateoption" method="get">
                        <input type="hidden" name="optionId" value=${option.optionId}>
                        <input type="submit" value="Details/Edit" class="btn btn-primary">
                    </form>
                </td>
                    <%-- <td>
                         <form action="/tariff/basetariff" method="get">
                             <input type="hidden" name="tariffId" value=${tariff.tariffId}>
                             <input type="submit" value="Base tariff" class="btn btn-info">
                         </form>
                     </td>--%>
                    <%--         <td>
                                 <form action="/option/delete" method="get">
                                     <input type="hidden" name="tariffId" value=${tariff.tariffId}>
                                     <input type="submit" value="Delete tariff" class="btn btn-warning">
                                 </form>
                             </td>--%>

            </tr>
        </c:forEach>
    </table>
</div>
</body>

</html>
