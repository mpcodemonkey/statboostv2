<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 9/29/2014
  Time: 8:39 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<div class="container">
    <div class="well well-lg">
        <div style="max-width:400px;margin-left: auto; margin-right: auto;">
            <h1 align="center">Payment Result</h1>
            <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
                <div class="alert alert-${requestScope.alertType} fade in">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Alert:</strong> <c:out value="${requestScope.alert}" />
                </div>
            </c:if>
            <c:choose>
                <c:when test="${requestScope.alertType != null && requestScope.alertType == 'success'}">
                    <div>
                        <h3>Thank you for your order! A receipt has been emailed to you.</h3>
                    </div>
                    <div>
                        <p>Payment Details:</p>
                        <p>
                            <c:if test="${requestScope.orderID != null}"> Order ID: ${requestScope.orderID}<br> </c:if>
                            Transaction ID: ${requestScope.transactionID}<br>
                            Amount Paid: <fmt:formatNumber value="${requestScope.amount}" type="currency"/><br>
                            Account Charged: ${requestScope.acctNumber}<br>
                            Card Type: ${requestScope.cardType}<br>
                        </p>
                    </div>
                    <div>
                        <p>
                            <c:choose>
                                <c:when test="${sessionScope.email != null}"><button class="btn btn-sm btn-primary" onclick="window.location='/user/orderhistory'">View Order History</button></c:when>
                                <c:otherwise>Create an account before future orders to log your order history.</c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </c:when>
                <c:otherwise>
                    <p>We are sorry, your transaction was not successful.</p>
                    <p>Please try again by validating that your payment information is correct, or by using a different card.</p><br>

                    <script>
                        setTimeout(function() {window.location='/checkout';}, 6000);
                    </script>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>


<jsp:include page="/include/Footer.jsp"/>