<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <title>New Option</title>
</head>
<body>
<br>
<div class="container">

    <form:form modelAttribute="option" method="post">
        <h1></h1>
        <h2>Edit option</h2>
        <h1></h1>
        <c:if test="${not empty message}">
            <div id="error">${message}</div>
        </c:if>
        <h5 class=" "></h5>
        Name <form:input maxlength="40" required="required" path="optionName"/>
        <br><br>
        Description <form:input maxlength="100" required="required" path="description"/>
        <br><br>
        Month price <form:input pattern="[0-9]{0,7}" maxlength="7" required="required" path="monthPrice"/>
        <br><br>
        Connection price <form:input pattern="[0-9]{0,7}" maxlength="7" required="required" path="connectionPrice"/>
        <br><br>
        <th>Current base option</th>
        <td>${option.baseOptionName} </td>
        <br><br>
        <%-- <th>Current additional options</th>
         <table class="table">
             <tr>
                 <th>Name</th>
                 <th>Description</th>
                 <th>Month price</th>
                 <th>Connection price</th>
                     &lt;%&ndash;    <th>Base option</th>&ndash;%&gt;
             </tr>
             <c:forEach var="additionaloption" items="${option.additionalOptionDtos}">
                 <tr>
                     <td>${additionaloption.optionName}</td>
                     <td>${additionaloption.description}</td>
                     <td>${additionaloption.monthPrice}</td>
                     <td>${additionaloption.connectionPrice}</td>
                         &lt;%&ndash;  <td>${option.baseOptionName}</td>&ndash;%&gt;
                     <td>
                         <span class="pull-right">
                             <a href="/option/deleteadditionaloption?optionId=${option.optionId}&addoptionId=${additionaloption.optionId}"
                                class="btn btn-info" role="button">Delete</a></span>
                     </td>
                 </tr>
             </c:forEach>
         </table>
 --%>
        <br>
        <label for="baseoption">Set new base option</label>
        <form:select path="baseOptionId" id="baseoption">
            <c:forEach items="${option.baseOptions}" var="item">
                <form:option label="${item.key}" value="${item.value}"/>
            </c:forEach>
        </form:select>
        <br>
        <br>
        <br>
        <%--   <label for="additionaloption">Set new additional options</label>
             <form:select path="additionalOptionIds" multiple="multiple" id="additionaloption">
                 <c:forEach items="${option.additionalOptions}" var="item">
                     <form:option label="${item.key}" value="${item.value}"/>
                 </c:forEach>
             </form:select>
                  <br><br>--%>
        <div id="checkbox">
            <form:checkbox path="multi" id="ismultioption" name="ismultioption" value="Is multioption"/>
                <%--    <input type="checkbox" name="ismultioption" id="ismultioption" path = "">--%>
            <label for="ismultioption"> Is multioption </label>
        </div>

        <form:hidden path="optionId" value="${option.optionId}"/>
        <input type="submit" value="Save">
    </form:form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>

</body>
</html>
