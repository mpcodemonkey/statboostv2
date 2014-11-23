<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.admin.InventorySqllistServlet" %>
<%@ page import="com.statboost.controllers.admin.InventoryEditorServlet" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<head>
    <%!
        static Logger logger = Logger.getLogger(InventorySqllistServlet.class);
    %>
    <%
        ResultSet inventory = (ResultSet) request.getAttribute(InventorySqllistServlet.ATTR_INVENTORY);
    %>
    <jsp:include page="/include/HeadTags.jsp"/>
</head>
    <div class="container-fluid">
        <div class="well well-sm col-sm-12">
            <div class="col-sm-12">
                <h3 class="col-sm-12">Inventory Listing</h3>
                <hr class="col-sm-10" />
                <div class="col-sm-2"><a href="<%=InventoryEditorServlet.SRV_MAP%>"><input type="button" class="btn btn-primary" value="Add New Inventory" /></a></div>
            </div>
            <hr class="col-sm-10" />
            <div class="col-sm-12">
                <table class="table">
                    <tbody>
                        <%--todo: pagination--%>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                        </tr>
                        <%
                            if(inventory != null)  {
                                try  {
                                    while(inventory.next())  {
                        %>
                        <tr>
                            <td><a href="<%=InventoryEditorServlet.getEditUrl(inventory.getInt("inv_uid"))%>"><%=inventory.getString("inv_name")%></a></td>
                            <td><%=inventory.getString("inv_description")%></td>
                        </tr>
                        <%
                                    }
                                } catch (Exception e)  {
                                    logger.error("Could not loop through the emails.", e);
                                }
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

<jsp:include page="/include/Footer.jsp"/>