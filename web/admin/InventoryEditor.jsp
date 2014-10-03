<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.admin.InventoryEditorServlet" %>
<%@ page import="static com.statboost.controllers.admin.InventoryEditorServlet.*" %>
<%@ page import="com.statboost.models.inventory.Inventory" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.statboost.models.mtg.MagicCard" %>
<%@ page import="com.statboost.models.ygo.YugiohCard" %>
<%@ page import="com.statboost.models.inventory.Event" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.statboost.controllers.admin.InventorySqllistServlet" %>
<%@ page import="com.statboost.util.ServletUtil" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Inventory</title>
    <%!
        static Logger logger = Logger.getLogger(InventoryEditorServlet.class);
    %>
    <%
        Inventory inventory = (Inventory) request.getAttribute(InventoryEditorServlet.ATTR_INVENTORY);
        MagicCard magicCard = (MagicCard) request.getAttribute(InventoryEditorServlet.ATTR_MAGIC_CARD);
        YugiohCard yugiohCard = (YugiohCard) request.getAttribute(InventoryEditorServlet.ATTR_YUGIOH_CARD);
        Event event = (Event) request.getAttribute(InventoryEditorServlet.ATTR_EVENT);
    %>
    <jsp:include page="/include/HeadTags.jsp"/>
    <script type="text/javascript">
        function switchType()  {
            if(jQuery('.type').val() == 'YUGIOH')  {
                jQuery('#yugiohDiv').show();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').hide();
            } else if(jQuery('.type').val() == 'MAGIC')  {
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').show();
                jQuery('#eventDiv').hide();
            } else if(jQuery('.type').val() == 'EVENT')  {
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').show();
            } else  {
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').hide();
            }
        }
    </script>
</head>
<body>
<form method="post" action="<%=InventoryEditorServlet.SRV_MAP%>">
<table>
    <input type="hidden" name="<%=InventoryEditorServlet.PARAM_INVENTORY_UID%>" value="<%=inventory.getUid()%>">
    <input type="hidden" name="<%=InventoryEditorServlet.PARAM_MAGIC_CARD_UID%>" value="<%=magicCard != null? magicCard.getMcrUid(): null%>">
    <input type="hidden" name="<%=InventoryEditorServlet.PARAM_YUGIOH_UID%>" value="<%=yugiohCard != null? yugiohCard.getYcrUid(): null%>">
    <input type="hidden" name="<%=InventoryEditorServlet.PARAM_EVENT_UID%>" value="<%=event != null? event.getUid(): null%>">
    <%
        if(request.getAttribute(InventoryEditorServlet.ATTR_INFO)  != null)  {
    %>
    <tr>
        <td colspan="2" class="info">
            <%=request.getAttribute(InventoryEditorServlet.ATTR_INFO)%>
        </td>
    </tr>
    <%
        }
    %>
    <%
        if(request.getAttribute(InventoryEditorServlet.ATTR_ERRORS) != null)  {
            ArrayList<String> errors = (ArrayList<String>) request.getAttribute(InventoryEditorServlet.ATTR_ERRORS);
            for(String currentError : errors)  {
    %>
    <tr>
        <td colspan="2" class="error">
            <%=currentError%>
        </td>
    </tr>
    <%
            }
        }
    %>

    <%
        if(request.getAttribute(InventoryEditorServlet.ATTR_WARNING) != null)  {
            ArrayList<String> warnings = (ArrayList<String>) request.getAttribute(InventoryEditorServlet.ATTR_WARNING);
            for(String currentWarning : warnings)  {
    %>
    <tr>
        <td colspan="2" class="warning">
            <%=currentWarning%>
        </td>
    </tr>
    <%
            }
        }
    %>
    <tr>
        <td>Inventory</td>
        <td><input type="submit"> &nbsp; <a href="<%=InventorySqllistServlet.SRV_MAP%>">Close</a></td></td>
    </tr>
    <tr>
        <td colspan="2"><input type="checkbox" <%=inventory.isPreOrder()? "checked=\"checked\"" : ""%>>&nbsp;Pre Order</td>
    </tr>
    <tr>
        <td>Name</td>
        <td><input type="text" name="<%=PARAM_INVENTORY_NAME%>" value="<%=inventory.getName()%>"></td>
    </tr>
    <tr>
        <td>Price</td>
        <td><input type="text" name="<%=PARAM_INVENTORY_PRICE%>" value="$<%=ServletUtil.formatCurrency(request.getParameter(PARAM_INVENTORY_PRICE))%>"></td>
    </tr>
    <%--todo: research how we are going to do image--%>
    <tr>
        <td>Image</td>
        <td><input type="text" name="<%=PARAM_INVENTORY_IMAGE%>" value="<%=inventory.getImage()%>"></td>
    </tr>
    <tr>
        <td>Description</td>
        <td><textarea name="<%=PARAM_INVENTORY_DESCRIPTION%>"><%=inventory.getDescription()%></textarea></td>
    </tr>
    <tr>
        <td colspan="2">
            <input class="type" type="radio" onclick="switchType(); return false;" name="<%=PARAM_TYPE%>" value="GENERIC"> Generic
            <input class="type" type="radio" onclick="switchType(); return false;" name="<%=PARAM_TYPE%>" value="MAGIC"> Magic
            <input class="type" type="radio" onclick="switchType(); return false;" name="<%=PARAM_TYPE%>" value="YUGIOH"> Yu-gi-oh
            <input class="type" type="radio" onclick="switchType(); return false;" name="<%=PARAM_TYPE%>" value="EVENT"> Event
        </td>
    </tr>
    <tr>
        <td>
            <div id="magicDiv">
                <table cellpadding="0" cellspacing="0" border="0">
                    <tr></tr>
                </table>
            </div>
        </td>
    </tr>
</table>
</form>
</body>
</html>