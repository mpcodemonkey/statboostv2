package com.statboost.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sam Kerr on 10/3/2014.
 */
@WebServlet("/paymentResult")
public class PaymentResultServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters.isEmpty()) {
            response.sendRedirect("/");
        } else {
            Set keys = parameters.keySet();
            String result = "";
            for (Object o : keys) {
                String key = (String)o;
                result += key + "=" + Arrays.toString(parameters.get(key)).replace("^\\[*|\\]*", "") + "\n";
            }

            request.setAttribute("alert", result);
            request.setAttribute("alertType", "warning");
            request.getRequestDispatcher("/PaymentResult.jsp").forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters.isEmpty()) {
            response.sendRedirect("/");
        } else {
            Set keys = parameters.keySet();
            String result = "";
            for (Object o : keys) {
                String key = (String)o;
                result += key + "=" + Arrays.toString(parameters.get(key)).replace("^\\[*|\\]*", "") + "\n";
            }

            request.setAttribute("alert", result);
            request.setAttribute("alertType", "warning");
            request.getRequestDispatcher("PaymentResult.jsp").forward(request, response);
        }
    }

}
