<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.statboost.controllers.admin.InventoryCategorySqllistServlet" %>
<%@ page import="com.statboost.controllers.admin.InventoryCategoryEditorServlet" %>
<%--
  Created by IntelliJ IDEA.
  User: Jessica
  Date: 11/15/14
  Time: 1:17 PM
  To change this template use File | Settings | File Templates.
--%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Inventory Category</title>
    <%!
        static Logger logger = Logger.getLogger(InventoryCategorySqllistServlet.class);
    %>
    <%
        ResultSet category = (ResultSet) request.getAttribute(InventoryCategorySqllistServlet.ATTR_INVENTORY_CATEGORY);
    %>
    <jsp:include page="/include/HeadTags.jsp"/>
</head>
<body>
<table>
    <tr>
        <td><a href="<%=InventoryCategoryEditorServlet.SRV_MAP%>">New Category</a></td>
    </tr>
    <tr>
        <td>Name</td>
        <td>Subject</td>
    </tr>
    <%
        if(category != null)  {
            try  {
                while(category.next())  {
    %>
    <tr>
        <td><a href="<%=InventoryCategoryEditorServlet.getEditUrl(category.getInt("cat_uid"))%>"><%=category.getString("category")%></a></td>
    </tr>
    <%
                }
            } catch (Exception e)  {
                logger.error("Could not loop through the categories.", e);
            }
        }
    %>
</table>
</body>
</html>
