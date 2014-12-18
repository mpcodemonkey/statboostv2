package com.statboost.controllers.admin;

import com.statboost.models.actor.User;
import com.statboost.models.enumType.ItemCondition;
import com.statboost.models.inventory.*;
import com.statboost.models.mtg.MagicCard;
import com.statboost.models.mtg.MagicSet;
import com.statboost.models.ygo.YugiohCard;
import com.statboost.util.HibernateUtil;
import com.statboost.util.ServletUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jessica on 9/25/14.
 */

@WebServlet("/admin/inventoryeditor")
public class InventoryEditorServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(InventoryEditorServlet.class);
    public static final String SRV_MAP = "/admin/inventoryeditor";
    public static final String PARAM_INVENTORY_UID = "inventoryUid";
    public static final String PARAM_INVENTORY_NAME = "inventoryName";
    public static final String PARAM_INVENTORY_IMAGE = "inventoryImage";
    public static final String PARAM_INVENTORY_PRE_ORDER = "inventoryPreOrder";
    public static final String PARAM_INVENTORY_DESCRIPTION = "inventoryDescription";
    public static final String PARAM_DAMAGED_PRICE = "damagedPrice";
    public static final String PARAM_NUM_DAMAGED_IN_STOCK = "numDamagedInStock";
    public static final String PARAM_HEAVILY_PLAYED_PRICE = "heavilyPlayedPrice";
    public static final String PARAM_NUM_HEAVILY_PLAYED_IN_STOCK = "numHeavilyDamagedInStock";
    public static final String PARAM_MODERATELY_PLAYED_PRICE = "moderatelyPlayedPrice";
    public static final String PARAM_NUM_MODERATELY_PLAYED_IN_STOCK = "numModeratelyPlayedInStock";
    public static final String PARAM_LIGHTLY_PLAYED_PRICE = "lightlyPlayedPrice";
    public static final String PARAM_NEAR_MINT_PRICE = "nearMintPrice";
    public static final String PARAM_NEW_PRICE = "newPrice";
    public static final String PARAM_NUM_LIGHTLY_PLAYED_IN_STOCK = "numLightlyPlayedInStock";
    public static final String PARAM_NUM_NEAR_MINT_IN_STOCK = "nearMintInStock";
    public static final String PARAM_NUM_NEW_IN_STOCK = "numNewInStock";
    public static final String PARAM_FOILED = "foiled";

    //determines if it is a magic card, yugioh card, event, or generic item
    public static final String PARAM_TYPE = "type";
    public static final String ATTR_TYPE = "attrtype";

    public static final String ATTR_COST_ITEMS = "costItems";
    public static final String ATTR_INVENTORY = "inventory";
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_INFO = "info";
    public static final String ATTR_WARNING = "warning";
    public static final String ATTR_CATEGORIES = "categories";
    public static final String PARAM_CATEGORIES = "categoriesparam";
    public static final String ATTR_INVENTORY_CATEGORIES = "inventoryCategories";

    //params for yugioh card
    public static final String PARAM_YUGIOH_NAME = "yugiohName";
    public static final String PARAM_YUGIOH_CARD_TYPE = "yugiohCardType";
    public static final String PARAM_YUGIOH_ATTRIBUTE = "yugiohAttribute";
    public static final String PARAM_YUGIOH_TYPE = "yugiohType";
    public static final String PARAM_YUGIOH_LEVEL = "yugiohLevel";
    public static final String PARAM_YUGIOH_ATK = "yugiohATK";
    public static final String PARAM_YUGIOH_DEF = "yugiohDEF";
    public static final String PARAM_YUGIOH_DESCRIPTION = "yugiohDescription";
    public static final String PARAM_YUGIOH_UID = "yugiohCardUid";
    public static final String ATTR_YUGIOH_CARD = "yugiohCard";

    //params for magic card
    public static final String PARAM_MAGIC_CARD_NAME = "magicCardName";
    public static final String PARAM_MAGIC_NAMES = "magicNames";
    public static final String PARAM_MAGIC_SET_ID = "magicSetID";
    public static final String PARAM_MAGIC_MANA_COST = "magicManaCost";
    public static final String PARAM_MAGIC_CMC = "magicCMC";
    public static final String PARAM_MAGIC_COLORS = "magicColors";
    public static final String PARAM_MAGIC_TYPE = "magicType";
    public static final String PARAM_MAGIC_SUPER_TYPES = "magicSuperTypes";
    public static final String PARAM_MAGIC_SUB_TYPES = "magicSubTypes";
    public static final String PARAM_MAGIC_TYPES = "magicTypes";
    public static final String PARAM_MAGIC_RARITY = "magicRarity";
    public static final String PARAM_MAGIC_TEXT = "magicText";
    public static final String PARAM_MAGIC_FLAVOR = "magicFlavor";
    public static final String PARAM_MAGIC_ARTIST = "magicArtrist";
    public static final String PARAM_MAGIC_NUMBER = "magicNumber";
    public static final String PARAM_MAGIC_POWER = "magicPower";
    public static final String PARAM_MAGIC_TOUGHNESS = "magicToughness";
    public static final String PARAM_MAGIC_LOYALTY = "magicLotyalty";
    public static final String PARAM_MAGIC_LAYOUT = "magicLayout";
    public static final String PARAM_MAGIC_MULTIVERSE_ID = "multiverseID";
    public static final String PARAM_MAGIC_VARIATIONS = "variations";
    public static final String PARAM_MAGIC_WATERMARK = "magicWatermark";
    public static final String PARAM_MAGIC_BORDER = "magicBorder";
    public static final String PARAM_MAGIC_HAND = "magicHand";
    public static final String PARAM_MAGIC_LIFE = "magicLife";
    public static final String PARAM_MAGIC_TIMESHIFTED = "magicTimeShifted";
    public static final String PARAM_MAGIC_RESERVED = "magicReserved";
    public static final String PARAM_MAGIC_RELEASE_DATE = "magicReleaseDate";
    public static final String PARAM_MAGIC_CARD_UID = "magicCardUid";
    public static final String ATTR_MAGIC_CARD = "magicCard";
    public static final String ATTR_MAGIC_SETS = "magicSets";

    //params for event
    public static final String PARAM_EVENT_TITLE = "eventTitle";
    public static final String PARAM_EVENT_DESCRIPTION = "eventDescription";
    public static final String PARAM_EVENT_PLAYER_LIMIT = "eventPlayerLimit";
    public static final String PARAM_EVENT_NUMBER_IN_STORE_USERS = "eventNumberInStoreUsers";
    public static final String PARAM_EVENT_UID = "eventUid";
    public static final String ATTR_EVENT = "event";
    public static final String PARAM_FROM_EVENT_HOUR = "fromEventHour";
    public static final String PARAM_FROM_EVENT_MIN = "fromEventMin";
    public static final String PARAM_FROM_EVENT_AM_PM = "fromEventAMPM";
    public static final String PARAM_FROM_EVENT_DATE = "fromEventDate";
    public static final String PARAM_TO_EVENT_HOUR = "toEventHour";
    public static final String PARAM_TO_EVENT_MIN = "toEventMin";
    public static final String PARAM_TO_EVENT_AM_PM = "toEventAMPM";
    public static final String PARAM_TO_EVENT_DATE = "toEventDate";

    private ArrayList<String> errors = new ArrayList<>();
    private ArrayList<String> info = new ArrayList<>();
    private ArrayList<String> warning = new ArrayList<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession userSession = request.getSession(false); //obtain the session object if exists
        if (!User.isAdmin(userSession)) {
            response.sendRedirect("/");
            return;
        }
        Inventory inventory = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        MagicCard magicCard = null;
        YugiohCard yugiohCard = null;
        ResultSet magicSets = null;
        Event event = null;
        String typeToPass = "GENERIC";
        List<Cost> costs = null;
        List<Category> categories = null;
        List<InventoryCategory> inventoryCategories = null;
        if(request.getParameter(PARAM_INVENTORY_UID) == null || request.getParameter(PARAM_INVENTORY_UID).equals("0") ||
                request.getParameter(PARAM_INVENTORY_UID).equals("0"))  {
            inventory = new Inventory();
            magicCard = new MagicCard();
            yugiohCard = new YugiohCard();
            event = new Event();
        } else  {
            inventory = (Inventory) session.load(Inventory.class, Integer.parseInt(request.getParameter(PARAM_INVENTORY_UID)));
            costs = (List<Cost>) session.createSQLQuery("select * from stt_cost where cst_inv_uid = " + inventory.getUid()).addEntity(Cost.class).list();
            inventoryCategories = inventory != null? (List<InventoryCategory>) session.createSQLQuery("select * from stt_inventory_category where inv_uid = " + inventory.getUid()).addEntity(InventoryCategory.class).list() : null;
            //check if it has the item if it does not initialize it to a new item so that we aren't throwing nulls in
            // the jsp and they can switch the type
            if(inventory != null && inventory.getMagicCard() != null)  {
                magicCard = (MagicCard) session.load(MagicCard.class, inventory.getMagicCard().getMcrUid());
                typeToPass = "MAGIC";
            } else  {
                magicCard = new MagicCard();
            }

            if(inventory != null && inventory.getYugiohCard() != null)  {
                yugiohCard = (YugiohCard) session.load(YugiohCard.class, inventory.getYugiohCard().getYcrUid());
                typeToPass = "YUGIOH";
            } else  {
                yugiohCard = new YugiohCard();
            }

            if(inventory != null && inventory.getEvent() != null)  {
                event = (Event) session.load(Event.class, inventory.getEvent().getUid());
                typeToPass = "EVENT";
                if(event == null)  {
                    event = new Event();
                }
            } else  {
                event = new Event();
            }
        }

        if(request.getParameter(PARAM_TYPE) != null && request.getParameter(PARAM_TYPE).equals("EVENT"))  {
            typeToPass = "EVENT";
        }

        info = new ArrayList<>();
        warning = new ArrayList<>();
        errors = new ArrayList<>();

        magicSets = ServletUtil.getResultSetFromSql("select * from stt_magic_set");
        categories = (List<Category>) session.createSQLQuery("select * from stt_category").addEntity(Category.class).list();

        forwardToEditor(request, response, null, null, inventory, magicCard, event, yugiohCard, null, magicSets, typeToPass, costs, categories, inventoryCategories);
        session.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession userSession = request.getSession(false); //obtain the session object if exists
        if (!User.isAdmin(userSession)) {
            response.sendRedirect("/");
            return;
        }

        if(!ServletFileUpload.isMultipartContent(request))  {
            logger.error("The form must be type multipart/form-data");
        }

        info = new ArrayList<>();
        warning = new ArrayList<>();
        errors = new ArrayList<>();

        //configure upload settings
        FileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
        List<FileItem> uploadFileItems;

        try  {
            uploadFileItems = servletFileUpload.parseRequest(request);
        } catch (FileUploadException e)  {
            logger.error("Could not handle the uploaded content.");
            return;
        }

        HashMap<String, String> normalFields = getNormalFields(uploadFileItems);
        HashMap<String, FileItem> uploadFields = getUploadedFields(uploadFileItems);

        if(uploadFields != null && uploadFields.size() > 1)  {
            throw new IllegalStateException("More than one upload detected.");
        }

        Inventory inventory = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        MagicCard magicCard = null;
        YugiohCard yugiohCard = null;
        Event event = null;
        List<Cost> costs = null;
        ResultSet magicSets = null;
        List<Category> categories = null;
        List<InventoryCategory> inventoryCategories = null;
        if(normalFields.get(PARAM_INVENTORY_UID) == null || normalFields.get(PARAM_INVENTORY_UID).equals("0") ||
                normalFields.get(PARAM_INVENTORY_UID).equals("0"))  {
            inventory = new Inventory();
            //do not initialize the magic card, yugioh, card, or event, will do this only if necessary
        } else  {
            inventory = (Inventory) session.load(Inventory.class, Integer.parseInt(normalFields.get(PARAM_INVENTORY_UID)));
            costs = (List<Cost>) session.createSQLQuery("select * from stt_cost where cst_inv_uid = " + inventory.getUid()).addEntity(Cost.class).list();
            inventoryCategories = inventory != null? (List<InventoryCategory>) session.createSQLQuery("select * from stt_inventory_category where inv_uid = " + inventory.getUid()) : null;
            if(normalFields.get(PARAM_MAGIC_CARD_UID) != null && ! normalFields.get(PARAM_MAGIC_CARD_UID).equals("0") &&
                    ! normalFields.get(PARAM_MAGIC_CARD_UID).equals(""))  {
                magicCard = (MagicCard) session.load(MagicCard.class, Integer.parseInt(normalFields.get(PARAM_MAGIC_CARD_UID)));
            } else if(normalFields.get(PARAM_YUGIOH_UID) != null && !normalFields.get(PARAM_YUGIOH_UID).equals("") &&
                    !normalFields.get(PARAM_YUGIOH_UID).equals("0"))  {
                yugiohCard = (YugiohCard) session.load(YugiohCard.class, Integer.parseInt(normalFields.get(PARAM_YUGIOH_UID)));
            } else if(normalFields.get(PARAM_EVENT_UID) != null && !normalFields.get(PARAM_EVENT_UID).equals("")  &&
                    !normalFields.get(PARAM_EVENT_UID).equals("0"))  {
                event = (Event) session.load(Event.class, Integer.parseInt(normalFields.get(PARAM_EVENT_UID)));
            }
        }

        if(costs == null){
            costs = new ArrayList<Cost>();
        }

        magicSets = ServletUtil.getResultSetFromSql("select * from stt_magic_set");
        categories = (List<Category>) session.createSQLQuery("select * from stt_category").addEntity(Category.class).list();

        //inventory validation & setting
        if(normalFields.get(PARAM_INVENTORY_PRE_ORDER) != null && !normalFields.get(PARAM_INVENTORY_PRE_ORDER).equals(""))  {
            inventory.setPreOrder(true);
        } else  {
            inventory.setPreOrder(false);
        }

        if(normalFields.get(PARAM_FOILED) != null && !normalFields.get(PARAM_FOILED).equals(""))  {
            inventory.setInvFoil(Byte.parseByte("1"));
        } else  {
            inventory.setInvFoil(Byte.parseByte("0"));
        }

        //warning about not filling price
        inventory.setName(normalFields.get(PARAM_INVENTORY_NAME));
        if(normalFields.get(PARAM_INVENTORY_NAME) == null || normalFields.get(PARAM_INVENTORY_NAME).equals(""))  {
            errors.add("You must enter the name of the inventory.");
        }

        //fields not required
        inventory.setDescription(normalFields.get(PARAM_INVENTORY_DESCRIPTION));
        inventory.setEvent(event);
        inventory.setMagicCard(magicCard);
        inventory.setYugiohCard(yugiohCard);

        Cost damagedItem = null;
        Cost heavilyPlayed = null;
        Cost moderatelyPlayed = null;
        Cost lightlyPlayed = null;
        Cost nearMint = null;
        Cost newCost = null;
        if(costs != null)  {
            for(Cost currentCost: costs)  {
                if(currentCost.getItemCondition() == ItemCondition.DAMAGED)  {
                    damagedItem = currentCost;
                    break;
                }
            }

            for(Cost currentCost: costs)  {
                if(currentCost.getItemCondition() == ItemCondition.HEAVILY_PLAYED)  {
                    heavilyPlayed = currentCost;
                    break;
                }
            }

            for(Cost currentCost: costs)  {
                if(currentCost.getItemCondition() == ItemCondition.MODERATELY_PLAYED)  {
                    moderatelyPlayed = currentCost;
                    break;
                }
            }

            for(Cost currentCost: costs)  {
                if(currentCost.getItemCondition() == ItemCondition.LIGHTLY_PLAYED)  {
                    lightlyPlayed = currentCost;
                    break;
                }
            }

            for(Cost currentCost: costs)  {
                if(currentCost.getItemCondition() == ItemCondition.NEAR_MINT)  {
                    nearMint = currentCost;
                    break;
                }
            }

            for(Cost currentCost: costs)  {
                if(currentCost.getItemCondition() == ItemCondition.NEW)  {
                    newCost = currentCost;
                    break;
                }
            }
        }

        if(normalFields.get(PARAM_NUM_DAMAGED_IN_STOCK) != null && !normalFields.get(PARAM_NUM_DAMAGED_IN_STOCK).equals(""))  {
            if(damagedItem == null)  {
                damagedItem = new Cost();
                damagedItem.setItemCondition(ItemCondition.DAMAGED);
                costs.add(damagedItem);
            }

            damagedItem.setItemQuantity(Integer.parseInt(normalFields.get(PARAM_NUM_DAMAGED_IN_STOCK)));
        }

        if(normalFields.get(PARAM_DAMAGED_PRICE) != null && !normalFields.get(PARAM_DAMAGED_PRICE).equals(""))  {
            if(damagedItem == null)  {
                damagedItem = new Cost();
                damagedItem.setItemCondition(ItemCondition.DAMAGED);
                costs.add(damagedItem);
            }

            damagedItem.setItemPrice(Double.parseDouble(normalFields.get(PARAM_DAMAGED_PRICE)));
        }

        if(normalFields.get(PARAM_NUM_HEAVILY_PLAYED_IN_STOCK) != null && !normalFields.get(PARAM_NUM_HEAVILY_PLAYED_IN_STOCK).equals(""))  {
            if(heavilyPlayed == null)  {
                heavilyPlayed = new Cost();
                heavilyPlayed.setItemCondition(ItemCondition.HEAVILY_PLAYED);
                costs.add(heavilyPlayed);
            }

            heavilyPlayed.setItemQuantity(Integer.parseInt(normalFields.get(PARAM_NUM_HEAVILY_PLAYED_IN_STOCK)));
        }

        if(normalFields.get(PARAM_HEAVILY_PLAYED_PRICE) != null && !normalFields.get(PARAM_HEAVILY_PLAYED_PRICE).equals(""))  {
            if(heavilyPlayed == null)  {
                heavilyPlayed = new Cost();
                heavilyPlayed.setItemCondition(ItemCondition.HEAVILY_PLAYED);
                costs.add(heavilyPlayed);
            }

            heavilyPlayed.setItemPrice(Double.parseDouble(normalFields.get(PARAM_HEAVILY_PLAYED_PRICE)));
        }

        if(normalFields.get(PARAM_NUM_LIGHTLY_PLAYED_IN_STOCK) != null && !normalFields.get(PARAM_NUM_LIGHTLY_PLAYED_IN_STOCK).equals(""))  {
            if(lightlyPlayed == null)  {
                lightlyPlayed = new Cost();
                lightlyPlayed.setItemCondition(ItemCondition.LIGHTLY_PLAYED);
                costs.add(lightlyPlayed);
            }

            lightlyPlayed.setItemQuantity(Integer.parseInt(normalFields.get(PARAM_NUM_LIGHTLY_PLAYED_IN_STOCK)));
        }

        if(normalFields.get(PARAM_LIGHTLY_PLAYED_PRICE) != null && !normalFields.get(PARAM_LIGHTLY_PLAYED_PRICE).equals(""))  {
            if(lightlyPlayed == null)  {
                lightlyPlayed = new Cost();
                lightlyPlayed.setItemCondition(ItemCondition.LIGHTLY_PLAYED);
                costs.add(lightlyPlayed);
            }

            lightlyPlayed.setItemPrice(Double.parseDouble(normalFields.get(PARAM_LIGHTLY_PLAYED_PRICE)));
        }

        String[] categoriesChecked = null;
        if(normalFields.get(PARAM_CATEGORIES) != null)  {
            categoriesChecked = normalFields.get(PARAM_CATEGORIES).split(",");
        }

        boolean didCheck;
        List<InventoryCategory> categorySaved = (List<InventoryCategory>) session.createSQLQuery("select * from stt_inventory_category where inv_uid =" + inventory.getUid()).addEntity(InventoryCategory.class).list();
        List<InventoryCategory> categoriesToRemove = new ArrayList<InventoryCategory>();
        if(categoriesChecked != null || categorySaved != null)  {
            for(Category currentCategory: categories)  {
                didCheck = false;
                if(categoriesChecked != null)  {
                    for(String currentUid : categoriesChecked)  {
                        if((currentCategory.getCatUid() + "").equals(currentUid))  {
                            didCheck = true;
                            break;
                        }
                    }
                }


                InventoryCategory inventoryCategory = null;
                if(categorySaved != null)  {
                    for(InventoryCategory currentInventoryCategory : categorySaved)  {
                        if(currentInventoryCategory.getCatUid() == currentCategory.getCatUid())  {
                            inventoryCategory =  currentInventoryCategory;
                            break;
                        }
                    }
                }

                if(!didCheck && inventoryCategory != null)  {
                    //need to delete
                    categoriesToRemove.add(inventoryCategory);
                    session.delete(inventoryCategory);
                } else if(didCheck && inventoryCategory == null)  {
                    //need to create new record
                    InventoryCategory inventoryCategory1 = new InventoryCategory();
                    inventoryCategory1.setCatUid(currentCategory.getCatUid());
                    categorySaved.add(inventoryCategory1);
                }
                //else leave the same
            }
        }

        for(InventoryCategory currentOneToRemove: categoriesToRemove)  {
            categorySaved.remove(currentOneToRemove);
        }


        if(normalFields.get(PARAM_NUM_MODERATELY_PLAYED_IN_STOCK) != null && !normalFields.get(PARAM_NUM_MODERATELY_PLAYED_IN_STOCK).equals(""))  {
            if(moderatelyPlayed == null)  {
                moderatelyPlayed = new Cost();
                moderatelyPlayed.setItemCondition(ItemCondition.MODERATELY_PLAYED);
                costs.add(moderatelyPlayed);
            }

            moderatelyPlayed.setItemQuantity(Integer.parseInt(normalFields.get(PARAM_NUM_MODERATELY_PLAYED_IN_STOCK)));
        }

        if(normalFields.get(PARAM_MODERATELY_PLAYED_PRICE) != null && !normalFields.get(PARAM_MODERATELY_PLAYED_PRICE).equals(""))  {
            if(moderatelyPlayed == null)  {
                moderatelyPlayed = new Cost();
                moderatelyPlayed.setItemCondition(ItemCondition.MODERATELY_PLAYED);
                costs.add(moderatelyPlayed);
            }

            moderatelyPlayed.setItemPrice(Double.parseDouble(normalFields.get(PARAM_MODERATELY_PLAYED_PRICE)));
        }

        if(normalFields.get(PARAM_NUM_NEAR_MINT_IN_STOCK) != null && !normalFields.get(PARAM_NUM_NEAR_MINT_IN_STOCK).equals(""))  {
            if(nearMint == null)  {
                nearMint = new Cost();
                nearMint.setItemCondition(ItemCondition.NEAR_MINT);
                costs.add(nearMint);
            }

            nearMint.setItemQuantity(Integer.parseInt(normalFields.get(PARAM_NUM_NEAR_MINT_IN_STOCK)));
        }

        if(normalFields.get(PARAM_NEAR_MINT_PRICE) != null && !normalFields.get(PARAM_NEAR_MINT_PRICE).equals(""))  {
            if(nearMint == null)  {
                nearMint = new Cost();
                nearMint.setItemCondition(ItemCondition.NEAR_MINT);
                costs.add(nearMint);
            }

            nearMint.setItemPrice(Double.parseDouble(normalFields.get(PARAM_NEAR_MINT_PRICE)));
        }

        if(normalFields.get(PARAM_NUM_NEW_IN_STOCK) != null && !normalFields.get(PARAM_NUM_NEW_IN_STOCK).equals(""))  {
            if(newCost == null)  {
                newCost = new Cost();
                newCost.setItemCondition(ItemCondition.NEW);
                costs.add(newCost);
            }

            newCost.setItemQuantity(Integer.parseInt(normalFields.get(PARAM_NUM_NEW_IN_STOCK)));
        }

        if(normalFields.get(PARAM_NEW_PRICE) != null && !normalFields.get(PARAM_NEW_PRICE).equals(""))  {
            if(newCost == null)  {
                newCost = new Cost();
                newCost.setItemCondition(ItemCondition.NEW);
                costs.add(newCost);
            }

            newCost.setItemPrice(Double.parseDouble(normalFields.get(PARAM_NEW_PRICE)));
        }

        //not going to make the price required since some they will not have inventory for

        //check if it is a yugioh card, if it is validate and set the yugioh fields
        if(normalFields.get(PARAM_TYPE) != null && !normalFields.get(PARAM_TYPE).equals("") &&
                normalFields.get(PARAM_TYPE).equals("YUGIOH"))  {
            //check if it is a new record
            if(yugiohCard == null)  {
                yugiohCard = new YugiohCard();
            }

            //all fields required except description, atk, and def
            yugiohCard.setYcrDescription(normalFields.get(PARAM_YUGIOH_DESCRIPTION));

            if(normalFields.get(PARAM_YUGIOH_ATK) != null && !normalFields.get(PARAM_YUGIOH_ATK).equals(""))  {
                if(Integer.parseInt(normalFields.get(PARAM_YUGIOH_ATK)) >= 0)  {
                    yugiohCard.setYcrAtk(Integer.parseInt(normalFields.get(PARAM_YUGIOH_ATK)));
                } else  {
                    errors.add("The ATK must be greater than or equal to zero for the Yu-gi-oh card.");
                }
            }

            if(normalFields.get(PARAM_YUGIOH_DEF) != null && !normalFields.get(PARAM_YUGIOH_DEF).equals(""))  {
                if(Integer.parseInt(normalFields.get(PARAM_YUGIOH_DEF)) >= 0)  {
                    yugiohCard.setYcrDef(Integer.parseInt(normalFields.get(PARAM_YUGIOH_DEF)));
                } else {
                    errors.add("The DEF must be greater than or equal to zero for the Yu-gi-oh card.");
                }
            }

            yugiohCard.setYcrAttribute(normalFields.get(PARAM_YUGIOH_ATTRIBUTE));
            if(normalFields.get(PARAM_YUGIOH_ATTRIBUTE) == null ||
                    normalFields.get(PARAM_YUGIOH_ATTRIBUTE).equals("") ||
                    normalFields.get(PARAM_YUGIOH_ATTRIBUTE).length() > 25)  {
                errors.add("You must enter an attribute for the Yu-gi-oh card.");
            }

            yugiohCard.setYcrCardType(normalFields.get(PARAM_YUGIOH_CARD_TYPE));
            if(normalFields.get(PARAM_YUGIOH_CARD_TYPE) == null ||
                    normalFields.get(PARAM_YUGIOH_CARD_TYPE).equals("") ||
                    normalFields.get(PARAM_YUGIOH_CARD_TYPE).length() > 50)  {
                errors.add("You must enter a card type for the Yu-gi-oh card.");
            }

            if(normalFields.get(PARAM_YUGIOH_LEVEL) != null && !normalFields.get(PARAM_YUGIOH_LEVEL).equals(""))  {
                yugiohCard.setYcrLevel(Integer.parseInt(normalFields.get(PARAM_YUGIOH_LEVEL)));
            } else  {
                errors.add("You must enter the level for the Yu-gi-oh card.");
            }

            yugiohCard.setYcrName(normalFields.get(PARAM_YUGIOH_NAME));
            if(normalFields.get(PARAM_YUGIOH_NAME) == null || normalFields.get(PARAM_YUGIOH_NAME).equals("") ||
                    normalFields.get(PARAM_YUGIOH_NAME).length() > 255)  {
                errors.add("You must enter the name for the Yu-gi-oh card.");
            }

            yugiohCard.setYcrType(normalFields.get(PARAM_YUGIOH_TYPE));
            if(normalFields.get(PARAM_YUGIOH_TYPE) == null || normalFields.get(PARAM_YUGIOH_TYPE).equals("") ||
                    normalFields.get(PARAM_YUGIOH_TYPE).length() > 25)  {
                errors.add("You must enter the type for the Yu-gi-oh card.");
            }

            //check if it is a magic card, if it is validate and set the magic fields
        } else if(normalFields.get(PARAM_TYPE) != null && !normalFields.get(PARAM_TYPE).equals("") &&
                normalFields.get(PARAM_TYPE).equals("MAGIC"))  {
            //check if it is a new record
            if(magicCard == null)  {
                magicCard = new MagicCard();
            }

            //all fields required except flavor, text, power, toughness, hand, life
            magicCard.setMcrFlavor(normalFields.get(PARAM_MAGIC_FLAVOR));
            magicCard.setMcrPower(normalFields.get(PARAM_MAGIC_POWER));
            if(magicCard.getMcrPower().length() > 5)  {
                errors.add("The power cannot be more than 5 characters long");
            }
            magicCard.setMcrToughness(normalFields.get(PARAM_MAGIC_TOUGHNESS));
            magicCard.setMcrText(normalFields.get(PARAM_MAGIC_TEXT));

            if(normalFields.get(PARAM_MAGIC_HAND) != null && !normalFields.get(PARAM_MAGIC_HAND).equals(""))  {
                magicCard.setMcrHand(Integer.parseInt(normalFields.get(PARAM_MAGIC_HAND)));
            }

            if(normalFields.get(PARAM_MAGIC_LIFE) != null && !normalFields.get(PARAM_MAGIC_LIFE).equals(""))  {
                magicCard.setMcrLife(Integer.parseInt(normalFields.get(PARAM_MAGIC_LIFE)));
            }

            //required fields
            magicCard.setMcrArtist(normalFields.get(PARAM_MAGIC_ARTIST));
            if(normalFields.get(PARAM_MAGIC_ARTIST) == null || normalFields.get(PARAM_MAGIC_ARTIST).equals(""))  {
                errors.add("You must enter the artist of the Magic Card.");
            }

            magicCard.setMcrBorder(normalFields.get(PARAM_MAGIC_BORDER));
            if(normalFields.get(PARAM_MAGIC_BORDER) == null || normalFields.get(PARAM_MAGIC_BORDER).equals(""))  {
                errors.add("You must enter the border of the Magic Card");
            }

            magicCard.setMcrCardName(normalFields.get(PARAM_MAGIC_CARD_NAME));
            if(normalFields.get(PARAM_MAGIC_CARD_NAME) == null || normalFields.get(PARAM_MAGIC_CARD_NAME).equals(""))  {
                errors.add("You must enter the card name of the Magic Card.");
            }

            magicCard.setMcrCmc(Double.parseDouble(normalFields.get(PARAM_MAGIC_CMC)));
            if(normalFields.get(PARAM_MAGIC_CMC) == null || normalFields.get(PARAM_MAGIC_CMC).equals(""))  {
                errors.add("You must enter the CMC of the Magic Card.");
            }

            magicCard.setMcrColors(normalFields.get(PARAM_MAGIC_COLORS));
            if(normalFields.get(PARAM_MAGIC_COLORS) == null || normalFields.get(PARAM_MAGIC_COLORS).equals(""))  {
                errors.add("You must enter the color of the Magic Card.");
            }

            magicCard.setMcrLayout(normalFields.get(PARAM_MAGIC_LAYOUT));
            if(normalFields.get(PARAM_MAGIC_LAYOUT) == null || normalFields.get(PARAM_MAGIC_LAYOUT).equals("") ||
                    normalFields.get(PARAM_MAGIC_LAYOUT).length() > 50)  {
                errors.add("You must select the layout of the Magic card.");
            }

            magicCard.setMcrLoyalty(Integer.parseInt(normalFields.get(PARAM_MAGIC_LOYALTY)));
            if(normalFields.get(PARAM_MAGIC_LOYALTY) == null || normalFields.get(PARAM_MAGIC_LOYALTY).equals("")
                    || Integer.parseInt(normalFields.get(PARAM_MAGIC_LOYALTY)) < 0)  {
                errors.add("You must select the life of the Magic card.");
            }

            magicCard.setMcrManaCost(normalFields.get(PARAM_MAGIC_MANA_COST));
            if(normalFields.get(PARAM_MAGIC_MANA_COST) == null ||
                    normalFields.get(PARAM_MAGIC_MANA_COST).equals("") ||
                    normalFields.get(PARAM_MAGIC_MANA_COST).length() > 50)  {
                errors.add("You must enter the mana cost of a Magic card.");
            }

            magicCard.setMcrMultiverseId(Integer.parseInt(normalFields.get(PARAM_MAGIC_MULTIVERSE_ID)));
            if(normalFields.get(PARAM_MAGIC_MULTIVERSE_ID) == null ||
                    normalFields.get(PARAM_MAGIC_MULTIVERSE_ID).equals("") ||
                    Integer.parseInt(normalFields.get(PARAM_MAGIC_MULTIVERSE_ID)) < 0)  {
                errors.add("You must enter the mutiverse id of the Magic card.");
            }

            magicCard.setMcrNames(normalFields.get(PARAM_MAGIC_NAMES));
            if(normalFields.get(PARAM_MAGIC_NAMES) == null || normalFields.get(PARAM_MAGIC_NAMES).equals("") ||
                    normalFields.get(PARAM_MAGIC_NAMES).length() > 150)  {
                errors.add("You must enter the names of the Magic card.");
            }

            magicCard.setMcrNumber(normalFields.get(PARAM_MAGIC_NUMBER));
            if(normalFields.get(PARAM_MAGIC_NUMBER) == null || normalFields.get(PARAM_MAGIC_NUMBER).equals("") ||
                    normalFields.get(PARAM_MAGIC_NUMBER).length() > 5)  {
                errors.add("You must enter the number of the Magic card.");
            }

            magicCard.setMcrRarity(normalFields.get(PARAM_MAGIC_RARITY));
            if(normalFields.get(PARAM_MAGIC_RARITY) == null || normalFields.get(PARAM_MAGIC_RARITY).equals("") ||
                    normalFields.get(PARAM_MAGIC_RARITY).length() > 50)  {
                errors.add("You must enter the rarity of the Magic card.");
            }

            magicCard.setMcrReleaseDate(normalFields.get(PARAM_MAGIC_RELEASE_DATE));
            if(normalFields.get(PARAM_MAGIC_RELEASE_DATE) == null ||
                    normalFields.get(PARAM_MAGIC_RELEASE_DATE).equals("") ||
                    normalFields.get(PARAM_MAGIC_RELEASE_DATE).length() > 50)  {
                errors.add("You must enter the release date of the magic card.");
            }

            if(normalFields.get(PARAM_MAGIC_RESERVED) != null && !normalFields.get(PARAM_MAGIC_RESERVED).equals("") &&
                    normalFields.get(PARAM_MAGIC_RESERVED).equals("true"))  {
                magicCard.setMcrReserved(Byte.parseByte("1"));
            } else  {
                magicCard.setMcrReserved(Byte.parseByte("0"));
            }

            if(normalFields.get(PARAM_MAGIC_SET_ID) != null && !normalFields.get(PARAM_MAGIC_SET_ID).equals(""))  {
                MagicSet magicSet = (MagicSet) session.get(MagicSet.class, normalFields.get(PARAM_MAGIC_SET_ID));
                if(magicSet != null)  {
                    magicCard.setMagicSet(magicSet);
                } else  {
                    errors.add("You must select a magic set.");
                }

            } else  {
                errors.add("You must select a magic set.");
            }

            magicCard.setMcrSubTypes(normalFields.get(PARAM_MAGIC_SUB_TYPES));
            if(normalFields.get(PARAM_MAGIC_SUB_TYPES) == null || normalFields.get(PARAM_MAGIC_SUB_TYPES).equals("") ||
                    normalFields.get(PARAM_MAGIC_SUB_TYPES).length() > 100)  {
                errors.add("You must enter a magic sub type.");
            }

            magicCard.setMcrSuperTypes(normalFields.get(PARAM_MAGIC_SUPER_TYPES));
            if(normalFields.get(PARAM_MAGIC_SUPER_TYPES) == null || normalFields.get(PARAM_MAGIC_SUPER_TYPES).equals("") ||
                    normalFields.get(PARAM_MAGIC_SUPER_TYPES).length() > 100)  {
                errors.add("You must enter the super type of the Magic Card.");
            }

            if(normalFields.get(PARAM_MAGIC_TIMESHIFTED) != null &&
                    normalFields.get(PARAM_MAGIC_TIMESHIFTED).equals("true"))  {
                magicCard.setMcrTimeshifted(Byte.parseByte("1"));
            } else  {
                magicCard.setMcrTimeshifted(Byte.parseByte("0"));
            }

            magicCard.setMcrType(normalFields.get(PARAM_MAGIC_TYPE));
            if(normalFields.get(PARAM_MAGIC_TYPE) == null || normalFields.get(PARAM_MAGIC_TYPE).equals("") ||
                    normalFields.get(PARAM_MAGIC_TYPE).length() > 500)  {
                errors.add("You must enter the type of the magic card,");
            }

            magicCard.setMcrTypes(normalFields.get(PARAM_MAGIC_TYPES));
            if(normalFields.get(PARAM_MAGIC_TYPES) == null || normalFields.get(PARAM_MAGIC_TYPES).equals("") ||
                    normalFields.get(PARAM_MAGIC_TYPES).length() > 100)  {
                errors.add("You must enter the types of the magic card.");
            }

            magicCard.setMcrVariations(normalFields.get(PARAM_MAGIC_VARIATIONS));
            if(normalFields.get(PARAM_MAGIC_VARIATIONS) == null ||
                    normalFields.get(PARAM_MAGIC_VARIATIONS).equals("") ||
                    normalFields.get(PARAM_MAGIC_VARIATIONS).length() > 100)  {
                errors.add("You must enter the variations of the magic card.");
            }

            magicCard.setMcrWatermark(normalFields.get(PARAM_MAGIC_WATERMARK));
            if(normalFields.get(PARAM_MAGIC_WATERMARK) == null ||
                    normalFields.get(PARAM_MAGIC_WATERMARK).equals("") ||
                   normalFields.get(PARAM_MAGIC_WATERMARK).length() > 50)  {
                errors.add("You must enter the watermark of the magic card.");
            }

            //check if it is a event, if it is validate and set the event fields
        } else if(normalFields.get(PARAM_TYPE) != null && !normalFields.get(PARAM_TYPE).equals("") &&
                normalFields.get(PARAM_TYPE).equals("EVENT"))  {

            event = new Event();

            Calendar fromDate = Calendar.getInstance();
            if(normalFields.get(PARAM_FROM_EVENT_DATE) != null && !normalFields.get(PARAM_FROM_EVENT_DATE).equals(""))  {
                fromDate.setTime(ServletUtil.getDateFromString(normalFields.get(PARAM_FROM_EVENT_DATE)));
            } else  {
                errors.add("You must select a start date for the event.");
            }

            if(normalFields.get(PARAM_FROM_EVENT_HOUR) != null && !normalFields.get(PARAM_FROM_EVENT_HOUR).equals(""))  {
                //sets it on a 12 hr basis
                fromDate.set(Calendar.HOUR, Integer.parseInt(normalFields.get(PARAM_FROM_EVENT_HOUR)));
            }

            if(normalFields.get(PARAM_FROM_EVENT_MIN) != null && !normalFields.get(PARAM_FROM_EVENT_MIN).equals(""))  {
                fromDate.set(Calendar.MINUTE, Integer.parseInt(normalFields.get(PARAM_FROM_EVENT_MIN)));
            }

            if(normalFields.get(PARAM_FROM_EVENT_AM_PM) != null && !normalFields.get(PARAM_FROM_EVENT_AM_PM).equals(""))  {
                if(normalFields.get(PARAM_FROM_EVENT_AM_PM).equalsIgnoreCase("AM"))  {
                    fromDate.set(Calendar.AM_PM, Calendar.AM);
                } else  {
                    fromDate.set(Calendar.AM_PM, Calendar.PM);
                }
            }

            event.setFromDate(fromDate.getTime());

            Calendar toDate = Calendar.getInstance();
            if(normalFields.get(PARAM_TO_EVENT_DATE) != null && !normalFields.get(PARAM_TO_EVENT_DATE).equals(""))  {
                toDate.setTime(ServletUtil.getDateFromString(normalFields.get(PARAM_TO_EVENT_DATE)));
            } else  {
                errors.add("You must select an end date for the event.");
            }

            if(normalFields.get(PARAM_TO_EVENT_HOUR) != null && !normalFields.get(PARAM_TO_EVENT_HOUR).equals(""))  {
                //sets it on a 12 hr basis
                toDate.set(Calendar.HOUR, Integer.parseInt(normalFields.get(PARAM_TO_EVENT_HOUR)));
            }

            if(normalFields.get(PARAM_TO_EVENT_MIN) != null && !normalFields.get(PARAM_TO_EVENT_MIN).equals(""))  {
                toDate.set(Calendar.MINUTE, Integer.parseInt(normalFields.get(PARAM_TO_EVENT_MIN)));
            }

            if(normalFields.get(PARAM_TO_EVENT_AM_PM) != null && !normalFields.get(PARAM_TO_EVENT_AM_PM).equals(""))  {
                if(normalFields.get(PARAM_TO_EVENT_AM_PM).equalsIgnoreCase("AM"))  {
                    toDate.set(Calendar.AM_PM, Calendar.AM);
                } else  {
                    toDate.set(Calendar.AM_PM, Calendar.PM);
                }
            }

            event.setToDate(toDate.getTime());

            if(event.getFromDate() != null && event.getToDate() != null && event.getFromDate().after(event.getToDate()))  {
                errors.add("The start date must fall before the end date.");
            }

            event.setDescription(normalFields.get(PARAM_EVENT_DESCRIPTION));
            if(normalFields.get(PARAM_EVENT_DESCRIPTION) == null ||
                    normalFields.get(PARAM_EVENT_DESCRIPTION).equals(""))  {
                errors.add("You must enter a description for the event.");
            }

            if(normalFields.get(PARAM_EVENT_NUMBER_IN_STORE_USERS) != null &&
                    !normalFields.get(PARAM_EVENT_NUMBER_IN_STORE_USERS).equals(""))  {
                event.setNumberInStoreUsers(Integer.parseInt(normalFields.get(PARAM_EVENT_NUMBER_IN_STORE_USERS)));
            } else  {
                errors.add("You must enter the number of in store users.");
            }

            if(normalFields.get(PARAM_EVENT_PLAYER_LIMIT) != null &&
                    !normalFields.get(PARAM_EVENT_PLAYER_LIMIT).equals(""))  {
                event.setPlayerLimit(Integer.parseInt(normalFields.get(PARAM_EVENT_PLAYER_LIMIT)));
            } else  {
                errors.add("You must enter the player limit.");
            }

            event.setTitle(normalFields.get(PARAM_EVENT_TITLE));
            if(normalFields.get(PARAM_EVENT_TITLE) == null || normalFields.get(PARAM_EVENT_TITLE).equals("") ||
                    normalFields.get(PARAM_EVENT_TITLE).length() > 200)  {
                errors.add("You must enter a title for the event.");
            }

        }

        //check that there are not errors before creating the image (otherwise we do not want to upload it)
        if(errors.size() == 0)  {
            processUpload(request, uploadFields, normalFields.get(PARAM_TYPE), session, inventory);
        }

        //make sure there are no errors generated from the image or otherwise
        if(errors.size() == 0)  {
            //no errors so save the object and forward back to the editor with a save message
            if(yugiohCard != null)  {
                inventory.setYugiohCard(yugiohCard);
                //set the others to null just in case they changed the type
                inventory.setEvent(null);
                inventory.setMagicCard(null);
            } else  {
                yugiohCard = new YugiohCard();
            }

            if(magicCard != null)  {
                inventory.setMagicCard(magicCard);
                //set the others to null just in case they changed the type
                inventory.setYugiohCard(null);
                inventory.setEvent(null);
            } else  {
                magicCard = new MagicCard();
            }

            if(event != null)  {
                inventory.setEvent(event);
                //set the others to null just in case they changed the type
                inventory.setMagicCard(null);
                inventory.setYugiohCard(null);
            } else  {
                event = new Event();
            }

            session.save(inventory);

            ResultSet rs = ServletUtil.getResultSetFromSql("select * from stt_cost where cst_inv_uid = " + inventory.getUid());
            ArrayList<Integer> stockNotificationsToRemove = new ArrayList<>();
            if(costs != null)  {
                for(Cost currentCost : costs)  {
                    if(currentCost.getItemPrice() > 0 || currentCost.getItemQuantity() > 0)  {
                        try {
                            if(rs != null && rs.next())  {
                                while(rs.next())  {
                                    if(rs.getString("cst_item_condition").equalsIgnoreCase(currentCost.getItemCondition().toString()))  {
                                        if(rs.getInt("cst_item_quantity") > currentCost.getItemQuantity())  {
                                            //do check here
                                            ResultSet usersToNotify = ServletUtil.getResultSetFromSql("select * from stt_stock_notification where snt_inv_uid = " + inventory.getUid() + " and snt_condition = '" + currentCost.getItemCondition() + "'");
                                            while(usersToNotify != null && usersToNotify.next())  {
                                                //send email here
                                                stockNotificationsToRemove.add(usersToNotify.getInt("snt_uid"));
                                            }
                                        }
                                        break;
                                    }
                                }
                                //possibly greater must check
                            } else  {
                                //don't exit so must check for any stock notifications that exist
                                currentCost.setInvUid(inventory.getUid());
                                session.save(currentCost);
                            }
                        } catch (SQLException e) {
                            logger.error("Could not go through resultset.", e);
                        }
                    }
                }
            }

            if(categorySaved != null)  {
                for(InventoryCategory currentInventoryCategory : categorySaved)  {
                    currentInventoryCategory.setInvUid(inventory.getUid());
                    session.save(currentInventoryCategory);
                }
            }

            //delete stock notification
            String toDelete = "";
            for(int currentNotification: stockNotificationsToRemove)  {
                toDelete += currentNotification + ", ";
            }

            //gets rid of last comma
            if(toDelete.length() > 2)  {
                toDelete = toDelete.substring(0, toDelete.length() - 2);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -3);
            String sql = "delete from stt_stock_notification where snt_uid in (:toDelete) or snt_created < :dateCreated";
            Query query = session.createSQLQuery(sql);
            query.setParameter("toDelete", toDelete);
            query.setParameter("dateCreated", calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DAY_OF_MONTH) + "0000");
            int result = query.executeUpdate();

            if(result == 0)  {
                logger.error("Could not delete the stock notifications.");
            }

            session.getTransaction().commit();
            info.add("The transaction was saved successfully");
            forwardToEditor(request, response, null, info, inventory,
                    magicCard, event, yugiohCard, warning, magicSets, normalFields.get(PARAM_TYPE), costs, categories, inventoryCategories);
        }  else  {
            if(magicCard == null)  {
                magicCard = new MagicCard();
            }

            if(yugiohCard == null)  {
                yugiohCard = new YugiohCard();
            }

            if(event == null)  {
                event = new Event();
            }
            forwardToEditor(request, response, errors, null, inventory, magicCard, event, yugiohCard, null, magicSets,
                    normalFields.get(PARAM_TYPE), costs, categories, inventoryCategories);
        }

        session.close();
    }

    private static void forwardToEditor(HttpServletRequest request, HttpServletResponse response,ArrayList<String> errors,
                                        ArrayList<String> info, Inventory inventory, MagicCard magicCard,
                                        Event event, YugiohCard yugiohCard, ArrayList<String> warning, ResultSet magicSets,
                                        String type, List<Cost> costs, List<Category> categories, List<InventoryCategory> inventoryCategories) throws IOException,
            ServletException {
        request.setAttribute(ATTR_ERRORS, errors);
        request.setAttribute(ATTR_INFO, info);
        request.setAttribute(ATTR_EVENT, event);
        request.setAttribute(ATTR_INVENTORY, inventory);
        request.setAttribute(ATTR_MAGIC_CARD, magicCard);
        request.setAttribute(ATTR_YUGIOH_CARD, yugiohCard);
        request.setAttribute(ATTR_WARNING, warning);
        request.setAttribute(ATTR_MAGIC_SETS, magicSets);
        request.setAttribute(ATTR_TYPE, type);
        request.setAttribute(ATTR_COST_ITEMS, costs);
        request.setAttribute(ATTR_CATEGORIES, categories);
        request.setAttribute(ATTR_INVENTORY_CATEGORIES, inventoryCategories);
        request.getRequestDispatcher("/admin/InventoryEditor.jsp").forward(request, response);
    }

    private void processUpload(HttpServletRequest request, HashMap<String,FileItem> uploadedFiles, String type, Session session, Inventory inventory)  {
        FileItem uploadFileItem = uploadedFiles.get(PARAM_INVENTORY_IMAGE);

        if(uploadFileItem == null)  {
            return;
        }

        String originalFileName = uploadFileItem.getName();
        if(originalFileName != null && !originalFileName.equals(""))  {
            inventory.setImage(originalFileName);
            String filePart = originalFileName;

            String magicPath = "inventory/mtg/";
            String yugiohPath = "inventory/yugioh/";
            String otherPath = "inventory/generic/";

            //default is website path
            String path = "website";
            if(type != null)  {
                if(type.equalsIgnoreCase("magic"))  {
                    path = magicPath;
                } else if(type.equalsIgnoreCase("yugioh"))  {
                    path = yugiohPath;
                } else if(type.equalsIgnoreCase("GENERIC"))  {
                    path = otherPath;
                }
            }

            //used for local
//            String uploadFilePath = "c:/Users/Jessica/IdeaProjects/statbooster2/web/images" + path + filePart;

            //used for prod on digitalocean
//            String uploadFilePath = "/home/images/inventory/other" + path + filePart;

            //used for prod on javapipe
            String uploadFilePath = "/static-images/" + path + filePart;


            File uploadFile = new File(uploadFilePath);
            Image image = new Image();
            image.setPath(uploadFilePath);

            try  {
                uploadFileItem.write(uploadFile);
                info.add("The file was uploaded successfully to " + uploadFilePath);
            }  catch(Exception e)  {
                logger.error("Could not write the file.", e);
                errors.add("The file could not be uploaded");
            }
        }
        //no error, they do not have to select an image to upload
    }


    //gets all of the other fields like text, checkbox, etc
    private HashMap<String, String> getNormalFields(List<FileItem> uploadItems) throws IOException  {
        HashMap<String, String> normalFields = new HashMap<>();

        for(FileItem currentItem : uploadItems)  {
            if(currentItem.isFormField())  {
                if(normalFields.get(currentItem.getFieldName()) == null)  {
                    normalFields.put(currentItem.getFieldName(), currentItem.getString());
                } else  {
                    normalFields.put(currentItem.getFieldName(), normalFields.get(currentItem.getFieldName()) + "," + currentItem.getString());
                }
            }
        }

        return normalFields;
    }

    //gets all of the fields that are files to upload
    private HashMap<String, FileItem> getUploadedFields(List<FileItem> uploadItems)  {
        HashMap<String, FileItem> normalFields = new HashMap<>();
        for (FileItem currentItem : uploadItems)  {
            if(!currentItem.isFormField())  {
                normalFields.put(currentItem.getFieldName(), currentItem);
            }
        }

        return normalFields;
    }

    public static String getEditUrl(int inventoryUid)  {
        return SRV_MAP + "?" + PARAM_INVENTORY_UID + "=" + inventoryUid;
    }
}
