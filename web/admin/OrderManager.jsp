<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 11/24/2014
  Time: 10:41 PM
--%>
<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>


<div class="container">
    <div class="well well-lg">

        <h1 align="center">Order Manager</h1>
        <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
            <div class="alert alert-${requestScope.alertType} fade in">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <strong>Alert: </strong> <c:out value="${requestScope.alert}" />
            </div>
        </c:if>
        <br>
        <form method="post">
            <div class="row">
                <div class="col-sm-3">
                    <div class="form-group-lg">
                        <label class="">Order Number:</label>
                        <input name="orderNumber" type="text" class="form-control">
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="form-group-lg">
                        <label class="">Email Address:</label>
                        <input name="orderEmail" type="text" class="form-control">
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="form-group-lg">
                        <label class="">Transaction ID:</label>
                        <input name="transactionId" type="text" class="form-control">
                    </div>
                </div>
                <div class="col-sm-3">
                    <br>
                    <button class="btn btn-primary" type="submit">Search</button>
                </div>
            </div>
        </form>
        <br><br>
        <table class="table table-responsive">
            <thead>
                <tr>
                    <th>Order Number</th>
                    <th>Date Submitted</th>
                    <th>Order Status</th>
                    <th>In Store Pickup</th>
                    <th>Order Total</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.orderList}" var="order" varStatus="i">
                    <tr>
                        <td><button class="btn btn-sm btn-primary">Order-${order.uid}</button></td>
                        <td><fmt:formatDate type="date" value="${order.dateSubmitted}"/></td>
                        <td>${order.status}</td>
                        <td><c:choose><c:when test="${order.inStorePickup == 'true'}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></td>
                        <td><fmt:formatNumber value="${order.orderTotal}" type="currency"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

     </div>
</div>








<jsp:include page="/include/Footer.jsp"/>