<%@ page import="com.statboost.controllers.admin.InventoryEditorServlet" %>
<%@ page import="com.statboost.controllers.admin.InventorySqllistServlet" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.statboost.controllers.admin.AdminCPServlet" %>

<jsp:include page="/include/Header.jsp"/>
<%--todo: DO NOT INCLUDE THIS WILL CONFLICT WITH THE INVENTORY SEARCH, JUST ADD A BUTTON THAT TAKES THEM BACK TO THE ADMIN CONTROL PANEL --%>
<%--todo: also there will be no list or pagination jsut a search box--%>
<%--<jsp:include page="/include/Navbar.jsp"/>--%>

<head>
    <%!
        static Logger logger = Logger.getLogger(InventorySqllistServlet.class);
    %>
    <%
        ResultSet inventory = (ResultSet) request.getAttribute(InventorySqllistServlet.ATTR_INVENTORY);
    %>
    <%--<jsp:include page="/include/HeadTags.jsp"/>--%>

    <style>
        .ui-autocomplete {
            max-height: 600px;
            overflow-y: auto;
            /* prevent horizontal scrollbar */
            overflow-x: hidden;
        }
        /* IE 6 doesn't support max-height
         * we use height instead, but this forces the menu to always be this tall
         */
        * html .ui-autocomplete {
            height: 600px;
        }
    </style>

</head>

<form class="navbar-form navbar-left" role="search" method="get" action="/magicSearch">
    <div class="form-group">
        <input type="text" id="search2" name="cardName" class="autocomplete form-control" data-url="/superauto">
    </div>
    &nbsp;<span class="glyphicon glyphicon-search" style="color:white" title="Type a card name to search for."></span>
</form>
<tr>
    <td style="margin-top:10px;"><a href="<%=InventoryEditorServlet.SRV_MAP%>"><input type="button" class="btn btn-primary" value="Add New Inventory" /></a></td>
    <td style="margin-top:10px;"><a href="/admin/adminCP"><input type="button" class="btn btn-primary" value="Return to Admin Control Panel" /></a></td>
</tr>

<jsp:include page="/include/Footer.jsp"/>