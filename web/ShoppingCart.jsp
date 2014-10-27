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
        $('#zip').mask('99999?-9999', {placeholder: "-"});


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

                <c:choose>
                    <c:when test="${fn:length(requestScope.itemsInCart) gt 0}">
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
                                <td class="col-sm-1"><input name="qty" type="text" class="form-control" placeholder="QTY" value="${cartItem.quantity}" maxlength="2" size="2"></td>
                                <td><fmt:formatNumber value="${cartItem.price}" type="currency"/></td>
                                <td><fmt:formatNumber value="${cartItem.total}" type="currency"/></td>
                                <td><button class="btn btn-sm btn-danger"><span class="glyphicon glyphicon-remove"></span></button></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>

                        <div>
                            <h2>There seems to be no items in your shopping cart.</h2>
                        </div>

                    </c:otherwise>
                </c:choose>



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
                <div>
                    Calculate shipping rates: null
                </div>
                <div>
                    Subtotal: null<br>
                    Tax: null<br>
                    <h2 style="color: darkred;">Order Total: null</h2><br>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-1 col-lg-push-10">
                <button class="btn btn-lg btn-primary">Checkout</button>
            </div>
        </div>

    </div>
</div>


<jsp:include page="/include/Footer.jsp"/>