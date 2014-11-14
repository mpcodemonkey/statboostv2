<%--
  Created by IntelliJ IDEA.
  User: Jon
  Date: 9/8/2014
  Time: 6:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<%--
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
--%>
<div class="container-fluid">
    <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
        <div class="alert alert-${requestScope.alertType} fade in">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            <strong>Alert:</strong> <c:out value="${requestScope.alert}" />
        </div>
    </c:if>
    <c:forEach items="${requestScope.cardList}" var="card">
        <c:set var="typeVar" value="${card.ycrCardType}"/>
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-primary">
                    <div class="panel-heading">${card.ycrName}</div>
                    <div class="panel-body">
                        <div class="col-md-2">
                            <img src="https://placehold.it/150X150" class="img-rounded">
                        </div>
                        <div class="col-md-8 col-md-offset-1">
                            <div class="row">
                                <div class="col-md-12">
                                    <table class="table">
                                        <thead><tr>
                                            <th class="col-sm-2">Type</th>
                                            <th class="col-sm-2">Card Type</th>
                                            <c:choose>
                                                <c:when test="${fn:contains(typeVar, 'Monster')}">
                                                    <th class="col-sm-2">ATK</th>
                                                    <th class="col-sm-2">DEF</th>
                                                    <th class="col-sm-2">Level</th>
                                                </c:when>
                                                <c:when test="${fn:contains(typeVar, 'Trap')}">
                                                    <th class="col-sm-2">ID</th>
                                                    <th class="col-sm-2">ID</th>
                                                    <th class="col-sm-2">ID</th>
                                                </c:when>
                                                <c:otherwise>
                                                    <th class="col-sm-2">ID</th>
                                                    <th class="col-sm-2">ID</th>
                                                    <th class="col-sm-2">ID</th>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr></thead>
                                        <tbody>
                                            <tr>
                                                <td>${card.ycrType}</td>
                                                <td>${card.ycrCardType}</td>
                                                <c:choose>
                                                    <c:when test="${fn:contains(typeVar, 'Monster')}">
                                                        <td>${card.ycrAtk}</td>
                                                        <td>${card.ycrDef}</td>
                                                        <td>${card.ycrLevel}</td>
                                                    </c:when>
                                                    <c:when test="${fn:contains(typeVar, 'Trap')}">
                                                        <td>${card.ycrUid}</td>
                                                        <td>${card.ycrUid}</td>
                                                        <td>${card.ycrUid}</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>${card.ycrUid}</td>
                                                        <td>${card.ycrUid}</td>
                                                        <td>${card.ycrUid}</td>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tr></tbody>
                                        <tbody><tr></tr></tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <c:choose>
                                        <c:when test="${card.ycrDescription} == null">N/A</c:when>
                                        <c:otherwise>${card.ycrDescription}</c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>

    <ul class="pagination">
        <c:if test="${currentPage != 1}">
            <li><a href="ygoSearch?page=${currentPage - 1}">Previous</a></li>
        </c:if>
        <c:forEach begin="1" end="${numberOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <li class="active"><a href="#">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="ygoSearch?page=${i}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage lt numberOfPages}">
            <li><a href="ygoSearch?page=${currentPage + 1}">Next</a></li>
        </c:if>
    </ul>

</div>




<jsp:include page="/include/Footer.jsp"/>
