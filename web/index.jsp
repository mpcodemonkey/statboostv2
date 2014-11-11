<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 8/6/14
  Time: 11:21 AM
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>
<!-- Begin Page Content (No html, head, or body tags required here)-->
<link href="include/stylesheets/owl.carousel.css" rel="stylesheet">
<link href="include/stylesheets/owl.theme.css" rel="stylesheet">
<script src="include/javascripts/owl.carousel.js"></script>

<div id="fb-root"></div>
<script>(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.0";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>



<div>
    <div class="container">
        <div class="jumbotron">
            <div class="container row">
                <div class="col-sm-8 col-xs-9"><img src="/include/images/logo.png" style="max-width: 250px;" class="center-block"></div>
                <div class="visible-xs col-xs-12"><br /></div>
                <div class="col-sm-4 col-xs-12" role="navigation">
                    <div class="list-group">
                        <a href="/" class="list-group-item active">Home</a>
                        <c:if test="${sessionScope.admin != null}">
                            <a href="/admin/adminCP" class="list-group-item">Admin CP</a>
                        </c:if>
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
                </div>
            </div>
        </div>

        <div>
            <div class="container">
                <div class="row">
                    <div class="span12">
                        <div id="owl-demobanner" class="owl-carousel">

                            <div class="item"><img src="include/images/banner_temp_1.jpg" alt="The Last of us"></div>
                            <div class="item"><img src="include/images/banner_temp_2.jpg" alt="GTA V"></div>
                            <div class="item"><img src="include/images/banner_temp_3.jpg" alt="Mirror Edge"></div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12 col-sm-12">
            <div class="well well-lg">
                <iframe src="//www.facebook.com/plugins/likebox.php?href=https%3A%2F%2Fwww.facebook.com%2FExpLevelUp%3Frf%3D211284022311583&amp;width=750&amp;height=395&amp;colorscheme=light&amp;show_faces=false&amp;header=false&amp;stream=true&amp;show_border=false" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:100%; min-height:375px; " allowTransparency="true"></iframe>
            </div><!--/span-->
        </div>

        <!--<div class="row row-offcanvas row-offcanvas-right">
            <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
                <div class="list-group">
                    <a href="/" class="list-group-item active">Home</a>
                    <c:if test="${sessionScope.admin != null}">
                        <a href="/admin/adminCP" class="list-group-item">Admin CP</a>
                    </c:if>
                    <a href="/magicSearch" class="list-group-item">Magic Search</a>
                    <a href="/ygoSearch" class="list-group-item">Yu-Gi-Oh Search</a>
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
            </div>
        </div><!--/row-->



    </div>
</div>

<!--
<div>
    <div class="container">
        <div class="row">
            <div class="span12">

                <div id="owl-demo" class="owl-carousel">
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(5).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(4).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(1).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(2).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(4).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(3).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(5).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(4).jpg" alt="Owl Image"></div>
                </div>

            </div>
        </div>
    </div>

</div>

<div>
    <div class="container">
        <div class="row">
            <div class="span12">

                <div id="owl-demo2" class="owl-carousel">
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(1).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(2).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(3).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(4).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(5).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(6).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(1).jpg" alt="Owl Image"></div>
                    <div class="item"><img src="include/images/MTG%20Cards/mtgcard%20(2).jpg" alt="Owl Image"></div>
                </div>

            </div>
        </div>
    </div>
</div>

-->

<div class="container center-block">
    <div class="row">
        <div class="span12" style="text-align: center;">
            <div class="col-sm-4 col-xs-12">
                <div class="thumbnail">
                    <div class="caption">
                        <h3>Magic</h3>
                        <p>
                            content of stuff!!
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-xs-12">
                <div class="thumbnail">
                    <div class="caption">
                        <h3>Yu Gi Oh</h3>
                        <p>
                            content of stuff!!
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-xs-12">
                <div class="thumbnail">
                    <div class="caption">
                        <h3>Accessories</h3>
                        <p>
                            content of stuff!!
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var bugmuncher_options = {
        api_key: "b616434b2747fa2a83e6cb17d1c64435be75d176"
    };
    (function(){
        var node = document.createElement("script");
        node.setAttribute("type", "text/javascript");
        node.setAttribute("src", "//app.bugmuncher.com/js/bugMuncher.min.js");
        document.getElementsByTagName("head")[0].appendChild(node);
    })();
</script>


<!-- carousel styling -->
<style>
    #fb-root {
        display: none;
    }

    /* To fill the container and nothing else */
    .fb_iframe_widget, .fb_iframe_widget span, .fb_iframe_widget span iframe[style] {
        width: 100% !important;}


    #owl-demobanner .item img{
        display: block;
        width: 80%;
        height: auto;
        margin-left: auto;
        margin-right: auto;
    }
    #owl-demo .item{
        margin: 3px;
    }
    #owl-demo .item img{
        display: block;
        width: 75%;
        height: auto;
        margin-left: auto;
        margin-right: auto;
    }
    #owl-demo2 .item{
        margin: 3px;
    }
    #owl-demo2 .item img{
        display: block;
        width: 75%;
        height: auto;
        margin-left: auto;
        margin-right: auto;
    }
</style>

<script>
    $(document).ready(function() {
        $("#owl-demobanner").owlCarousel({

            autoPlay : 8000,
            stopOnHover : true,
            navigation: true,
            paginationSpeed : 1000,
            goToFirstSpeed : 2000,
            singleItem : true,
            autoHeight : true

            // "singleItem:true" is a shortcut for:
            // items : 1,
            // itemsDesktop : false,
            // itemsDesktopSmall : false,
            // itemsTablet: false,
            // itemsMobile : false

        });

        $("#owl-demo").owlCarousel({
            autoPlay: 3000,
            items : 5,
            itemsDesktop : [1199,3],
            itemsDesktopSmall : [979,3]
        });

        $("#owl-demo2").owlCarousel({
            autoPlay: 2500,
            items : 5,
            itemsDesktop : [1199,3],
            itemsDesktopSmall : [979,3]
        });

    });
</script>



<footer>
    <p align="center" style="color:white;">&copy; StatBoost 2014</p>
</footer>


<!-- End Page Content -->
<jsp:include page="/include/Footer.jsp"/>
