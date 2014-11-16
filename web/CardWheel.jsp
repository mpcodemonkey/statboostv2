<%--
  Created by IntelliJ IDEA.
  User: Alex Villucci
  Date: 9/17/2014
  Time: 7:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>



<link href="include/stylesheets/bootstrapTheme.css" rel="stylesheet">
<link href="include/stylesheets/custom.css" rel="stylesheet">
<link href="include/stylesheets/owl.carousel.css" rel="stylesheet">
<link href="include/stylesheets/owl.theme.css" rel="stylesheet">
<link href="include/stylesheets/prettify.css" rel="stylesheet">


<div id="title">
    <div class="container">
        <div class="row">
            <div class="span12">
                <h1>Content with Images</h1>
            </div>
        </div>
    </div>
</div>

<div id="demo">
    <div class="container">
        <div class="row">
            <div class="span12">

                <div id="owl-demo" class="owl-carousel">
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
            autoPlay: 3000,
            items : 4,
            itemsDesktop : [1199,3],
            itemsDesktopSmall : [979,3]
        });

    });
</script>

<script src="include/javascripts/bootstrap-collapse.js"></script>
<script src="include/javascripts/bootstrap-transition.js"></script>
<script src="include/javascripts/bootstrap-tab.js"></script>
<script src="include/javascripts/google-code-prettify/prettify.js"></script>
<script src="include/javascripts/application.js"></script>
<jsp:include page="/include/Footer.jsp"/>