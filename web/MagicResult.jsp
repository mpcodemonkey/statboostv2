<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>
<%--
  Created by IntelliJ IDEA.
  User: Jon
  Date: 8/21/2014
  Time: 3:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <c:forEach items="${requestScope.cardList}" var="card">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">
                    ${card.cardName}
                </h3>
            </div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Set ID</th>
                        <th>Card Number</th>
                        <th>Rarity</th>
                        <th>Type</th>
                        <th>Mana cost</th>
                        <th>Text</th>
                        <th>Flavor</th>
                        <th>Image</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${card.setID}</td>
                        <td>${card.cardNumber}</td>
                        <td>${card.rarity}</td>
                        <td>${card.types}</td>
                        <td>${card.manacost}</td>
                        <td>${card.text}</td>
                        <td>
                            <c:choose>
                                <c:when test="${card.flavor} == null">N/A</c:when>
                                <c:otherwise>${card.flavor}</c:otherwise>
                            </c:choose>
                        </td>
                        <td><img src="http://mtgimage.com/set/${card.setID}/${card.imageName}&#46;jpg" width="50%" ></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </c:forEach>
</body>
</html>
