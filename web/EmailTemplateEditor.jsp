<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.statboost.controllers.EmailTemplateEditorServlet" %>
<%@ page import="com.statboost.EmailTemplate" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Email Template Editor</title>
    <%!
        static Logger logger = Logger.getLogger(EmailTemplateEditorServlet.class);
    %>
    <%
        EmailTemplate emailTemplate = (EmailTemplate) request.getAttribute(EmailTemplateEditorServlet.ATTR_EMAIL_TEMPLATE);
    %>
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
<form method="post" action="<%=EmailTemplateEditorServlet.SRV_MAP%>">
    <input type="hidden" name="<%=EmailTemplateEditorServlet.PARAM_EMAIL_TEMPLATE_UID%>" value="<%=emailTemplate.getUid()%>">
    <table cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td>Name</td>
            <td><input type="text" name="<%=EmailTemplateEditorServlet.PARAM_NAME%>" value="<%=emailTemplate.getName()%>"></td>
        </tr>
        <tr>
            <td colspan="2">
                <textarea id="elm1" name="area"><%=emailTemplate.getBody()%></textarea>
            </td>
        </tr>
    </table>
</form>
</body>
</html>