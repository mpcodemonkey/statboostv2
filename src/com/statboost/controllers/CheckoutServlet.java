package com.statboost.controllers;

import net.authorize.sim.Fingerprint;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Sam on 9/29/2014.
 */
@WebServlet("/pay")
public class CheckoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //populate attributes with user's checkout data


        request.getRequestDispatcher("Checkout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO:validate user has session open with an active cart/order

        //DPM to Authorize
        String apiLoginId = "8qLJKr37W"; //Authorize API Login ID
        String transactionKey = "9Rw9M2K8UsgSu28x"; //Authorize Transaction Key
        String relayResponseUrl = "http://teamjjacs.us/paymentResponse";
        String amount = "102.21";

        Random rdm = new Random();
        Fingerprint fingerprint = Fingerprint.createFingerprint(apiLoginId, transactionKey, rdm.nextLong(), amount);

        long x_fp_sequence = fingerprint.getSequence();
        long x_fp_timestamp = fingerprint.getTimeStamp();
        String x_fp_hash = fingerprint.getFingerprintHash();
        String x_version = "3.1";
        String x_method = "CC";
        String x_type = "AUTH_CAPTURE";

        String x_test_request = "FALSE";
        String notes = "";
        String x_invoice_num = ""+System.currentTimeMillis(); //research if this is ok?

        //TODO: pass data off to authorize and prepare for response



    }
}
