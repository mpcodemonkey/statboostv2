<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.statboost.controllers.EmailSqllistServlet" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.EmailEditorServlet" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Emails</title>
    <%!
        static Logger logger = Logger.getLogger(EmailSqllistServlet.class);
    %>
    <%
        ResultSet emails = (ResultSet) request.getAttribute(EmailSqllistServlet.ATTR_EMAILS);
    %>
</head>
<body>
    <table>
        <tr>
            <td colspan="2"><a href="<%=EmailEditorServlet.SRV_MAP%>">New Email</a></td>
        </tr>
        <tr>
            <td>Name</td>
            <td>Subject</td>
        </tr>
        <%
            if(emails != null)  {
                try  {
                    while(emails.next())  {
        %>
        <tr>
            <td><a href="<%=EmailEditorServlet.getEditUrl(emails.getInt("eml_uid"))%>"><%=emails.getString("eml_name")%></a></td>
            <td><a href="<%=EmailEditorServlet.getEditUrl(emails.getInt("eml_uid"))%>"><%=emails.getString("eml_subject")%></a></td>
        </tr>
        <%
                    }
                } catch (Exception e)  {
                    logger.error("Could not loop through the emails.", e);
                }
            }
        %>
    </table>
</body>
</html>