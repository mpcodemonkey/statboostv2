<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.admin.ConditionEditorServlet" %>
<%@ page import="com.statboost.models.inventory.Condition" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.statboost.controllers.admin.ConditionSqllistServlet" %>
<%@ page import="com.statboost.util.ServletUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Jessica
  Date: 9/28/14
  Time: 9:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Condition Editor</title>
    <%!
        static Logger logger = Logger.getLogger(ConditionEditorServlet.class);
    %>
    <%
        Condition condition = (Condition) request.getAttribute(ConditionEditorServlet.ATTR_CONDITION);
    %>
    <jsp:include page="/include/HeadTags.jsp"/>

</head>
<body>
<form method="post" action="<%=ConditionEditorServlet.SRV_MAP%>">
    <input type="hidden" name="<%=ConditionEditorServlet.PARAM_CONDITION_UID%>" value="<%=condition.getUid()%>">
    <table>
        <%
            if(request.getAttribute(ConditionEditorServlet.ATTR_INFO)  != null)  {
        %>
        <tr>
            <td colspan="2" class="info">
                <%=request.getAttribute(ConditionEditorServlet.ATTR_INFO)%>
            </td>
        </tr>
        <%
            }
        %>
        <%
            if(request.getAttribute(ConditionEditorServlet.ATTR_ERRORS) != null)  {
                ArrayList<String> errors = (ArrayList<String>) request.getAttribute(ConditionEditorServlet.ATTR_ERRORS);
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
            <td><a href="<%=ConditionSqllistServlet.SRV_MAP%>">Close</a></td>
        </tr>
        <tr>
            <td class="leftNav">Name</td>
            <td><%=ServletUtil.hideNulls(condition.getName())%></td>
        </tr>
        <tr>
            <td class="leftNav">Percent of Price</td>
            <td><input type="text" name="<%=ConditionEditorServlet.PARAM_PERCENT_OF_PRICE%>" value="<%=condition.getPercentOfPrice()%>"> (If you want it to equal 100% of the price put 1 otherwise put a decimal ex. .25 for 25 percent)</td>
        </tr>
    </table>
</form>
</body>
</html>
