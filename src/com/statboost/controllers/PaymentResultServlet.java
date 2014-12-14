package com.statboost.controllers;

import com.statboost.models.actor.User;
import com.statboost.models.inventory.Cost;
import com.statboost.models.session.ShoppingCartSessionObject;
import com.statboost.util.OrderManager;
import net.authorize.ResponseField;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sam Kerr on 10/3/2014.
 */
@WebServlet("/paymentResult")
public class PaymentResultServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(OrderManager.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean issueOccurred = false;
        String responseCode = request.getParameter(ResponseField.RESPONSE_CODE.getFieldName());
        /**
         * Response Code - Description
         *  1 - This transaction has been approved.
         *  2 - This transaction has been declined.
         *  3 - There has been an error processing this transaction.
         *  4 - This transaction is being held for review.
         */
        //if response code is not 1, then transaction was not approved.
        if (responseCode == null || !responseCode.equals("1")) {
            issueOccurred = true;
        }


        if (issueOccurred) {
            String responseReason = request.getParameter(ResponseField.RESPONSE_REASON_TEXT.getFieldName());
            request.setAttribute("alert", responseReason);
            request.setAttribute("alertType", "danger");
            request.getRequestDispatcher("/PaymentResult.jsp").forward(request, response);
        } else {
            /**
             * The transaction was successful
             */
            HttpSession session = request.getSession();

            //get transaction details
            String transactionID = request.getParameter(ResponseField.TRANSACTION_ID.getFieldName());
            String amount = request.getParameter(ResponseField.AMOUNT.getFieldName());
            String cardType = request.getParameter(ResponseField.CARD_TYPE.getFieldName());
            String acctNumber = request.getParameter(ResponseField.ACCOUNT_NUMBER.getFieldName());

            request.setAttribute("transactionID", transactionID);
            request.setAttribute("amount", amount);
            request.setAttribute("cardType", cardType);
            request.setAttribute("acctNumber", acctNumber);

            //get contact information
            String contactEmail = request.getParameter(ResponseField.EMAIL_ADDRESS.getFieldName());
            String contactFirstName = request.getParameter(ResponseField.FIRST_NAME.getFieldName());
            String contactLastName = request.getParameter(ResponseField.LAST_NAME.getFieldName());

            //get shipping details
            String inStorePickup = request.getParameter("inStorePickup");
            inStorePickup = inStorePickup.equalsIgnoreCase("No") ? "false" : "true";
            String shippingMethod = "null"; //TODO: obtain shipping method
            String shipAddress = request.getParameter(ResponseField.SHIP_TO_ADDRESS.getFieldName());
            String shipCity = request.getParameter(ResponseField.SHIP_TO_CITY.getFieldName());
            String shipState = request.getParameter(ResponseField.SHIP_TO_STATE.getFieldName());
            String shipZip = request.getParameter(ResponseField.SHIP_TO_ZIP_CODE.getFieldName());

            //get order totals
            String orderTotal = amount;
            String taxTotal = request.getParameter(ResponseField.TAX.getFieldName());
            String shippingTotal = request.getParameter(ResponseField.FREIGHT.getFieldName());

            //set orderParams for creating order
            Map<String, String> orderParams = new HashMap<>();
            orderParams.put("transactionId", transactionID);
            orderParams.put("contactEmail", contactEmail);
            orderParams.put("contactFirstName", contactFirstName);
            orderParams.put("contactLastName", contactLastName);
            orderParams.put("orderTotal", orderTotal);
            orderParams.put("shippingTotal", shippingTotal);
            orderParams.put("taxTotal", taxTotal);
            orderParams.put("inStorePickup", inStorePickup);
            orderParams.put("shippingMethod", shippingMethod);
            orderParams.put("shippingAddress", shipAddress);
            orderParams.put("shippingCity", shipCity);
            orderParams.put("shippingState", shipState);
            orderParams.put("shippingZip", shipZip);

            //create the order
            User user = User.find((String)session.getAttribute("email"));
            ShoppingCartSessionObject shoppingCart = (ShoppingCartSessionObject)session.getAttribute("shoppingCart");
            Integer orderID = null;
            try {
               orderID = OrderManager.createOrder(user, shoppingCart, orderParams);
            } catch (Exception e) {
                logger.setLevel(Level.DEBUG);
                logger.debug("Something terrible happened when creating the order for transaction #" + transactionID + ".");
            }
            request.setAttribute("orderID", orderID);

            //decrement cost table, items have been purchased.
            Cost.decrementCosts(shoppingCart);

            //session clean up
            session.removeAttribute("shoppingCart");
            session.removeAttribute("cartTotals");
            session.removeAttribute("itemsInCart");
            session.removeAttribute("orderTotal");



            //set the response alert and direct to payment result page
            String responseReason = request.getParameter(ResponseField.RESPONSE_REASON_TEXT.getFieldName());
            request.setAttribute("alert", responseReason);
            request.setAttribute("alertType", "success");
            request.getRequestDispatcher("/PaymentResult.jsp").forward(request, response);
        }

    }

}
