package com.statboost.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam Kerr on 8/3/2014.
 */
@WebServlet("/test")
public class SamsTestServlet extends HttpServlet {
    public boolean testBoolean = true;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> words = new ArrayList<>();
        words.add("I");
        words.add("am");
        words.add("job");
        request.setAttribute("words", words);
        request.setAttribute("testBoolean", testBoolean);


        request.getRequestDispatcher("SamsTest.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
