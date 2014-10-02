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



<link href="include/stylesheets/bootstrapTheme.css" rel="stylesheet">
<link href="include/stylesheets/custom.css" rel="stylesheet">
<link href="include/stylesheets/owl.carousel.css" rel="stylesheet">
<link href="include/stylesheets/owl.theme.css" rel="stylesheet">
<link href="include/stylesheets/prettify.css" rel="stylesheet">


<div>
    <div class="container">


        <div id="demo">
            <div class="container">
                <div class="row">
                    <div class="span12">
                        <div id="owl-demobanner" class="owl-carousel">

                            <div class="item"><img src="include/images/MagicBanner1.jpg" alt="The Last of us"></div>
                            <div class="item"><img src="include/images/frontitem2.jpg" alt="GTA V"></div>
                            <div class="item"><img src="include/images/frontitem3.jpg" alt="Mirror Edge"></div>

                        </div>
                    </div>
                </div>
            </div>
        </div>





        <div class="row row-offcanvas row-offcanvas-right">
            <div class="col-xs-12 col-sm-9">
                     <!-- PUT THE FEED HEREEEEE!!-->
            </div><!--/span-->


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
            </div><!--/span-->
        </div><!--/row-->


    </div>
</div>

<div id="demo">
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

<div id="demo">
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





<script src="include/javascripts/jquery-1.9.1.min.js"></script>
<script src="include/javascripts/owl.carousel.js"></script>


<!-- Demo -->

<style>
    #owl-demobanner .item img{
        display: block;
        width: 100%;
        height: auto;
    }
</style>

<style>
    #owl-demo .item{
        margin: 3px;
    }
    #owl-demo .item img{
        display: block;
        width: 100%;
        height: auto;
    }
</style>


<script>
    $(document).ready(function() {
        $("#owl-demobanner").owlCarousel({

            navigation : true,
            slideSpeed : 300,
            paginationSpeed : 400,
            singleItem : true

            // "singleItem:true" is a shortcut for:
            // items : 1,
            // itemsDesktop : false,
            // itemsDesktopSmall : false,
            // itemsTablet: false,
            // itemsMobile : false

        });
    });
</script>

<script>
    $(document).ready(function() {
        $("#owl-demo").owlCarousel({
            autoPlay: 3000,
            items : 4,
            itemsDesktop : [1199,3],
            itemsDesktopSmall : [979,3]
        });

    });
</script>

<script>
    $(document).ready(function() {
        $("#owl-demo2").owlCarousel({
            autoPlay: 2500,
            items : 4,
            itemsDesktop : [1199,3],
            itemsDesktopSmall : [979,3]
        });

    });
</script



<footer>
    <p align="center">&copy; StatBoost 2014</p>
</footer>


<!-- End Page Content -->

<script src="include/javascripts/bootstrap-collapse.js"></script>
<script src="include/javascripts/bootstrap-transition.js"></script>
<script src="include/javascripts/bootstrap-tab.js"></script>
<script src="include/javascripts/google-code-prettify/prettify.js"></script>
<script src="include/javascripts/application.js"></script>
<jsp:include page="/include/Footer.jsp"/>
