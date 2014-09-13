<%--
  Created by IntelliJ IDEA.
  User: Jon
  Date: 9/8/2014
  Time: 6:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<div class="container-fluid">
    <c:forEach items="${requestScope.cardList}" var="card">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">
                        ${card.ycrName}
                </h3>
            </div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Card Type</th>
                        <th>Attribute</th>
                        <th>Monster Type</th>
                        <th>Card Text</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${card.ycrCardType}</td>
                        <td>${card.ycrAttribute}</td>
                        <td>${card.ycrType}</td>
                        <td>
                            <c:choose>
                                <c:when test="${card.ycrDescription} == null">N/A</c:when>
                                <c:otherwise>${card.ycrDescription}</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </c:forEach>
</div>



<jsp:include page="/include/Footer.jsp"/>
