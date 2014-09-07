<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.statboost.controllers.EmailTemplateSqllistServlet" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.EmailTemplateEditorServlet" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Emails</title>
    <%!
        static Logger logger = Logger.getLogger(EmailTemplateSqllistServlet.class);
    %>
    <%
        ResultSet emailTemplates = (ResultSet) request.getAttribute(EmailTemplateSqllistServlet.ATTR_EMAILS_TEMPLATES);
    %>
</head>
<body>
<table>
    <tr>
        <td><a href="<%=EmailTemplateEditorServlet.SRV_MAP%>">New Email</a></td>
    </tr>
    <tr>
        <td>Name</td>
    </tr>
    <%
        if(emailTemplates != null)  {
            try  {
                while(emailTemplates.next())  {
    %>
    <tr>
        <td><a href="<%=EmailTemplateEditorServlet.getEditUrl(emailTemplates.getInt("etm_uid"))%>"><%=emailTemplates.getString("etm_name")%></a></td>
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