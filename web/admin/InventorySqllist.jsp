<%@ page import="com.statboost.controllers.admin.InventoryEditorServlet" %>
<%@ page import="com.statboost.controllers.admin.InventorySqllistServlet" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.statboost.controllers.admin.AdminCPServlet" %>

<jsp:include page="/include/Header.jsp"/>
<%--todo: DO NOT INCLUDE THIS WILL CONFLICT WITH THE INVENTORY SEARCH, JUST ADD A BUTTON THAT TAKES THEM BACK TO THE ADMIN CONTROL PANEL --%>
<%--todo: also there will be no pagination--%>
<%--<jsp:include page="/include/Navbar.jsp"/>--%>

<head>
    <%!
        static Logger logger = Logger.getLogger(InventorySqllistServlet.class);
    %>
    <%
        ResultSet inventory = (ResultSet) request.getAttribute(InventorySqllistServlet.ATTR_INVENTORY);
    %>
</head>

<form method="get" action="<%=InventorySqllistServlet.SRV_MAP%>">
    <table>
        <tr>
            <td style="margin-top:10px;"><a href="<%=InventoryEditorServlet.SRV_MAP%>"><input type="button" class="btn btn-primary" value="Add New Inventory" /></a></td>
            <td style="margin-top:10px;"><a href="/admin/adminCP"><input type="button" class="btn btn-primary" value="Return to Admin Control Panel" /></a></td>
        </tr>
        <tr>
            <td><input type="text" name="<%=InventorySqllistServlet.PARAM_KEYWORD%>"></td>
            <td><input type="submit"></td>
        </tr>
    <%    if(inventory != null)  {
            while(inventory.next())  {
    %>
    <tr>
        <td><a href="<%=InventoryEditorServlet.getEditUrl(inventory.getInt("inv_uid"))%>"><%=inventory.getString("inv_name")%></a></td>
        <td><a href="<%=InventoryEditorServlet.getEditUrl(inventory.getInt("inv_uid"))%>"><%=inventory.getString("inv_description")%></a></td>
    </tr>
    <%
            }
        }
    %>
    </table>
</form>

<jsp:include page="/include/Footer.jsp"/>