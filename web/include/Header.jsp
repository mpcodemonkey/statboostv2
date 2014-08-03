<%@ page import="com.csc131.ProjectSearchServlet" %>
<%@ page import="com.csc131.DatabaseSearchServlet" %>
<%@ page import="com.csc131.DatabaseReportServlet" %>
<LINK href="/include/stylesheet.css" rel="stylesheet" type="text/css">
<table cellpadding="0" cellspacing="0" border="0" style="width: 100%;">
    <tr>
        <td style="width: 960px;">
            <table cellpadding="0" cellspacing="0" border="0" style="width: 960px; margin-left: auto; margin-right: auto;">
                <tr>
                    <td>
                       <table cellpadding="0" cellspacing="0" border="0" style="width: 100%;">
                           <tr>
                               <td id="logo"><a href="/Home.jsp"><img src="/images/logo.gif"></a></td>
                               <%--will need to change this to actually changing the security--%>
                               <td style="vertical-align: top; text-align: right; padding-top:5px;">
                                   <a href="/Login.jsp">Logout</a>
                               </td>
                           </tr>
                       </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table cellpadding="0" cellspacing="0" border="0" style="width: 100%;">
                            <tr>
                                <td><a href="<%=ProjectSearchServlet.SRV_MAP%>"><img src="/images/first_button.gif" alt="first"></a></td>
                                <td><a href="<%=DatabaseSearchServlet.SRV_MAP%>"><img src="/images/second_button.gif" alt="second"></a></td>
                                <td><a href="<%=DatabaseReportServlet.SRV_MAP%>"><img src="/images/third_button.gif" alt="third"></a></td>
                                <td><a href=""><img src="/images/fourth_button.gif" alt="fourth"></a></td>
                                <td><a href="#"><img src="/images/fifth_button.gif" alt="fifth"></a></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>