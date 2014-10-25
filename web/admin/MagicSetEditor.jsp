<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.admin.MagicSetEditorServlet" %>
<%@ page import="com.statboost.models.mtg.MagicSet" %>
<%@ page import="com.statboost.controllers.admin.MagicSetSqllistServlet" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.statboost.util.ServletUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Jessica
  Date: 10/20/14
  Time: 3:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Magic Set Editor</title>
    <%!
        static Logger logger = Logger.getLogger(MagicSetEditorServlet.class);
    %>
    <%
        MagicSet magicSet = (MagicSet) request.getAttribute(MagicSetEditorServlet.ATTR_MAGIC_SET);
    %>
    <jsp:include page="/include/HeadTags.jsp"/>
    <script type="text/javascript">
        jQuery(document).ready(function()  {
            jQuery('.datePicker').datepicker();
        })
    </script>
</head>
<body>
    <form method="post" action="<%=MagicSetEditorServlet.SRV_MAP%>">
        <input type="hidden" name="<%=MagicSetEditorServlet.PARAM_MAGIC_SET_UID%>" value="<%=magicSet.getMstUid()%>">
        <table cellpadding="0" cellspacing="0" border="0">
                <%
            if(request.getAttribute(MagicSetEditorServlet.ATTR_INFO)  != null)  {
        %>
            <tr>
                <td colspan="2" class="info">
                    <%=request.getAttribute(MagicSetEditorServlet.ATTR_INFO)%>
                </td>
            </tr>
                <%
            }
        %>
                <%
            if(request.getAttribute(MagicSetEditorServlet.ATTR_ERRORS) != null)  {
                ArrayList<String> errors = (ArrayList<String>) request.getAttribute(MagicSetEditorServlet.ATTR_ERRORS);
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
                <td><a href="<%=MagicSetSqllistServlet.SRV_MAP%>">Close</a></td>
            </tr>
            <tr>
                <td class="leftNav">Name</td>
                <td><input type="text" name="<%=MagicSetEditorServlet.PARAM_NAME%>" value="<%=ServletUtil.hideNulls(magicSet.getMstName())%>"/></td>
            </tr>
            <tr>
                <td class="leftNav">Block</td>
                <td><input type="text" name="<%=MagicSetEditorServlet.PARAM_BLOCK%>" value="<%=ServletUtil.hideNulls(magicSet.getMstBlock())%>"/></td>
            </tr>
            <tr>
                <td class="leftNav">Border</td>
                <td><input type="text" name="<%=MagicSetEditorServlet.PARAM_BORDER%>" value="<%=ServletUtil.hideNulls(magicSet.getMstBorder())%>"/></td>
            </tr>
            <tr>
                <td class="leftNav">Release Date</td>
                <td><input class="datePicker" type="text" name="<%=MagicSetEditorServlet.PARAM_RELEASE_DATE%>" value="<%=magicSet.getMstReleaseDate() == null? "" : magicSet.getMstReleaseDate()%>"/></td>
            </tr>
            <tr>
                <td class="leftNav">Type</td>
                <td><input type="text" name="<%=MagicSetEditorServlet.PARAM_TYPE%>" value="<%=ServletUtil.hideNulls(magicSet.getMstType())%>"/></td>
            </tr>
        </table>
    </form>
</body>
</html>
