package com.statboost.controllers;

import com.statboost.models.enumType.ItemCondition;
import com.statboost.models.inventory.Cost;
import com.statboost.models.inventory.Inventory;
import com.statboost.models.mtg.MagicCard;
import com.statboost.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by Jon on 10/27/2014.
 */
@WebServlet("/dbmod")
public class DBModServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(DBModServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("dbmod.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        List<MagicCard> resultSet = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM MagicCard");
            resultSet = q.list();
        } catch (Exception e) {
            logger.error("Could not get the result set.", e);
        }

        if (resultSet != null) {

            try {
                Random r = new Random();
                for (int j = 0; j < resultSet.size(); j++) {
                    Inventory inventory = new Inventory();
                    inventory.setName(resultSet.get(j).getMcrCardName());
                    inventory.setImage(resultSet.get(j).getMcrImageName());
                    //generate random card costs

                    inventory.setMagicCard(resultSet.get(j));

                    session.save(inventory);

                    System.out.println("Inserted " + inventory.getName() + " into db");
                }
                tx.commit();
                session.close();
            } catch (Exception e) {
                logger.error("Could not loop through the magic cards.");
            }
        }

        session = sessionFactory.openSession();
        List<Inventory> iSet = null;
        tx = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("from Inventory");
            iSet = q.list();
        } catch (Exception e) {
            logger.error("bad juju bra");
        }
        if (iSet != null) {
            try {
                Random r = new Random();
                for (int j = 0; j < iSet.size(); j++) {
                    Cost cost0 = new Cost();
                    cost0.setItemPrice((r.nextInt(300) + 1) / 100.0);
                    cost0.setItemQuantity(r.nextInt(5) + 1);
                    cost0.setInvUid(iSet.get(j).getUid());
                    cost0.setInventory(iSet.get(j));
                    cost0.setItemCondition(ItemCondition.NEAR_MINT);
                    session.save(cost0);

                    Cost cost1 = new Cost();
                    cost1.setItemPrice((r.nextInt(300) + 1) / 100.0);
                    cost1.setItemQuantity(r.nextInt(5) + 1);
                    cost1.setInvUid(iSet.get(j).getUid());
                    cost1.setInventory(iSet.get(j));
                    cost1.setItemCondition(ItemCondition.NEAR_MINT);
                    session.save(cost1);

                    Cost cost2 = new Cost();
                    cost2.setItemPrice((r.nextInt(300) + 1) / 100.0);
                    cost2.setItemQuantity(r.nextInt(5) + 1);
                    cost2.setInvUid(iSet.get(j).getUid());
                    cost2.setInventory(iSet.get(j));
                    cost2.setItemCondition(ItemCondition.LIGHTLY_PLAYED);
                    session.save(cost2);

                    Cost cost3 = new Cost();
                    cost3.setItemPrice((r.nextInt(300) + 1) / 100.0);
                    cost3.setItemQuantity(r.nextInt(5) + 1);
                    cost3.setInvUid(iSet.get(j).getUid());
                    cost3.setInventory(iSet.get(j));
                    cost3.setItemCondition(ItemCondition.MODERATELY_PLAYED);
                    session.save(cost3);

                    Cost cost4 = new Cost();
                    cost4.setItemPrice((r.nextInt(300) + 1) / 100.0);
                    cost4.setItemQuantity(r.nextInt(5) + 1);
                    cost4.setInvUid(iSet.get(j).getUid());
                    cost4.setInventory(iSet.get(j));
                    cost4.setItemCondition(ItemCondition.HEAVILY_PLAYED);
                    session.save(cost4);

                    Cost cost5 = new Cost();
                    cost5.setItemPrice((r.nextInt(300) + 1) / 100.0);
                    cost5.setItemQuantity(r.nextInt(5) + 1);
                    cost5.setInvUid(iSet.get(j).getUid());
                    cost5.setInventory(iSet.get(j));
                    cost5.setItemCondition(ItemCondition.DAMAGED);
                    session.save(cost5);

                    System.out.println("Generated costs for: " + iSet.get(j).getName());

                }
                tx.commit();
                session.close();
            } catch (Exception e) {
                logger.error("Could not loop through the magic cards.");
            }


            //todo: repeat but for Yo-ug-ioh
        }
    }
}