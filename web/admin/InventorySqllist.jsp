<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.statboost.controllers.admin.InventoryEditorServlet" %>
<%@ page import="com.statboost.controllers.admin.InventorySqllistServlet" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.statboost.controllers.admin.AdminCPServlet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.statboost.models.inventory.Inventory" %>
<%@ page import="java.util.ArrayList" %>

<jsp:include page="/include/Header.jsp"/>
<%--todo: DO NOT INCLUDE THIS WILL CONFLICT WITH THE INVENTORY SEARCH, JUST ADD A BUTTON THAT TAKES THEM BACK TO THE ADMIN CONTROL PANEL --%>
<%--todo: also there will be no pagination--%>
<%--<jsp:include page="/include/Navbar.jsp"/>--%>

<head>
    <%!
        static Logger logger = Logger.getLogger(InventorySqllistServlet.class);
    %>
</head>
<div class="col-sm-12">
    &nbsp;
</div>
<div class="container-fluid">
    <div class="well well-sm col-sm-12">
        <div class="col-sm-12">
            <form method="get" action="<%=InventorySqllistServlet.SRV_MAP%>">
                <div>
                    <a href="<%=InventoryEditorServlet.SRV_MAP%>"><input type="button" class="btn btn-primary" value="Add New Inventory" /></a>
                    <a href="/admin/adminCP"><input type="button" class="btn btn-primary" value="Return to Admin Control Panel" /></a>
                </div>
                <hr class="col-sm-10" />
                <div>
                    <span><input type="text" name="<%=InventorySqllistServlet.PARAM_KEYWORD%>"></span>
                    <span><input type="submit"></span>
                </div>
                <hr class="col-sm-10" />
                <div class="col-sm-8">
                    <table class="table">
                        <tr>
                            <th>Item Name:</th>
                            <th></th>
                            <th>Description:</th>
                        </tr>
                        <c:forEach items="${requestScope.inventory}" var="inv" varStatus="i">
                            <c:set var="urlp1" value="<%=InventoryEditorServlet.SRV_MAP + '?' + InventoryEditorServlet.PARAM_INVENTORY_UID%>"/>
                            <c:set var="url" value="${urlp1}=${inv.uid}"/>
                            <tr>
                                <td>
                                    <a href="${url}">
                                        ${inv.name}
                                    </a>
                                </td>
                                <td> - </td>
                                <td>
                                    ${inv.description}
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/include/Footer.jsp"/>