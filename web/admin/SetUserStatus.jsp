<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>
<script type="text/javascript" src="/include/javascripts/customerAuto.js"></script>

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
                <h4 class="modal-title" id="myModalLabel">Confirm ${requestScope.actionType}</h4>
            </div>
            <div class="modal-body">
                Are you sure you wish to change the selected account statuses?
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
            <div>
                <button class="btn btn-sm btn-primary" onclick="window.location='/admin/adminCP'">Admin CP</button>
                <c:choose>
                    <c:when test="${requestScope.actionType == 'Activate'}">
                        <button class="btn btn-sm btn-primary" onclick="window.location='/admin/setUserStatus?action=deactivate'">Dectivate Users</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-sm btn-primary" onclick="window.location='/admin/setUserStatus?action=activate'">Activate Users</button>
                    </c:otherwise>
                </c:choose>
            </div>
            <h1 style="text-align: center">${requestScope.actionType} Users</h1>
            <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
                <div class="alert alert-${requestScope.alertType} fade in">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Alert: </strong><c:out value="${requestScope.alert}" />
                </div>
            </c:if>

            <div class="panel panel-primary" id="adminSelect">
                <div class="panel-heading">
                    <h1 class="panel-title">${requestScope.actionType} an Administrator</h1>
                </div>
                <div class="panel-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>${requestScope.actionType}</th>
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
                                    <c:when test="${requestScope.actionType == 'Activate'}">
                                        <c:choose>
                                            <c:when test="${sessionScope.email == admin.usrEmail}">
                                                <td><span class="glyphicon glyphicon-remove" title="You cannot modify your own account status"></span></td>
                                            </c:when>
                                            <c:when test="${admin.usrActive != '127'}">
                                                <td><input name="adminSelect" value="${admin.usrEmail}" type="checkbox"></td>
                                            </c:when>
                                            <c:otherwise><td><input type="checkbox" title="User already activated" disabled></td></c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${sessionScope.email == admin.usrEmail}">
                                                <td><span class="glyphicon glyphicon-remove" title="You cannot modify your own account status"></span></td>
                                            </c:when>
                                            <c:when test="${admin.usrActive != '-128'}">
                                                <td><input name="adminSelect" value="${admin.usrEmail}" type="checkbox"></td>
                                            </c:when>
                                            <c:otherwise><td><input type="checkbox" title="User already deactivated" disabled></td></c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
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
                    <h1 class="panel-title">${requestScope.actionType} an Employee</h1>
                </div>
                <div class="panel-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>${requestScope.actionType}</th>
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
                                    <c:when test="${requestScope.actionType == 'Activate'}">
                                        <c:choose>
                                            <c:when test="${employee.usrActive != '127'}">
                                                <td><input name="employeeSelect" value="${employee.usrEmail}" type="checkbox"></td>
                                            </c:when>
                                            <c:otherwise><td><input type="checkbox" title="User already activated" disabled></td></c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${employee.usrActive != '-128'}">
                                                <td><input name="employeeSelect" value="${employee.usrEmail}" type="checkbox"></td>
                                            </c:when>
                                            <c:otherwise><td><input type="checkbox" title="User already deactivated" disabled></td></c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
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
                    <h1 class="panel-title">${requestScope.actionType} a Customer</h1>
                </div>
                <div class="panel-body">

                    <form class="navbar-form navbar-left" role="search" method="get" action="/admin/setUserStatus">
                        <div class="form-group">
                            <input type="text" id="searchCust" name="customerID" class="cust-autocomplete form-control" data-url="/admin/setUserStatus?customerSearch">
                        </div>
                        &nbsp;<span class="glyphicon glyphicon-search" style="color:black" title="Type a user name to search for."></span>
                    </form>

                </div>
            </div>

            <div align="center"> <button class="btn btn-lg btn-primary" data-toggle="modal" data-target="#confirmAction">${requestScope.actionType} Selected Users</button></div>

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

        $.post("/admin/setUserStatus",'selectList='+selectAdmins.concat(selectEmployees),success);

        setTimeout(function() {window.location='/admin/setUserStatus?action=${requestScope.actionType}';}, 1000);
    }

    function success(response) {
        //do something maybe
    }

</script>


<jsp:include page="/include/Footer.jsp"/>