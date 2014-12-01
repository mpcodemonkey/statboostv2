<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.statboost.util.ServletUtil" %>
<%@ page import="com.statboost.models.form.Announcement" %>
<%@ page import="com.statboost.controllers.admin.AnnouncementSqllistServlet" %>
<%@ page import="com.statboost.controllers.admin.AnnouncementEditorServlet" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Announcement Editor</title>
    <%!
        static Logger logger = Logger.getLogger(AnnouncementEditorServlet.class);
    %>
    <%
        Announcement announcement = (Announcement) request.getAttribute(AnnouncementEditorServlet.ATTR_ANNOUNCEMENT);
    %>
    <jsp:include page="/include/HeadTags.jsp"/>
    <script type="text/javascript">
        jQuery(document).ready(function() {
            jQuery('#deleteButton').click(function() {
                if (confirm('Are you sure you want to delete the announcement?')) {
                    document.location.assign('<%=AnnouncementEditorServlet.getDeleteUrl(announcement.getUid())%>');
                }
            });
        });
    </script>
</head>
<body>
    <table cellpadding="0" cellspacing="0" border="0">
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
            <td><a href="<%=AnnouncementSqllistServlet.SRV_MAP%>">Close</a></td>
            <td><a href="#" onclick="confirmDelete()" id="deleteButton">Delete</a></td>
        </tr>
        <tr>
            <td><%=ServletUtil.hideNulls(announcement.getTitle())%></td>
            <td><%=announcement.getCreated()%></td>
        </tr>
        <tr>
            <td colspan="2">
                <%=ServletUtil.hideNulls(announcement.getContent())%>
            </td>
        </tr>
    </table>
</body>
</html>