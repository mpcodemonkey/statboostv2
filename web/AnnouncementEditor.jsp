<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.statboost.util.ServletUtil" %>
<%@ page import="com.statboost.controllers.admin.AnnouncementEditorServlet" %>
<%@ page import="com.statboost.models.form.Announcement" %>
<%@ page import="com.statboost.controllers.admin.AnnouncementSqllistServlet" %>
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
    <script type="text/javascript" src="/include/javascripts/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/include/javascripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="/tinymce/tinymce.min.js"></script>
    <script type="text/javascript">
        tinymce.init({
            selector: "textarea#elm1",
            theme: "modern",
            width: 700,
            height: 300,

            toolbar: "undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | l      ink image | print preview media fullpage | forecolor backcolor emoticons",
            menubar: "edit view insert tools table format",
            style_formats: [
                {title: 'Bold text', inline: 'b'},
                {title: 'Red text', inline: 'span', styles: {color: '#ff0000'}},
                {title: 'Red header', block: 'h1', styles: {color: '#ff0000'}},
                {title: 'Example 1', inline: 'span', classes: 'example1'},
                {title: 'Example 2', inline: 'span', classes: 'example2'},
                {title: 'Table styles'},
                {title: 'Table row 1', selector: 'tr', classes: 'tablerow1'}
            ]
        });
    </script>
</head>
<body>
<form method="post" action="<%=AnnouncementEditorServlet.SRV_MAP%>">
    <input type="hidden" name="<%=AnnouncementEditorServlet.PARAM_ANNOUNCEMENT_UID%>" value="<%=announcement.getUid()%>">

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
        <%
            if(request.getAttribute(AnnouncementEditorServlet.ATTR_ERRORS) != null)  {
                ArrayList<String> errors = (ArrayList<String>) request.getAttribute(AnnouncementEditorServlet.ATTR_ERRORS);
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
            <td><a href="<%=AnnouncementSqllistServlet.SRV_MAP%>">Close</a></td>
        </tr>
        <tr>
            <td class="leftNav">Title</td>
            <td><input type="text" name="<%=AnnouncementEditorServlet.PARAM_TITLE%>" value="<%=ServletUtil.hideNulls(announcement.getTitle())%>"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <textarea id="elm1" name="area"><%=ServletUtil.hideNulls(announcement.getContent())%></textarea>
            </td>
        </tr>
    </table>
</form>
</body>
</html>