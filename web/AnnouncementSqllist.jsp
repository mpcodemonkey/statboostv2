<%@ page import="java.sql.ResultSet" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.admin.AnnouncementSqllistServlet" %>
<%@ page import="com.statboost.controllers.admin.AnnouncementEditorServlet" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Announcements</title>
    <%!
        static Logger logger = Logger.getLogger(AnnouncementSqllistServlet.class);
    %>
    <%
        ResultSet announcements = (ResultSet) request.getAttribute(AnnouncementSqllistServlet.ATTR_ANNOUNCEMENTS);
    %>
</head>
<body>
<table>
    <%
        if(request.getAttribute(AnnouncementEditorServlet.ATTR_INFO)  != null)  {
    %>
    <tr>
        <td colspan="2" class="info">
            <%=request.getAttribute(AnnouncementEditorServlet.ATTR_INFO)%>
        </td>
    </tr>
    <%
        }
    %>
    <tr>
        <td colspan="2"><a href="<%=AnnouncementEditorServlet.SRV_MAP%>">New Announcement</a></td>
    </tr>
    <tr>
        <td>Name</td>
        <td>Created</td>
    </tr>
    <%
        if(announcements != null)  {
            try  {
                while(announcements.next())  {
    %>
    <tr>
        <td><a href="<%=AnnouncementEditorServlet.getEditUrl(announcements.getInt("ann_uid"))%>"><%=announcements.getString("ann_title")%></a></td>
        <td><a href="<%=AnnouncementEditorServlet.getEditUrl(announcements.getInt("ann_uid"))%>"><%=announcements.getDate("ann_created")%></a></td>
    </tr>
    <%
                }
            } catch (Exception e)  {
                logger.error("Could not loop through the announcements.", e);
            }
        }
    %>
</table>
</body>
</html>