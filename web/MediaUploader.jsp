<%@ page import="com.statboost.controllers.admin.MediaUploaderServlet" %>
<%--
  Created by IntelliJ IDEA.
  User: Jessica
  Date: 10/19/14
  Time: 7:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form method="post" action="<%=MediaUploaderServlet.SRV_MAP%>" enctype="multipart/form-data">
    <table cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td>Image</td>
            <td><input type="file" name="<%=MediaUploaderServlet.PARAM_IMAGE_TO_UPLOAD%>"></td>
        </tr>
        <tr>
            <td><input type="submit" value="Upload Image"/></td>
        </tr>
    </table>
</form>
</body>
</html>
