<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.admin.MagicSetSqllistServlet" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.statboost.controllers.admin.MagicSetEditorServlet" %>
<%--
  Created by IntelliJ IDEA.
  User: Jessica
  Date: 10/20/14
  Time: 2:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Magic Sets</title>
    <%!
        static Logger logger = Logger.getLogger(MagicSetSqllistServlet.class);
    %>
    <%
        ResultSet magicSets = (ResultSet) request.getAttribute(MagicSetSqllistServlet.ATTR_SETS);
    %>
    <jsp:include page="/include/HeadTags.jsp"/>
</head>
<body>
<table>
    <tr>
        <td><a href="<%=MagicSetEditorServlet.SRV_MAP%>">New</a></td>
    </tr>
    <tr>
        <td>Name</td>
    </tr>
    <%
        if(magicSets != null)  {
            try  {
                while(magicSets.next())  {
    %>
    <tr>
        <td><a href="<%=MagicSetEditorServlet.getEditUrl(magicSets.getString("mst_uid"))%>"><%=magicSets.getString("mst_name")%></a></td>
    </tr>
    <%
                }
            } catch (Exception e)  {
                logger.error("Could not loop through the magic sets.", e);
            }
        }
    %>
</table>
</body>
</html>
