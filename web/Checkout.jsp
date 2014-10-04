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
<script src="/include/javascripts/jquery.maskedinput.min.js"></script>
<script>
    $(document).ready(function () {
        //init input masks
        $('#cc').mask('9999-9999-9999-9999', {placeholder: "_"});
        $('#exp').mask('99/99', {placeholder: "_"});

    });

</script>

<%@ page import="net.authorize.sim.Fingerprint" %>
<%@ page import="java.util.Random" %>
<%
    String apiLoginId = "8qLJKr37W"; //Authorize API Login ID
    String transactionKey = "9Rw9M2K8UsgSu28x"; //Authorize Transaction Key
    String relayResponseUrl = "http://teamjjacs.us/relayPaymentResponse.jsp";
    String amount = "4.57";

    Random rdm = new Random();
    String num = ""+rdm.nextLong();
    num = num.substring(0, 8);
    Fingerprint fingerprint = Fingerprint.createFingerprint(
            apiLoginId,
            transactionKey,
            Long.parseLong(num),  // Random val?
            amount);

    long x_fp_sequence = fingerprint.getSequence();
    long x_fp_timestamp = fingerprint.getTimeStamp();
    String x_fp_hash = fingerprint.getFingerprintHash();
%>


<div class="container">
    <div class="well well-lg">
        <div style="max-width:400px;margin-left: auto; margin-right: auto;">
            <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
                <div class="alert alert-${requestScope.alertType} fade in">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Alert:</strong> <c:out value="${requestScope.alert}" />
                </div>
            </c:if>
            <h1 align="center">Checkout</h1>
            <br>
            <form method="post" action="https://test.authorize.net/gateway/transact.dll">
                <div class="form-group">
                    <label class="">Credit Card Number</label>
                    <input class="form-control" id="cc" type='text' name='x_card_num' required/>
                    <label class="">Exp.</label>
                    <input class="form-control" id="exp" type='text' class='text' name='x_exp_date' required/>
                </div>
                <div class="form-group">
                </div>
                <div class="form-group">
                    <label class="">Amount</label>
                    <input type='text' class='form-control' name='x_amount' readonly value='<%=amount%>' />
                </div>
                <input type='hidden' name='x_invoice_num' value='<%=System.currentTimeMillis()%>' />
                <input type='hidden' name='x_relay_url' value='<%=relayResponseUrl%>' />
                <input type='hidden' name='x_relay_response' value='TRUE' />
                <input type='hidden' name='x_login' value='<%=apiLoginId%>' />
                <input type='hidden' name='x_fp_sequence' value='<%=x_fp_sequence%>' />
                <input type='hidden' name='x_fp_timestamp' value='<%=x_fp_timestamp%>' />
                <input type='hidden' name='x_fp_hash' value='<%=x_fp_hash%>' />
                <input type='hidden' name='x_version' value='3.1' />
                <input type='hidden' name='x_method' value='CC' />
                <input type='hidden' name='x_type' value='AUTH_CAPTURE' />
                <input type='hidden' name='x_amount' value='<%=amount%>' />
                <input type='hidden' name='x_test_request' value='TRUE' />
                <input type='hidden' name='notes' value='extra hot please' />
                <br><br><br>
                <div align="center">
                    <button class="btn btn-lg btn-success" type='submit' name='buy_button' value='BUY'>Purchase</button>
                    <span class="btn btn-lg btn-default" type='button' onclick="window.location='/';">Run Away</span>
                </div>
            </form>
        </div>
    </div>
</div>


<jsp:include page="/include/Footer.jsp"/>