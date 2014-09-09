<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<div>
    <div class="container-fluid">
        <div class="well well-lg">
            <h1 style="text-align: center">Delete Users</h1>
            <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
                <div class="alert alert-${requestScope.alertType} fade in">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Alert: </strong><c:out value="${requestScope.alert}" />
                </div>
            </c:if>

            <div class="panel panel-primary" id="adminDelete">
                <div class="panel-heading">
                    <h1 class="panel-title">Delete an Administrator</h1>
                </div>
                <div class="panel-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Delete</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email Address</th>
                            <th>Role</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${admins}" var="admin">

                            <tr>
                                <td><input name="adminDelete" value="${admin.usrEmail}" type="checkbox"></td>
                                <td>${admin.usrFirstName}</td>
                                <td>${admin.usrLastName}</td>
                                <td>${admin.usrEmail}</td>
                                <td>${admin.usrRole}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel panel-primary" id="employeeDelete">
                <div class="panel-heading">
                    <h1 class="panel-title">Delete an Employee</h1>
                </div>
                <div class="panel-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Delete</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email Address</th>
                            <th>Role</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${employees}" var="employee">
                            <tr>
                                <td><input name="employeeDelete" value="${employee.usrEmail}" type="checkbox"></td>
                                <td>${employee.usrFirstName}</td>
                                <td>${employee.usrLastName}</td>
                                <td>${employee.usrEmail}</td>
                                <td>${employee.usrRole}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h1 class="panel-title">Search for a Customer</h1>
                </div>
                <div class="panel-body">

                    <form class="navbar-form navbar-left" role="search" method="get" action="">
                        <div class="form-group">
                            <input type="text" id="search" name="userName" class="autocomplete form-control" data-url="">
                        </div>
                        &nbsp;<span class="glyphicon glyphicon-search" style="color:black" title="Type a user name to search for."></span>
                    </form>

                </div>
            </div>

            <div align="center"> <button class="btn btn-primary" onclick="submit();">Delete Selected Users</button></div>

        </div>
    </div>
</div>

<script>
    function submit() {
        var deleteAdmins = [];
        $('#adminDelete input:checked').each(function() {
            deleteAdmins.push($(this).attr('value'));
        });

        var deleteEmployees = [];
        $('#employeeDelete input:checked').each(function() {
            deleteEmployees.push($(this).attr('value'));
        });

        $.post("/admin/deleteUser",'deleteList='+deleteAdmins.concat(deleteEmployees),success);
    }

    function success(response) {
        //do something maybe
    }

</script>


<jsp:include page="/include/Footer.jsp"/>