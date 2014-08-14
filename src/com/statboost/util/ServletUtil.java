package com.statboost.util;

import org.apache.log4j.Logger;

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
                    logger.error("Could nto close the connection", e);
                }
            }
        }
        return null;
    }
}
