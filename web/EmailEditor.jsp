<%@ page import="com.statboost.controllers.admin.EmailEditorServlet" %>
<%@ page import="com.statboost.models.email.Email" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.sql.ResultSet" %>
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
        ResultSet emailTemplates = (ResultSet) request.getAttribute(EmailEditorServlet.ATTR_EMAIL_TEMPLATE);
        ResultSet emailVariables = (ResultSet) request.getAttribute(EmailEditorServlet.ATTR_EMAIL_VARIABLES);
        Email email = (Email) request.getAttribute(EmailEditorServlet.ATTR_EMAIL);
    %>
    <style type="text/css">
        <%--class example--%>
        .leftNav   {
            font-weight:bold;
            padding-right:20px;
        }
        /*id example*/
        #leftNav  {
            font-family: Arial, Helvetica, Sans-Serif;
        }
    </style>
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
//            todo: check that this works
            insertAtCaret('elm1', document.getElementById('emailVariable').value);
        }

        function insertAtCaret(areaId,text) {
            var txtarea = document.getElementById(areaId);
            var scrollPos = txtarea.scrollTop;
            var strPos = 0;
            var br = ((txtarea.selectionStart || txtarea.selectionStart == '0') ?
                    "ff" : (document.selection ? "ie" : false ) );
            if (br == "ie") {
                txtarea.focus();
                var range = document.selection.createRange();
                range.moveStart ('character', -txtarea.value.length);
                strPos = range.text.length;
            }
            else if (br == "ff") strPos = txtarea.selectionStart;

            var front = (txtarea.value).substring(0,strPos);
            var back = (txtarea.value).substring(strPos,txtarea.value.length);
            txtarea.value=front+text+back;
            strPos = strPos + text.length;
            if (br == "ie") {
                txtarea.focus();
                var range = document.selection.createRange();
                range.moveStart ('character', -txtarea.value.length);
                range.moveStart ('character', strPos);
                range.moveEnd ('character', 0);
                range.select();
            }
            else if (br == "ff") {
                txtarea.selectionStart = strPos;
                txtarea.selectionEnd = strPos;
                txtarea.focus();
            }
            txtarea.scrollTop = scrollPos;
        }
    </script>
</head>
<body>
<form method="post" action="<%=EmailEditorServlet.SRV_MAP%>">
    <input type="hidden" name="<%=EmailEditorServlet.PARAM_EMAIL_UID%>" value="<%=email.getUid()%>">
    <input type="hidden" name="<%=EmailEditorServlet.PARAM_EMAIL_VARIABLE_GROUP_UID%>" value="<%=email.getEmailVariableGroup()%>">
<table cellpadding="0" cellspacing="0" border="0">
    <tr>
        <td>Email Variable</td>
        <td>
            <select id="emailVariable" onchange="insertVariable();return false;">
                <option value="">Select One</option>
                <%
                    while(emailVariables != null && emailVariables.next())  {
                %>
                <option value="<%=emailVariables.getString("evr_name")%>"><%=emailVariables.getString("evr_display_name")%></option>
                <%
                    }    
                %>
            </select>    
        </td>
    </tr>
    <tr>
        <td class="leftNav">Name</td>
        <td><input type="text" name="<%=EmailEditorServlet.PARAM_NAME%>" value="<%=email.getName()%>"/></td>
    </tr>
    <tr>
        <td>Email Template</td>
        <td>
            <select id="emailTemplates" name="<%=EmailEditorServlet.PARAM_EMAIL_TEMPLATE_UID%>">
                <%
                    while(emailTemplates.next())  {
                %>
                <option value="<%=emailTemplates.getInt("etm_uid")%>"><%=emailTemplates.getString("etm_name")%></option>
                <%
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
        <%--todo: Load up the workflow event associated with the email--%>
        <td>Sent</td>
        <td></td>
    </tr>
    <tr>
        <td>From</td>
        <td><input type="text" name="<%=EmailEditorServlet.PARAM_FROM%>" value="<%=email.getFrom()%>"></td>
    </tr>
    <tr>
        <%--todo: figure out what we are doing for this--%>
        <td>To</td>
        <td></td>
    </tr>
    <tr>
        <td>Subject</td>
        <td><input type="text" name="<%=EmailEditorServlet.PARAM_SUBJECT%>" value="<%=email.getSubject()%>"></td>
    </tr>
    <tr>
        <td colspan="2">
            <textarea id="elm1" name="area"><%=email.getBody()%></textarea>
        </td>
    </tr>
</table>
</form>
</body>
</html>