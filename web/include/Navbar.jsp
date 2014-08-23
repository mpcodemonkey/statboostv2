<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 8/3/2014
  Time: 10:23 PM
--%>



<!--
<script type="text/javascript">
    var basePath = '${pageContext.request.contextPath}';
</script>

<script>

        $("input.search").autocomplete({
            source: function( request, response ) {
                $.getJSON( "/autocomplete", {
                    term: request.term
                }, response );
            }

        });

</script>
-->

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
                <li class="active"><a href="/">Home</a></li>
                <li><a href="#store">Store</a></li>
                <li><a href="#about">About</a></li>
                <li><a href="#contact">Contact</a></li>
            </ul>
            <form class="navbar-form navbar-left" role="search" method="post" action="">
                <div class="form-group">
                    <input type="text" id="search" name="search" class="autocomplete form-control" data-url="/autocomplete">
                </div>
                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search" action=""></span></button>
            </form>

            <c:if test="${sessionScope.email != null}">
                <div class="btn-group">
                    <button type="button" class="btn navbar-btn navbar-right btn-success dropdown-toggle" data-toggle="dropdown">
                        <c:out value="${sessionScope.email}"/>&nbsp;<span class="glyphicon glyphicon-user"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="/profile">User Profile</a></li>
                        <c:if test="${sessionScope.admin != null}">
                            <li><a href="/adminCP">Admin CP</a></li>
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

