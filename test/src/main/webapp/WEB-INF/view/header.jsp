<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header class="navbar">
    <div class="nav-center">

        <sec:authorize access="isAuthenticated()" method="GET">
            <div class="login_container logout_container">
                <table>
                    <thead>
                    <th style="width:20%"></th>
                    <th style="width:80%"></th>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <form action="/logout" method="get">
                                    <%--<a href="/logout " style="font-size: 20px"> Logout </a>--%>
                                <input type="submit" value="Logout" class="btn btn-link" name="${_csrf.parameterName}"
                                       value="${_csrf.token}"/></form>
                        </td>
                        <td>

                        </td>
                        <td>
                            <form action="/tariffs" method="get">
                                <input type="submit" value="Tariffs" class="btn btn-link"></form>
                        </td>


                        <sec:authorize access="hasRole('EMPLOYEE')">
                            <td>
                                <form action="/employee/options" method="get">
                                    <input type="submit" value="Options" class="btn btn-link"></form>
                            </td>
                            <td>
                                <form action="/employee/users" method="get">
                                    <input type="submit" value="Users" class="btn btn-link"></form>
                            </td>
                            <td>
                                <form action="/employee/clients" method="get">
                                    <input type="submit" value="Clients" class="btn btn-link"></form>
                            </td>
              <%--              <td>
                                <form action="/employee/contracts" method="get">
                                    <input type="submit" value="Contracts" class="btn btn-link"></form>
                            </td>--%>
                            <td>
                                <form action="/employee/employeehomepage" method="get">
                                    <input type="submit" value="Homepage" class="btn btn-link"></form>
                            </td>
                        </sec:authorize>

                        <sec:authorize access="!hasRole('EMPLOYEE')">
                            <td>
                                <form action="/client/homepage" method="get">
                                    <input type="submit" value="Homepage" class="btn btn-link"></form>
                            </td>
                        </sec:authorize>
                    </tr>
                    </tbody>
                </table>
            </div>
        </sec:authorize>

    </div>
</header>