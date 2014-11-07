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
<script src="/include/javascripts/jquery.maskedinput.min.js"></script>
<script src="/include/javascripts/jquery.creditCardValidator.js"></script>
<script>
    $(document).ready(function () {
        //init input masks
       // $('#cc_number').mask('9999-9999-9999-9999', {placeholder: " "});
        $('#exp').mask('99/99', {placeholder: " "});

    });

</script>

<%@ page import="net.authorize.sim.Fingerprint" %>
<%@ page import="java.util.Random" %>
<%
    String apiLoginId = "8qLJKr37W"; //Authorize API Login ID
    String transactionKey = "9Rw9M2K8UsgSu28x"; //Authorize Transaction Key
    String relayResponseUrl = "http://teamjjacs.us/relayPaymentResponse.jsp";
    String amount = (String)session.getAttribute("orderTotal");

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
        <div style="max-width:1000px;margin-left: auto; margin-right: auto;">
            <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
                <div class="alert alert-${requestScope.alertType} fade in">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Alert:</strong> <c:out value="${requestScope.alert}" />
                </div>
            </c:if>
            <h1 align="center">Checkout</h1>
            <br>
            <form method="post" action="https://test.authorize.net/gateway/transact.dll">
                <div class="row">
                    <div class="col-xs-6">
                        <fieldset style="">
                            <legend>Shipping Information</legend>
                            <input class="form-control" type='text' name='x_ship_to_first_name' placeholder="First Name" value='<c:if test="${requestScope.user != null}">${requestScope.user.usrFirstName}</c:if>' required/>
                            <input class="form-control" type='text' name='x_ship_to_last_name' placeholder="Last Name" value='<c:if test="${requestScope.user != null}">${requestScope.user.usrLastName}</c:if>' required/>
                            <input class="form-control" type='text' name='x_ship_to_address' placeholder="Address" value='<c:if test="${requestScope.user != null}">${requestScope.user.usrAddress1}</c:if>' required/>
                            <input class="form-control" type='text' name='x_ship_to_city' placeholder="City" value='<c:if test="${requestScope.user != null}">${requestScope.user.usrCity}</c:if>' required/>
                            <input class="form-control" type='text' name='x_ship_to_state' placeholder="State" value='<c:if test="${requestScope.user != null}">${requestScope.user.usrState}</c:if>' required/>
                            <input class="form-control" type='text' name='x_ship_to_zip' placeholder="Zip" value='<c:if test="${requestScope.user != null}">${requestScope.user.usrZip}</c:if>' required/>
                        </fieldset>
                    </div>
                    <div class="col-xs-6">
                        <fieldset style="">
                            <legend>Billing Information</legend>
                            <input class="form-control" type='text' name='x_first_name' placeholder="Cardholder's First Name" value='' required/>
                            <input class="form-control" type='text' name='x_last_name' placeholder="Cardholder's Last Name" value='' required/>
                            <input class="form-control" type='text' name='x_address' placeholder="Billing Address" value='' required/>
                            <input class="form-control" type='text' name='x_city' placeholder="Billing City" value='' required/>
                            <input class="form-control" type='text' name='x_state' placeholder="Billing State" value='' required/>
                            <input class="form-control" type='text' name='x_zip' placeholder="Billing Zip" value='' required/>
                        </fieldset>
                    </div>
                </div>
                <div class="checkbox">
                    <label class=""><input id="copyShipping" type="checkbox" class="checkbox">&nbsp;Copy Shipping Info</label>
                </div>
                <br><br>
                <div class="row">
                    <div class="col-xs-6">
                        <div class="form-group has-feedback">
                            <label class="">Credit Card Number</label>
                            <input class="form-control" id="cc_number" type='text' name='x_card_num' required/>
                            <i id="cc_card_feedback" class="glyphicon form-control-feedback" style="color: green;"></i>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="">CVV2/CVC2/CID</label>
                            <input class="form-control" id="cc_code" type='text' name='x_card_code' required/>
                            <i id="cc_code_feedback" class="glyphicon form-control-feedback" style="color: green;"></i>
                        </div>
                        <div class="form-group">
                            <label class="">Exp.</label>
                            <input class="form-control" id="exp" type='text' class='text' name='x_exp_date' placeholder="MM/YY" required/>
                        </div>
                        <div class="form-group">
                            <label class="">Amount</label>
                            <input type='text' class='form-control' style="color: darkred;font-size: 24px;" name='x_amount' readonly value='<fmt:formatNumber value="<%=amount%>" type="currency"/>' />
                        </div>
                    </div>
                </div>

                <input type='hidden' name='x_invoice_num' value='<%=System.currentTimeMillis()%>' /> <!-- Will need to update with our invoice nums -->
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
                <input type='hidden' name='notes' value='none' />
                <input type='hidden' name='x_header_email_receipt' value='Your payment to EXP:Level-Up has been processed.' />
                <input type='hidden' name='x_footer_email_receipt' value='Thank you for shopping with EXP:Level-Up online!' />
                <!-- Customer fields -->
                <input type='hidden' name='x_email_customer' value='FALSE' />
                <input type='hidden' name='x_email' value='' />


                <!-- for each of all cart items -->
                <input type='hidden' name='x_line_item' value='item ID<|>item name<|>item description<|>item quantity<|>item price per unit<|>TRUE' />


                <br><br><br>
                <div align="center">
                    <button class="btn btn-lg btn-primary" type='submit' name='buy_button' value='BUY'>Purchase</button>
                    <span class="btn btn-lg btn-default" type='button' onclick="window.location='/';">Cancel</span>
                </div>
            </form>
        </div>
    </div>
</div>


<script>
    $('#cc_number').validateCreditCard(function(result) {
        /*
        alert('CC type: ' + result.card_type.name
                + '\nLength validation: ' + result.length_valid
                + '\nLuhn validation: ' + result.luhn_valid);
*/
        if (result.length_valid && result.luhn_valid) {
            $('#cc_number').addClass('valid');
            $('#cc_card_feedback').addClass('glyphicon-ok');
            $('#cc_number').addClass(result.card_type.name);
        } else {
            $('#cc_number').removeClass();
            $('#cc_card_feedback').removeClass('glyphicon-ok');
            $('#cc_number').addClass('form-control');
        }
    }/*, {
        accept: ['visa', 'mastercard', 'amex']
    }*/);
</script>

<style>
    form #cc_number {
        background-image: url(/include/images/cc_images.png);
        background-position: 2px -121px, 260px -61px;
        background-size: 120px 361px, 120px 361px;
        background-repeat: no-repeat;
        padding-left: 54px;
    }
    form #cc_number.visa {
        background-position: 2px -163px, 260px -61px;
    }
    form #cc_number.visa_electron {
        background-position: 2px -205px, 260px -61px;
    }
    form #cc_number.mastercard {
        background-position: 2px -247px, 260px -61px;
    }
    form #cc_number.maestro {
        background-position: 2px -289px, 260px -61px;
    }
    form #cc_number.discover {
        background-position: 2px -331px, 260px -61px;
    }
    form #cc_feedback.valid{

    }
    form #cc_feedback.invalid{

    }

    /*
    form #cc_number.valid.visa {
        background-position: 2px -163px, 260px -87px;
    }
    form #cc_number.valid.visa_electron {
        background-position: 2px -205px, 260px -87px;
    }
    form #cc_number.valid.mastercard {
        background-position: 2px -247px, 260px -87px;
    }
    form #cc_number.valid.maestro {
        background-position: 2px -289px, 260px -87px;
    }
    form #cc_number.valid.discover {
        background-position: 2px -331px, 260px -87px;
    }*/
</style>


<jsp:include page="/include/Footer.jsp"/>