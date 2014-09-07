package com.statboost.util;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.*;
import java.util.Properties;

public class ServletUtil {
    static Logger logger = Logger.getLogger(ServletUtil.class);

    public static ResultSet getResultSetFromSql(String sql)  {
        Connection connection = null;
        Properties connectionProperties = new Properties();
        ResultSet rs = null;
        try   {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/statboost", "root", "");
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            return rs;
        } catch (Exception e)  {
            logger.error("Could not get the result set", e);
        } finally  {
            //todo: closing it here may cause issues
            if(connection != null)  {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Could not close the connection", e);
                }
            }
        }
        return null;
    }

    public static Object getObjectFromSql(String sql)  {
        SessionFactory sessionFactory = HibernateUtil.getMTGSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(sql);
        return query.list();
    }

    public static boolean isEmailPattern(String possibleEmail)  {
        return possibleEmail.matches("[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}");
    }

   public static void saveObject(Object objectToSave)  {
       SessionFactory sessionFactory = HibernateUtil.getMTGSessionFactory();
       Session session = sessionFactory.openSession();
       session.beginTransaction();
       //todo: make sure that this will save without knowing the specific class, if not, fix
       session.save(objectToSave);
       session.getTransaction().commit();
       session.close();
       sessionFactory.close();
   }

}
