<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.statboost.controllers.admin.InventoryEditorServlet" %>
<%@ page import="static com.statboost.controllers.admin.InventoryEditorServlet.*" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.statboost.models.mtg.MagicCard" %>
<%@ page import="com.statboost.models.ygo.YugiohCard" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.statboost.controllers.admin.InventorySqllistServlet" %>
<%@ page import="com.statboost.util.ServletUtil" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="net.authorize.util.DateUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.statboost.models.enumType.ItemCondition" %>
<%@ page import="com.statboost.models.inventory.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

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
        ResultSet magicSets = (ResultSet) request.getAttribute(InventoryEditorServlet.ATTR_MAGIC_SETS);
        String type = (String) request.getAttribute(InventoryEditorServlet.ATTR_TYPE);
        List<Cost> costs = (List<Cost>) request.getAttribute(InventoryEditorServlet.ATTR_COST_ITEMS);
        List<Category> categories = (List<Category>) request.getAttribute(InventoryEditorServlet.ATTR_CATEGORIES);

        HashMap<ItemCondition, Cost> costHash = new HashMap<ItemCondition, Cost>();
        if(costs != null)  {
            for(Cost currentCost: costs)  {
                costHash.put(currentCost.getItemCondition(), currentCost);
            }
        }
    %>
    <jsp:include page="/include/HeadTags.jsp"/>
    <script type="text/javascript">
        jQuery(document).ready(function()  {
            jQuery('.datePicker').datepicker();

            <%
                if(type != null && type.equals("MAGIC"))  {
            %>
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').show();
                jQuery('#eventDiv').hide();
                $('input[name="<%=PARAM_TYPE%>"][value="MAGIC"]').prop('checked', true);

            <%
               } else if(type != null && type.equals("YUGIOH"))  {
            %>
                jQuery('#yugiohDiv').show();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').hide();
                $('input[name="<%=PARAM_TYPE%>"][value="YUGIOH"]').prop('checked', true);
            <%
                } else if(type != null && type.equals("EVENT"))  {
            %>
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').show();
                $('input[name="<%=PARAM_TYPE%>"][value="EVENT"]').prop('checked', true);
            <%
                } else  {
            %>
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').hide();
                $('input[name="<%=PARAM_TYPE%>"][value="GENERIC"]').prop('checked', true);
            <%
                }
            %>


        })

        function switchType()  {
            var value = $('input[name="<%=PARAM_TYPE%>"]:checked').val();
            if(value == 'YUGIOH')  {
                jQuery('#yugiohDiv').show();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').hide();
            } else if(value == 'MAGIC')  {
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').show();
                jQuery('#eventDiv').hide();
            } else if(value == 'EVENT')  {
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

<div class="container-fluid">
<div class="well well-sm col-sm-12">
<div class="col-sm-12">
    <form method="post" action="<%=InventoryEditorServlet.SRV_MAP%>" enctype="multipart/form-data">
        <input type="hidden" name="<%=InventoryEditorServlet.PARAM_INVENTORY_UID%>" value="<%=inventory.getUid()%>">
        <input type="hidden" name="<%=InventoryEditorServlet.PARAM_MAGIC_CARD_UID%>" value="<%=magicCard != null? magicCard.getMcrUid(): null%>">
        <input type="hidden" name="<%=InventoryEditorServlet.PARAM_YUGIOH_UID%>" value="<%=yugiohCard != null? yugiohCard.getYcrUid(): null%>">
        <input type="hidden" name="<%=InventoryEditorServlet.PARAM_EVENT_UID%>" value="<%=event != null? event.getUid(): null%>">
        <%
            if(request.getAttribute(InventoryEditorServlet.ATTR_INFO)  != null)  {
                ArrayList<String> info = (ArrayList<String>) request.getAttribute(InventoryEditorServlet.ATTR_INFO);
                for(String currentInfo : info)  {
        %>
        <%=currentInfo%>
        <%
                }
            }
        %>
        <%
            if(request.getAttribute(InventoryEditorServlet.ATTR_ERRORS) != null)  {
                ArrayList<String> errors = (ArrayList<String>) request.getAttribute(InventoryEditorServlet.ATTR_ERRORS);
                for(String currentError : errors)  {
        %>
        <%=currentError%>
        <%
                }
            }
        %>

        <%
            if(request.getAttribute(InventoryEditorServlet.ATTR_WARNING) != null)  {
                ArrayList<String> warnings = (ArrayList<String>) request.getAttribute(InventoryEditorServlet.ATTR_WARNING);
                for(String currentWarning : warnings)  {
        %>
        <%=currentWarning%>
        <%
                }
            }
        %>
        <h3 class="col-sm-12">Add New Inventory</h3>
        <hr class="col-sm-10" />
        <div class="col-sm-12">
            <input class="btn btn-primary" type="submit" value="Save Item" />
            <a href="<%=InventorySqllistServlet.SRV_MAP%>"><input class="btn btn-danger" type="button" value="Cancel" /></a>
        </div>
        <hr class="col-sm-10" />
        <div class="col-sm-12">
            <div class="col-sm-12"><b>Categories:</b></div>
            <div class="col-sm-12">
                <%
                    for(Category currentCategory : categories)  {
                %>
                    <label class="checkbox-inline col-sm-3">
                        <input type="checkbox" name="<%=PARAM_CATEGORIES%>" value="<%=currentCategory.getCatUid()%>">
                        <%=currentCategory.getCategory()%>
                    </label>
                <% if (currentCategory.getCatUid() == 3) {%>
                    </div>
                    <div class="col-sm-12">
                <%}%>
                <%
                    }
                %>
            </div>
        </div>
        <hr class="col-sm-10" />
        <div class="col-sm-12">
            <div class="col-sm-12"><b>Is this a Pre-Order item?</b></div>
            <div class="col-sm-4">
            <label class="checkbox-inline">
                <input name="<%=PARAM_INVENTORY_PRE_ORDER%>" type="checkbox" <%=inventory.isPreOrder()? "checked=\"checked\"" : ""%>>
                Pre Order
            </label>
            </div>
        </div>
        <hr class="col-sm-10" />
        <div class="col-sm-12">
            <div class="col-sm-12"><b>Is this a foiled item?</b></div>
            <div class="col-sm-4">
                <label class="checkbox-inline">
                    <input type="checkbox" name="<%=PARAM_FOILED%>" <%=inventory.getInvFoil() == 1? "checked=\"checked\"" : ""%>>
                    Foiled
                </label>
            </div>
        </div>
        <hr class="col-sm-10" />
        <div class="col-sm-12">
            <div class="col-sm-2"><b>Item Name:</b></div>
            <div class="col-sm-4">
                <input type="text" name="<%=PARAM_INVENTORY_NAME%>" value="<%=ServletUtil.hideNulls(inventory.getName())%>">
            </div>
        </div>
        <div class="col-sm-6 container-fluid">
            <div>
                <b>Price by Condition:</b>
            </div>
            <div>
                <div class="col-sm-12"><b>New Price:</b></div>
                <div class="col-sm-12"><input type="text" name="<%=PARAM_NEW_PRICE%>" value="<%=costHash.get(ItemCondition.NEW) != null ? costHash.get(ItemCondition.NEW).getItemPrice(): 0%>"></div>
            </div>
            <div>
                <div class="col-sm-12"><b>Near Mint Price:</b></div>
                <div class="col-sm-12"><input type="text" name="<%=PARAM_NEAR_MINT_PRICE%>" value="<%=costHash.get(ItemCondition.NEAR_MINT) != null ? costHash.get(ItemCondition.NEAR_MINT).getItemPrice(): 0%>"></div>
            </div>
            <div>
                <div class="col-sm-12"><b>Lightly Played Price:</b></div>
                <div class="col-sm-12"><input type="text" name="<%=PARAM_LIGHTLY_PLAYED_PRICE%>" value="<%=costHash.get(ItemCondition.LIGHTLY_PLAYED) != null ? costHash.get(ItemCondition.LIGHTLY_PLAYED).getItemPrice(): 0%>"></div>
            </div>
            <div>
                <div class="col-sm-12"><b>Moderately Played Price:</b></div>
                <div class="col-sm-12"><input type="text" name="<%=PARAM_MODERATELY_PLAYED_PRICE%>" value="<%=costHash.get(ItemCondition.MODERATELY_PLAYED) != null ? costHash.get(ItemCondition.MODERATELY_PLAYED).getItemPrice(): 0%>"></div>
            </div>
            <div>
                <div class="col-sm-12"><b>Heavily Played Price:</b></div>
                <div class="col-sm-12"><input type="text" name="<%=PARAM_HEAVILY_PLAYED_PRICE%>" value="<%=costHash.get(ItemCondition.HEAVILY_PLAYED) != null ? costHash.get(ItemCondition.HEAVILY_PLAYED).getItemPrice(): 0%>"/></div>
            </div>
            <div>
                <div class="col-sm-12"><b>Damaged Price:</b></div>
                <div class="col-sm-12"><input type="text" name="<%=PARAM_DAMAGED_PRICE%>" value="<%=costHash.get(ItemCondition.DAMAGED) != null ? costHash.get(ItemCondition.DAMAGED).getItemPrice(): 0%>"/></div>
            </div>
        </div>
        <div class="col-sm-6 container-fluid">
            <div>
                <div><b>Number in Stock:</b></div>
            </div>
            <div>
                <div><b>New</b></div>
                <div><input type="text" name="<%=PARAM_NUM_NEW_IN_STOCK%>" value="<%=costHash.get(ItemCondition.NEW) != null ? costHash.get(ItemCondition.NEW).getItemQuantity(): 0%>"></div>
            </div>
            <div>
                <div><b>Near Mint</b></div>
                <div><input type="text" name="<%=PARAM_NUM_NEAR_MINT_IN_STOCK%>" value="<%=costHash.get(ItemCondition.NEAR_MINT) != null ? costHash.get(ItemCondition.NEAR_MINT).getItemQuantity(): 0%>"></div>
            </div>
            <div>
                <div><b>Lightly Played</b></div>
                <div><input type="text" name="<%=PARAM_NUM_LIGHTLY_PLAYED_IN_STOCK%>" value="<%=costHash.get(ItemCondition.LIGHTLY_PLAYED) != null ? costHash.get(ItemCondition.LIGHTLY_PLAYED).getItemQuantity(): 0%>"></div>
            </div>
            <div>
                <div><b>Moderately Played</b></div>
                <div><input type="text" name="<%=PARAM_NUM_MODERATELY_PLAYED_IN_STOCK%>" value="<%=costHash.get(ItemCondition.MODERATELY_PLAYED) != null ? costHash.get(ItemCondition.MODERATELY_PLAYED).getItemQuantity(): 0%>"></div>
            </div>
            <div>
                <div><b>Heavily Played</b></div>
                <div><input type="text" name="<%=PARAM_NUM_HEAVILY_PLAYED_IN_STOCK%>" value="<%=costHash.get(ItemCondition.HEAVILY_PLAYED) != null ? costHash.get(ItemCondition.HEAVILY_PLAYED).getItemQuantity(): 0%>"></div>
            </div>
            <div>
                <div><b>Damaged</b></div>
                <div><input type="text" name="<%=PARAM_NUM_DAMAGED_IN_STOCK%>" value="<%=costHash.get(ItemCondition.DAMAGED) != null ? costHash.get(ItemCondition.DAMAGED).getItemQuantity(): 0%>"></div>
            </div>
            <div>
                <div>Please make sure that the image name is accurate.</div>
                <div>Current Image: <%=inventory.getImage() == null? "N/A" : inventory.getImage()%></div>
            </div>
            <div>
                <div><b>Image</b></div>
                <div>
                    <input type="file" name="<%=PARAM_INVENTORY_IMAGE%>">
                </div>
            </div>
            <div>
                <div><b>Description</b></div>
                <div><textarea cols="50" rows="5" name="<%=PARAM_INVENTORY_DESCRIPTION%>"><%=ServletUtil.hideNulls(inventory.getDescription())%></textarea></div>
            </div>
            <div>
                <input id="GENERIC" class="inventoryType" type="radio" onclick="switchType();" name="<%=PARAM_TYPE%>" value="GENERIC">
                <label for="GENERIC">Generic</label>
                <input id="MAGIC" class="inventoryType" type="radio" onclick="switchType();" name="<%=PARAM_TYPE%>" value="MAGIC">
                <label for="MAGIC">Magic</label>
                <input id="YUGIOH" class="inventoryType" type="radio" onclick="switchType();" name="<%=PARAM_TYPE%>" value="YUGIOH">
                <label for="YUGIOH">Yu-gi-oh</label>
                <input id="EVENT" class="inventoryType" type="radio" onclick="switchType();" name="<%=PARAM_TYPE%>" value="EVENT">
                <label for="EVENT">Event</label>
            </div>
        </div>

        <table>
        <tbody>
            <tr>
                <td colspan="2">
                    <div id="magicDiv">
                        <table cellpadding="0" cellspacing="0" border="0">
                            <tr>
                                <td>Timeshifted</td>
                                <td><input type="checkbox" name="<%=PARAM_MAGIC_TIMESHIFTED%>" <%=magicCard != null && magicCard.getMcrTimeshifted() != null && magicCard.getMcrTimeshifted() == 1? "checked = \"checked\"" : ""%>/></td>
                            </tr>
                            <tr>
                                <td>Reserved</td>
                                <td><input type="checkbox" name="<%=PARAM_MAGIC_RESERVED%>" <%=magicCard != null && magicCard.getMcrReserved() != null && magicCard.getMcrReserved() == 1? "checked = \"checked\"" : ""%>></td>
                            </tr>
                            <tr>
                                <td>Set</td>
                                <td>
                                    <select id="magicSet" name="<%=PARAM_MAGIC_SET_ID%>">
                                        <option value="">Select One</option>
                                        <%
                                            if(magicSets != null)  {
                                                while(magicSets.next())  {
                                        %>
                                        <option value="<%=magicSets.getString("mst_uid")%>"><%=magicSets.getString("mst_name")%></option>
                                        <%
                                                }
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
                                <td><input type="text" name="<%=PARAM_MAGIC_CMC%>" value="<%=magicCard.getMcrCmc() == null? "$0.00" : magicCard.getMcrCmc()%>"></td>
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
                                <td><input type="text" name="<%=PARAM_MAGIC_SUPER_TYPES%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrSuperTypes())%>"></td>
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
                                <td><input type="text" name="<%=PARAM_MAGIC_LOYALTY%>" value="<%=magicCard.getMcrLoyalty() == null? "0" : magicCard.getMcrLoyalty()%>"></td>
                            </tr>
                            <tr>
                                <td>Layout</td>
                                <td><input type="text" name="<%=PARAM_MAGIC_LAYOUT%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrLayout())%>"></td>
                            </tr>
                            <tr>
                                <td>Multiverse ID</td>
                                <td><input type="text" name="<%=PARAM_MAGIC_MULTIVERSE_ID%>" value="<%=magicCard.getMcrMultiverseId() == null? "0" : magicCard.getMcrMultiverseId()%>"></td>
                            </tr>
                            <tr>
                                <td>Variations</td>
                                <td><input type="text" name="<%=PARAM_MAGIC_VARIATIONS%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrVariations())%>"></td>
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
                                <td><input type="text" name="<%=PARAM_MAGIC_HAND%>" value="<%=magicCard.getMcrHand() == null? "0" : magicCard.getMcrHand()%>"></td>
                            </tr>
                            <tr>
                                <td>Life</td>
                                <td><input type="text" name="<%=PARAM_MAGIC_LIFE%>" value="<%=magicCard.getMcrLife() == null? "0" : magicCard.getMcrLife()%>"></td>
                            </tr>
                            <%--todo: give them a calendar object to select from that will auto populate this field.--%>
                            <tr>
                                <td>Release Date</td>
                                <td><input class="datePicker" type="text" name="<%=PARAM_MAGIC_RELEASE_DATE%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrReleaseDate())%>"></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2">
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
                                <td><input type="text" name="<%=PARAM_YUGIOH_LEVEL%>" value="<%=yugiohCard.getYcrLevel() == null? "0" : yugiohCard.getYcrLevel()%>"></td>
                            </tr>
                            <tr>
                                <td>ATK</td>
                                <td><input type="text" name="<%=PARAM_YUGIOH_ATK%>" value="<%=yugiohCard.getYcrAtk() == null? "0" : yugiohCard.getYcrAtk()%>"/></td>
                            </tr>
                            <tr>
                                <td>DEF</td>
                                <td><input type="text" name="<%=PARAM_YUGIOH_DEF%>" value="<%=yugiohCard.getYcrDef() == null? "0" : yugiohCard.getYcrDef()%>"></td>
                            </tr>
                            <tr>
                                <td style="vertical-align: top;">Description</td>
                                <td><textarea cols="50" rows="5"  name="<%=PARAM_YUGIOH_DESCRIPTION%>"><%=ServletUtil.hideNulls(yugiohCard.getYcrDescription())%></textarea></td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <div id="eventDiv">
                    <table cellpadding="0 "cellspacing="0" border="0">
                        <%--todo: give them a calendar object to select from that will auto populate this field.--%>
                        <tr>
                            <td>Start Date</td>
                            <td><input class="datePicker" type="text" name="<%=PARAM_FROM_EVENT_DATE%>" value="<%=event.getFromDate() == null? "" : DateUtil.getFormattedDate(event.getFromDate(), "MM/dd/yy")%>">
                                <select id="startHour" name="<%=PARAM_FROM_EVENT_HOUR%>">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                    <option value="6">6</option>
                                    <option value="7">7</option>
                                    <option value="8">8</option>
                                    <option value="9">9</option>
                                    <option value="10">10</option>
                                    <option value="11">11</option>
                                    <option value="0">12</option>
                                </select>
                                <%
                                    if(event != null && event.getFromDate() != null)  {
                                        Calendar fromDate = Calendar.getInstance();
                                        fromDate.setTime(event.getFromDate());
                                %>

                                <script type="text/javascript">
                                    document.getElementById('startHour').value = '<%=fromDate.get(Calendar.HOUR)%>';
                                </script>
                                <%
                                    }
                                %>
                                :
                                <select id="startMinute" name="<%=PARAM_FROM_EVENT_MIN%>">
                                    <%
                                        for(int i = 0; i<60; i++)  {
                                    %>
                                    <option value="<%=i%>"><%= i < 10 ? "0" + i : i%></option>
                                    <%
                                        }
                                    %>
                                </select>
                                <%
                                    if(event != null && event.getFromDate() != null)  {
                                        Calendar fromDate = Calendar.getInstance();
                                        fromDate.setTime(event.getFromDate());
                                %>

                                <script type="text/javascript">
                                    document.getElementById('startMinute').value = '<%=fromDate.get(Calendar.MINUTE)%>';
                                </script>
                                <%
                                    }
                                %>
                                <select id="startAmPM" name="<%=PARAM_FROM_EVENT_AM_PM%>">
                                    <option value="AM">AM</option>
                                    <option value="PM">PM</option>
                                </select>
                                <%
                                    if(event != null && event.getFromDate() != null)  {
                                        Calendar fromDate = Calendar.getInstance();
                                        fromDate.setTime(event.getFromDate());
                                %>

                                <script type="text/javascript">
                                    document.getElementById('startAMPM').value = '<%=fromDate.get(Calendar.AM_PM) == Calendar.AM? "AM" : "PM"%>';
                                </script>
                                <%
                                    }
                                %>
                            </td>
                        </tr>
                            <tr>
                                <td>End Date</td>
                                <td><input class="datePicker" type="text" name="<%=PARAM_TO_EVENT_DATE%>" value="<%=event.getFromDate() == null? "" : DateUtil.getFormattedDate(event.getFromDate(), "MM/dd/yy")%>">
                                    <select id="endHour" name="<%=PARAM_TO_EVENT_HOUR%>">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                        <option value="6">6</option>
                                        <option value="7">7</option>
                                        <option value="8">8</option>
                                        <option value="9">9</option>
                                        <option value="10">10</option>
                                        <option value="11">11</option>
                                        <option value="0">12</option>
                                    </select>
                                    <%
                                        if(event != null && event.getToDate() != null)  {
                                            Calendar toDate = Calendar.getInstance();
                                            toDate.setTime(event.getToDate());
                                    %>

                                    <script type="text/javascript">
                                        document.getElementById('endHour').value = '<%=toDate.get(Calendar.HOUR)%>';
                                    </script>
                                    <%
                                        }
                                    %>
                                    :
                                    <select id="endMinute" name="<%=PARAM_TO_EVENT_MIN%>">
                                        <%
                                            for(int i = 0; i<60; i++)  {
                                        %>
                                        <option value="<%=i%>"><%= i < 10 ? "0" + i : i%></option>
                                        <%
                                            }
                                        %>
                                    </select>
                                    <%
                                        if(event != null && event.getToDate() != null)  {
                                            Calendar toDate = Calendar.getInstance();
                                            toDate.setTime(event.getToDate());
                                    %>

                                    <script type="text/javascript">
                                        document.getElementById('endMinute').value = '<%=toDate.get(Calendar.MINUTE)%>';
                                    </script>
                                    <%
                                        }
                                    %>
                                    <select id="endAMPM" name="<%=PARAM_TO_EVENT_AM_PM%>">
                                        <option value="AM">AM</option>
                                        <option value="PM">PM</option>
                                    </select>
                                    <%
                                        if(event != null && event.getToDate() != null)  {
                                            Calendar toDate = Calendar.getInstance();
                                            toDate.setTime(event.getToDate());
                                    %>

                                    <script type="text/javascript">
                                        document.getElementById('endAMPM').value = '<%=toDate.get(Calendar.AM_PM) == Calendar.AM? "AM" : "PM"%>';
                                    </script>
                                    <%
                                        }
                                    %>
                                </td>
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
                            <td style="vertical-align: top">Description</td>
                            <td><textarea cols="50" rows="5" name="<%=PARAM_EVENT_DESCRIPTION%>"><%=ServletUtil.hideNulls(event.getDescription())%></textarea></td>
                        </tr>
                    </table>
                    </div>
                </td>
            </tr>
        </tbody>
        </table>
    </form>
</div>
</div>
</div>


<jsp:include page="/include/Footer.jsp"/>