<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Jon
  Date: 9/8/2014
  Time: 6:08 PM
  To change this template use File | Settings | File Templates.
--%>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<link rel="stylesheet" href="/include/stylesheets/bootstrap-multiselect.css">
<link rel="stylesheet" href="/include/stylesheets/bootstrap-select.min.css">

<script src="/include/javascripts/bootstrap-multiselect.js"></script>
<script src="/include/javascripts/bootstrap-select.min.js"></script>

<link href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,400,300,600,700' rel='stylesheet' type='text/css'>

<link href="include/stylesheets/owl.carousel.css" rel="stylesheet">
<link href="include/stylesheets/owl.theme.css" rel="stylesheet">

<div id="title" class="center-block" name="title">
    <div class="container row col-sm-12 center-block">
        <div class="span12">
            <div id="owl-demo" class="owl-carousel">
                <div class="item"><img src="include/images/banner_temp_1.jpg" alt="The Last of us"></div>
                <div class="item"><img src="include/images/banner_temp_2.jpg" alt="GTA V"></div>
                <div class="item"><img src="include/images/banner_temp_3.jpg" alt="Mirror Edge"></div>
            </div>
        </div>
    </div>
</div>

<div class="row container col-sm-12 center-block">
    <div class="col-sm-3 hidden-xs" id="leftSide">
        <img style="width: 80%; max-width: 240px; height:auto;" src="include/images/SarkhanSide.PNG">
    </div>

    <div class="container col-sm-6">
    <div class="row">
            <div class="jumbotron">
                <img src="/include/images/yugiohlogo.png" style="width:80%; max-width: 350px; height: auto;" class="center-block" />
                <div class="row">
                    <div class="tabbed-search">
                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs" role="tablist">
                            <li class="active"><a href="#simple" role="tab" data-toggle="tab">Simple Search</a>
                            </li>
                            <li><a href="#advanced" role="tab" data-toggle="tab">Advanced Search</a>
                            </li>
                        </ul>

                        <!-- Tab panes -->
                        <div class="tab-content">
                            <div class="tab-pane active" id="simple">
                                <form action="" method="post" class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-lg-3 control-label" for="fi1">Search Text:</label>
                                        <div class="col-lg-8">
                                            <input type="text" class="form-control" id="fi1" name="fi1" placeholder="" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-3 control-label">Search By:</label>
                                        <br>
                                        <div class="col-lg-5">
                                                <span class="col-lg-4">
                                                    <input name="r1" type="radio" id="monster" value="monster">
                                                    <label>Monster</label>
                                                </span>
                                                <span class="col-lg-4">
                                                    <input name="r1" type="radio" id="spell" value="spell">
                                                    <label>Spell</label>
                                                </span>
                                                <span class="col-lg-4">
                                                    <input name="r1" type="radio" id="trap" value="trap">
                                                    <label>Trap</label>
                                                </span>
                                                <span class="col-lg-4">
                                                    <input checked="" name="r1" type="radio" id="all" value="all">
                                                    <label>All</label>
                                                </span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <span class="col-lg-3"></span>
                                        <button class="col-lg-4 btn btn-primary" type="submit" name="simpleSubmit">Search</button>
                                    </div>
                                </form>
                            </div>
                            <div class="tab-pane" id="advanced" name="advancedSubmit">
                                <form action="" method="post" >
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="control-label" for="nameInput">Name</label>
                                            <input type="text" class="form-control" id="nameInput" name="nameInput" placeholder="The name of the card">
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label" for="textInput">Card Text</label>
                                            <input type="text" class="form-control" id="textInput" name="textInput" placeholder="Ability or flavor text">
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label" for="effectInput">Pendulum Effect</label>
                                            <input type="text" class="form-control" id="effectInput" placeholder="For cards with pendulum effects">
                                        </div>
                                        <div class="form-group">


                                        </div>
                                        <div class="form-group">
                                            <div class="form-horizontal">




                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label class="control-label" for="formInput1">ATK</label>
                                                    <input type="text" class="form-control" id="formInput1" name="atkInput" placeholder="#">
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label class="control-label" for="formInput2">DEF</label>
                                                    <input type="text" class="form-control" id="formInput2" name="defInput" placeholder="#">
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label class="control-label" for="scaleInput">Pendulum</label>
                                                    <input type="text" class="form-control" id="scaleInput" name="scaleInput" placeholder="Scale #">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <!-- multiselect for card attribute -->
                                            <label class="control-label" for="attrib">Attribute</label>
                                            <br>
                                            <label class="control-label" for="ac1">and</label>
                                            <input type="checkbox" class="attribCheck" id="ac1">
                                            <label class="control-label" for="ac1">or</label>
                                            <input type="checkbox" class="attribCheck" id="ac2">
                                            <select class="form-control multiselect" multiple="multiple" id="attrib" name="attribInput">
                                                <c:forEach items="${requestScope.attribList}" var="attribs">
                                                    <option value="${attribs}">${attribs}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <!-- multiselect for card icon -->
                                            <label class="control-label" for="iconInput">Icon</label>
                                            <br>
                                            <label class="control-label" for="ic1">and</label>
                                            <input type="checkbox" class="iconCheck" id="ic1">
                                            <label class="control-label" for="ic2">or</label>
                                            <input type="checkbox" class="iconCheck" id="ic2">
                                            <select class="form-control multiselect" multiple="multiple" name="iconInput" id="iconInput">
                                                <c:forEach items="${requestScope.iconList}" var="icons">
                                                    <option value="${icons}">${icons}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <!-- multiselect for monster type -->
                                            <label class="control-label" for="monsterType">Monster Type</label>
                                            <br>
                                            <label class="control-label" for="mc1">and</label>
                                            <input type="checkbox" class="monsterCheck" id="mc1">
                                            <label class="control-label" for="mc2">or</label>
                                            <input type="checkbox" class="monsterCheck" id="mc2">
                                            <select class="form-control multiselect" multiple="multiple" id="monsterType" name="monsterType">
                                                <c:forEach items="${requestScope.mTypeList}" var="mTypes">
                                                    <option value="${mTypes}">${mTypes}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <!-- multiselect for card Type(not type) -->
                                            <label class="control-label" for="cardTypeInput">Card Type</label>
                                            <br>
                                            <label class="control-label" for="cc1">and</label>
                                            <input type="checkbox" class="cardCheck" id="cc1">
                                            <label class="control-label" for="cc2">or</label>
                                            <input type="checkbox" class="cardCheck" id="cc2">
                                            <select class="form-control multiselect" multiple="multiple" id="cardTypeInput" name="cardTypeInput" >
                                                <option value="effect">Effect</option>
                                                <option value="flip">Flip</option>
                                                <option value="fusion">Fusion</option>
                                                <option value="gemini">Gemini</option>
                                                <option value="normal">Normal</option>
                                                <option value="pendulum">Pendulum</option>
                                                <option value="ritual">Ritual</option>
                                                <option value="spirit">Spirit</option>
                                                <option value="synchro">Synchro</option>
                                                <option value="toon">Toon</option>
                                                <option value="tuner">Tuner</option>
                                                <option value="union">Union</option>
                                                <option value="xyz">Xyz</option>
                                            </select>
                                        </div>
                                        <input type="submit" name="advancedSubmit" value="Search">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-sm-3 hidden-xs" id="rightSide" name="rightSide">
        <img style="width: 80%; max-width: 240px; height:auto; float:right;" src="include/images/SorinSide.PNG">
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

<style>
    #owl-demo .item img{
        display: block;
        width: 90%;
        height: auto;
        margin: 0 auto;
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

<jsp:include page="/include/Footer.jsp"/>

<script type="text/javascript">
    $(document).ready(function() {
        $('.multiselect').multiselect({
            maxHeight: 200
        });
    });
</script>

<!-- javascript for check box logic (and or or, not both) -->
<script type="text/javascript">
    $('input.attribCheck').on('change', function() {
        $('input.attribCheck').not(this).prop('checked', false);
    });

    $('input.iconCheck').on('change', function() {
        $('input.iconCheck').not(this).prop('checked', false);
    });

    $('input.monsterCheck').on('change', function() {
        $('input.monsterCheck').not(this).prop('checked', false);
    });

    $('input.cardCheck').on('change', function() {
        $('input.cardCheck').not(this).prop('checked', false);
    });
</script>