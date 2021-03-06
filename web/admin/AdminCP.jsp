<%@ page import="com.statboost.controllers.admin.InventoryEditorServlet" %>
<%@ page import="com.statboost.controllers.admin.InventorySqllistServlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<div>
    <div class="container-fluid">
        <div class="well well-lg">
            <h1 style="text-align: center">Admin Control Panel</h1>
            <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
                <div class="alert alert-${requestScope.alertType} fade in">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Alert: </strong><c:out value="${requestScope.alert}" />
                </div>
            </c:if>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h1 class="panel-title">Manage Users</h1>
                </div>
                <div class="panel-body">
                    <div class="btn-toolbar">
                        <div class="btn-group"><a href="/admin/addNewUser?type=employee"><button class="btn btn-primary">Add New Employee</button></a></div>
                        <div class="btn-group"><a href="/admin/addNewUser?type=admin"><button class="btn btn-primary">Add New Admin</button></a></div>
                        <div class="btn-group"><a href="/admin/addNewUser?type=customer"><button class="btn btn-primary">Add New Customer</button></a></div>
                        <div class="btn-group"><a href="/admin/setUserStatus?action=deactivate"><button class="btn btn-primary">Deactivate User</button></a></div>
                        <div class="btn-group"><a href="/admin/setUserStatus?action=activate"><button class="btn btn-primary">Activate User</button></a></div>
                    </div>
                </div>
            </div>
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h1 class="panel-title">Manage Orders</h1>
                </div>
                <div class="panel-body">
                    <div class="btn-toolbar">
                        <div class="btn-group"><a href="/admin/ordermanager"><button class="btn btn-primary">Order Manager</button></a></div>
                    </div>
                </div>
            </div>
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h1 class="panel-title">Manage Events</h1>
                </div>
                <div class="panel-body">
                    <div class="btn-toolbar">
                        <div class="btn-group"><a href="/Events"><button class="btn btn-primary">View Event Calendar</button></a></div>
                        <div class="btn-group"><a href="<%=InventoryEditorServlet.SRV_MAP + "?" + InventoryEditorServlet.PARAM_TYPE + "=EVENT"%>" class="btn btn-primary">Add New Event</a></div>
                    </div>
                </div>
            </div>
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h1 class="panel-title">Manage Inventory</h1>
                </div>
                <div class="panel-body">
                    <div class="btn-toolbar">
                        <div class="btn-group"><a href="<%=InventorySqllistServlet.SRV_MAP%>"><button class="btn btn-primary">Browse Inventory</button></a></div>
                        <div class="btn-group"><a href="<%=InventoryEditorServlet.SRV_MAP%>"><button class="btn btn-primary">Add New Inventory</button></a></div>
                    </div>
                </div>
            </div>
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h1 class="panel-title">Manage Email Notifications</h1>
                </div>
                <div class="panel-body">
                    <div class="btn-group"><a href="https://mandrillapp.com/templates"><button class="btn btn-primary">Mandrill Template Editor</button></a></div><br><br>
                    *You may need to log in in order to view web client.  Currently linked to TeamJJACS@gmail.com login
                </div>
            </div>
        </div>
    </div>
</div>


<jsp:include page="/include/Footer.jsp"/>