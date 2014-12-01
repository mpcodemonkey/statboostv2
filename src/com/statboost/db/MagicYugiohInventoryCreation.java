package com.statboost.db;

import com.statboost.models.inventory.Inventory;
import com.statboost.models.mtg.MagicCard;
import com.statboost.util.HibernateUtil;
import com.statboost.util.ServletUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.*;
import java.util.Properties;

/**
 * Created by Jessica on 10/26/14.
 */
public class MagicYugiohInventoryCreation {
    static Logger logger = Logger.getLogger(MagicYugiohInventoryCreation.class);
    public static void main(String args[])  {
        Connection connection = null;
        Properties connectionProperties = new Properties();
        ResultSet rs = null;
        try   {
            connection = DriverManager.getConnection("jdbc:mysql://107.138.65.186:3306/statboost?characterEncoding=UTF-8", "generic", "generic11PASSWORD");
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("select * from stt_magic_card");
        } catch (Exception e)  {
            logger.error("Could not get the result set.", e);
        }

        if(rs != null)  {
            try {
                while(rs.next())  {
                    Inventory inventory = new Inventory();
                    inventory.setName(rs.getString("mcr_card_name"));
                    //todo: fill in other fields you feel are necessary here
                    //todo: discuss the need to get the session every time.
                    SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
                    Session session = sessionFactory.openSession();
                    session.beginTransaction();
                    MagicCard magicCard = (MagicCard) session.load(MagicCard.class, inventory.getMagicCard().getMcrUid());
                    inventory.setMagicCard(magicCard);
                    session.getTransaction().commit();
                    session.close();
                }
            } catch (SQLException e) {
                logger.error("Could not loop through the magic cards.", e);
            }
        }

        //todo: repeat but for Yo-ug-ioh
    }
}
