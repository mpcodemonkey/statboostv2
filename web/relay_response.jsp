<%@ page import="java.util.Map" %>
<%@ page import="net.authorize.*" %>

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
             String apiLoginId = "9W7GGc4kh6B";
             String receiptPageUrl = "http://MERCHANT_HOST/order_receipt.jsp";
             /*
              * Leave the MD5HashKey as is - unless you have explicitly set it in the
              * merchant interface: Account > Settings > Security Settings > MD5-Hash
              */
             String MD5HashKey = "9W7GGc4kh6B";

             // Perform Java server side processing...
             net.authorize.sim.Result result = net.authorize.sim.Result.createResult(apiLoginId,
        MD5HashKey, request.getParameterMap());

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
               receiptUrlBuffer.append(result.getResponseMap().get(
        ResponseField.RESPONSE_REASON_TEXT.getFieldName()));

               if(result.isApproved())
               {
                 receiptUrlBuffer.append("&").append(ResponseField.TRANSACTION_ID.getFieldName()
        ).append("=");
                 receiptUrlBuffer.append(result.getResponseMap().get(
        ResponseField.TRANSACTION_ID.getFieldName()));
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