<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.admin.InventoryEditorServlet" %>
<%@ page import="static com.statboost.controllers.admin.InventoryEditorServlet.*" %>
<%@ page import="com.statboost.models.inventory.Inventory" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.statboost.models.mtg.MagicCard" %>
<%@ page import="com.statboost.models.ygo.YugiohCard" %>
<%@ page import="com.statboost.models.inventory.Event" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.statboost.controllers.admin.InventorySqllistServlet" %>
<%@ page import="com.statboost.util.ServletUtil" %>
<%@ page import="com.statboost.models.mtg.MagicSet" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Inventory</title>
    <%!
        static Logger logger = Logger.getLogger(InventoryEditorServlet.class);
    %>
    <%
        Inventory inventory = (Inventory) request.getAttribute(InventoryEditorServlet.ATTR_INVENTORY);
        MagicCard magicCard = (MagicCard) request.getAttribute(InventoryEditorServlet.ATTR_MAGIC_CARD);
        YugiohCard yugiohCard = (YugiohCard) request.getAttribute(InventoryEditorServlet.ATTR_YUGIOH_CARD);
        Event event = (Event) request.getAttribute(InventoryEditorServlet.ATTR_EVENT);
        ResultSet magicSets = (ResultSet) request.getAttribute(InventoryEditorServlet.ATTR_MAGIC_SETS)
    %>
    <jsp:include page="/include/HeadTags.jsp"/>
    <script type="text/javascript">
        jQuery(document).ready(function()  {
            <%
                if(magicCard != null)  {
            %>
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').show();
                jQuery('#eventDiv').hide();
                $('input[name=<%=PARAM_INVENTORY_DESCRIPTION%>]').val('MAGIC');

            <%
               } else if(yugiohCard != null)  {
            %>
                jQuery('#yugiohDiv').show();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').hide();
                $('input[name=<%=PARAM_INVENTORY_DESCRIPTION%>]').val('YUGIOH');
            <%
                } else if(event != null)  {
            %>
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').show();
                $('input[name=<%=PARAM_INVENTORY_DESCRIPTION%>]').val('EVENT');
            <%
                } else  {
            %>
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').hide();
                $('input[name=<%=PARAM_INVENTORY_DESCRIPTION%>]').val('GENERIC');
            <%
                }
            %>
        })

        function switchType()  {
            if(jQuery('.type').val() == 'YUGIOH')  {
                jQuery('#yugiohDiv').show();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').hide();
            } else if(jQuery('.type').val() == 'MAGIC')  {
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').show();
                jQuery('#eventDiv').hide();
            } else if(jQuery('.type').val() == 'EVENT')  {
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').show();
            } else  {
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').hide();
            }
        }
    </script>
</head>
<body>
<form method="post" action="<%=InventoryEditorServlet.SRV_MAP%>">
<table>
    <input type="hidden" name="<%=InventoryEditorServlet.PARAM_INVENTORY_UID%>" value="<%=inventory.getUid()%>">
    <input type="hidden" name="<%=InventoryEditorServlet.PARAM_MAGIC_CARD_UID%>" value="<%=magicCard != null? magicCard.getMcrUid(): null%>">
    <input type="hidden" name="<%=InventoryEditorServlet.PARAM_YUGIOH_UID%>" value="<%=yugiohCard != null? yugiohCard.getYcrUid(): null%>">
    <input type="hidden" name="<%=InventoryEditorServlet.PARAM_EVENT_UID%>" value="<%=event != null? event.getUid(): null%>">
    <%
        if(request.getAttribute(InventoryEditorServlet.ATTR_INFO)  != null)  {
    %>
    <tr>
        <td colspan="2" class="info">
            <%=request.getAttribute(InventoryEditorServlet.ATTR_INFO)%>
        </td>
    </tr>
    <%
        }
    %>
    <%
        if(request.getAttribute(InventoryEditorServlet.ATTR_ERRORS) != null)  {
            ArrayList<String> errors = (ArrayList<String>) request.getAttribute(InventoryEditorServlet.ATTR_ERRORS);
            for(String currentError : errors)  {
    %>
    <tr>
        <td colspan="2" class="error">
            <%=currentError%>
        </td>
    </tr>
    <%
            }
        }
    %>

    <%
        if(request.getAttribute(InventoryEditorServlet.ATTR_WARNING) != null)  {
            ArrayList<String> warnings = (ArrayList<String>) request.getAttribute(InventoryEditorServlet.ATTR_WARNING);
            for(String currentWarning : warnings)  {
    %>
    <tr>
        <td colspan="2" class="warning">
            <%=currentWarning%>
        </td>
    </tr>
    <%
            }
        }
    %>
    <tr>
        <td>Inventory</td>
        <td><input type="submit"> &nbsp; <a href="<%=InventorySqllistServlet.SRV_MAP%>">Close</a></td></td>
    </tr>
    <tr>
        <td colspan="2"><input type="checkbox" <%=inventory.isPreOrder()? "checked=\"checked\"" : ""%>>&nbsp;Pre Order</td>
    </tr>
    <tr>
        <td>Name</td>
        <td><input type="text" name="<%=PARAM_INVENTORY_NAME%>" value="<%=ServletUtil.hideNulls(inventory.getName())%>"></td>
    </tr>
    <tr>
        <td>New Price</td>
        <td><input type="text" name="<%=PARAM_NEW_PRICE%>" value="$<%=inventory.getNewPrice()%>"></td>
    </tr>
    <tr>
        <td>Near Mint Price</td>
        <td><input type="text" name="<%=PARAM_NEAR_MINT_PRICE%>" value="$<%=inventory.getNearMintPrice()%>"></td>
    </tr>
    <tr>
        <td>Lightly Played Price</td>
        <td><input type="text" name="<%=PARAM_LIGHTLY_PLAYED_PRICE%>" value="$<%=inventory.getLightlyPlayedPrice()%>"></td>
    </tr>
    <tr>
        <td>Moderately Played Price</td>
        <td><input type="text" name="<%=PARAM_MODERATELY_PLAYED_PRICE%>" value="$<%=inventory.getModeratelyPlayedPrice()%>"></td>
    </tr>
    <tr>
        <td>Heavily Played Price</td>
        <td><input type="text" name="<%=PARAM_HEAVILY_PLAYED_PRICE%>" value="$<%=inventory.getHeavilyPlayedPrice()%>"/></td>
    </tr>
    <tr>
        <td>Damaged Price</td>
        <td><input type="text" name="<%=PARAM_DAMAGED_PRICE%>" value="$<%=inventory.getDamagedPrice()%>"/></td>
    </tr>
    <tr>
        <td colspan="2">Number in Stock</td>
    </tr>
    <tr>
        <td>New</td>
        <td><input type="text" name="<%=PARAM_NUM_NEW_IN_STOCK%>" value="<%=inventory.getNumNewInStock()%>"></td>
    </tr>
    <tr>
        <td>Near Mint</td>
        <td><input type="text" name="<%=PARAM_NUM_NEAR_MINT_IN_STOCK%>" value="<%=inventory.getNearMintInStock()%>"></td>
    </tr>
    <tr>
        <td>Lightly Played</td>
        <td><input type="text" name="<%=PARAM_NUM_LIGHTLY_PLAYED_IN_STOCK%>" value="<%=inventory.getLightlyPlayedInStock()%>"></td>
    </tr>
    <tr>
        <td>Moderately Played</td>
        <td><input type="text" name="<%=PARAM_NUM_MODERATELY_PLAYED_IN_STOCK%>" value="<%=inventory.getModeratelyPlayedInStock()%>"></td>
    </tr>
    <tr>
        <td>Heavily Played</td>
        <td><input type="text" name="<%=PARAM_NUM_HEAVILY_PLAYED_IN_STOCK%>" value="<%=inventory.getHeavilyPlayedInStock()%>"></td>
    </tr>
    <tr>
        <td>Damaged</td>
        <td><input type="text" name="<%=PARAM_NUM_DAMAGED_IN_STOCK%>" value="<%=inventory.getDamagedInStock()%>"></td>
    </tr>
    <%--todo: research how we are going to do image--%>
    <tr>
        <td>Image</td>
        <td><input type="text" name="<%=PARAM_INVENTORY_IMAGE%>" value="<%=ServletUtil.hideNulls(inventory.getImage())%>"></td>
    </tr>
    <tr>
        <td>Description</td>
        <td><textarea name="<%=PARAM_INVENTORY_DESCRIPTION%>"><%=ServletUtil.hideNulls(inventory.getDescription())%></textarea></td>
    </tr>
    <tr>
        <td colspan="2">
            <input class="type" type="radio" onclick="switchType(); return false;" name="<%=PARAM_TYPE%>" value="GENERIC"> Generic
            <input class="type" type="radio" onclick="switchType(); return false;" name="<%=PARAM_TYPE%>" value="MAGIC"> Magic
            <input class="type" type="radio" onclick="switchType(); return false;" name="<%=PARAM_TYPE%>" value="YUGIOH"> Yu-gi-oh
            <input class="type" type="radio" onclick="switchType(); return false;" name="<%=PARAM_TYPE%>" value="EVENT"> Event
        </td>
    </tr>
    <tr>
        <td>
            <div id="magicDiv">
                <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td>Timeshifted</td>
                        <td><input type="checkbox" name="<%=PARAM_MAGIC_TIMESHIFTED%>" <%=magicCard.getMcrTimeshifted() == 1? "checked = \"checked\"" : ""%>/></td>
                    </tr>
                    <tr>
                        <td>Reserved</td>
                        <td><input type="checkbox" name="<%=PARAM_MAGIC_RESERVED%>" <%=magicCard.getMcrReserved() == 1? "checked = \"checked\"" : ""%>></td>
                    </tr>
                    <tr>
                        <td>Set</td>
                        <td>
                            <select id="magicSet" name="<%=PARAM_MAGIC_SET_ID%>">
                                <option value="">Select One</option>
                                <%
                                    if(magicSets != null)  {
                                %>
                                <option value="<%=magicSets.getInt("mst_uid")%>"><%=magicSets.getString("mst_name")%></option>
                                <%
                                    }
                                %>
                            </select>
                            <%
                                if(magicCard != null && magicCard.getMagicSet() != null)  {
                            %>
                            <script type="text/javascript">
                                document.getElementById('magicSet').value = '<%=magicCard.getMagicSet().getMstUid()%>';
                            </script>
                            <%
                                }
                            %>
                        </td>
                    </tr>
                    <tr>
                        <td>Name</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_CARD_NAME%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrCardName())%>"/></td>
                    </tr>
                    <tr>
                        <td>Names</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_NAMES%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrNames())%>"></td>
                    </tr>
                    <tr>
                        <td>Mana Cost</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_MANA_COST%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrManaCost())%>"></td>
                    </tr>
                    <tr>
                        <td>CMC</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_CMC%>" value="<%=magicCard.getMcrCmc()%>"></td>
                    </tr>
                    <tr>
                        <td>Colors</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_COLORS%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrColors())%>"></td>
                    </tr>
                    <tr>
                        <td>Type</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_TYPE%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrType())%>"></td>
                    </tr>
                    <tr>
                        <td>Types</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_TYPES%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrTypes())%>"></td>
                    </tr>
                    <tr>
                        <td>Super Types</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_TYPES%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrTypes())%>"></td>
                    </tr>
                    <tr>
                        <td>Sub Types</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_SUB_TYPES%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrSubTypes())%>"></td>
                    </tr>
                    <tr>
                        <td>Rarity</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_RARITY%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrRarity())%>"></td>
                    </tr>
                    <tr>
                        <td>Text</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_TEXT%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrText())%>"></td>
                    </tr>
                    <tr>
                        <td>Flavor</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_FLAVOR%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrFlavor())%>"></td>
                    </tr>
                    <tr>
                        <td>Artist</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_ARTIST%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrArtist())%>"></td>
                    </tr>
                    <tr>
                        <td>Number</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_NUMBER%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrNumber())%>"></td>
                    </tr>
                    <tr>
                        <td>Power</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_POWER%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrPower())%>"></td>
                    </tr>
                    <tr>
                        <td>Toughness</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_TOUGHNESS%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrToughness())%>"></td>
                    </tr>
                    <tr>
                        <td>Loyalty</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_LOYALTY%>" value="<%=magicCard.getMcrLoyalty()%>"></td>
                    </tr>
                    <tr>
                        <td>Layout</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_LAYOUT%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrLayout())%>"></td>
                    </tr>
                    <tr>
                        <td>Multiverse ID</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_MULTIVERSE_ID%>" value="<%=magicCard.getMcrMultiverseId()%>"></td>
                    </tr>
                    <tr>
                        <td>Variations</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_VARIATIONS%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrVariations())%>"></td>
                    </tr>
                    <%--todo: do it this way for now but will later be changed--%>
                    <tr>
                        <td>Image Name</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_IMAGE_NAME%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrImageName())%>"></td>
                    </tr>
                    <tr>
                        <td>Watermark</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_WATERMARK%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrWatermark())%>"></td>
                    </tr>
                    <tr>
                        <td>Border</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_BORDER%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrBorder())%>"></td>
                    </tr>
                    <tr>
                        <td>Hand</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_HAND%>" value="<%=magicCard.getMcrHand()%>"></td>
                    </tr>
                    <tr>
                        <td>Life</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_LIFE%>" value="<%=magicCard.getMcrLife()%>"></td>
                    </tr>
                    <%--todo: give them a calendar object to select from that will auto populate this field.--%>
                    <tr>
                        <td>Release Date</td>
                        <td><input type="text" name="<%=PARAM_MAGIC_RELEASE_DATE%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrReleaseDate())%>"></td>
                    </tr>
                    <tr>
                        <td></td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <div id="yugiohDiv">
                <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td>Name</td>
                        <td><input type="text" name="<%=PARAM_YUGIOH_NAME%>" value="<%=ServletUtil.hideNulls(yugiohCard.getYcrName())%>"></td>
                    </tr>
                    <tr>
                        <td>Card Type</td>
                        <td><input type="text" name="<%=PARAM_YUGIOH_CARD_TYPE%>" value="<%=ServletUtil.hideNulls(yugiohCard.getYcrCardType())%>"></td>
                    </tr>
                    <tr>
                        <td>Attribute</td>
                        <td><input type="text" name="<%=PARAM_YUGIOH_ATTRIBUTE%>" value="<%=ServletUtil.hideNulls(yugiohCard.getYcrAttribute())%>"></td>
                    </tr>
                    <tr>
                        <td>Type</td>
                        <td><input type="text" name="<%=PARAM_YUGIOH_TYPE%>" value="<%=ServletUtil.hideNulls(yugiohCard.getYcrType())%>"></td>
                    </tr>
                    <tr>
                        <td>Level</td>
                        <td><input type="text" name="<%=PARAM_YUGIOH_LEVEL%>" value="<%=yugiohCard.getYcrLevel()%>"></td>
                    </tr>
                    <tr>
                        <td>ATK</td>
                        <td><input type="text" name="<%=PARAM_YUGIOH_ATK%>" value="<%=yugiohCard.getYcrAtk()%>"/></td>
                    </tr>
                    <tr>
                        <td>DEF</td>
                        <td><input type="text" name="<%=PARAM_YUGIOH_DEF%>" value="<%=yugiohCard.getYcrDef()%>"></td>
                    </tr>
                    <tr>
                        <td>Description</td>
                        <td><textarea name="<%=PARAM_YUGIOH_DESCRIPTION%>"><%=ServletUtil.hideNulls(yugiohCard.getYcrDescription())%></textarea></td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <table cellpadding="0 "cellspacing="0" border="0">
                <%--todo: give them a calendar object to select from that will auto populate this field.--%>
                <tr>
                    <td>Date</td>
                    <td><input type="text" name="<%=PARAM_EVENT_DATE%>" value="<%=event.getDate() == null? "" : event.getDate()%>"></td>
                </tr>
                <tr>
                    <td>Title</td>
                    <td>
                        <input type="text" name="<%=PARAM_EVENT_TITLE%>" value="<%=ServletUtil.hideNulls(event.getTitle())%>"/>
                    </td>
                </tr>
                <tr>
                    <td>Player Limit</td>
                    <td><input type="text" name="<%=PARAM_EVENT_PLAYER_LIMIT%>" value="<%=event.getPlayerLimit()%>"></td>
                </tr>
                <tr>
                    <td>Number in Store Users</td>
                    <td><input type="text" name="<%=PARAM_EVENT_NUMBER_IN_STORE_USERS%>" value="<%=event.getNumberInStoreUsers()%>"></td>
                </tr>
                <tr>
                    <td>Description</td>
                    <td><textarea name="<%=PARAM_EVENT_DESCRIPTION%>"><%=event.getDescription()%></textarea></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>