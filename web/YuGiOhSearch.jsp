<%--
  Created by IntelliJ IDEA.
  User: Jon
  Date: 9/8/2014
  Time: 6:08 PM
  To change this template use File | Settings | File Templates.
--%>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>
<script src="/include/javascripts/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="/include/stylesheets/bootstrap-multiselect.css">

<div class="container">
    <div class="row">
        <div class="col-md-10">
            <div class="jumbotron">
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
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <form action="" method="post">
                                            <label class="control-label" for="fi1">Name</label>
                                            <input type="text" class="form-control" id="fi1" name="fi1" placeholder="">
                                            <label>Card Type:</label>
                                            <input name="r1" type="radio" id="monster" value="monster">
                                            <label>Monster</label>
                                            <input name="r1" type="radio" id="spell" value="spell">
                                            <label>Spell</label>
                                            <input name="r1" type="radio" id="trap" value="trap">
                                            <label>Trap</label>
                                            <input checked="" name="r1" type="radio" id="all" value="all">
                                            <label>All</label>
                                            <br>
                                            <input type="submit" name="simpleSubmit" value="Search">
                                        </form>
                                    </div>
                                </div>
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
                                                    <label class="control-label" for="formInput3">Pendulum</label>
                                                    <input type="text" class="form-control" id="formInput3" name="scaleInput" placeholder="Scale #">
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
                                                <option value="dark">Dark</option>
                                                <option value="light">Light</option>
                                                <option value="earth">Earth</option>
                                                <option value="water">Water</option>
                                                <option value="fire">Fire</option>
                                                <option value="wind">Wind</option>
                                                <option value="divine">Divine</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <!-- multiselect for card icon -->
                                            <label class="control-label" for="icon">Icon</label>
                                            <br>
                                            <label class="control-label" for="ic1">and</label>
                                            <input type="checkbox" class="iconCheck" id="ic1">
                                            <label class="control-label" for="ic2">or</label>
                                            <input type="checkbox" class="iconCheck" id="ic2">
                                            <select class="form-control multiselect" multiple="multiple" name="iconInput" id="icon">
                                                <option value="equip">Equip</option>
                                                <option value="field">Field</option>
                                                <option value="quick-play">Quick-Play</option>
                                                <option value="ritual">Ritual</option>
                                                <option value="continuous">Continuous</option>
                                                <option value="counter">Counter</option>
                                                <option value="normal">Normal</option>
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
                                            <select class="form-control multiselect" multiple="multiple" id="monsterType" name="monsterTypeInput">
                                                <option value="aqua">Aqua</option>
                                                <option value="beast">Beast</option>
                                                <option value="beast-warrior">Beast-Warrior</option>
                                                <option value="dinosaur">Dinosaur</option>
                                                <option value="divine-beast">Divine-Beast</option>
                                                <option value="dragon">Dragon</option>
                                                <option value="fairy">Fairy</option>
                                                <option value="fiend">Fiend</option>
                                                <option value="fish">Fish</option>
                                                <option value="insect">Insect</option>
                                                <option value="machine">Machine</option>
                                                <option value="plant">Plant</option>
                                                <option value="psychic">Psychic</option>
                                                <option value="pyro">Pyro</option>
                                                <option value="reptile">Reptile</option>
                                                <option value="warrior">Warrior</option>
                                                <option value="winged beast">Winged Beast</option>
                                                <option value="zombie">Zombie</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <!-- multiselect for card Type(not type) -->
                                            <label class="control-label" for="cardType">Card Type</label>
                                            <br>
                                            <label class="control-label" for="cc1">and</label>
                                            <input type="checkbox" class="cardCheck" id="cc1">
                                            <label class="control-label" for="cc2">or</label>
                                            <input type="checkbox" class="cardCheck" id="cc2">
                                            <select class="form-control multiselect" multiple="multiple" id="cardType" name="cardTypeInput" >
                                                <option value="effect">Effect</option>
                                                <option value="flip">Flip</option>
                                                <option value="fusion">Fusion</option>
                                                <option value="gemini">Gemini</option>
                                                <option value="normal">Normal</option>
                                                <option value="pendulum">Pendulum</option>
                                                <option value="ritual">Ritual</option>
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
</div>

<jsp:include page="/include/Footer.jsp"/>

<!-- Initialize the plugin: -->
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