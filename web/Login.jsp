<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>


<form method="post">
    <div class="container">
        <h2>Authenticate Yourself</h2>

        <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
            <div class="alert alert-${requestScope.alertType} fade in">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <strong>Oops!</strong> <c:out value="${requestScope.alert}" />
            </div>
        </c:if>


        <div class="form-inline">
            <div class="form-group">
                <input name="email" type="email" class="form-control" placeholder="Email" value="" required>
            </div>
            <div class="form-group">
                <input name="password" type="password" class="form-control" placeholder="Password" required>
            </div>
            <button type="submit" class="btn btn-primary">Sign in</button>
        </div>
    </div>
</form>


<jsp:include page="/include/Footer.jsp"/>