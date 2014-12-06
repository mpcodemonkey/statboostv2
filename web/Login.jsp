<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>


<form method="post">
    <div class="container">
        <div class="well well-lg" style="max-width: 500px; margin-left: auto; margin-right: auto;">
            <h2>Customer Login</h2>

            <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
                <div class="alert alert-${requestScope.alertType} fade in">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Alert: </strong> <c:out value="${requestScope.alert}" />
                </div>
            </c:if>


            <div class="form-inline">
                <div class="form-group">
                    <input name="email" type="email" class="form-control" placeholder="Email" value="" required>
                </div>
                <div class="form-group">
                    <input name="password" type="password" class="form-control" placeholder="Password" required>
                </div><!--
                <br><br>
                <div class="checkbox">
                    <label class=""><input name="remember" type="checkbox" class="checkbox">&nbsp;Remember me</label>
                </div>-->
                <br><br>
                <div>
                    <button type="submit" class="btn btn-primary" style="float:left;">Sign in</button>
                    <span style="float:right;">Need an Account? <a href="/register">Register here!</a></span>
                </div>
                <div>&nbsp;</div>
            </div>
        </div>
    </div>
</form>


<jsp:include page="/include/Footer.jsp"/>