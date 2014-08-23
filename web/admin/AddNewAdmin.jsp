<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<div class="container">
    <div class="well well-lg">
        <h1 style="text-align: center">Add New Administrator</h1>
        <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
            <div class="alert alert-${requestScope.alertType} fade in">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <strong>Oops!</strong> <c:out value="${requestScope.alert}" />
            </div>
        </c:if>

        <form method="post">
            <div class="form-group">
                <div class="form-inline">
                    <div class="form-group">
                        <input name="firstname" type="firstname" class="form-control" placeholder="First Name" value="">
                    </div>
                    <div class="form-group">
                        <input name="lastname" type="lastname" class="form-control" placeholder="Last Name" value="">
                    </div>
                </div>
                <br>
                <div class="form-inline">
                    <div class="form-group">
                        <input name="email" type="email" class="form-control" placeholder="Email" value="">
                    </div>
                    <div class="form-group">
                        <input name="password" type="password" class="form-control" placeholder="Password">
                    </div>
                    <div class="form-group">
                        <input name="passwordConf" type="password" class="form-control" placeholder="Confirm Password">
                    </div>
                </div>
                <br>
                <div class="form-inline">
                    <button type="submit" class="btn btn-primary">Create Admin</button>
                </div>
            </div>
        </form>
    </div>
</div>



<jsp:include page="/include/Footer.jsp"/>