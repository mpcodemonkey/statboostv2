<%--
  Created by IntelliJ IDEA.
  User: Jon
  Date: 9/3/2014
  Time: 12:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<HTML lang='en'>
<HEAD>
    <TITLE> Sample SIM Implementation </TITLE>
</HEAD>
<BODY>

<!-- This section generates the "Submit Payment" button using JSP           -->
<%@ page import="javax.crypto.KeyGenerator" %>
<!-- SimpleDateFormat is used to generate an invoice number based off of the date -->
<%@ page import="javax.crypto.Mac" %>
<!-- the following imports are used to generate the fingerprint -->
<%@ page import="javax.crypto.SecretKey" %>
<%@ page import="javax.crypto.spec.SecretKeySpec" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Random" %>

<%

    // the parameters for the payment can be configured here
// the API Login ID and Transaction Key must be replaced with valid values
    String loginID			= "9W7GGc4kh6B";
    String transactionKey	= "8Uz2gV2696A5cuxN";
    String amount			= "0.01";
    String description		= "";
    String label 			= "Checkout"; // The is the label on the 'submit' button
    String testMode			= "true";
    String first_name       = "";
    String last_name        = "";
    String bill_address     = "";
    String bill_city        = "";
    String bill_state       = "";
    String bill_zip         = "";
    String phone            = "";
    String email            = "";
    String customer_id      = "";
    String ship_first_name  = "";
    String ship_last_name   = "";
    String ship_address     = "";
    String ship_city        = "";
    String ship_state       = "";
    String ship_zip         = "";
    String ship_country     = "";

// By default, this sample code is designed to post to our test server for
// developer accounts: https://test.authorize.net/gateway/transact.dll
// for real accounts (even in test mode), please make sure that you are
// posting to: https://secure.authorize.net/gateway/transact.dll
    String url			= "https://test.authorize.net/gateway/transact.dll";

// If an amount or description were posted to this page, the defaults are overidden
    if ( request.getParameter("amount") != null )
    { amount = request.getParameter("amount"); }
    if ( request.getParameter("description") != null )
    { description = request.getParameter("description"); }

// an invoice is generated using the date and time
    Date myDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String invoice = dateFormat.format(myDate);
// a sequence number is randomly generated
    Random generator = new Random();
    int sequence = generator.nextInt(1000);
// a timestamp is generated
    long timeStamp = System.currentTimeMillis()/1000;

//This section uses Java Cryptography functions to generate a fingerprint
    // First, the Transaction key is converted to a "SecretKey" object
    KeyGenerator kg = KeyGenerator.getInstance("HmacMD5");
    SecretKey key = new SecretKeySpec(transactionKey.getBytes(), "HmacMD5");
    // A MAC object is created to generate the hash using the HmacMD5 algorithm
    Mac mac = Mac.getInstance("HmacMD5");
    mac.init(key);
    String inputstring = loginID + "^" + sequence + "^" + timeStamp + "^" + amount + "^";
    byte[] result = mac.doFinal(inputstring.getBytes());
    // Convert the result from byte[] to hexadecimal format
    StringBuffer strbuf = new StringBuffer(result.length * 2);
    for(int i=0; i< result.length; i++)
    {
        if(((int) result[i] & 0xff) < 0x10)
            strbuf.append("0");
        strbuf.append(Long.toString((int) result[i] & 0xff, 16));
    }
    String fingerprint = strbuf.toString();


    //test for sending user info to authorize. Eventually will be based on if the user is logged in or not
    if(amount.equalsIgnoreCase("5"))
    {
        first_name = "John";
        last_name = "Snow";
    }
    else
    {
        first_name = "sandy";
        last_name = "cheeks";
    }
// end of fingerprint generation
%>
    <form method='post' action='<%=url%>' >
    <INPUT type='hidden' name='x_login' value='<%=loginID%>' />
    <INPUT type='hidden' name='x_amount' value='<%=amount%>' />
    <INPUT type='hidden' name='x_description' value='<%=description%>' />
    <INPUT type='hidden' name='x_invoice_num' value='<%=invoice%>' />
    <INPUT type='hidden' name='x_fp_sequence' value='<%=sequence%>' />
    <INPUT type='hidden' name='x_fp_timestamp' value='<%=timeStamp%>' />
    <INPUT type='hidden' name='x_fp_hash' value='<%=fingerprint%>' />
    <INPUT type='hidden' name='x_test_request' value='" + testMode + "' />
    <input type='hidden' name='x_first_name' value='<%=first_name%>' />
    <input type='hidden' name='x_last_name'  value='<%=last_name%>' />
    <INPUT TYPE='hidden' NAME='x_address' VALUE='<%=bill_address%>' />
    <INPUT TYPE='hidden' NAME='x_city' VALUE='<%=bill_city%>' />
    <INPUT TYPE='hidden' NAME='x_state' VALUE='<%=bill_state%>' />
    <INPUT TYPE='hidden' NAME='x_zip' VALUE='<%=bill_zip %>' />
    <INPUT TYPE='hidden' NAME='x_phone' VALUE='<%=phone %>' />
    <INPUT TYPE='hidden' NAME='x_email' VALUE='<%=email%>' />
    <INPUT TYPE='hidden' NAME='x_cust_id' VALUE='<%=customer_id%>' />
    <INPUT TYPE='hidden' NAME='x_ship_to_first_name' VALUE='<%=ship_first_name%>' />
    <INPUT TYPE='hidden' NAME='x_ship_to_last_name' VALUE='<%=ship_last_name%>' />
    <INPUT TYPE='hidden' NAME='x_ship_to_address' VALUE='<%=ship_address %>' />
    <INPUT TYPE='hidden' NAME='x_ship_to_city' VALUE='<%=ship_city%>' />
    <INPUT TYPE='hidden' NAME='x_ship_to_state' VALUE='<%=ship_state%>' />
    <INPUT TYPE='hidden' NAME='x_ship_to_zip' VALUE='<%=ship_zip%>' />
    <INPUT TYPE='hidden' NAME='x_ship_to_country' VALUE='<%=ship_country%>' />
    <INPUT type='hidden' name='x_show_form' value='PAYMENT_FORM' />
    <INPUT TYPE='submit' VALUE="<%=label%>">

    </form>

<!-- This is the end of the code generating the "submit payment" button.    -->
</BODY>
</HTML>

<%--
Things to include:
-JSTL loop that iterates through the number of different items in the cart, and displays them with a remove button for each
-Submission logic that sends the payment cart info to the authorize gateway
-uniquely generated fingerprint using jbcrypt instead of the java crypto library(cannot do, has to be done with HmacMD5)
--%>