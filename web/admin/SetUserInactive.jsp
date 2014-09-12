<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<style>
    .active {
        color: #009a02;
        background-color: rgba(19, 19, 19, 0.91);
        font-size: 18px;
    }
    .inactive {
        color: #cc0012;
        background-color: rgba(19, 19, 19, 0.91);
        font-size: 18px;
    }
</style>


<!-- Modal - This is the confirmation box for setting users to inactive -->
<div class="modal fade" id="confirmAction" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Confirm Inactivity</h4>
            </div>
            <div class="modal-body">
                Are you sure you wish to set the selected account statuses to inactive?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" onclick="submit();">Confirm</button>
            </div>
        </div>
    </div>
</div>

<div>
    <div class="container-fluid">
        <div class="well well-lg">
            <h1 style="text-align: center">Deactivate Users</h1>
            <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
                <div class="alert alert-${requestScope.alertType} fade in">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Alert: </strong><c:out value="${requestScope.alert}" />
                </div>
            </c:if>

            <div class="panel panel-primary" id="adminSelect">
                <div class="panel-heading">
                    <h1 class="panel-title">Deactivate an Administrator</h1>
                </div>
                <div class="panel-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Deactivate</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email Address</th>
                            <th>Role</th>
                            <th>Account Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${admins}" var="admin">
                            <tr>
                                <c:choose>
                                    <c:when test="${admin.usrActive != '-128'}">
                                        <td><input name="employeeSelect" value="${admin.usrEmail}" type="checkbox"></td>
                                    </c:when>
                                    <c:otherwise><td><input type="checkbox" disabled></td></c:otherwise>
                                </c:choose>
                                <td>${admin.usrFirstName}</td>
                                <td>${admin.usrLastName}</td>
                                <td>${admin.usrEmail}</td>
                                <td>${admin.usrRole}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${admin.usrActive == '-128'}"><span class="badge inactive">Inactive</span></c:when>
                                        <c:otherwise><span class="badge active">Active</span></c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel panel-primary" id="employeeSelect">
                <div class="panel-heading">
                    <h1 class="panel-title">Deactivate an Employee</h1>
                </div>
                <div class="panel-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Deactivate</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email Address</th>
                            <th>Role</th>
                            <th>Account Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${employees}" var="employee">
                            <tr>
                                <c:choose>
                                    <c:when test="${employee.usrActive != '-128'}">
                                        <td><input name="employeeSelect" value="${employee.usrEmail}" type="checkbox"></td>
                                    </c:when>
                                    <c:otherwise><td><input type="checkbox" disabled></td></c:otherwise>
                                </c:choose>
                                <td>${employee.usrFirstName}</td>
                                <td>${employee.usrLastName}</td>
                                <td>${employee.usrEmail}</td>
                                <td>${employee.usrRole}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${employee.usrActive == '-128'}"><span class="badge inactive">Inactive</span></c:when>
                                        <c:otherwise><span class="badge active">Active</span></c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h1 class="panel-title">Deactivate a Customer</h1>
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

            <div align="center"> <button class="btn btn-lg btn-primary" data-toggle="modal" data-target="#confirmAction">Deactivate Selected Users</button></div>

        </div>
    </div>
</div>

<script>
    function submit() {
        $('#confirmAction').modal('hide');

        var selectAdmins = [];
        $('#adminSelect input:checked').each(function() {
            selectAdmins.push($(this).attr('value'));
        });

        var selectEmployees = [];
        $('#employeeSelect input:checked').each(function() {
            selectEmployees.push($(this).attr('value'));
        });

        $.post("/admin/setUserInactive",'selectList='+selectAdmins.concat(selectEmployees),success);

        setTimeout(function() {window.location='/admin/setUserInactive';}, 1000);
    }

    function success(response) {
        //do something maybe
    }

</script>


<jsp:include page="/include/Footer.jsp"/>