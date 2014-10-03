<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.admin.ConditionSqllistServlet" %>
<%@ page import="com.statboost.models.inventory.Condition" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.statboost.controllers.admin.ConditionEditorServlet" %>
<%--
  Created by IntelliJ IDEA.
  User: Jessica
  Date: 9/28/14
  Time: 9:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Condition Sqllist</title>
    <%!
        static Logger logger = Logger.getLogger(ConditionSqllistServlet.class);
    %>
    <%
        ResultSet conditions = (ResultSet) request.getAttribute(ConditionSqllistServlet.ATTR_CONDITIONS);
    %>
    <jsp:include page="/include/HeadTags.jsp"/>
</head>
<body>
<table>
    <%
        if(request.getAttribute(ConditionEditorServlet.ATTR_INFO)  != null)  {
    %>
    <tr>
        <td colspan="2" class="info">
            <%=request.getAttribute(ConditionEditorServlet.ATTR_INFO)%>
        </td>
    </tr>
    <%
        }
    %>
    <tr>
        <td>Name</td>
        <td>Percent of Price</td>
    </tr>
    <%
        if(conditions != null)  {
            try  {
                while(conditions.next())  {
    %>
    <tr>
        <td><a href="<%=ConditionEditorServlet.getEditUrl(conditions.getInt("cnd_uid"))%>"><%=conditions.getString("cnd_name")%></a></td>
        <td><a href="<%=ConditionEditorServlet.getEditUrl(conditions.getInt("cnd_uid"))%>"><%=conditions.getDouble("cnd_percent_of_price")%>%</a></td>
    </tr>
    <%
                }
            } catch (Exception e)  {
                logger.error("Could not loop through the conditions.", e);
            }
        }
    %>
</table>
</body>
</html>
