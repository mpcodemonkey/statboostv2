<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>


<div class="container">
    <div class="well well-lg">

        <h1 align="center">Order History</h1>
        <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
            <div class="alert alert-${requestScope.alertType} fade in">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <strong>Alert: </strong> <c:out value="${requestScope.alert}" />
            </div>
        </c:if>
        <br>
        <c:choose>
            <c:when test="${param.specificOrder == 'true'}">
                <c:if test="${fn:length(requestScope.orderItems) gt 0}">
                    <div>
                        <div>
                            <button class="btn btn-primary" onclick="history.go(-1)"><< Back</button>
                        </div>


                        <h2 align="center">Order-000${param.orderID}</h2>
                        <table class="table table-responsive table-bordered">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Condition</th>
                                <th>Quantity</th>
                                <th>Price</th>
                            </tr>
                            </thead>
                            <thead>
                            <c:forEach  items="${requestScope.orderItems}" var="item" varStatus="i">
                                <tr>
                                    <td>${i.count}</td>
                                    <td>${item.name}</td>
                                    <td>${item.description}</td>
                                    <td>${item.condition}</td>
                                    <td>${item.quantity}</td>
                                    <td><fmt:formatNumber value="${item.price}" type="currency"/></td>
                                </tr>
                            </c:forEach>
                            </thead>
                        </table>
                    </div>
                </c:if>
            </c:when>
            <c:otherwise>
                <c:if test="${fn:length(requestScope.orderList) gt 0}">
                    <table class="table table-responsive table-bordered">
                        <thead>
                        <tr>
                            <th>Order Number</th>
                            <th>Order Status</th>
                            <th>Date Submitted</th>
                            <th>In Store Pickup</th>
                            <th>Order Total</th>
                        </tr>
                        </thead>
                        <thead>

                        <c:forEach  items="${requestScope.orderList}" var="order" varStatus="i">
                            <div class="modal fade" id="orderInfo-${i.index}" tabindex="-1" role="dialog" aria-labelledby="label_${i.index}" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                            <h4 class="modal-title" id="label_${i.index}">Order-000${order.uid} &#8226; <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${order.dateSubmitted}" /></h4>
                                        </div>
                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="col-lg-6 col-lg-push-3">
                                                    <p>
                                                        <b>Order Status:</b> ${order.status}<br>
                                                        <b>Paid:</b> ${order.paid}<br>
                                                        <b>Date Paid:</b> ${order.datePaid}<br>
                                                        <b>Transaction ID:</b> ${order.transactionId}<br>
                                                        <b>In Store Pickup:</b> ${order.inStorePickup}<br>
                                                        <b>Tracking Number:</b> ${order.trackingNumber}<br><br>

                                                        <b>Shipping Method:</b> ${order.shippingMethod}<br>
                                                        <b>Shipping Address:</b> ${order.shippingAddress1}<br>
                                                        <b>Shipping City:</b> ${order.shippingCity}<br>
                                                        <b>Shipping State:</b> ${order.shippingState}<br>
                                                        <b>Shipping Zip:</b> ${order.shippingZip}<br><br>
                                                    </p>
                                                    <div align="left">
                                                        <button type="button" class="btn btn-sm btn-primary" onclick="window.location='?specificOrder=true&orderID=${order.uid}'">View Items Purchased</button>
                                                    </div><br>
                                                    <p>
                                                        <b>Tax Total:</b> <fmt:formatNumber value="${order.taxTotal}" type="currency"/><br>
                                                        <b>Shipping Total:</b> <fmt:formatNumber value="${order.shippingTotal}" type="currency"/><br>
                                                        <b>Order Total:</b> <fmt:formatNumber value="${order.orderTotal}" type="currency"/><br>
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                </div>
                            </div>



                            <tr>
                                <td><button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#orderInfo-${i.index}">Order-000${order.uid}</button></td>
                                <td>${order.status}</td>
                                <td><fmt:formatDate type="date" value="${order.dateSubmitted}" /></td>
                                <td><c:choose><c:when test="${order.inStorePickup == 'true'}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></td>
                                <td><fmt:formatNumber value="${order.orderTotal}" type="currency"/></td>
                            </tr>
                        </c:forEach>

                        </thead>
                    </table>
                </c:if>
            </c:otherwise>
        </c:choose>


    </div>
</div>



<jsp:include page="/include/Footer.jsp"/>