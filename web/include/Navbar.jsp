<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 8/3/2014
  Time: 10:23 PM
--%>


<script type="text/css">
    ul.nav li.dropdown:hover ul.dropdown-menu{
        display: block;
    }
</script>

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

                <li><a href="/">Home</a></li>
                <li><a href="/events">Events</a></li>
                <li><a href="#store">Store</a></li>
                <li class="dropdown"><a href="/Games.jsp">Games</a>
                        <ul class="dropdown-menu">
                        <li><a href="/MagicMain.jsp">Magic: The Gathering</a></li>
                        <li><a href="/YugiohMain.jsp">Yu Gi Oh</a></li>
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

            <c:if test="${sessionScope.email != null}">
                <div class="btn-group">
                    <button type="button" class="btn navbar-btn navbar-right btn-success dropdown-toggle" data-toggle="dropdown">
                        <c:out value="${sessionScope.email}"/>&nbsp;<span class="glyphicon glyphicon-user"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="/profile">User Profile</a></li>
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

