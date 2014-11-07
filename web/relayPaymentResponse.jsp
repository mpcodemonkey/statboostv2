<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 10/3/2014
  Time: 7:07 PM

  This purpose of this scriptlet is to intercept payment responses from the Authorize.NET server and
  redirect the user to the payment result page.  The transaction response code and transaction ID
  will be passed to the payment result page to display appropriate results to the user and perform
  any application logic with what is returned.
--%>
<%@ page import="net.authorize.ResponseField" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head></head>
<body>
<script type="text/javascript">
    <!--
    var referrer = document.referrer;
    if (referrer.substr(0,7)=="http://") referrer = referrer.substr(7);
    if (referrer.substr(0,8)=="https://") referrer = referrer.substr(8);
    if (referrer && referrer.indexOf(document.location.hostname) != 0) {
        <%
             String apiLoginId = "8qLJKr37W";
             String receiptPageUrl = "http://teamjjacs.us/paymentResult";
             /*
              * Leave the MD5HashKey as is - unless you have explicitly set it in the
              * merchant interface: Account > Settings > Security Settings > MD5-Hash
              */
             String MD5HashKey = "teamjjacs";

             // Perform Java server side processing...
             net.authorize.sim.Result result = net.authorize.sim.Result.createResult(apiLoginId, MD5HashKey, request.getParameterMap());

             // Build receipt url buffer
             StringBuffer receiptUrlBuffer = new StringBuffer(receiptPageUrl);

             if(result != null)
             {
               receiptUrlBuffer.append("?");
               receiptUrlBuffer.append(ResponseField.RESPONSE_CODE.getFieldName()).append("=");
               receiptUrlBuffer.append(result.getResponseCode().getCode());

               receiptUrlBuffer.append("&");
               receiptUrlBuffer.append(ResponseField.RESPONSE_REASON_CODE.getFieldName()).append("=");
               receiptUrlBuffer.append(result.getReasonResponseCode().getResponseReasonCode());

               receiptUrlBuffer.append("&");
               receiptUrlBuffer.append(ResponseField.RESPONSE_REASON_TEXT.getFieldName()).append("=");
               receiptUrlBuffer.append(result.getResponseMap().get(ResponseField.RESPONSE_REASON_TEXT.getFieldName()));

               receiptUrlBuffer.append("&");
               receiptUrlBuffer.append(ResponseField.AUTHORIZATION_CODE.getFieldName()).append("=");
               receiptUrlBuffer.append(result.getResponseMap().get(ResponseField.AUTHORIZATION_CODE.getFieldName()));

               receiptUrlBuffer.append("&");
               receiptUrlBuffer.append(ResponseField.CARD_CODE_RESPONSE.getFieldName()).append("=");
               receiptUrlBuffer.append(result.getResponseMap().get(ResponseField.CARD_CODE_RESPONSE.getFieldName()));

               receiptUrlBuffer.append("&");
               receiptUrlBuffer.append(ResponseField.ACCOUNT_NUMBER.getFieldName()).append("=");
               receiptUrlBuffer.append(result.getResponseMap().get(ResponseField.ACCOUNT_NUMBER.getFieldName()));

               receiptUrlBuffer.append("&");
               receiptUrlBuffer.append(ResponseField.CARD_TYPE.getFieldName()).append("=");
               receiptUrlBuffer.append(result.getResponseMap().get(ResponseField.CARD_TYPE.getFieldName()));

               receiptUrlBuffer.append("&");
               receiptUrlBuffer.append(ResponseField.ACCOUNT_NUMBER.getFieldName()).append("=");
               receiptUrlBuffer.append(result.getResponseMap().get(ResponseField.ACCOUNT_NUMBER.getFieldName()));

               receiptUrlBuffer.append("&");
               receiptUrlBuffer.append(ResponseField.AMOUNT.getFieldName()).append("=");
               receiptUrlBuffer.append(result.getResponseMap().get(ResponseField.AMOUNT.getFieldName()));

               receiptUrlBuffer.append("&");
               receiptUrlBuffer.append(ResponseField.ACCOUNT_NUMBER.getFieldName()).append("=");
               receiptUrlBuffer.append(result.getResponseMap().get(ResponseField.ACCOUNT_NUMBER.getFieldName()));

               /** SHIPPING INFO YES? NO?**/



               if(result.isApproved())
               {
                 receiptUrlBuffer.append("&").append(ResponseField.TRANSACTION_ID.getFieldName()).append("=");
                 receiptUrlBuffer.append(result.getResponseMap().get(ResponseField.TRANSACTION_ID.getFieldName()));
               }
             }
        %>
        // Use Javascript to redirect the page to the receipt redirect url.  If Javascript is
        // not available, then the <meta> refresh tag will handle the redirect.
        document.location = "<%=receiptUrlBuffer.toString()%>";
    }
    //-->
</script>
<noscript><meta http-equiv="refresh" content="0;url=<%=receiptUrlBuffer.toString()%>">
</noscript>
</body>
</html>