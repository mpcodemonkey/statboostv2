package com.statboost.controllers.admin;

import com.statboost.models.inventory.Event;
import com.statboost.models.inventory.Inventory;
import com.statboost.models.mtg.MagicCard;
import com.statboost.models.ygo.YugiohCard;
import com.statboost.util.HibernateUtil;
import com.statboost.util.ServletUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Jessica on 9/25/14.
 */

@WebServlet("/admin/inventoryeditor")
public class InventoryEditorServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(InventoryEditorServlet.class);
    public static final String SRV_MAP = "/admin/inventoryeditor";
    public static final String PARAM_INVENTORY_UID = "inventoryUid";
    public static final String PARAM_INVENTORY_PRICE = "inventoryPrice";
    public static final String PARAM_INVENTORY_NAME = "inventoryName";
    public static final String PARAM_INVENTORY_IMAGE = "inventoryImage";
    public static final String PARAM_INVENTORY_PRE_ORDER = "inventoryPreOrder";
    public static final String PARAM_INVENTORY_DESCRIPTION = "inventoryDescription";
    //detemines if it is a magic card, yugioh card, event, or generic item
    public static final String PARAM_TYPE = "type";

    public static final String ATTR_INVENTORY = "inventory";
    public static final String ATTR_CONDITION_INVENTORY_LINKS = "conditionInventoryLinks";
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_INFO = "info";
    public static final String ATTR_WARNING = "warning";

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
    public static final String PARAM_MAGIC_IMAGE_NAME = "magicImageName";
    public static final String PARAM_MAGIC_WATERMARK = "magicWatermark";
    public static final String PARAM_MAGIC_BORDER = "magicBorder";
    public static final String PARAM_MAGIC_HAND = "magicHand";
    public static final String PARAM_MAGIC_LIFE = "magicLife";
    public static final String PARAM_MAGIC_TIMESHIFTED = "magicTimeShifted";
    public static final String PARAM_MAGIC_RESERVED = "magicReserved";
    public static final String PARAM_MAGIC_RELEASE_DATE = "magicReleaseDate";
    public static final String PARAM_MAGIC_CARD_UID = "magicCardUid";
    public static final String ATTR_MAGIC_CARD = "magicCard";

    //params for event
    public static final String PARAM_EVENT_DATE = "eventDate";
    public static final String PARAM_EVENT_TITLE = "eventTitle";
    public static final String PARAM_EVENT_DESCRIPTION = "eventDescription";
    public static final String PARAM_EVENT_PLAYER_LIMIT = "eventPlayerLimit";
    public static final String PARAM_EVENT_NUMBER_IN_STORE_USERS = "eventNumberInStoreUsers";
    public static final String PARAM_EVENT_UID = "eventUid";
    public static final String ATTR_EVENT = "event";



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Inventory inventory = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        ResultSet conditionInventoryLinks = null;
        MagicCard magicCard = null;
        YugiohCard yugiohCard = null;
        Event event = null;
        if(request.getParameter(PARAM_INVENTORY_UID) == null || request.getParameter(PARAM_INVENTORY_UID).equals("0") ||
                request.getParameter(PARAM_INVENTORY_UID).equals("0"))  {
            inventory = new Inventory();
        } else  {
            inventory = (Inventory) session.load(Inventory.class, Integer.parseInt(request.getParameter(PARAM_INVENTORY_UID)));
            magicCard = (MagicCard) session.load(MagicCard.class, Integer.parseInt(request.getParameter(PARAM_MAGIC_CARD_UID)));
            yugiohCard = (YugiohCard) session.load(YugiohCard.class, Integer.parseInt(request.getParameter(PARAM_YUGIOH_UID)));
            event = (Event) session.load(Event.class, Integer.parseInt(request.getParameter(PARAM_EVENT_UID)));
        }

        ResultSet conditions = ServletUtil.getResultSetFromSql("select * from stt_condition left join stt_condition_inventory_link on cnd_uid = cil_cnd_uid");

        forwardToEditor(request, response, null, "", conditions, inventory, magicCard, event, yugiohCard, "");
        session.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Inventory inventory = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        ResultSet conditionInventoryLinks = null;
        MagicCard magicCard = null;
        YugiohCard yugiohCard = null;
        Event event = null;
        if(request.getParameter(PARAM_INVENTORY_UID) == null || request.getParameter(PARAM_INVENTORY_UID).equals("0") ||
                request.getParameter(PARAM_INVENTORY_UID).equals("0"))  {
            inventory = new Inventory();
        } else  {
            inventory = (Inventory) session.load(Inventory.class, Integer.parseInt(request.getParameter(PARAM_INVENTORY_UID)));
            magicCard = (MagicCard) session.load(MagicCard.class, Integer.parseInt(request.getParameter(PARAM_MAGIC_CARD_UID)));
            yugiohCard = (YugiohCard) session.load(YugiohCard.class, Integer.parseInt(request.getParameter(PARAM_YUGIOH_UID)));
            event = (Event) session.load(Event.class, Integer.parseInt(request.getParameter(PARAM_EVENT_UID)));
        }

        ResultSet conditions = ServletUtil.getResultSetFromSql("select * from stt_condition left join " +
                "stt_condition_inventory_link on cnd_uid = cil_cnd_uid");

        ArrayList<String> errors = new ArrayList<String>();
        String warning = "";

        //inventory validation & setting
        if(request.getParameter(PARAM_INVENTORY_PRE_ORDER) != null && !request.getParameter(PARAM_INVENTORY_PRE_ORDER).equals(""))  {
            inventory.setPreOrder(true);
        } else  {
            inventory.setPreOrder(false);
        }

        //warning about not filling price
        if(request.getParameter(PARAM_INVENTORY_NAME) != null && !request.getParameter(PARAM_INVENTORY_NAME).equals(""))  {
            inventory.setName(request.getParameter(PARAM_INVENTORY_NAME));
        } else  {
            errors.add("You must enter the name of the inventory.");
        }

        if(request.getParameter(PARAM_INVENTORY_PRICE) != null && !request.getParameter(PARAM_INVENTORY_PRICE).equals("") &&
                !request.getParameter(PARAM_INVENTORY_PRICE).equals("0"))  {
          inventory.setPrice(Double.parseDouble(request.getParameter(PARAM_INVENTORY_PRICE)));
        } else  {
            warning = "You did not enter a price.";
        }

        inventory.setDescription(request.getParameter(PARAM_INVENTORY_DESCRIPTION));
        inventory.setEvent(event);
        inventory.setImage(request.getParameter(PARAM_INVENTORY_IMAGE));
        inventory.setMagicCard(magicCard);
        inventory.setYugiohCard(yugiohCard);

        //check if it is a yugioh card, if it is validate and set the yugioh fields
        if(request.getParameter(PARAM_TYPE) != null && !request.getParameter(PARAM_TYPE).equals("") &&
                request.getParameter(PARAM_TYPE).equals("YUGIOH"))  {
            //all fields required except description, atk, adn def


            //check if it is a magic card, if it is validate and set the magic fields
        } else if(request.getParameter(PARAM_TYPE) != null && !request.getParameter(PARAM_TYPE).equals("") &&
                request.getParameter(PARAM_TYPE).equals("MAGIC"))  {
            //all fields required except flavor and rules

            //check if it is a event, if it is validate and set the event fields
        } else if(request.getParameter(PARAM_TYPE) != null && !request.getParameter(PARAM_TYPE).equals("") &&
                request.getParameter(PARAM_TYPE).equals("EVENT"))  {

        }
        //otherwise it is just a generic item

        if(errors.size() == 0)  {
            //no errors so save the object and forward back to the editor with a save message
            //must save dependent objects first
            if(yugiohCard != null)  {
                session.save(yugiohCard);
            }

            if(magicCard != null)  {
                session.save(magicCard);
            }

            if(event != null)  {
                session.save(event);
            }

            session.save(inventory);

            session.getTransaction().commit();
            forwardToEditor(request, response, null, "The transaction was saved successfully", conditions, inventory,
                    magicCard, event, yugiohCard, warning);
        }  else  {
            forwardToEditor(request, response, errors, "", conditions, inventory, magicCard, event, yugiohCard, "");
        }

        session.close();
    }

    private static void forwardToEditor(HttpServletRequest request, HttpServletResponse response,ArrayList<String> errors,
                                        String info, ResultSet conditions, Inventory inventory, MagicCard magicCard,
                                        Event event, YugiohCard yugiohCard, String warning) throws IOException,
            ServletException {
        request.setAttribute(ATTR_CONDITION_INVENTORY_LINKS, conditions);
        request.setAttribute(ATTR_ERRORS, errors);
        request.setAttribute(ATTR_INFO, info);
        request.setAttribute(ATTR_EVENT, event);
        request.setAttribute(ATTR_INVENTORY, inventory);
        request.setAttribute(ATTR_MAGIC_CARD, magicCard);
        request.setAttribute(ATTR_YUGIOH_CARD, yugiohCard);
        request.setAttribute(ATTR_WARNING, warning);
        request.getRequestDispatcher("/admin/InventoryEditor.jsp").forward(request, response);
    }

    public static String getEditUrl(int inventoryUid)  {
        return SRV_MAP + "?" + PARAM_INVENTORY_UID + "=" + inventoryUid;
    }
}
