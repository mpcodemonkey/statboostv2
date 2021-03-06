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

<link href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,400,300,600,700' rel='stylesheet' type='text/css'>


<!-- Owl Carousel Assets -->
<link href="include/stylesheets/owl.carousel.css" rel="stylesheet">
<link href="include/stylesheets/owl.theme.css" rel="stylesheet">

<link href="/include/javascripts/google-code-prettify/prettify.css" rel="stylesheet">

<div id="title">
    <div class="container">
        <div class="row">
            <div class="span12">
                <h1>
                    <img alt="" src="include/images/YugiohBanner.jpg" style="width: 1024px; height: 250px"></h1>
            </div>
        </div>
    </div>
</div>


<span class="col-lg-3">

<div class="item" style="float: right ;"><img src="include/images/SarkhanSide.PNG"></div>
</span>



<span class="col-lg-6">
    <div id="demo" style="float: left;">
        <div class="container">
            <div class="row">

                <div class="span12">
                    <div id="owl-demo" class="owl-carousel">

                        <div class="item"><img src="include/images/MagicBanner1.jpg" alt="The Last of us"></div>
                        <div class="item"><img src="include/images/frontitem2.png" alt="GTA V"></div>
                        <div class="item"><img src="include/images/frontitem3.png" alt="Mirror Edge"></div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</span>

<span class="col-lg-3">

<div class="item" style="float: left;"><img src="include/images/SorinSide.PNG"></div>
</span>


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
                    <div class="item"><img src="assets/owl1.jpg" alt="Owl Image"></div>
                    <div class="item"><img src="assets/owl2.jpg" alt="Owl Image"></div>
                    <div class="item"><img src="assets/owl3.jpg" alt="Owl Image"></div>
                    <div class="item"><img src="assets/owl4.jpg" alt="Owl Image"></div>
                    <div class="item"><img src="assets/owl5.jpg" alt="Owl Image"></div>
                    <div class="item"><img src="assets/owl6.jpg" alt="Owl Image"></div>
                    <div class="item"><img src="assets/owl7.jpg" alt="Owl Image"></div>
                    <div class="item"><img src="assets/owl8.jpg" alt="Owl Image"></div>
                </div>

            </div>
        </div>
    </div>

</div>
</pre>

</div>


</div>
</div><!--End Tab Content-->

</div>
</div>
</div>
</div>


<div id="footer">
    <div class="container">
        <div class="row">
            <div class="span12">
                <h5>Team JJACS Prototype
                    <a href="mailto:teamjjacs@gmail.com?subject=Hey Owl!">email</a>
                    <script>
                        var owldomain = window.location.hostname.indexOf("owlgraphic");
                        if(owldomain !== -1){
                            !function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document, 'script', 'twitter-wjs');
                        }
                    </script>
                </h5>
            </div>
        </div>
    </div>
</div>



<script src="include/javascripts/owl.carousel.min.js"></script>


<!-- Demo -->

<style>
    #owl-demo .item img{
        display: block;
        width: 100%;
        height: auto;
    }
</style>


<script>
    $(document).ready(function() {
        $("#owl-demo").owlCarousel({

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

<script src="include/javascripts/bootstrap-collapse.js"></script>
<script src="include/javascripts/bootstrap-transition.js"></script>
<script src="include/javascripts/bootstrap-tab.js"></script>

<script src="include/javascripts/google-code-prettify/prettify.js"></script>
<script src="include/javascripts/application.js"></script>


<jsp:include page="/include/Footer.jsp"/>