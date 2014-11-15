<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.admin.InventoryCategoryEditorServlet" %>
<%@ page import="com.statboost.models.inventory.Category" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.statboost.controllers.admin.InventoryCategorySqllistServlet" %>
<%@ page import="com.statboost.util.ServletUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Jessica
  Date: 11/15/14
  Time: 1:27 PM
  To change this template use File | Settings | File Templates.
--%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Inventory Category</title>
<%!
    static Logger logger = Logger.getLogger(InventoryCategoryEditorServlet.class);
%>
<%
    Category category = (Category) request.getAttribute(InventoryCategoryEditorServlet.ATTR_INVENTORY_CATEGORY);
%>
<jsp:include page="/include/HeadTags.jsp"/>
</head>
<body>
<form method="post" action="<%=InventoryCategoryEditorServlet.SRV_MAP%>">
    <input type="hidden" name="<%=InventoryCategoryEditorServlet.PARAM_INVENTORY_CATEGORY_UID%>" value="<%=category.getCatUid()%>">
    <table cellpadding="0" cellspacing="0" border="0">
        <%
            if(request.getAttribute(InventoryCategoryEditorServlet.ATTR_INFO)  != null)  {
        %>
        <tr>
            <td colspan="2" class="info">
                <%=request.getAttribute(InventoryCategoryEditorServlet.ATTR_INFO)%>
            </td>
        </tr>
        <%
            }
        %>
        <%
            if(request.getAttribute(InventoryCategoryEditorServlet.ATTR_ERRORS) != null)  {
                ArrayList<String> errors = (ArrayList<String>) request.getAttribute(InventoryCategoryEditorServlet.ATTR_ERRORS);
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

        <tr>
            <td><input type="submit"></td>
            <td><a href="<%=InventoryCategorySqllistServlet.SRV_MAP%>">Close</a></td>
        </tr>
        <tr>
            <td class="leftNav">Name</td>
            <td><input type="text" name="<%=InventoryCategoryEditorServlet.PARAM_NAME%>" value="<%=ServletUtil.hideNulls(category.getCategory())%>"/></td>
        </tr>
    </table>
</form>
</body>
</html>
