package com.statboost.controllers;


import com.statboost.models.mtg.MagicCard;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Sam Kerr
 * 4:35 PM on 8/6/2014
 */
@WebServlet("/magicSearch")
public class MagicSearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("MagicSearch.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MagicCard testCard = MagicCard.find("Bogardan Firefiend");
        request.setAttribute("card", testCard);

        request.getRequestDispatcher("MagicSearch.jsp").forward(request, response);
    }

}
