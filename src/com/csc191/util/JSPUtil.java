package com.csc191.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JSPUtil {
    public static String nvl(String string)  {
        if(string == null) {
            return "";
        } else  {
            return  string;
        }
    }

    public static String toMySqlDateString(Date date) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        if(date == null) {
            return "null";
        }

        return "'" + dateTimeFormat.format(date) + "'";
    }

    public static Date getDateFromString(String stringDate)  {
        //need to figure out what format this date will be coming in at
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        if(stringDate != null && !stringDate.equals(""))  {
            try  {
                date = dateTimeFormat.parse(stringDate);
                return date;
            } catch (Exception e)  {
                return null;
            }

        }

        return null;
    }

    public static boolean goodDateFormat(String stringDate)  {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yy");
        if(stringDate != null && !stringDate.equals(""))  {
            try  {
                dateTimeFormat.parse(stringDate);
                return true;
            } catch (Exception e)  {
                return false;
            }
        }

        return false;
    }

    public static String formatDate(Date date)  {
        if (date == null) {
            return "";
        }

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        return format.format(date);
    }

}
