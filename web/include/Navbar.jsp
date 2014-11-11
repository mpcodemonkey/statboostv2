<%@ page import="com.statboost.models.session.ShoppingCartSessionObject" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 8/3/2014
  Time: 10:23 PM
--%>

<style type="text/css">
    .dropdown:hover .dropdown-menu {
        display: block;
    }
</style>

<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">StatBoost</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <!--<li><a href="/">Home</a></li>-->
                <li><a href="/Events">Events</a></li>
                <li><a href="/Store">Store</a></li>
                <li class="dropdown"><!--"/Games.jsp"-->
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        Games <span class="caret"></span>
                    </a>
                        <ul class="dropdown-menu">
                            <li><a href="/magicSearch">Magic: The Gathering</a></li>
                            <!--<li><a href="/YugiohMain.jsp">Yu Gi Oh</a></li>-->
                            <li><a href="/ygoSearch">Yu Gi Oh</a></li>
                        </ul>
                </li>
                <li><a href="/AboutUs.jsp">Contact Us</a></li>
            </ul>

            <form class="navbar-form navbar-left" role="search" method="get" action="/magicSearch">
                <div class="form-group">
                    <input type="text" id="search" name="cardName" class="autocomplete form-control" data-url="/autocomplete">
                </div>
                &nbsp;<span class="glyphicon glyphicon-search" style="color:white" title="Type a card name to search for."></span>
            </form>

            <c:if test="${sessionScope.shoppingCart != null}">
                <%
                    int cartSize = ((ShoppingCartSessionObject)session.getAttribute("shoppingCart")).getCartItems().size();
                    session.setAttribute("cartSize", cartSize);
                %>
                <div style="float:right;">
                    <button class="btn btn-sm navbar-btn" onclick="window.location='/cart'"><span class="glyphicon glyphicon-shopping-cart"></span> Cart &nbsp;<span class="badge">${cartSize}</span></button>
                </div>
            </c:if>

            <c:if test="${sessionScope.email != null}">
                <div class="btn-group" style="max-width: 50px;">
                    <button type="button" class="btn navbar-btn btn-success dropdown-toggle" data-toggle="dropdown">
                        <span class="glyphicon glyphicon-user"></span>&nbsp;<span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li role="presentation" class="dropdown-header"><span style="font-size: 20px;"><c:out value="${sessionScope.email}"/></span></li>
                        <li class="divider"></li>
                        <li><a href="/user/profile">User Profile</a></li>
                        <li><a href="/user/orderhistory">Order History</a></li>
                        <c:if test="${sessionScope.admin != null}">
                            <li><a href="/admin/adminCP">Admin CP</a></li>
                        </c:if>
                        <li class="divider"></li>
                        <li><a href="/logout">Logout</a></li>
                    </ul>
                </div>
            </c:if>

        </div><!-- /.nav-collapse -->
    </div><!-- /.container -->
</div><!-- /.navbar -->
<br><br><br>
<%--Alert Handler--%>
<c:if test="${requestScope.navAlert != null && requestScope.alertType != null}">
    <div class="alert alert-${requestScope.alertType} fade in">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <strong>Alert: </strong> <c:out value="${requestScope.navAlert}" />
    </div>
</c:if>

