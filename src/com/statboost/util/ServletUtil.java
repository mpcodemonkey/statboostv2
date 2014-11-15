package com.statboost.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
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
            connection = DriverManager.getConnection("jdbc:mysql://104.131.189.31:3306/statboost?characterEncoding=UTF-8", "generic", "generic11PASSWORD");
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

    //todo: needs to be tested
    public static Date getDateFromString(String dateString)  {
        try {
            return new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
        } catch (ParseException e) {
            logger.error("Could not turn the date into a string", e);
            return null;
        }
    }

    //todo: needs to be tested
    public static Date getDateTimeFromString(String dateString)  {
        try {
            return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").parse(dateString);
        } catch (ParseException e) {
            logger.error("Could not turn the date into a string", e);
            return null;
        }
    }


    public static String formatCurrency(Double amount)  {
        if(amount == null)  {
            return null;
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(amount).replace("$", "");
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

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }


    /**
     * Get client's IP Address
     * @param request
     * @return - String
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }



}
