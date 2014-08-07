<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>


<form method="post">
    <div class="container">
        <h2>Authenticate Yourself</h2>
        <!--
        @if(loginForm.hasGlobalErrors) {
        <div class="alert alert-danger fade in">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
            <strong>Oops!</strong> @loginForm.globalError.message
        </div>
        } -->
        <div class="form-inline">
            <div class="form-group">
                <input name="email" type="email" class="form-control" placeholder="Email" value="">
            </div>
            <div class="form-group">
                <input name="password" type="password" class="form-control" placeholder="Password">
            </div>
            <button type="submit" class="btn btn-primary">Sign in</button>
        </div>
    </div>
</form>


<jsp:include page="/include/Footer.jsp"/>