<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.admin.InventorySqllistServlet" %>
<%@ page import="com.statboost.controllers.admin.InventoryEditorServlet" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Inventory</title>
    <%!
        static Logger logger = Logger.getLogger(InventorySqllistServlet.class);
    %>
    <%
        ResultSet inventory = (ResultSet) request.getAttribute(InventorySqllistServlet.ATTR_INVENTORY);
    %>
    <jsp:include page="/include/HeadTags.jsp"/>
</head>
<body>
<table>
    <%--todo: pagination--%>
    <tr>
        <td>Name</td>
        <td>Subject</td>
    </tr>
    <%
        if(inventory != null)  {
            try  {
                while(inventory.next())  {
    %>
    <tr>
        <td><a href="<%=InventoryEditorServlet.getEditUrl(inventory.getInt("inv_uid"))%>"><%=inventory.getString("inv_name")%></a></td>
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