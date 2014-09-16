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

<c:if test="${requestScope.card != null}">
    <div class="alert alert-info fade in">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <strong>Test Result:</strong> <c:out value="${requestScope.card.cardName}" /> has been retrieved from the database.
    </div>
</c:if>

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
                            <div class="tab-pane active fade in" id="simple" style="">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <form action="" method="post">
                                            <label class="control-label" for="fi1">Card Search</label>
                                            <input type="text" class="form-control" id="fi1" name="fi1" placeholder="">
                                            <label>Search By:</label>
                                            <input name="r1" type="checkbox" id="cName" value="cName">
                                            <label>Name</label>
                                            <input name="r1" type="checkbox" id="cType" value="cType">
                                            <label>Type</label>
                                            <input name="r1" type="checkbox" id="cText" value="cText">
                                            <label>Text</label>
                                            <br>
                                            <input type="submit" name="simpleSubmit" value="Search">
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="advanced" name="advancedSubmit">
                                <form action="" method="post">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="control-label" for="cardName">Name</label>
                                            <input type="text" class="form-control" id="cardName" name="cardName" placeholder="The name of the card">
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
										<div class="form-group">
											<!-- multiselect for card icon -->
											<label class="control-label" for="rarities">Rarity</label>
											<br>
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
												<optgroup label="Core Sets">
													<option value="LEA">Limited Edition Alpha</option>
													<option value="LEB">Limited Edition Beta</option>
													<option value="2ED">Unlimited Edition</option>
													<option value="3ED">Revised Edition</option>
													<option value="4ED">4th Edition</option>
													<option value="5ED">5th Edition</option>
													<option value="6ED">6th Edition</option>
													<option value="7ED">7th Edition</option>
													<option value="8ED">8th Edition</option>
													<option value="9ED">9th Edition</option>
													<option value="10E">10th Edition</option>
													<option value="M10">2010 Core Set</option>
													<option value="M11">2011 Core Set</option>
													<option value="M12">2012 Core Set</option>
													<option value="M13">2013 Core Set</option>
													<option value="M14">2014 Core Set</option>
												</optgroup>
												<optgroup label="Expansions">
													<option value="ARN">Arabian Nights</option>
													<option value="ATQ">Antiquities</option>
													<option value="LEG">Legends</option>
													<option value="DRK">The Dark</option>
													<option value="FEM">Fallen Empires</option>
													<option value="HML">Homelands</option>
													<option value="ICE">Ice Age</option>
													<option value="ALL">Alliances</option>
													<option value="CSP">Coldsnap</option>
													<option value="MIR">Mirage</option>
													<option value="VIS">Visions</option>
													<option value="WTH">Weatherlight</option>
													<option value="TMP">Tempest</option>
													<option value="STH">Stronghold</option>
													<option value="EXO">Exodus</option>
													<option value="USG">Urza's Saga</option>
													<option value="ULG">Urza's Legacy</option>
													<option value="UDS">Urza's Destiny</option>
													<option value="MMQ">Mercadian Masques</option>
													<option value="NEM">Nemesis</option>
													<option value="PCY">Prophecy</option>
													<option value="INV">Invasion</option>
													<option value="PLS">Planeshift</option>
													<option value="APC">Apocalypse</option>
													<option value="ODY">Odyssey</option>
													<option value="TOR">Torment</option>
													<option value="JUD">Judgment</option>
													<option value="ONS">Onslaught</option>
													<option value="LGN">Legions</option>
													<option value="SCG">Scourge</option>
													<option value="MRD">Mirrodin</option>
													<option value="DST">Darksteel</option>
													<option value="5DN">Fifth Dawn</option>
													<option value="CHK">Champions of Kamigawa</option>
													<option value="BOK">Betrayers of Kamigawa</option>
													<option value="SOK">Saviors of Kamigawa</option>
													<option value="RAV">Ravnica: City of Guilds</option>
													<option value="GPT">Guildpact</option>
													<option value="DIS">Dissension</option>
													<option value="TSP">Time Spiral</option>
													<option value="PLC">Planar Chaos</option>
													<option value="FUT">Future Sight</option>
													<option value="LRW">Lorwyn</option>
													<option value="MOR">Morningtide</option>
													<option value="SHM">Shadowmoor</option>
													<option value="EVE">Eventide</option>
													<option value="ALA">Shards of Alara</option>
													<option value="CON">Conflux</option>
													<option value="ARB">Alara Reborn</option>
													<option value="ZEN">Zendikar</option>
													<option value="WWK">Worldwake</option>
													<option value="ROE">Rise of the Eldrazi</option>
													<option value="SOM">Scars of Mirrodin</option>
													<option value="MBS">Mirrodin Beseiged</option>
													<option value="NPH">New Phyrexia</option>
													<option value="ISD">Innistrad</option>
													<option value="DKA">Dark Ascension</option>
													<option value="AVR">Avacyn Restored</option>
													<option value="RTR">Return to Ravnica</option>
													<option value="GTC">Gatecrash</option>
													<option value="DGM">Dragon's Maze</option>
													<option value="THS">Theros</option>
													<option value="BNG">Born of the Gods</option>
												</optgroup>
												<optgroup label="Duel Decks">
													<option value="EVG">Elves vs. Goblins</option>
													<option value="DD2">Jace vs. Chandra</option>
													<option value="DDC">Divine vs. Demonic</option>
													<option value="DDD">Garruk vs. Liliana</option>
													<option value="DDE">Phyrexia vs. the Coalition</option>
													<option value="DDF">Elspeth vs. Tezzeret</option>
													<option value="DDG">Knights vs. Dragons</option>
													<option value="DDH">Ajani vs. Nicol Bolas</option>
													<option value="DDI">Venser vs. Koth</option>
													<option value="DDJ">Izzet vs. Golgari</option>
													<option value="DDK">Sorin vs. Tibalt</option>
													<option value="DDL">Heroes vs. Monsters</option>
													<option value="DDM">Jace vs. Vraska</option>
												</optgroup>
												<optgroup label="From the Vault">
													<option value="DRB">Dragons</option>
													<option value="V09">Exiled</option>
													<option value="V10">Relics</option>
													<option value="V11">Legends</option>
													<option value="V12">Realms</option>
													<option value="V13">Twenty</option>
												</optgroup>
												<optgroup label="Premium Deck Series">
													<option value="H09">Slivers</option>
													<option value="PD2">Fire and Lightning</option>
													<option value="PD3">Graveborn</option>
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

<!-- scripts at bottom, faster load time: -->


<script type="text/javascript">
    $(document).ready(function() {
        $('.multiselect').multiselect({
            maxHeight: 200
        });
    });
</script>

<jsp:include page="/include/Footer.jsp"/>


