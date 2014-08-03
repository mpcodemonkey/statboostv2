<%@ page import="static com.csc191.util.JSPUtil.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.csc191.LoginServlet" %>
<%@ page import="static com.csc191.LoginServlet.*" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Login</title>
    <%
        ArrayList<String> errors = (ArrayList<String>) request.getAttribute(ATTR_ERRORS);
    %>
    <LINK href="/include/stylesheet.css" rel="stylesheet" type="text/css">
</head>
<body>
    <form style="height: 100%;" method="post" action="<%=LoginServlet.SRV_MAP%>">
        <table align=center; cellpadding="0" cellspacing="0" border="0" style="width:100%; height:100%; text-align: center;">
            <%
                if(errors != null && !errors.isEmpty())  {
            %>
            <tr>
                <td>
                    <table cellpadding="0" cellspacing="0" border="0" style="margin-left: auto; margin-right:auto; margin-top:15px;">
                        <tr>
                            <td style="color: white; font-size: 18px; font-weight: bold; text-align: center">
                                <table cellpadding="0" cellspacing="0" border="0">
                                    <%
                                        for(String error: errors)  {
                                    %>
                                    <tr>
                                        <td><%=error%></td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <%
                }
            %>
            <tr>
                <td id="topLogin"></td>
            </tr>
            <tr>
                <td>
                    <input class="loginBoxes" style="margin-bottom:10px;" type="text" value="<%=nvl(request.getParameter(PARAM_USERNAME))%>" name="<%=PARAM_USERNAME%>">
                </td>
            </tr>
            <tr>
                <td>
                    <input class="loginBoxes" type="password" value="" name="<%=PARAM_PASSWORD%>">
                </td>
            </tr>
            <tr>
                <td><input type="submit" value="login" id="loginButtonGreen"></td>
            </tr>
            <tr>
                <td style="height: 100%;"></td>
            </tr>
        </table>
        </form>
</body>

</html>