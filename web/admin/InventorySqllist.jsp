<%@ page import="com.statboost.controllers.admin.InventoryEditorServlet" %>
<%@ page import="com.statboost.controllers.admin.InventorySqllistServlet" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.sql.ResultSet" %>

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
        <input type="text" id="search" name="cardName" class="autocomplete form-control" data-url="/superauto">
    </div>
    &nbsp;<span class="glyphicon glyphicon-search" style="color:white" title="Type a card name to search for."></span>
</form>

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