<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: Jon
  Date: 11/1/2014
  Time: 8:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<c:set var="previousItemName" value="startName" />
<c:set var="previousItemDesc" value="startName" />


    <c:forEach items="${requestScope.inventoryList}" var="inventory" varStatus="i">
        <c:choose>
            <c:when test="${((previousItemName != inventory.name) || ((previousItemName == inventory.name) && (previousItemDesc != inventory.description)))}">
                <c:choose>
                    <c:when test="${!fn:contains(previousItemName, 'startName')}">
                        </tbody>
                        </table>
                        </div>
                        </div>
                        </div>
                        </div>
                        </div>
                        </div>
                        </div>
                    </c:when>
                </c:choose>
                <div class="container-fluid">
                <div class="panel panel-default">
                <div class="panel-heading">${inventory.name}</div>
                <div class="panel-body">

                    <div class="row">
                        <div class="col-sm-4">
                            <c:choose>
                                <c:when test="${inventory.type == 'YGO'}">
                                    <img id="YGO-${i.count}" src="//teamjjacs.us/static-images/inventory/yugioh/${inventory.imageName}" style="max-height: 200px;" onclick="toggleSize(this.id);" onerror="this.style.display='none'">
                                </c:when>
                                <c:when test="${inventory.type == 'MTG'}">
                                    <img id="MTG-${i.count}" src="//teamjjacs.us/static-images/inventory/mtg/${inventory.imageName}" style="max-height: 200px;" onclick="toggleSize(this.id);" onerror="this.src='https://placehold.it/150x200';this.onerror=null;">
                                </c:when>
                                <c:when test="${inventory.type == 'GEN'}">
                                    <img id="GEN-${i.count}" src="//teamjjacs.us/static-images/inventory/generic/${inventory.imageName}" style="max-height: 200px;" onclick="toggleSize(this.id);" onerror="this.style.display='none'">
                                </c:when>
                                <c:otherwise>
                                    <img class="media-object img-rounded img-responsive" src="https://placehold.it/150x200">
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="col-sm-8">
                            <div class="row col-sm-12">
                                <label>Description:</label>
                                <label id="descLabel">${inventory.description}</label>
                                <br>
                            </div>
                            <div class="row col-sm-12">
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <th>Condition</th>
                                        <th>Price</th>
                                        <th>Quantity</th>
                                        <th></th>
                                    </tr>
            </c:when>
        </c:choose>
                                    <tr>
                                        <div id="${inventory.inv_uid}" class="alert alert-success fade in" style="display: none;">
                                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                            <span id="inv_name${inventory.inv_uid}" style="font-weight: bold;"></span> has been added to your shopping cart!
                                        </div>
                                    </tr>
                                    <tr>
                                        <td>${fn:replace(inventory.condition, "_", " ")}</td>
                                        <td><fmt:formatNumber value="${inventory.price}" type="currency"/></td>
                                        <td>
                                            <select id="quantityPicker-${inventory.inv_uid}" class="form-control">
                                                <c:forEach begin="1" end="${inventory.quantity}" varStatus="loop">
                                                    <option>${loop.index}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-primary" style="float:right;" onclick="addToCart(${inventory.inv_uid}, '${inventory.condition}', $('#quantityPicker-${inventory.inv_uid}').val(), '${inventory.name}')">Add To Cart</button>
                                        </td>
                                    </tr>
            <c:set var="previousItemName" value="${inventory.name}" />
            <c:set var="previousItemDesc" value="${inventory.description}" />


    </c:forEach>
                    </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    </div>
    </div>
    </div>

    <ul class="pagination">
        <c:if test="${currentPage != 1}">
            <li><a href="Store?page=${1}">First</a></li>
            <li><a href="Store?page=${currentPage - 1}">Previous</a></li>
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
                                <li><a href="Store?page=${j}">${j}</a></li>
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
                                <li><a href="Store?page=${i}">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <c:if test="${currentPage lt numberOfPages}">
            <li><a href="Store?page=${currentPage + 1}">Next</a></li>
            <li><a href="Store?page=${numberOfPages}">Last</a></li>
        </c:if>
    </ul>
</div>

<script>
    function addToCart(inv_uid, condition, quantity, name) {
        xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", '/cart?addItem=true&inv_uid='+inv_uid+'&condition='+condition+'&quantity='+quantity, true);
        xmlhttp.send();
        $('#inv_name'+inv_uid).text(name);
        $('#'+inv_uid).show();
        setTimeout(function() {location.reload(true)}, 500);
    }

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