<%@ page import="com.statboost.controllers.admin.EmailTemplateEditorServlet" %>
<%@ page import="com.statboost.models.email.EmailTemplate" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.statboost.models.email.EmailVariable" %>
<%@ page import="java.util.List" %>
<%@ page import="com.statboost.util.ServletUtil" %>
<%@ page import="com.statboost.controllers.admin.EmailTemplateSqllistServlet" %>
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
        List<EmailVariable> emailVariables = (List<EmailVariable>) request.getAttribute(EmailTemplateEditorServlet.ATTR_EMAIL_VARIABLES);

    %>
    <jsp:include page="/include/HeadTags.jsp"/>
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

        function insertVariable()  {
            tinymce.activeEditor.execCommand('mceInsertContent', false, '\${' + document.getElementById('emailVariable').value + '}');
        }
    </script>
</head>
<body>
<form method="post" action="<%=EmailTemplateEditorServlet.SRV_MAP%>">
    <input type="hidden" name="<%=EmailTemplateEditorServlet.PARAM_EMAIL_TEMPLATE_UID%>" value="<%=emailTemplate.getUid()%>">
    <table cellpadding="0" cellspacing="0" border="0">
    <%
        if(request.getAttribute(EmailTemplateEditorServlet.ATTR_INFO)  != null)  {
    %>
    <tr>
        <td colspan="2" class="info">
            <%=request.getAttribute(EmailTemplateEditorServlet.ATTR_INFO)%>
        </td>
    </tr>
    <%
        }
    %>
    <%
        if(request.getAttribute(EmailTemplateEditorServlet.ATTR_ERRORS) != null)  {
            ArrayList<String> errors = (ArrayList<String>) request.getAttribute(EmailTemplateEditorServlet.ATTR_ERRORS);
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
        <td><input type="submit"/></td>
        <td><a href="<%=EmailTemplateSqllistServlet.SRV_MAP%>">Close</a></td>
    </tr>
    <tr>
        <td colspan="2">
            <select id="emailVariable" onchange="insertVariable();return false;">
                <option value="">Select One</option>
                <%
                    if(emailVariables != null)  {
                        for(EmailVariable currentEmailVariable : emailVariables)  {
                %>
                <option value="<%=currentEmailVariable.getName()%>"><%=currentEmailVariable.getDisplayName()%></option>
                <%
                        }
                    }
                %>
            </select>
        </td>
    </tr>
    <tr>
        <td>Name</td>
        <td><input type="text" name="<%=EmailTemplateEditorServlet.PARAM_NAME%>" value="<%=ServletUtil.hideNulls(emailTemplate.getName())%>"></td>
    </tr>
    <tr>
        <td colspan="2">
            <textarea id="elm1" name="area"><%=ServletUtil.hideNulls(emailTemplate.getBody())%></textarea>
        </td>
    </tr>
    </table>
</form>
</body>
</html>