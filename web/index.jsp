<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 8/6/14
  Time: 11:21 AM
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>


<div>
<!--
    @if(flash().contains("success")) {
    <div class="alert alert-info fade in">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        <strong>@flash().get("success")</strong>
    </div>
    }
    @if(flash().contains("forbidden")) {
    <div class="alert alert-warning fade in">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        <strong>@flash().get("forbidden")</strong>
    </div>
    }
    -->


    <div class="container">
        <div class="row row-offcanvas row-offcanvas-right">
            <div class="col-xs-12 col-sm-9">
                <p class="pull-right visible-xs">
                    <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
                </p>
                <div class="jumbotron">

                    <h1><img src="/include/images/logo.png" style="max-width: 100px;"> StatBoost v1.15</h1>
                    <p>This is an example to showing the offcanvas layout pattern in Bootstrap. Feel free to play around with Bootstrap and different templates.
                        A lot of this content (such as the wording below) could be dynamically generated by Scala code, with info passed in from dedicated controllers.
                    </p>
                </div>

                <div class="row">
                    <div class="col-6 col-sm-6 col-lg-4">
                        <h2>Heading</h2>
                        <p>Jon figures Catherine. Catherine nests on top of a drained verse. On top of each elephant's ribbon grows Jon. Catherine cries next to the shifting evidence. The guideline benches Jon under the designer textbook.</p>
                        <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
                    </div><!--/span-->
                    <div class="col-6 col-sm-6 col-lg-4">
                        <h2>Heading</h2>
                        <p>How will whatever hierarchy fool a beating landscape? Against Alex matures the popular calm. Jon challenges the intent drill. The trial scarf influences Jon. Jon spans Alex inside the biology. Should each civilian cough?</p>
                        <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
                    </div><!--/span-->
                    <div class="col-6 col-sm-6 col-lg-4">
                        <h2>Heading</h2>
                        <p>Does the comfortable tribe score around Jessica? A horrendous ratio primes Catherine into a spiritual domestic. Catherine refreshes Jessica. The power prepares his lost stray.</p>
                        <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
                    </div><!--/span-->
                </div><!--/row-->
            </div><!--/span-->


            <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
                <div class="list-group">
                    <a href="/" class="list-group-item active">Home</a>
                    <c:if test="${sessionScope.admin != null}">
                        <a href="/admin/adminCP" class="list-group-item">Admin CP</a>
                    </c:if>
                    <a href="/magicSearch" class="list-group-item">Card Search</a>
                    <c:if test="${sessionScope.email == null}">
                        <a href="/login" class="list-group-item">Login</a>
                    </c:if>
                    <c:if test="${sessionScope.email == null}">
                        <a href="/register" class="list-group-item">Register</a>
                    </c:if>
                    <c:if test="${sessionScope.email != null}">
                        <a href="/logout" class="list-group-item">Logout</a>
                    </c:if>
                </div>
            </div><!--/span-->
        </div><!--/row-->

        <footer>
            <p align="center">&copy; StatBoost 2014</p>
        </footer>
    </div>
</div>
<jsp:include page="/include/Footer.jsp"/>
