<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 8/6/2014
  Time: 3:35 PM
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>


<form method="post">
    <div class="container">
        <h2>Register</h2>

        <c:if test="${requestScope.alert != null}">
            <div class="alert alert-danger fade in">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <strong>Oops!</strong> <c:out value="${requestScope.alert}" />
            </div>
        </c:if>

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
                <button type="submit" class="btn btn-primary">Register User</button>
            </div>
        </div>
    </div>
</form>


<jsp:include page="/include/Footer.jsp"/>
