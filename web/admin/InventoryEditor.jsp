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
        ResultSet inventoryCategories = ServletUtil.getResultSetFromSql("select * from stt_inventory_category where inv_uid = " + inventory.getUid());

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
                jQuery('#item_ext_name').text("New Yu-gi-oh Card");
            } else if(value == 'MAGIC')  {
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').show();
                jQuery('#eventDiv').hide();
                jQuery('#item_ext_name').text("New Magic Card");
            } else if(value == 'EVENT')  {
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').show();
                jQuery('#item_ext_name').text("New Event");
            } else  {
                jQuery('#yugiohDiv').hide();
                jQuery('#magicDiv').hide();
                jQuery('#eventDiv').hide();
                jQuery('#item_ext_name').text("");
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
                    boolean isChecked = false;
                    for(Category currentCategory : categories)  {
                        isChecked = false;
                        if(inventoryCategories != null)  {
                            while(inventoryCategories.next())  {
                                if(inventoryCategories.getInt("cat_uid") == currentCategory.getCatUid())  {
                                    isChecked = true;
                                }
                            }
                            inventoryCategories.beforeFirst();
                        }
                %>
                    <label class="checkbox-inline col-sm-3">
                        <input type="checkbox" <%=isChecked? "checked=\"checked\"" : ""%> name="<%=PARAM_CATEGORIES%>" value="<%=currentCategory.getCatUid()%>">
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
            <div class="col-sm-12"><b>Is this a foil/holo item?</b></div>
            <div class="col-sm-4">
                <label class="checkbox-inline">
                    <input type="checkbox" name="<%=PARAM_FOILED%>" <%=inventory.getInvFoil() == 1? "checked=\"checked\"" : ""%>>
                    Foil/Holo
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
        </div>
        <hr class="col-sm-10" />
        <div class="col-sm-6 container-fluid">
            <div>
                <div><b>Please make sure that the image name is accurate.</b></div>
                <div><b>Current Image:</b> <i><%=inventory.getImage() == null? "N/A" : inventory.getImage()%></i></div>
            </div>
            <div>
                <div><b>Image: </b> <input type="file" name="<%=PARAM_INVENTORY_IMAGE%>"></div>
            </div>
        </div>
        <div class="col-sm-6 container-fluid">
            <div>
                <div><b>Description</b></div>
                <div><textarea cols="40" rows="5" name="<%=PARAM_INVENTORY_DESCRIPTION%>"><%=ServletUtil.hideNulls(inventory.getDescription())%></textarea></div>
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
        <hr class="col-sm-10" />
        <div class="col-sm-12"><h3 id="item_ext_name"></h3></div>
        <hr class="col-sm-10" />

        <div id="magicDiv" class="col-sm-12">
            <div>
                <span><b>Timeshifted?: </b></span>
                <span><input type="checkbox" name="<%=PARAM_MAGIC_TIMESHIFTED%>" <%=magicCard != null && magicCard.getMcrTimeshifted() != null && magicCard.getMcrTimeshifted() == 1? "checked = \"checked\"" : ""%>/></span>
            </div>
            <hr class="col-sm-10" />
            <div class="col-sm-4">
                <div>
                    <div><b>Set:</b></div>
                    <div>
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
                    </div>
                </div>
                <div>
                    <div>
                        <div><b>Name: </b></div>
                        <div><input type="text" name="<%=PARAM_MAGIC_CARD_NAME%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrCardName())%>"/></div>
                    </div>
                    <div>
                        <div><b>Names: </b></div>
                        <div><input type="text" name="<%=PARAM_MAGIC_NAMES%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrNames())%>"></div>
                    </div>
                </div>
                <div>
                    <div><b>Rarity: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_RARITY%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrRarity())%>"></div>
                </div>
                <div>
                    <div><b>Type: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_TYPE%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrType())%>"></div>
                </div>
                <div>
                    <div><b>Types: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_TYPES%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrTypes())%>"></div>
                </div>
                <div>
                    <div><b>Super Types: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_SUPER_TYPES%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrSuperTypes())%>"></div>
                </div>
                <div>
                    <div><b>Sub Types: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_SUB_TYPES%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrSubTypes())%>"></div>
                </div>
            </div>
            <div class="col-sm-4">
                <div>
                    <div><b>Mana Cost: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_MANA_COST%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrManaCost())%>"></div>
                </div>
                <div>
                    <div><b>Colors: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_COLORS%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrColors())%>"></div>
                </div>
                <div>
                    <div><b>CMC: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_CMC%>" value="<%=magicCard.getMcrCmc() == null? "$0.00" : magicCard.getMcrCmc()%>"></div>
                </div>
                <div>
                    <div><b>Power: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_POWER%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrPower())%>"></div>
                </div>
                <div>
                    <div><b>Toughness: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_TOUGHNESS%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrToughness())%>"></div>
                </div>
                <div>
                    <div><b>Loyalty: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_LOYALTY%>" value="<%=magicCard.getMcrLoyalty() == null? "0" : magicCard.getMcrLoyalty()%>"></div>
                </div>
                <div>
                    <div><b>Set Number: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_NUMBER%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrNumber())%>"></div>
                </div>
                <div>
                    <div><b>Multiverse ID: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_MULTIVERSE_ID%>" value="<%=magicCard.getMcrMultiverseId() == null? "0" : magicCard.getMcrMultiverseId()%>"></div>
                </div>
            </div>
            <div class="col-sm-4">
                <div>
                    <div><b>Artist: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_ARTIST%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrArtist())%>"></div>
                </div>
                <div>
                    <div><b>Layout: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_LAYOUT%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrLayout())%>"></div>
                </div>
                <div>
                    <div><b>Variations: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_VARIATIONS%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrVariations())%>"></div>
                </div>
                <div>
                    <div><b>Watermark: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_WATERMARK%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrWatermark())%>"></div>
                </div>
                <div>
                    <div><b>Border: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_BORDER%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrBorder())%>"></div>
                </div>
                <div>
                    <div><b>Hand: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_HAND%>" value="<%=magicCard.getMcrHand() == null? "0" : magicCard.getMcrHand()%>"></div>
                </div>
                <div>
                    <div><b>Life: </b></div>
                    <div><input type="text" name="<%=PARAM_MAGIC_LIFE%>" value="<%=magicCard.getMcrLife() == null? "0" : magicCard.getMcrLife()%>"></div>
                </div>
                <div>
                    <%--todo: give them a calendar object to select from that will auto populate this field.--%>
                    <div><b>Release Date: </b></div>
                    <div><input class="datePicker" type="text" name="<%=PARAM_MAGIC_RELEASE_DATE%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrReleaseDate())%>"></div>
                </div>
            </div>
            <div class="col-sm-12">
                <div class="col-sm-6">
                    <div><b>Text: </b></div>
                    <div><textarea cols="40" rows="5" name="<%=PARAM_MAGIC_TEXT%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrText())%>"></textarea></div>
                </div>
                <div class="col-sm-6">
                    <div><b>Flavor: </b></div>
                    <div><textarea cols="40" rows="5" name="<%=PARAM_MAGIC_FLAVOR%>" value="<%=ServletUtil.hideNulls(magicCard.getMcrFlavor())%>"></textarea></div>
                </div>
            </div>
        </div>
        <div id="yugiohDiv" class="col-sm-12">
            <div class="col-sm-4">
                <div>
                    <div><b>Name: </b></div>
                    <div><input type="text" name="<%=PARAM_YUGIOH_NAME%>" value="<%=ServletUtil.hideNulls(yugiohCard.getYcrName())%>"></div>
                </div>
                <div>
                    <div><b>Card Type: </b></div>
                    <div><input type="text" name="<%=PARAM_YUGIOH_CARD_TYPE%>" value="<%=ServletUtil.hideNulls(yugiohCard.getYcrCardType())%>"></div>
                </div>
                <div>
                    <div><b>Attribute: </b></div>
                    <div><input type="text" name="<%=PARAM_YUGIOH_ATTRIBUTE%>" value="<%=ServletUtil.hideNulls(yugiohCard.getYcrAttribute())%>"></div>
                </div>
                <div>
                    <div><b>Type: </b></div>
                    <div><input type="text" name="<%=PARAM_YUGIOH_TYPE%>" value="<%=ServletUtil.hideNulls(yugiohCard.getYcrType())%>"></div>
                </div>
            </div>
            <div class="col-sm-4">
                <div>
                    <div><b>Level: </b></div>
                    <div><input type="text" name="<%=PARAM_YUGIOH_LEVEL%>" value="<%=yugiohCard.getYcrLevel() == null? "0" : yugiohCard.getYcrLevel()%>"></div>
                </div>
                <div>
                    <div><b>ATK: </b></div>
                    <div><input type="text" name="<%=PARAM_YUGIOH_ATK%>" value="<%=yugiohCard.getYcrAtk() == null? "0" : yugiohCard.getYcrAtk()%>"/></div>
                </div>
                <div>
                    <div><b>DEF: </b></div>
                    <div><input type="text" name="<%=PARAM_YUGIOH_DEF%>" value="<%=yugiohCard.getYcrDef() == null? "0" : yugiohCard.getYcrDef()%>"></div>
                </div>
            </div>
            <div class="col-sm-12">
                <div>
                    <div><b>Text/Lore: </b></div>
                    <div><textarea cols="40" rows="5"  name="<%=PARAM_YUGIOH_DESCRIPTION%>"><%=ServletUtil.hideNulls(yugiohCard.getYcrDescription())%></textarea></div>
                </div>
            </div>
        </div>
        <div class="col-sm-12" id="eventDiv">
            <div class="col-sm-5">
                <div>
                    <div><b>Start Date: </b></div>
                    <div>
                        <span>
                            <input class="datePicker" type="text" name="<%=PARAM_FROM_EVENT_DATE%>" value="<%=event.getFromDate() == null? "" : DateUtil.getFormattedDate(event.getFromDate(), "MM/dd/yyyy")%>">
                        </span>
                        <span>
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
                        </span>
                        <span>
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
                        </span>
                        <span>
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
                        </span>
                    </div>
                </div>
                <div>
                    <div><b>End Date: </b></div>
                    <div>
                        <span>
                            <input class="datePicker" type="text" name="<%=PARAM_TO_EVENT_DATE%>" value="<%=event.getFromDate() == null? "" : DateUtil.getFormattedDate(event.getFromDate(), "MM/dd/yyyy")%>">
                        </span>
                        <span>
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
                        </span>
                        <span>
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
                        </span>
                        <span>
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
                        </span>
                    </div>
                </div>
                <div>
                    <div><b>Title</b></div>
                    <div><input type="text" name="<%=PARAM_EVENT_TITLE%>" value="<%=ServletUtil.hideNulls(event.getTitle())%>"/></div>
                </div>
            </div>
            <div class="col-sm-5">
                <div>
                    <div><b>Player Limit</b></div>
                    <div><input type="text" name="<%=PARAM_EVENT_PLAYER_LIMIT%>" value="<%=event.getPlayerLimit()%>"></div>
                </div>
                <div>
                    <div><b>Number in Store Users</b></div>
                    <div><input type="text" name="<%=PARAM_EVENT_NUMBER_IN_STORE_USERS%>" value="<%=event.getNumberInStoreUsers()%>"></div>
                </div>
            </div>
            <div class="col-sm-12">
                <div><b>Description</b></div>
                <div><textarea cols="40" rows="5" name="<%=PARAM_EVENT_DESCRIPTION%>"><%=ServletUtil.hideNulls(event.getDescription())%></textarea></div>
            </div>
        </div>
    </form>
<hr class="col-sm-10" />
</div>
</div>
</div>


<jsp:include page="/include/Footer.jsp"/>