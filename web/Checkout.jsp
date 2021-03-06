<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 9/29/2014
  Time: 8:39 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>
<script src="/include/javascripts/jquery.maskedinput.min.js"></script>
<script src="/include/javascripts/jquery.creditCardValidator.js"></script>
<script>
    $(document).ready(function () {
        var ccNumber = document.getElementById('cc_number');
        ccNumber.onpaste = function(e) {
            e.preventDefault();
        }

        //init input masks
        $('.zip').mask('99999', {placeholder: ""});
        $('#cc_number').mask('9999999999999?999', {placeholder: ""});
        $('#cc_code').mask('999?9', {placeholder: ""});
        $('#exp').mask('99/99', {placeholder: "#"});

        $(".state").keyup(function(){
            this.value = this.value.toUpperCase();
        });

        //copyFields($('#checkout'));
        //$('#copyShipping').prop('checked', true);

        //clear and disable shipping info if in store pickup order
        if (window.location.search.indexOf("pickupOrder=No") == -1) {
            //$('#shippingInfo').children().val('');
            $('#shippingInfo').children().attr("disabled", "disabled");
        }

    });
</script>

<%@ page import="com.statboost.controllers.ShoppingCartServlet" %>
<%@ page import="com.statboost.util.ServletUtil" %>
<%@ page import="net.authorize.sim.Fingerprint" %>

<%
    String apiLoginId = "8qLJKr37W"; //Authorize API Login ID
    String transactionKey = "9Rw9M2K8UsgSu28x"; //Authorize Transaction Key
    String relayResponseUrl = "https://teamjjacs.us/relayPaymentResponse.jsp";
    String amount = (String)session.getAttribute("orderTotal");
    String tax = ServletUtil.formatCurrency(((ShoppingCartServlet.ShoppingCartTotal)session.getAttribute("cartTotals")).getTaxTotal());
    String shipping = ServletUtil.formatCurrency(((ShoppingCartServlet.ShoppingCartTotal)session.getAttribute("cartTotals")).getShippingTotal());


    Fingerprint fingerprint = Fingerprint.createFingerprint(
            apiLoginId,
            transactionKey,
            1051628394,  // Random sequence (will be appended with random value)
            amount);

    long x_fp_sequence = fingerprint.getSequence();
    long x_fp_timestamp = fingerprint.getTimeStamp();
    String x_fp_hash = fingerprint.getFingerprintHash();


    String clientIP = ServletUtil.getClientIpAddr(request);
%>


<div class="container">
    <div class="well well-lg">
        <div style="max-width:800px;margin-left: auto; margin-right: auto;">
            <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
                <div class="alert alert-${requestScope.alertType} fade in">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Alert:</strong> <c:out value="${requestScope.alert}" />
                </div>
            </c:if>
            <h1 align="center">Order Checkout</h1>
            <br>
            <form id="checkout" method="post" action="https://test.authorize.net/gateway/transact.dll">
                <div class="row">
                    <div class="col-xs-6">
                        <fieldset style="">
                            <legend>Billing Information</legend>
                            <input class="form-control" type='text' name='x_first_name' placeholder="Cardholder's First Name" value='<c:if test="${requestScope.user != null}">${requestScope.user.usrFirstName}</c:if>' required/><br>
                            <input class="form-control" type='text' name='x_last_name' placeholder="Cardholder's Last Name" value='<c:if test="${requestScope.user != null}">${requestScope.user.usrLastName}</c:if>' required/><br>
                            <input class="form-control" type='text' name='x_address' placeholder="Billing Address" value='<c:if test="${requestScope.user != null}">${requestScope.user.usrAddress1}</c:if>' required/><br>
                            <input class="form-control" type='text' name='x_city' placeholder="Billing City" value='<c:if test="${requestScope.user != null}">${requestScope.user.usrCity}</c:if>' required/><br>
                            <input class="form-control state" type='text' name='x_state' maxlength="2" placeholder="Billing State" value='<c:if test="${requestScope.user != null}">${requestScope.user.usrState}</c:if>' required/><br>
                            <input class="form-control zip" type='text' name='x_zip' maxlength="5" placeholder="Billing Zip" value='<c:if test="${requestScope.user != null}">${requestScope.user.usrZip}</c:if>' required/><br>
                        </fieldset>
                    </div>
                    <div class="col-xs-6">
                        <fieldset id="shippingInfo">
                            <legend>Shipping Information</legend>
                            <input class="form-control" type='text' name='x_ship_to_first_name' placeholder="First Name" <c:if test="${param.pickupOrder == 'No'}">required</c:if>/><br>
                            <input class="form-control" type='text' name='x_ship_to_last_name' placeholder="Last Name" <c:if test="${param.pickupOrder == 'No'}">required</c:if>/><br>
                            <input class="form-control" type='text' name='x_ship_to_address' placeholder="Address" <c:if test="${param.pickupOrder == 'No'}">required</c:if>/><br>
                            <input class="form-control" type='text' name='x_ship_to_city' placeholder="City" <c:if test="${param.pickupOrder == 'No'}">required</c:if>/><br>
                            <input class="form-control state" type='text' name='x_ship_to_state' maxlength="2" placeholder="State" maxlength="2" <c:if test="${param.pickupOrder == 'No'}">required</c:if>/><br>
                            <input class="form-control zip" type='text' name='x_ship_to_zip' maxlength="5" placeholder="Zip" <c:if test="${param.pickupOrder == 'No'}">required</c:if>/><br>
                        </fieldset>
                    </div>
                </div>
                <c:choose>
                    <c:when test="${param.pickupOrder == 'No'}">
                        <div class="checkbox">
                            <label><input name="copyShipping" id="copyShipping" type="checkbox" class="checkbox" onclick="copyFields(this.form)">&nbsp;Copy Billing Info</label>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="checkbox">
                            <h4 style="color: darkred"><span class="glyphicon glyphicon-hand-right">&nbsp;</span> Store pickup will be at <u>${param.pickupOrder}</u></h4>
                        </div>
                    </c:otherwise>
                </c:choose>
                <br><br>
                <div class="row">
                    <div class="col-lg-6" style="max-width: 500px;">
                        <div class="form-group">
                            <label class="">Contact Email</label><br><small>NOTE: This email will be used for receipt and order status updates.</small>
                            <input class="form-control" id="email" type='text' class='text' name='x_email' value="${sessionScope.email}" required/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-6" style="max-width: 500px;">
                        <div class="form-group has-feedback">
                            <label class="">Credit Card Number</label>
                            <input class="form-control" id="cc_number" type='text' name='x_card_num' required/>
                            <i id="cc_card_feedback" class="glyphicon form-control-feedback" style="color: green;"></i>
                        </div>
                        <div class="row">
                            <div class="col-lg-6" style="max-width: 500px;">
                                <div class="form-group has-feedback">
                                    <label class="">CVV2/CVC2/CID</label>
                                    <input class="form-control" id="cc_code" type='text' name='x_card_code' required/>
                                    <i id="cc_code_feedback" class="glyphicon form-control-feedback" style="color: green;"></i>
                                </div>
                            </div>
                            <div class="col-lg-6" style="max-width: 500px;">
                                <div class="form-group">
                                    <label class="">Exp.</label>
                                    <input class="form-control" id="exp" type='text' class='text' name='x_exp_date' placeholder="MM/YY" required/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6" style="max-width: 500px;">
                        <div class="well well-sm totalBox">
                            <br>
                            <h2 style="display: inline;">Order Total:&nbsp;</h2>
                            <span style="color: darkred;font-size: 34px;"><fmt:formatNumber value="<%=amount%>" type="currency"/></span>
                            <br><br>
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
                <input type='hidden' name='x_tax' value='<%=tax%>' />
                <input type='hidden' name='x_freight' value='<%=shipping%>' />
                <input type='hidden' name='x_test_request' value='FALSE' />
                <input type='hidden' name='notes' value='none' />
                <input type='hidden' name='x_header_email_receipt' value='Your payment to EXP:Level-Up has been processed.' />
                <input type='hidden' name='x_footer_email_receipt' value='Thank you for shopping with EXP:Level-Up online!' />
                <!-- Customer fields -->
                <input type='hidden' name='x_customer_ip' value='<%=clientIP%>' />
                <input type='hidden' name='x_cust_id' value='${requestScope.user.usrUid}' />
                <input type='hidden' name='x_phone' value='${requestScope.user.usrPhone}' />
                <input type='hidden' name='x_email_customer' value='TRUE' />
                <input type='hidden' name='inStorePickup' value='${param.pickupOrder}' />

                <!-- for each of all cart items item ID<|>item name<|>item description<|>item quantity<|>item price per unit<|>is Taxable -->
                <c:forEach items="${sessionScope.itemsInCart}" var="item" varStatus="status">
                    <input type='hidden' name='x_line_item' value='${item.invUID}<|><c:choose><c:when test="${fn:length(item.name) > 30}">${fn:substring(item.name, 0, 30)}</c:when><c:otherwise>${item.name}</c:otherwise></c:choose><|>${item.description} - ${item.condition}<|>${item.quantity}<|>${item.price}<|>TRUE' />
                </c:forEach>
                <br><br><br>
                <div align="center">
                    <button class="btn btn-lg btn-primary" type='submit' name='buy_button' value='BUY'>Purchase</button>
                    <span class="btn btn-lg btn-default" type='button' onclick="window.location='/';">Cancel</span>
                </div>
                <br>
                <a href="//www.rapidssl.com"><img src="/include/images/RapidSSL_SEAL-90x50.gif"></a>
            </form>
        </div>
    </div>
</div>


<script>
    function copyFields(form) {
        if (form.copyShipping.checked == true) {
             form.x_ship_to_first_name.value = form.x_first_name.value;
             form.x_ship_to_last_name.value = form.x_last_name.value;
             form.x_ship_to_address.value = form.x_address.value;
             form.x_ship_to_city.value = form.x_city.value;
             form.x_ship_to_state.value = form.x_state.value;
             form.x_ship_to_zip.value = form.x_zip.value;
        } else {
            form.x_ship_to_first_name.value = '';
            form.x_ship_to_last_name.value = '';
            form.x_ship_to_address.value = '';
            form.x_ship_to_city.value = '';
            form.x_ship_to_state.value = '';
            form.x_ship_to_zip.value = '';
        }
    }


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
    form #cc_number.amex {
        background-position: 2px -79px, 260px -61px;
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
</style>


<jsp:include page="/include/Footer.jsp"/>