<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
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
                                                <div class="col-sm-6" style="font-size: large;"><a href="/Store?mId=${card.mcrMultiverseId}">${card.mcrCardName}</a></div>
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
                                <!--${card.mcrSetId}-->
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

        </c:forEach>



        <ul class="pagination">
            <c:if test="${currentPage != 1}">
                <li><a href="magicSearch?page=${1}">First</a></li>
                <li><a href="magicSearch?page=${currentPage - 1}">Previous</a></li>
            </c:if>
            <c:choose>
                <c:when test="${numberOfPages <= 6}">
                    <c:forEach begin="${1}" end="${numberOfPages}" var="j">
                        <c:if test="${j le numberOfPages}">
                            <c:choose>
                                <c:when test="${currentPage eq j}">
                                    <li class="active"><a href="#">${j}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="magicSearch?page=${j}">${j}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="${currentPage}" end="${currentPage + 5}" var="i">
                        <c:if test="${i le numberOfPages}">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <li class="active"><a href="#">${i}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="magicSearch?page=${i}">${i}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            <c:if test="${currentPage lt numberOfPages}">
                <li><a href="magicSearch?page=${currentPage + 1}">Next</a></li>
                <li><a href="magicSearch?page=${numberOfPages}">Last</a></li>
            </c:if>
        </ul>


    </div>



<jsp:include page="/include/Footer.jsp"/>