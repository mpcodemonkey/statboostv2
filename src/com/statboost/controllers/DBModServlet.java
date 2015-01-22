package com.statboost.controllers;

import com.statboost.models.enumType.ItemCondition;
import com.statboost.models.inventory.*;
import com.statboost.models.mtg.MagicCard;
import com.statboost.models.ygo.YugiohCard;
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
                Query qCats = session.createQuery("From Category where category = :m or category = :s");
                qCats.setParameter("m", "Magic");
                qCats.setParameter("s", "Single");
                List<Category> cats = qCats.list();
                for (int j = 0; j < resultSet.size(); j++) {
                    Inventory inventory = new Inventory();
                    Inventory foil = new Inventory();

                    inventory.setName(resultSet.get(j).getMcrCardName());
                    inventory.setImage(resultSet.get(j).getMcrImageName());
                    inventory.setDescription("MTG - " + resultSet.get(j).getMcrSetId());

                    foil.setName(resultSet.get(j).getMcrCardName());
                    foil.setImage(resultSet.get(j).getMcrImageName());
                    foil.setDescription("MTG - " + resultSet.get(j).getMcrSetId());
                    byte b = 1;
                    foil.setInvFoil(b);
                    //generate random card costs

                    inventory.setMagicCard(resultSet.get(j));
                    foil.setMagicCard(resultSet.get(j));

                    session.save(inventory);
                    session.save(foil);

                    for(Category c: cats){
                        InventoryCategory ic = new InventoryCategory();
                        InventoryCategory ic2 = new InventoryCategory();
                        ic.setInvUid(inventory.getUid());
                        ic.setCatUid(c.getCatUid());
                        ic2.setInvUid(foil.getUid());
                        ic2.setCatUid(c.getCatUid());
                        session.save(ic);
                        session.save(ic2);
                    }

                    System.out.println("Inserted " + inventory.getName() + " into db, as well as foil");
                }
                tx.commit();
                session.close();
            } catch (Exception e) {
                logger.error("Could not loop through the magic cards.");
            }
        }

            session = sessionFactory.openSession();
            List<YugiohCard> ySet = null;

            try {
                tx = session.beginTransaction();
                Query q = session.createQuery("Select y from YugiohCard y inner join fetch y.yugiohSet");
                ySet = q.list();
            } catch (Exception e) {
                logger.error("Could not get the result set.", e);
            }

            if (resultSet != null) {

                try {
                    Query qCats = session.createQuery("From Category where category = :y or category = :s");
                    qCats.setParameter("y", "YuGiOh");
                    qCats.setParameter("s", "Single");
                    List<Category> cats = qCats.list();
                    Random r = new Random();
                    for (int j = 0; j < ySet.size(); j++) {
                        Inventory inventory = new Inventory();
                        Inventory holo = new Inventory();
                        inventory.setName(ySet.get(j).getYcrName());
                        inventory.setImage(ySet.get(j).getYugiohSet().getYstPathName() + '/' + ySet.get(j).getYcrImageName());
                        inventory.setDescription("YGO");
                        holo.setName(ySet.get(j).getYcrName());
                        holo.setImage(ySet.get(j).getYcrImageName());
                        holo.setDescription("YGO");
                        //generate random card costs

                        inventory.setYugiohCard(ySet.get(j));
                        holo.setYugiohCard(ySet.get(j));

                        byte b = 1;
                        holo.setInvFoil(b);

                        session.save(inventory);
                        session.save(holo);

                        for(Category c: cats){
                            InventoryCategory ic = new InventoryCategory();
                            InventoryCategory ic2 = new InventoryCategory();
                            ic.setInvUid(inventory.getUid());
                            ic.setCatUid(c.getCatUid());
                            ic2.setInvUid(holo.getUid());
                            ic2.setCatUid(c.getCatUid());
                            session.save(ic);
                        }

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
                    cost0.setItemPrice((r.nextInt(3000) + 1) / 100.0);
                    cost0.setItemQuantity(r.nextInt(50) + 1);
                    cost0.setInvUid(iSet.get(j).getUid());
                    cost0.setInventory(iSet.get(j));
                    cost0.setItemCondition(ItemCondition.NEW);
                    session.save(cost0);

                    Cost cost1 = new Cost();
                    cost1.setItemPrice((r.nextInt(2500) + 1) / 100.0);
                    cost1.setItemQuantity(r.nextInt(25) + 1);
                    cost1.setInvUid(iSet.get(j).getUid());
                    cost1.setInventory(iSet.get(j));
                    cost1.setItemCondition(ItemCondition.NEAR_MINT);
                    session.save(cost1);

                    Cost cost2 = new Cost();
                    cost2.setItemPrice((r.nextInt(2000) + 1) / 100.0);
                    cost2.setItemQuantity(r.nextInt(20) + 1);
                    cost2.setInvUid(iSet.get(j).getUid());
                    cost2.setInventory(iSet.get(j));
                    cost2.setItemCondition(ItemCondition.LIGHTLY_PLAYED);
                    session.save(cost2);

                    Cost cost3 = new Cost();
                    cost3.setItemPrice((r.nextInt(1500) + 1) / 100.0);
                    cost3.setItemQuantity(r.nextInt(15) + 1);
                    cost3.setInvUid(iSet.get(j).getUid());
                    cost3.setInventory(iSet.get(j));
                    cost3.setItemCondition(ItemCondition.MODERATELY_PLAYED);
                    session.save(cost3);

                    Cost cost4 = new Cost();
                    cost4.setItemPrice((r.nextInt(1000) + 1) / 100.0);
                    cost4.setItemQuantity(r.nextInt(10) + 1);
                    cost4.setInvUid(iSet.get(j).getUid());
                    cost4.setInventory(iSet.get(j));
                    cost4.setItemCondition(ItemCondition.HEAVILY_PLAYED);
                    session.save(cost4);

                    Cost cost5 = new Cost();
                    cost5.setItemPrice((r.nextInt(500) + 1) / 100.0);
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
        }
        List<Event> eventList = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Event");
            eventList = q.list();
        } catch (Exception e) {
            logger.error("Could not get the result set.", e);
        }
        if(eventList != null){
            try {
                Random r = new Random();
                for (int j = 0; j < eventList.size(); j++) {
                    Inventory inventory = new Inventory();

                    inventory.setName(eventList.get(j).getTitle());
                    inventory.setDescription(eventList.get(j).getDescription());

                    inventory.setEvent(eventList.get(j));

                    session.save(inventory);

                    Cost cost0 = new Cost();
                    cost0.setItemPrice((r.nextInt(3000) + 1) / 100.0);
                    cost0.setItemQuantity(r.nextInt(50) + 1);
                    cost0.setInvUid(inventory.getUid());
                    cost0.setInventory(inventory);
                    cost0.setItemCondition(ItemCondition.NEW);
                    session.save(cost0);

                    System.out.println("Inserted " + inventory.getName() + " into db, as well as foil");
                }
                tx.commit();
                session.close();
            } catch (Exception e) {
                logger.error("Could not loop through the magic cards.");
            }
        }
    }
}