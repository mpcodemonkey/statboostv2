<%--
  Created by IntelliJ IDEA.
  User: Sam & Jon, like bosses!
  Date: 8/6/2014
  Time: 4:42 PM
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<link rel="stylesheet" href="/include/stylesheets/bootstrap-multiselect.css">
<link rel="stylesheet" href="/include/stylesheets/bootstrap-select.min.css">

<script src="/include/javascripts/bootstrap-multiselect.js"></script>
<script src="/include/javascripts/bootstrap-select.min.js"></script>


<!-- scripts at bottom, faster load time: -->

<link href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,400,300,600,700' rel='stylesheet' type='text/css'>


<!-- Owl Carousel Assets -->
<link href="include/stylesheets/owl.carousel.css" rel="stylesheet">
<link href="include/stylesheets/owl.theme.css" rel="stylesheet">

<!--<link href="/include/javascripts/google-code-prettify/prettify.css" rel="stylesheet">-->


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

    <div class="col-sm-6 container" id="searcher">

        <c:if test="${requestScope.card != null}">
            <div class="alert alert-info fade in">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <strong>Test Result:</strong> <c:out value="${requestScope.card.cardName}" /> has been retrieved from the database.
            </div>
        </c:if>

        <div class="row">
            <div class="jumbotron">
                <img src="/include/images/mtglogo.png" style="width:80%; max-width: 350px; height: auto;" class="center-block" />
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
                            <div class="tab-pane active fade in" id="simple" name="simple" style="">
                                <!--<div class="col-md-6">-->
                                <form action="" method="post" id="searchFailSafe1" class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-lg-3 control-label" for="fi1">Search Text:</label>
                                        <div class="col-lg-8">
                                            <input type="text" class="form-control" id="fi1" name="fi1" placeholder="" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-3 control-label">Search By:</label>
                                        <div class="col-lg-5">
                                                                <span class="col-lg-4">
                                                                    <input name="r1" type="radio" id="cName" value="cName" checked>
                                                                    <label>Name</label>
                                                                </span>
                                                                <span class="col-lg-4">
                                                                    <input name="r1" type="radio" id="cType" value="cType">
                                                                    <label>Type</label>
                                                                </span>
                                                                <span class="col-lg-4">
                                                                    <input name="r1" type="radio" id="cText" value="cText">
                                                                    <label>Text</label>
                                                                </span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <span class="col-lg-3"></span>
                                        <input class="col-lg-4 btn btn-primary" type="submit" name="simpleSubmit" id="simpleSubmit" value="Search">
                                        <input type="hidden" name="simpleForm" value="simpleForm">
                                    </div>
                                </form>
                                <!--</div>-->
                            </div>
                            <div class="tab-pane fade" id="advanced" name="advanced">
                                <form action="" method="post">
                                    <div class="col-md-6">
                                        <br />
                                        <div class="form-group">
                                            <label class="control-label" for="magicCardName">Name</label>
                                            <input type="text" class="form-control" id="magicCardName" name="magicCardName" placeholder="The name of the card">
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label" for="type">Type</label>
                                            <input type="text" class="form-control" id="type" name="type" placeholder="Card Supertype (eg Creature, Land)">
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label" for="subType">Subtype</label>
                                            <input type="text" class="form-control" id="subType" name="subType" placeholder="eg: Goblin, Homarid">
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label" for="rulesContain">Rules Text</label>
                                            <input type="text" class="form-control" id="rulesContain" name=rulesContain" placeholder="Abilities or errata">
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label" for="cmcModifier">Mana Cost</label>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-2">
                                                <div class="form-group">
                                                    <select name="cmcModifier" id="cmcModifier" class="selectpicker" data-width="auto">
                                                        <option value="=">=</option>
                                                        <option value="<">&lt;</option>
                                                        <option value=">">&gt;</option>
                                                        <option value="<=">&le;</option>
                                                        <option value=">=">&ge;</option>
                                                        <option value="!=">!=</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <input type="text" class="form-control" id="cmc" name="cmc" placeholder="#">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <!-- multiselect for card attribute -->
                                            <label class="control-label" for="colors">Colors</label>
                                            <br>
                                            <label class="control-label" for="ac1">and</label>
                                            <input type="radio" name="attribCheck" id="ac1" value="and">
                                            <label class="control-label" for="ac1">or</label>
                                            <input type="radio" name="attribCheck" id="ac2" value="or">
                                            <select class="form-control multiselect" multiple="multiple" id="colors" name="colors">
                                                <option value="white">White</option>
                                                <option value="blue">Blue</option>
                                                <option value="black">Black</option>
                                                <option value="red">Red</option>
                                                <option value="green">Green</option>
                                            </select>
                                        </div>
                                        <div class="row">
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <br />
                                        <div class="form-group">
                                            <!-- multiselect for card icon -->
                                            <label class="control-label" for="rarities">Rarity</label>
                                            <br />
                                            <label class="control-label" for="ic1">and</label>
                                            <input type="radio" name="rarity" id="ic1" value="and">
                                            <label class="control-label" for="ic2">or</label>
                                            <input type="radio" name="rarity" id="ic2" value="or">
                                            <select class="form-control multiselect" multiple="multiple" name="rarities" id="rarities">
                                                <option value="common">Common</option>
                                                <option value="uncommon">Uncommon</option>
                                                <option value="rare">Rare</option>
                                                <option value="mythic rare">Mythic Rare</option>
                                                <option value="special">Special</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <!-- multiselect for monster type -->
                                            <label class="control-label" for="setID">Expansion</label>
                                            <br>
                                            <label class="control-label" for="mc1">and</label>
                                            <input type="radio" name="expansion" id="mc1" value="and">
                                            <label class="control-label" for="mc2">or</label>
                                            <input type="radio" name="expansion" id="mc2" value="or">
                                            <select class="form-control multiselect" multiple="multiple" id="setID" name="setID">
                                                <optgroup>
                                                    <c:forEach items="${requestScope.expansionList}" var="expansion">
                                                        <option value="${expansion}">${expansion}</option>
                                                    </c:forEach>
                                                </optgroup>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <!-- multiselect for card Type(not type) -->
                                            <label class="control-label" for="format">Format</label>
                                            <br>
                                            <label class="control-label" for="cc1">and</label>
                                            <input type="radio" name="format" id="cc1" value="and">
                                            <label class="control-label" for="cc2">or</label>
                                            <input type="radio" name="format" id="cc2" value="or">
                                            <select class="form-control multiselect" multiple="multiple" id="format" name="format">
                                                <option value="Classic">Classic</option>
                                                <option value="Commander">Commander</option>
                                                <option value="Extended">Extended</option>
                                                <option value="Freeform">Freeform</option>
                                                <option value="Ice Age Block">Ice Age Block</option>
                                                <option value="Innistrad Block">Innistrad Block</option>
                                                <option value="Invasion Block">Invasion Block</option>
                                                <option value="Kamigawa Block">Kamigawa Block</option>
                                                <option value="Legacy">Legacy</option>
                                                <option value="Lorwyn-Shadowmoor Block">Lorwyn-Shadowmoor Block</option>
                                                <option value="Masques Block">Masques Block</option>
                                                <option value="Mirage Block">Mirage Block</option>
                                                <option value="Mirrodin Block">Mirrodin Block</option>
                                                <option value="Modern">Modern</option>
                                                <option value="Odyssey Block">Odyssey Block</option>
                                                <option value="Onslaught Block">Onslaught Block</option>
                                                <option value="Prismatic">Prismatic</option>
                                                <option value="Ravnica Block">Ravnica Block</option>
                                                <option value="Return to Ravnica Block">Return to Ravnica Block</option>
                                                <option value="Scars of Mirrodin Block">Scars of Mirrodin Block</option>
                                                <option value="Shards of Alara Block">Shards of Alara Block</option>
                                                <option value="Singleton 100">Singleton 100</option>
                                                <option value="Standard">Standard</option>
                                                <option value="Tempest Block">Tempest Block</option>
                                                <option value="Theros Block">Theros Block</option>
                                                <option value="Time Spiral Block">Time Spiral Block</option>
                                                <option value="Tribal Wars Legacy">Tribal Wars Legacy</option>
                                                <option value="Tribal Wars Standard">Tribal Wars Standard</option>
                                                <option value="Un-Sets">Un-Sets</option>
                                                <option value="Urza Block">Urza Block</option>
                                                <option value="Vintage">Vintage</option>
                                                <option value="Zendikar Block">Zendikar Block</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <!-- multiselect for card Type(not type) -->
                                            <label class="control-label" for="orderBy">Sort By</label>
                                            <br>
                                            <select name="orderBy" id="orderBy" class="selectpicker">
                                                <option value="mcrMultiverseId">Multiverse ID</option>
                                                <option value="mcrCardName">Name</option>
                                                <option value="mcrRarity">Rarity</option>
                                                <option value="mcrPower">Power</option>
                                                <option value="mcrToughness">Toughness</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label" for="order">Sort Order</label>
                                            <br>
                                            <select name="order" id="order" class="selectpicker">
                                                <option value="asc">Ascending</option>
                                                <option value="desc">Descending</option>
                                            </select>
                                        </div>
                                        <input class="btn btn-primary" type="submit" name="advancedSubmit" id="advancedSubmit" value="Search">
                                        <input type="hidden" name="advancedForm" value="advancedForm">
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


<!-- Demo -->

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





<script type="text/javascript">
    $(document).ready(function() {
        $('.multiselect').multiselect({
            maxHeight: 200
        });
    });

    $(document).ready(function (){
        $('#searchFailSafe1').bootstrapValidator({
            container: 'tooltip',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields:{
                fi1:{
                    validators:{
                        notEmpty:{
                            message: 'Your search cannot be blank'
                        },
                        stringLength: {
                            enabled: true,
                            min: 3,
                            message: 'Your search must be 3 or more characters'
                        }
                    }
                },

                r1:{
                    validators:{
                        choice:{
                            min:1,
                            message: 'Your search must be for Name, Type and/or Text'
                        }
                    }
                }
            }
        })
    });
</script>



<script src="include/javascripts/bootstrap-collapse.js"></script>
<script src="include/javascripts/bootstrap-transition.js"></script>
<script src="include/javascripts/bootstrap-tab.js"></script>

<script src="include/javascripts/google-code-prettify/prettify.js"></script>
<script src="include/javascripts/application.js"></script>


<jsp:include page="/include/Footer.jsp"/>


