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
                        <%    if(inventory != null)  {
                                while(inventory.next())  {
                        %>
                        <tr>
                            <td>
                                <a href="<%=InventoryEditorServlet.getEditUrl(inventory.getInt("inv_uid"))%>">
                                    <%=inventory.getString("inv_name")%>
                                </a>
                            </td>
                            <td> - </td>
                            <td>
                                <%=inventory.getString("inv_description")%>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                    </table>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/include/Footer.jsp"/>