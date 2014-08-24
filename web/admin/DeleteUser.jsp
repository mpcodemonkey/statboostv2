<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<div>
    <div class="container-fluid">
        <div class="well well-lg">
            <h1 style="text-align: center">Delete a User</h1>
            <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
                <div class="alert alert-${requestScope.alertType} fade in">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Alert: </strong><c:out value="${requestScope.alert}" />
                </div>
            </c:if>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h1 class="panel-title">Delete an Administrator</h1>
                </div>
                <div class="panel-body">

                TODO: List all admins here

                </div>
            </div>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h1 class="panel-title">Delete an Employee</h1>
                </div>
                <div class="panel-body">

                    TODO: List all employees here

                </div>
            </div>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h1 class="panel-title">Search for a Customer</h1>
                </div>
                <div class="panel-body">

                    TODO: add customer search bar, maybe auto complete?

                </div>
            </div>

        </div>
    </div>
</div>


<jsp:include page="/include/Footer.jsp"/>