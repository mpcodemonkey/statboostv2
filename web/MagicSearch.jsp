<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 8/6/2014
  Time: 4:42 PM
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>


<c:if test="${requestScope.card != null}">
    <div class="alert alert-info fade in">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <strong>Test Result:</strong> <c:out value="${requestScope.card.cardName}" /> has been retrieved from the database.
    </div>
</c:if>

<div>
    <style>
        .bloc { display:inline-block; vertical-align:top; overflow:hidden; border:solid darkgray 1px; }
        .bloc select { padding:10px; margin:-5px -20px -5px -5px; }
    </style>
    <style>
        h1 { text-align:center; }
    </style>
    <style>
        .searchContainer { background:#dddddd; padding:20px; margin:auto; width:700px; }
        .searchRight { background:#c8c8c8; width:300px; float:right; }
        .searchLeft { background:#c8c8c8; width:375px;  float:left; }
        .searchFooter { background:transparent; clear:both; text-align:center; }
    </style>

    <div class="container-fluid">
        <div class="well well-lg">
            <form action="" method="post">
                <div class="header">
                    <h1>Advanced Search Go Between</h1>
                </div>


                <div id="container" align="center" class="searchContainer">
                    <div id="menu" class="searchLeft">
                    <table>
                    <tr>
                        <td align="right"><b>Name: </b></td>
                        <td></td>
                        <td><input type="text" id="cardName" name="cardName"></td>
                    </tr>
                    <tr>
                        <td align="right"><b>Types: </b></td>
                        <td></td>
                        <td><input type="text" id="type" name="type"></td>
                    </tr>
                    <tr>
                        <td align="right"><b>Subtypes: </b></td>
                        <td></td>
                        <td><input type="text" id="subType" name="subType"></td>
                    </tr>
                    <tr>
                        <td align="right"><b>Converted&nbsp;&nbsp;<br>Mana Cost: </b></td>
                        <td>
                            <select name="cmcModifier" id="cmcModifier">
                                <option value="=">=</option>
                                <option value="<">&lt;</option>
                                <option value=">">&gt;</option>
                                <option value="<=">&le;</option>
                                <option value=">=">&ge;</option>
                                <option value="!=">!=</option>
                            </select>
                        </td>
                        <td><input type="text" id="cmc" name="cmc"></td>
                    </tr>
                    <tr>
                        <td align="right"><b>Rules Text: </b></td>
                        <td></td>
                        <td><input type="text" id="rulesContain" name="rulesContain"></td>
                    </tr>
                    <tr>
                        <td align="right"><b>Color: </b></td>
                        <td><select name="colorMod" id="colorMod">
                            <option value="and">AND</option>
                            <option value="or">OR</option>
                            <option value="not">NOT</option>
                        </select></td>
                        <td><span class="bloc">
                                                <select name="colors[]" id="colors" size="5" multiple>
                                                    <option value="White">White</option>
                                                    <option value="Blue">Blue</option>
                                                    <option value="Black">Black</option>
                                                    <option value="Red">Red</option>
                                                    <option value="Green">Green</option>
                                                </select>
                                                </span></td>

                    </tr>
                    <tr>
                        <td align="right"><b>Rarity: </b></td>
                        <td><select name="rarityMod" id="rarityMod">
                            <option value="or">OR</option>
                            <option value="not">NOT</option>
                        </select></td>
                        <td><span class="bloc">
                                                <select name="rarities[]" id="rarities" size="5" multiple>
                                                    <option value="Common">Common</option>
                                                    <option value="Uncommon">Uncommon</option>
                                                    <option value="Rare">Rare</option>
                                                    <option value="Mythic Rare">Mythic Rare</option>
                                                    <option value="Special">Special</option>
                                                </select>
                                                </span></td>
                    </tr>
                    <tr>
                        <td align="right"><b>Expansion: </b></td>
                        <td></td>
                        <td><select name="setID" id="setID">
                            <option value="">&nbsp;</option>
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
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><b>Format: </b></td>
                        <td></td>
                        <td><select name="format" id="setFormat">
                            <option value="">&nbsp;</option>
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
                        </td>
                    </tr>
                    </table>
                    </div>
                    <div id="content" class="searchRight">
                        <table>
                            <tr>
                                <td align="right"><b>Sort By: </b></td>
                                <td><select name="sortby" id="orderByGroup">
                                    <option value="">&nbsp;</option>
                                    <option value="multiverseid">Multiverse ID</option>
                                    <option value="cardname">Name</option>
                                    <option value="rarity">Rarity</option>
                                    <option value="cardpower">Power</option>
                                    <option value="toughness">Toughness</option>
                                </select>
                                </td>
                            </tr>
                            <tr>
                                <td align="right"><b>Sort Order: </b></td>
                                <td><select name="sortorder" id="order">
                                    <option value="">&nbsp;</option>
                                    <option value="asc">Ascending</option>
                                    <option value="desc">Descending</option>
                                </select>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="searchFooter">
                        <input type="submit" name="Submit" value="submit">
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="searchFooter">
        <p>
            &copy; Copyright by Catherine Kramer
        </p>
    </div>
</div>

<jsp:include page="/include/Footer.jsp"/>


