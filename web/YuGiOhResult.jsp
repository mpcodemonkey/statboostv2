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
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>


<div class="container-fluid">
    <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
        <div class="alert alert-${requestScope.alertType} fade in">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            <strong>Alert:</strong> <c:out value="${requestScope.alert}" />
        </div>
    </c:if>
    <c:forEach items="${requestScope.cardList}" var="card" varStatus="i">
        <c:set var="typeVar" value="${card.ycrSuperType}"/>
        <div class="panel panel-default">
            <div class="panel-heading"><a href="/Store?yId=${card.ycrUid}">${card.ycrName}</a></div>
            <div class="row">
                <div class="col-md-3" style="text-align:center; vertical-align: middle">
                    <img id="YGO-${i.count}" src="//teamjjacs.us/static-images/inventory/yugioh/${card.yugiohSet.ystPathName}/${card.ycrImageName}" class="img-rounded" style="max-height: 200px;" onclick="toggleSize(this.id)" onerror="this.style.display='none'">
                </div>
                <div class="col-md-8">
                    <div class="row">

                        <div class="col-md-6">
                            <table class="table" >
                                <c:choose>
                                    <c:when test="${fn:contains(typeVar, 'Monster')}">
                                        <tbody>
                                        <tr>
                                            <th style="border-top:none;">
                                                Attribute
                                            </th>
                                            <td style="border-top:none;">
                                                <c:choose>
                                                    <c:when test="${card.ycrAttribute == null }">N/A</c:when>
                                                    <c:otherwise>${card.ycrAttribute}</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Type(s)</th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${card.ycrMonsterType == null}">N/A</c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${card.ycrType == null}">
                                                                ${card.ycrMonsterType}
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${card.ycrMonsterType},${card.ycrType}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Level
                                            </th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${card.ycrLevel == null}">N/A</c:when>
                                                    <c:otherwise>${card.ycrLevel}</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Rarity
                                            </th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${card.ycrRarity == null}">
                                                        N/A
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${card.ycrRarity}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <th style="border-top:none;">
                                                Type
                                            </th>
                                            <td style="border-top:none;">
                                                <c:choose>
                                                    <c:when test="${card.ycrType == null}">
                                                        N/A
                                                    </c:when>
                                                    <c:otherwise>${card.ycrType}</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Icon
                                            </th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${card.ycrIcon == null}">N/A</c:when>
                                                    <c:otherwise>${card.ycrIcon}</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </table>
                        </div>
                        <div class="col-md-6">
                            <table class="table">
                                <tbody>
                                    <c:choose>
                                        <c:when test="${fn:contains(typeVar, 'Monster')}">
                                            <tr>
                                                <th style="border-top:none;">
                                                    ATK
                                                </th>
                                                <td style="border-top:none;">
                                                    <c:choose>
                                                        <c:when test="${card.ycrAtk == null}">N/A</c:when>
                                                        <c:otherwise>${card.ycrAtk}</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>
                                                    DEF
                                                </th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${card.ycrDef == null}">N/A</c:when>
                                                        <c:otherwise>${card.ycrDef}</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>
                                                    Effect Types</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${card.ycrCardEffectType == null}">N/A</c:when>
                                                        <c:otherwise>${card.ycrCardEffectType}</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <c:choose>
                                                    <c:when test="${card.ycrPendulumScale != 0}">
                                                        <th>
                                                            Pendulum Scale</th>
                                                        <td>
                                                            ${card.ycrPendulumScale}
                                                        </td>
                                                    </c:when>
                                                </c:choose>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <th style="border-top:none;">
                                                    Rarity
                                                </th>
                                                <td style="border-top:none;">
                                                    <c:choose>
                                                        <c:when test="${card.ycrRarity == null}">N/A</c:when>
                                                        <c:otherwise>${card.ycrRarity}</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>
                                                    Effect Types</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${card.ycrCardEffectType == null}">N/A</c:when>
                                                        <c:otherwise>${card.ycrCardEffectType}</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <c:choose>
                                <c:when test="${card.ycrFlavorText == null}">N/A</c:when>
                                <c:otherwise>${card.ycrFlavorText}</c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${card.ycrPendulumFlavor != null}">
                                    <br><br>
                                    Pendulum Effect:
                                    <br>
                                    ${card.ycrPendulumFlavor}
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>

    <ul class="pagination">
        <c:if test="${currentPage != 1}">
            <li><a href="ygoSearch?page=${1}">First</a></li>
            <li><a href="ygoSearch?page=${currentPage - 1}">Previous</a></li>
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
                                <li><a href="ygoSearch?page=${j}">${j}</a></li>
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
                                <li><a href="ygoSearch?page=${i}">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <c:if test="${currentPage lt numberOfPages}">
            <li><a href="ygoSearch?page=${currentPage + 1}">Next</a></li>
            <li><a href="ygoSearch?page=${numberOfPages}">Last</a></li>
        </c:if>
    </ul>

</div>


<script>
    function toggleSize(id) {
        if( $('#'+id).css('max-height') == '200px' )  {
            $('#'+id).animate({
                "max-height": 350
            }, 200 );
        }
        else {
            $('#'+id).animate({
                "max-height": 200
            }, 200 );
        }
    }
</script>




<jsp:include page="/include/Footer.jsp"/>
