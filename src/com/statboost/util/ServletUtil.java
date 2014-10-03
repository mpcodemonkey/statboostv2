package com.statboost.util;

import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class ServletUtil {
    static Logger logger = Logger.getLogger(ServletUtil.class);

    public static ResultSet getResultSetFromSql(String sql)  {
        Connection connection = null;
        Properties connectionProperties = new Properties();
        ResultSet rs = null;
        try   {
            connection = DriverManager.getConnection("jdbc:mysql://107.138.64.59:3306/statboost?characterEncoding=UTF-8", "generic", "generic11PASSWORD");
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            return rs;
        } catch (Exception e)  {
            logger.error("Could not get the result set.", e);
        }
        return null;
    }

    public static boolean isEmailPattern(String possibleEmail)  {
        return possibleEmail.matches("[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}");
    }

    //checks if a string is null and if it is replaces it with a blank string so null isn't shown to the user
    public static String hideNulls(String stringToCheck)  {
        if(stringToCheck == null)  {
            return "";
        }

        return stringToCheck;
    }

    public static Date getCurrentDate()  {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String formatDate(Date date)  {
        return new SimpleDateFormat("MM/dd/yyyy").format(date);
    }

    public static String formatDateTime(Date date)  {
        return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(date);
    }

    /**
     * The purpose of this method is to strip out wildcards or any other malicious characters from the given string.
     * @param dirty
     * @return clean
     */
    public static String sanitizeWildcard(String dirty) {
        String clean = dirty.replaceAll("%", "");
        if (!clean.equals("")) {
            clean = "%" + clean + "%";
        }
        return clean;
    }

    /**
     * Get a thrown exception stack trace returned as a String. Can be useful for server logging or debugging purposes.
     * @param t - Throwable
     * @return - stack trace string
     */
    public static String getStackStraceString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

}
