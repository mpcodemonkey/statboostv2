<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <div class="row">
                    <div class="col-md-4">
                        <img class="media-object img-rounded img-responsive" src="http://placehold.it/350x250" alt="placehold.it/350x250">
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
                            <div class="col-md-4">
                                <select id="quantityPicker" class="form-control">
                                    <c:forEach begin="1" end="${inventory.quantity}" varStatus="loop">
                                        <option>${loop.index}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="control-label" for="quantityPicker">Quantity</label>
                            <br>

                            <label class="control-label" for="priceLabel">Price:</label>
                            <label class="control-label" id="priceLabel">${inventory.price}</label>
                        </div>
                        <button type="button" class="btn btn-default" style="float:right;">Add to cart</button>
                    </div>
                </div>
            </div>
        </div>

    </c:forEach>


    <ul class="pagination">
        <c:if test="${currentPage != 1}">
            <li><a href="inventorySearch?page=${currentPage - 1}">Previous</a></li>
        </c:if>
        <c:forEach begin="1" end="${numberOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <li class="active"><a href="#">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="inventorySearch?page=${i}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage lt numberOfPages}">
            <li><a href="inventorySearch?page=${currentPage + 1}">Next</a></li>
        </c:if>
    </ul>
</div>

<jsp:include page="/include/Footer.jsp"/>