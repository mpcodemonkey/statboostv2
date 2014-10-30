<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 10/18/2014
  Time: 1:34 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="/include/javascripts/jquery.maskedinput.min.js"></script>
<script>
    $(document).ready(function () {
        //init input masks
        $('.qty').mask('9?9', {placeholder: ""});
    });
</script>


<div class="container">
    <div class="well well-lg" style="min-width: 600px;">
        <h2 align="center"><span class="glyphicon glyphicon-shopping-cart"></span> Shopping Cart</h2>
        <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
            <div class="alert alert-${requestScope.alertType} fade in">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <strong>Alert:</strong> <c:out value="${requestScope.alert}" />
            </div>
        </c:if>
        <c:choose>
            <c:when test="${fn:length(requestScope.itemsInCart) gt 0}">
                <table class="table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Item Description</th>
                            <th>Condition</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                            <th>Remove</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.itemsInCart}" var="cartItem" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td>
                                    <div class="col-lg-10">
                                        ${cartItem.name}:
                                        ${cartItem.description}
                                    </div>
                                    <div class="col-lg-2">
                                        <img src="http://placehold.it/75X75" class="img-rounded">
                                         ${cartItem.imageName}
                                    </div>
                                </td>
                                <td>${cartItem.condition}</td>
                                <td class="col-sm-1"><input name="qty" type="text" class="form-control qty" placeholder="QTY" onchange="updateItemQty(${status.count}, this.value)" value="${cartItem.quantity}" maxlength="2" size="2"></td>
                                <td><fmt:formatNumber value="${cartItem.price}" type="currency"/></td>
                                <td><fmt:formatNumber value="${cartItem.total}" type="currency"/></td>
                                <td><button class="btn btn-sm btn-danger" onclick="removeItem(${status.count})"><span class="glyphicon glyphicon-remove"></span></button></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>


                <div class="row">
                    <div class="col-md-4">
                        <div class="form-group">
                            <label>In Store Pickup?</label><br>
                            <select class="form-control">
                                <option>2920 Arden Way, Sacramento CA</option>
                                <option selected>No</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-4 col-md-push-2">
                       <div class="well well-lg totalBox">
                            <div>
                                <p class="lead">
                                    Calculate shipping rates: null<br>

                                </p>
                            </div>
                            <div>
                                <p class="lead">
                                    Subtotal: <fmt:formatNumber value="${cartTotal.subtotal}" type="currency"/><br>
                                    Tax: <fmt:formatNumber value="${cartTotal.taxTotal}" type="currency"/><br>
                                    Shipping Total: <fmt:formatNumber value="${cartTotal.shippingTotal}" type="currency"/>
                                </p>
                                <h2 style="color: darkred;">Order Total: <fmt:formatNumber value="${cartTotal.orderTotal}" type="currency"/></h2><br>
                            </div>
                       </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-1 col-lg-push-10">
                        <button class="btn btn-lg btn-primary">Checkout</button>
                    </div>
                </div>

            </c:when>
            <c:otherwise>
                <div style="margin-top: 200px;">
                    <h2 align="center">There seems to be no items in your shopping cart.</h2>
                </div>
            </c:otherwise>
        </c:choose>

    </div>
</div>

<script>
    function removeItem(index) {
        $.post("/cart", 'removeItem='+index);
        setTimeout(function() {window.location='/cart';}, 500);
    }

    function updateItemQty(index, quantity) {
        $.post("/cart", 'updateQuantity='+index+'&newQty='+quantity);
        setTimeout(function() {window.location='/cart';}, 500);
    }
</script>

<style>
    .totalBox {
        background: #f5f6f6; /* Old browsers */
        background: -moz-linear-gradient(top,  #f5f6f6 0%, #dbdce2 21%, #b8bac6 49%, #dddfe3 80%, #dbdbdb 100%); /* FF3.6+ */
        background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#f5f6f6), color-stop(21%,#dbdce2), color-stop(49%,#b8bac6), color-stop(80%,#dddfe3), color-stop(100%,#dbdbdb)); /* Chrome,Safari4+ */
        background: -webkit-linear-gradient(top,  #f5f6f6 0%,#dbdce2 21%,#b8bac6 49%,#dddfe3 80%,#dbdbdb 100%); /* Chrome10+,Safari5.1+ */
        background: -o-linear-gradient(top,  #f5f6f6 0%,#dbdce2 21%,#b8bac6 49%,#dddfe3 80%,#dbdbdb 100%); /* Opera 11.10+ */
        background: -ms-linear-gradient(top,  #f5f6f6 0%,#dbdce2 21%,#b8bac6 49%,#dddfe3 80%,#dbdbdb 100%); /* IE10+ */
        background: linear-gradient(to bottom,  #f5f6f6 0%,#dbdce2 21%,#b8bac6 49%,#dddfe3 80%,#dbdbdb 100%); /* W3C */
        filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#f5f6f6', endColorstr='#dbdbdb',GradientType=0 ); /* IE6-9 */
    }
</style>

<jsp:include page="/include/Footer.jsp"/>