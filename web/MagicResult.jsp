<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
            <div class="alert alert-${requestScope.alertType} fade in">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <strong>Alert:</strong> <c:out value="${requestScope.alert}" />
            </div>
        </c:if>
        <c:forEach items="${requestScope.cardList}" var="card">
            <%-- Prepare card manacost for mtgimage symbol search --%>
            <c:set var="manacost">${fn:replace(fn:replace(card.mcrManaCost,"{",""),"}"," ")}</c:set>
            <c:set var="manaParts" value="${fn:split(manacost, ' ')}" />

            <!--
            <div class="panel panel-primary" style="min-width: 710px;">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        ${card.mcrCardName}
                        <img src="http://mtgimage.com/symbol/set/${card.mcrSetId}/${card.mcrRarity}/64&#46;png" align="right" style="margin-top: -7px;max-height: 32px;">
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
                            <td>
                                <c:forEach items="${manaParts}" var="mana">
                                    <c:if test="${mana != ''}">
                                    <img src="http://mtgimage.com/symbol/mana/${mana}/24&#46;png" style="" align="middle">
                                    </c:if>
                                </c:forEach>
                            </td>
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
            </div>-->

            <div class="panel panel-primary" style="min-width: 480px;">
                <div class="panel-body">
                    <table class="table">
                        <tr>
                            <td><img src="http://mtgimage.com/set/${card.mcrSetId}/${card.mcrImageName}&#46;jpg" style="min-width: 175px; max-width: 175px;">
                            </td>
                            <td>
                                <table>
                                    <tr>
                                        <td class="container">
                                                <div class="col-sm-6" style="font-size: large;">${card.mcrCardName}</div>
                                                <div class="col-sm-6">
                                                    <c:forEach items="${manaParts}" var="mana">
                                                        <c:if test="${mana != ''}">
                                                            <img src="http://mtgimage.com/symbol/mana/${mana}/24&#46;png" style="" align="middle">
                                                        </c:if>
                                                    </c:forEach>
                                                    (<fmt:formatNumber type="number" maxFractionDigits="1" value="${card.mcrCmc}" />)
                                                </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                                <c:forEach items="${card.mcrTypes}" var="type">
                                                    <c:if test="${type != ''}">
                                                        ${type}
                                                    </c:if>
                                                </c:forEach>
                                                <c:choose>
                                                    <c:when test="${card.mcrSubTypes == null}"> </c:when>
                                                    <c:otherwise> -
                                                        <c:forEach items="${card.mcrSubTypes}" var="sType">
                                                            <c:if test="${sType != ''}">
                                                                ${sType}
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:otherwise>
                                                </c:choose>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                                <br />
                                                ${card.mcrText}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <br />
                                            <c:choose>
                                                <c:when test="${card.mcrFlavor == null}"></c:when>
                                                <c:otherwise><i>${card.mcrFlavor}</i></c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td>
                                <img src="http://mtgimage.com/symbol/set/${card.mcrSetId}/${card.mcrRarity}/64&#46;png" align="right" style="margin-top: -7px;max-height: 32px;">
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

        </c:forEach>
    </div>



<jsp:include page="/include/Footer.jsp"/>