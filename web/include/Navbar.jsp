<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 8/3/2014
  Time: 10:23 PM
--%>

<script src="/include/javascripts/autocomplete.js" type="text/javascript"></script>
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
                    <input type="text" id="search" name="search" class="autocomplete form-control" data-url="">
                </div>
                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search" action=""></span></button>
            </form>
            <!--
            @if(session().contains("email")) {
                <div class="btn-group">
                    <button type="button" class="btn navbar-btn navbar-right btn-success dropdown-toggle" data-toggle="dropdown">
                        @session().get("email").substring(0, session.get("email").indexOf('@'))&nbsp;<span class="glyphicon glyphicon-user"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="/profile">User Profile</a></li>
                        @if(session().containsKey("admin")) {
                        <li><a href="/adminCP">Admin CP</a></li>
                        }
                        <li class="divider"></li>
                        <li><a href="/logout">Logout</a></li>
                    </ul>
                </div>
            }
            -->
        </div><!-- /.nav-collapse -->
    </div><!-- /.container -->
</div><!-- /.navbar -->
<br><br><br>

<!--
@if(flash().contains("message") && flash().contains("type")) {
    <div class="alert alert-@flash().get("type") fade in">
    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
    <strong>@flash().get("message")</strong>
    </div>
}

-->
