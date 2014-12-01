<%@ page import="com.statboost.controllers.admin.MediaUploaderServlet" %>
<%@ page import="com.statboost.util.ServletUtil" %>
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
    <title>Media</title>
</head>
<body>
<form method="post" action="<%=MediaUploaderServlet.SRV_MAP%>" enctype="multipart/form-data">
    <table border="0">
        <%
            if(request.getAttribute(MediaUploaderServlet.ATTR_INFO)  != null)  {
        %>
        <tr>
            <td colspan="2" class="info">
                <%=request.getAttribute(MediaUploaderServlet.ATTR_INFO)%>
            </td>
        </tr>
        <%
            }
        %>
        <%
            if(request.getAttribute(MediaUploaderServlet.ATTR_ERROR)  != null)  {
        %>
        <tr>
            <td colspan="2" class="error">
                <%=request.getAttribute(MediaUploaderServlet.ATTR_ERROR)%>
            </td>
        </tr>
        <%
            }
        %>
        <tr>
            <td colspan="2">Please make sure the image has a meaningful name before uploading the image. You will NOT be able to edit or delete the image after it has been uploaded.</td>
        </tr>
        <%--todo: create radio buttons for determining where the image will be used--%>
        <%--todo: maybe give them a way to pick the file name--%>
        <tr>
            <td colspan="2">
                <input id="other" class="inventoryType" type="radio" onclick="switchType();" name="<%=MediaUploaderServlet.PARAM_TYPE%>" value="other">
                <label for="other">Generic</label>
                <input id="magic" class="inventoryType" type="radio" onclick="switchType();" name="<%=MediaUploaderServlet.PARAM_TYPE%>" value="magic">
                <label for="magic">Magic</label>
                <input id="yugioh" class="inventoryType" type="radio" onclick="switchType();" name="<%=MediaUploaderServlet.PARAM_TYPE%>" value="yugioh">
                <label for="yugioh">Yu-gi-oh</label>
                <input id="website" class="inventoryType" type="radio" onclick="switchType();" name="<%=MediaUploaderServlet.PARAM_TYPE%>" value="website">
                <label for="website">Website</label>
            </td>
        </tr>
        <tr>
            <td>Name</td>
            <td><input type="text" name="<%=MediaUploaderServlet.PARAM_NAME%>" value="<%=ServletUtil.hideNulls(request.getParameter(MediaUploaderServlet.PARAM_NAME))%>"></td>
        </tr>
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
