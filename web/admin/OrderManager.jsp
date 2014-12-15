<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        <c:if test="${requestScope.specificOrder != null}">
            <button class="btn btn-sm btn-primary" onclick="window.location='/admin/ordermanager'"><< Back</button>
        </c:if>
        <h1 align="center">Order Manager</h1>
        <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
            <div class="alert alert-${requestScope.alertType} fade in">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <strong>Alert: </strong> ${requestScope.alert}
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
        <c:choose>
            <c:when test="${requestScope.specificOrder != null}">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h1 class="panel-title">Order-${specificOrder.uid}</h1>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <form method="POST">
                                <div class="col-sm-4">
                                    <div id="editStatus" style="display: none;" class="form-group-sm">
                                        <br><label>Status:</label>
                                        <select id="status" name="orderStatus" class="form-control">
                                            <c:forEach items="${statusValues}" var="status">
                                                <option value="${status}">${status}</option>
                                            </c:forEach>
                                        </select>
                                        <script>
                                            $('#status option[value="${specificOrder.status}"]').attr("selected", "selected");
                                        </script>
                                    </div>
                                    <div id="fixedStatus">
                                        Status: <span class="badge">${fn:replace(specificOrder.status, "_", " ")}</span>
                                    </div>
                                    <br>
                                    Transaction ID: ${specificOrder.transactionId}<br>
                                    First Name: ${specificOrder.contactFirstName}<br>
                                    Last Name: ${specificOrder.contactLastName}<br>
                                    Contact Email: ${specificOrder.contactEmail}<br>
                                    <br>
                                    Shipping Total: <fmt:formatNumber value="${specificOrder.shippingTotal}" type="currency"/><br>
                                    Tax Total: <fmt:formatNumber value="${specificOrder.taxTotal}" type="currency"/><br>
                                    Order Total: <fmt:formatNumber value="${specificOrder.orderTotal}" type="currency"/><br><br>
                                    Date Submitted: <fmt:formatDate pattern="MMM dd, yyyy h:mm a" value="${specificOrder.dateSubmitted}" /><br>
                                    Date Completed:<fmt:formatDate pattern="MMM dd, yyyy h:mm a" value="${specificOrder.dateComplete}" /><br>
                                </div>
                                <div class="col-sm-4">
                                    In Store Pickup?: <c:choose><c:when test="${specificOrder.inStorePickup == 'true'}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose><br>
                                    Shipping Method: ${specificOrder.shippingMethod}<br>
                                    Tracking Number: ${specificOrder.trackingNumber}<br>
                                    Shipping Address: ${specificOrder.shippingAddress1}<br>
                                    Shipping City: ${specificOrder.shippingCity}<br>
                                    Shipping State: ${specificOrder.shippingState}<br>
                                    Shipping Zip: ${specificOrder.shippingZip}<br><br>
                                </div>
                                <input type="hidden" name="orderId" value="${specificOrder.uid}">
                                <div class="col-sm-4">
                                    <div class="btn-group-sm">
                                        <button id="editButton" class="btn btn-sm btn-primary" onclick="editOrder()" type="button">Edit</button>
                                        <button id="saveButton" style="display: none;" class="btn btn-sm btn-primary" type="submit">Save</button>
                                        <button id="cancelButton" style="display: none;" class="btn btn-sm btn-primary" onclick="cancel()" type="button">Cancel</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <br><br>
                        <div class="row">
                            <div class="col-lg-12">
                                <table class="table table-responsive">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Item Name</th>
                                            <th>Description</th>
                                            <th>Condition</th>
                                            <th>Quantity</th>
                                            <th>Price</th>
                                        </tr>
                                    </thead>
                                    <tbody>
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
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <script>
                    function editOrder() {
                        $('#fixedStatus').hide();
                        $('#editStatus').show();
                        $('#editButton').hide();
                        $('#saveButton').show();
                        $('#cancelButton').show();
                    }

                    function cancel() {
                        $('#editStatus').hide();
                        $('#fixedStatus').show();
                        $('#editButton').show();
                        $('#saveButton').hide();
                        $('#cancelButton').hide();
                    }
                </script>
            </c:when>
            <c:otherwise>
                <table class="table table-responsive">
                    <thead>
                        <tr>
                            <th>Order Number</th>
                            <th>Date Submitted</th>
                            <th>Order Status</th>
                            <th>Contact Name</th>
                            <th>Contact Email</th>
                            <th>Order Total</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.orderList}" var="order" varStatus="i">
                        <tr>
                            <td><button class="btn btn-sm btn-primary" onclick="window.location='?orderId=${order.uid}'">Order-${order.uid}</button></td>
                            <td><fmt:formatDate pattern="MMM dd, yyyy h:mm a" value="${order.dateSubmitted}"/></td>
                            <td><span class="badge">${fn:replace(order.status, "_", " ")}</span></td>
                            <td>${order.contactFirstName}&nbsp;${order.contactLastName}</td>
                            <td>${order.contactEmail}</td>
                            <td><fmt:formatNumber value="${order.orderTotal}" type="currency"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
     </div>
</div>


<jsp:include page="/include/Footer.jsp"/>