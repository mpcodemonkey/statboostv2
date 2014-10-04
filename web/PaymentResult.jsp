<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 9/29/2014
  Time: 8:39 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<div class="container">
    <div class="well well-lg">
        <div style="max-width:400px;margin-left: auto; margin-right: auto;">
            <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
                <div class="alert alert-${requestScope.alertType} fade in">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Alert:</strong> <c:out value="${requestScope.alert}" />
                </div>
            </c:if>
            <h1 align="center">Payment Result</h1>
            <br>



        </div>
    </div>
</div>


<jsp:include page="/include/Footer.jsp"/>