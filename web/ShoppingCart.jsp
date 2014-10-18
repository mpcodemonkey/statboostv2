<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 10/18/2014
  Time: 1:34 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>
<script src="/include/javascripts/jquery.maskedinput.min.js"></script>
<script>
    $(document).ready(function () {
        //init input masks
        $('#zip').mask('99999?-9999', {placeholder: "-"});


    });

</script>


<div class="container">
    <div class="well well-lg">
        <h2 align="center">Shopping Cart</h2>
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
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Total</th>
                </tr>
            </thead>
            <tbody>
                <!--Begin test rows -->
                <tr>
                    <td>1</td>
                    <td>Red-Eyes Black Dragon</td>
                    <td class="col-sm-1"><input name="qty" type="text" class="form-control" placeholder="QTY" value="2" maxlength="2" size="2"></td>
                    <td>$12.99</td>
                    <td>$25.98</td>
                </tr>

                <tr>
                    <td>2</td>
                    <td>Blue-Eyes White Dragon</td>
                    <td class="col-sm-1"><input name="qty" type="text" class="form-control" placeholder="QTY" value="1" maxlength="2" size="2"></td>
                    <td>$14.99</td>
                    <td>$14.99</td>
                </tr>
                <!--End test rows -->

                <c:forEach items="${requestScope.itemsInCart}" var="cartItem" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${cartItem.description}</td>
                        <td class="col-sm-1"><input name="qty" type="text" class="form-control" placeholder="QTY" value="${cartItem.quantity}" maxlength="2" size="2"></td>
                        <td>${cartItem.price}</td>
                        <td>${cartItem.total}</td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>

        <div class="row">
            <div class="col-md-3 col-md-pull-9"></div>
            <div class="col-md-9 col-md-push-3">
                <div>
                    Calculate shipping rates: TODO
                </div>
                <div>
                    Subtotal: TODO<br>
                    Tax: TODO<br>
                    Order Total: TODO<br>
                </div>
            </div>
        </div>
    </div>
</div>


<jsp:include page="/include/Footer.jsp"/>