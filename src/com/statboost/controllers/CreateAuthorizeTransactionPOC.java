package com.statboost.controllers;

import org.apache.log4j.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Jessica on 9/7/14.
 */

@WebServlet("/createauthorizetransaction")
public class CreateAuthorizeTransactionPOC extends HttpServlet  {
    static Logger logger = Logger.getLogger(CreateAuthorizeTransactionPOC.class);
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        String url = "https://test.authorize.net/gateway/transact.dll";

        // If an amount or description were posted to this page, the defaults are overidden
        if (request.getParameter("amount") != null){
            amount = request.getParameter("amount");
        }
        if (request.getParameter("description") != null)  {
            description = request.getParameter("description");
        }

        // an invoice number is generated using the date and time to pass over to Authorize
        Date myDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String invoice = dateFormat.format(myDate);

        // a sequence number is randomly generated to use for the fingerprint
        //fingerprint = a unique key for the specific transaction between user and the authorize server
        Random generator = new Random();
        int sequence = generator.nextInt(1000);

        // a timestamp is generated to use for the fingerprint
        long timeStamp = System.currentTimeMillis()/1000;

        //This section uses Java Cryptography functions to generate a fingerprint
        //First, the Transaction key is converted to a "SecretKey" object
        try {
            //specifies the type of encryption to generate the fingerprint
            KeyGenerator kg = KeyGenerator.getInstance("HmacMD5");
            SecretKey key = new SecretKeySpec(transactionKey.getBytes(), "HmacMD5");
            // A MAC object is created to generate the hash using the HmacMD5 algorithm
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(key);
            String inputstring = loginID + "^" + sequence + "^" + timeStamp + "^" + amount + "^";
            byte[] result = mac.doFinal(inputstring.getBytes());

            // Convert the result from byte[] to hexadecimal format
            StringBuffer strbuf = new StringBuffer(result.length * 2);
            for(int i=0; i< result.length; i++)  {
                if(((int) result[i] & 0xff) < 0x10)  {
                    strbuf.append("0");
                }
                strbuf.append(Long.toString((int) result[i] & 0xff, 16));
            }

            String fingerprint = strbuf.toString();

            //test for sending user info to authorize. Eventually will be based on if the user is logged in or not
            if(amount.equalsIgnoreCase("5")) {
                first_name = "John";
                last_name = "Snow";
            } else  {
                first_name = "sandy";
                last_name = "cheeks";
            }

            // end of fingerprint generation
            request.getRequestDispatcher(url + "?x_login=&9W7GGc4kh6B&x_amount=0.01&x_test_request=true&x_fp_hash=" +
                    fingerprint).forward(request, response);
        } catch (Exception e) {
            logger.error("Could not finish the authorize transaction.");
            //todo: put failed code here
        }

//        System.out.println("We are here" + response.);
    }
}
