<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Jon
  Date: 11/1/2014
  Time: 8:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<div class="container-fluid">
    <c:forEach items="${requestScope.inventoryList}" var="inventory">

        <div class="panel panel-default">
            <div class="panel-heading">${inventory.name}</div>
            <div class="panel-body">
                <div id="${inventory.inv_uid}" class="alert alert-success fade in" style="display: none;">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Alert:</strong> Item has been added to your shopping cart!
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <img class="media-object img-rounded img-responsive" src="https://placehold.it/350x250" alt="placehold.it/350x250">
                    </div>
                    <div class="col-md-4">
                        <div class="row">
                            <label>Condition:</label>
                            <label id="condLabel">${inventory.condition}</label>
                            <br>

                            <label>Number in stock:</label>
                            <label id="stockLabel">${inventory.quantity}</label>
                            <br>

                            <label>Description:</label>
                            <label id="descLabel">${inventory.description}</label>
                            <br>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label class="control-label" for="quantityPicker-${inventory.inv_uid}">Quantity: </label>
                            <br>
                            <div class="col-md-4">
                                <select id="quantityPicker-${inventory.inv_uid}" class="form-control">
                                    <c:forEach begin="1" end="${inventory.quantity}" varStatus="loop">
                                        <option>${loop.index}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <br>
                            <h3>Price: <fmt:formatNumber value="${inventory.price}" type="currency"/></h3>

                            <div style="float: right;">
                                <button type="button" class="btn btn-primary" style="float:right;" onclick="addToCart(${inventory.inv_uid}, '${inventory.condition}', $('#quantityPicker-${inventory.inv_uid}').val())">Add To Cart</button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

    </c:forEach>

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
    function addToCart(inv_uid, condition, quantity) {
        xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", '/cart?addItem=true&inv_uid='+inv_uid+'&condition='+condition+'&quantity='+quantity, true);
        xmlhttp.send();
        $('#'+inv_uid).show();
        setTimeout(function() {location.reload(true)}, 500);
    }
</script>

<jsp:include page="/include/Footer.jsp"/>