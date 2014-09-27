<%@ page import="net.authorize.sim.*" %>
<%
//    test credit card: 4007000000027
    String apiLoginId = "9W7GGc4kh6B";
    String transactionKey = "8Uz2gV2696A5cuxN";
    String relayResponseUrl = "http://73.48.144.55/relay_response.jsp";
    String amount = "1.99";

    Fingerprint fingerprint = Fingerprint.createFingerprint(
            apiLoginId,
            transactionKey,
            1234567890,  // Random sequence used for creating the fingerprint
            amount);

    long x_fp_sequence = fingerprint.getSequence();
    long x_fp_timestamp = fingerprint.getTimeStamp();
    String x_fp_hash = fingerprint.getFingerprintHash();
%>
<form name='secure_redirect_form' id='secure_redirect_form_id'
      action='https://test.authorize.net/gateway/transact.dll' method='post'>
    <label>CreditCardNumber</label>
    <input type='text' class='text' name='x_card_num' size='15' />
    <label>Exp.</label>
    <input type='text' class='text' name='x_exp_date' size='4' />
    <label>Amount</label>
    <input type='text' class='text' name='x_amount' size='9' readonly value='<%=amount%>' />
    <input type='hidden' name='x_invoice_num' value='<%=System.currentTimeMillis()%>' />
    <input type='hidden' name='x_relay_url' value='<%=relayResponseUrl%>' />
    <input type='hidden' name='x_login' value='<%=apiLoginId%>' />
    <input type='hidden' name='x_fp_sequence' value='<%=x_fp_sequence%>' />
    <input type='hidden' name='x_fp_timestamp' value='<%=x_fp_timestamp%>' />
    <input type='hidden' name='x_fp_hash' value='<%=x_fp_hash%>' />
    <input type='hidden' name='x_version' value='3.1' />
    <input type='hidden' name='x_method' value='CC' />
    <input type='hidden' name='x_type' value='AUTH_CAPTURE' />
    <input type='hidden' name='x_amount' value='<%=amount%>' />
    <input type='hidden' name='x_test_request' value='FALSE' />
    <input type='hidden' name='notes' value='extra hot please' />
    <input type='submit' name='buy_button' value='BUY' />
</form>