<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>
<%--
  Created by IntelliJ IDEA.
  User: Jon
  Date: 8/21/2014
  Time: 3:21 PM
--%>
    <div class="container-fluid">
        <c:forEach items="${requestScope.cardList}" var="card">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        ${card.mcrCardName}
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
                            <th>ManaCost</th>
                            <th>Text</th>
                            <th>Flavor</th>
                            <th>Image</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${card.mcrSetId}</td>
                            <td>${card.mcrNumber}</td>
                            <td>${card.mcrRarity}</td>
                            <td>${card.mcrTypes}</td>
                            <td>${card.mcrManaCost}</td>
                            <td>${card.mcrText}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${card.mcrFlavor == null}">N/A</c:when>
                                    <c:otherwise>${card.mcrFlavor}</c:otherwise>
                                </c:choose>
                            </td>
                            <td><img src="http://mtgimage.com/set/${card.mcrSetId}/${card.mcrImageName}&#46;jpg" style="min-width: 175px; max-width: 175px;"></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:forEach>
    </div>



<jsp:include page="/include/Footer.jsp"/>