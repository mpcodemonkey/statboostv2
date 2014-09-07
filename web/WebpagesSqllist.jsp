<%@ page import="java.sql.ResultSet" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.WebpageSqllistServlet" %>
<%@ page import="com.statboost.controllers.WebpageEditorServlet" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Webpages</title>
    <%!
        static Logger logger = Logger.getLogger(WebpageSqllistServlet.class);
    %>
    <%
        ResultSet webpages = (ResultSet) request.getAttribute(WebpageSqllistServlet.ATTR_WEBPAGES);
    %>
</head>
<body>
<table>
    <tr>
        <td><a href="<%=WebpageEditorServlet.SRV_MAP%>">New Email</a></td>
    </tr>
    <tr>
        <td>Name</td>
    </tr>
    <%
        if(webpages != null)  {
            try  {
                while(webpages.next())  {
    %>
    <tr>
        <td><a href="<%=WebpageEditorServlet.getEditUrl(webpages.getInt("wbp_uid"))%>"><%=webpages.getString("wbp_name")%></a></td>
    </tr>
    <%
                }
            } catch (Exception e)  {
                logger.error("Could not loop through the webpages.", e);
            }
        }
    %>
</table>
</body>
</html>