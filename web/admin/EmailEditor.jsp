<%@ page import="com.statboost.controllers.admin.EmailEditorServlet" %>
<%@ page import="com.statboost.models.email.Email" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.List" %>
<%@ page import="com.statboost.models.email.EmailVariable" %>
<%@ page import="com.statboost.models.email.EmailTemplate" %>
<%@ page import="com.statboost.models.email.WorkflowEvent" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.statboost.util.ServletUtil" %>
<%@ page import="com.statboost.controllers.admin.EmailSqllistServlet" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Email Editor</title>
    <%!
        static Logger logger = Logger.getLogger(EmailEditorServlet.class);
    %>
    <%
        Email email = (Email) request.getAttribute(EmailEditorServlet.ATTR_EMAIL);
        List<EmailTemplate> emailTemplates = (List<EmailTemplate>) request.getAttribute(EmailEditorServlet.ATTR_EMAIL_TEMPLATE);
        List<EmailVariable> emailVariables = (List<EmailVariable>) request.getAttribute(EmailEditorServlet.ATTR_EMAIL_VARIABLES);
        WorkflowEvent workflowEvent = (WorkflowEvent) request.getAttribute(EmailEditorServlet.ATTR_WORKFLOW_EVENT);

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

        function insertVariable()  {
            tinymce.activeEditor.execCommand('mceInsertContent', false, '\${' + document.getElementById('emailVariable').value + '}');
        }
    </script>
</head>
<body>
<form method="post" action="<%=EmailEditorServlet.SRV_MAP%>">
    <input type="hidden" name="<%=EmailEditorServlet.PARAM_EMAIL_UID%>" value="<%=email.getUid()%>">
    <input type="hidden" name="<%=EmailEditorServlet.PARAM_EMAIL_VARIABLE_GROUP_UID%>" value="<%=email.getEmailVariableGroup()%>">
<table cellpadding="0" cellspacing="0" border="0">
    <%
        if(request.getAttribute(EmailEditorServlet.ATTR_INFO)  != null)  {
    %>
    <tr>
        <td colspan="2" class="info">
            <%=request.getAttribute(EmailEditorServlet.ATTR_INFO)%>
        </td>
    </tr>
    <%
        }
    %>
    <%
      if(request.getAttribute(EmailEditorServlet.ATTR_ERRORS) != null)  {
          ArrayList<String> errors = (ArrayList<String>) request.getAttribute(EmailEditorServlet.ATTR_ERRORS);
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
        <td><a href="<%=EmailSqllistServlet.SRV_MAP%>">Close</a></td>
    </tr>
    <tr>
        <td>Email Variable</td>
        <td>
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
        <td class="leftNav">Name</td>
        <td><input type="text" name="<%=EmailEditorServlet.PARAM_NAME%>" value="<%=ServletUtil.hideNulls(email.getName())%>"/></td>
    </tr>
    <tr>
        <td>Email Template</td>
        <td>
            <select id="emailTemplates" name="<%=EmailEditorServlet.PARAM_EMAIL_TEMPLATE_UID%>">
                <%
                    if(emailTemplates != null)  {
                        for(EmailTemplate currentEmailTemplate: emailTemplates)  {
                %>
                <option value="<%=currentEmailTemplate.getUid()%>"><%=currentEmailTemplate.getName()%></option>
                <%
                        }
                    }
                %>
            </select>
            <%
                if(email.getEmailTemplate() != null)  {
            %>
            <script type="text/javascript">
                document.getElementById('emailTemplates').value = '<%=email.getEmailTemplate().getUid()%>';
            </script>
            <%
                }
            %>
        </td>
    </tr>
    <tr>
        <td>Sent</td>
        <%--Just get the first one because there will only ever be 1 event associated with an email--%>
        <td><%=workflowEvent.getName()%>:<%=workflowEvent.getDescription()%></td>
    </tr>
    <tr>
        <td>From</td>
        <td><%=email.getFrom()%></td>
    </tr>
    <tr>
        <td>To</td>
        <td><%=email.getTo()%></td>
    </tr>
    <tr>
        <td>Subject</td>
        <td><input type="text" name="<%=EmailEditorServlet.PARAM_SUBJECT%>" value="<%=ServletUtil.hideNulls(email.getSubject())%>"></td>
    </tr>
    <tr>
        <td colspan="2">
            <textarea id="elm1" name="area"><%=ServletUtil.hideNulls(email.getBody())%></textarea>
        </td>
    </tr>
</table>
</form>
</body>
</html>