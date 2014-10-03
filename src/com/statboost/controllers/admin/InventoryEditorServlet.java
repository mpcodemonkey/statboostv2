package com.statboost.controllers.admin;

import com.statboost.models.inventory.Event;
import com.statboost.models.inventory.Inventory;
import com.statboost.models.mtg.MagicCard;
import com.statboost.models.mtg.MagicSet;
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
            //check if it is a new record
            if(yugiohCard == null)  {
                yugiohCard = new YugiohCard();
            }

            //all fields required except description, atk, and def
            yugiohCard.setYcrDescription(request.getParameter(PARAM_YUGIOH_DESCRIPTION));

            if(request.getParameter(PARAM_YUGIOH_ATK) != null && !request.getParameter(PARAM_YUGIOH_ATK).equals(""))  {
                if(Integer.parseInt(request.getParameter(PARAM_YUGIOH_ATK)) >= 0)  {
                    yugiohCard.setYcrAtk(Integer.parseInt(request.getParameter(PARAM_YUGIOH_ATK)));
                } else  {
                    errors.add("The ATK must be greater than or equal to zero for the Yu-gi-oh card.");
                }
            }

            if(request.getParameter(PARAM_YUGIOH_DEF) != null && !request.getParameter(PARAM_YUGIOH_DEF).equals(""))  {
                if(Integer.parseInt(request.getParameter(PARAM_YUGIOH_DEF)) >= 0)  {
                    yugiohCard.setYcrDef(Integer.parseInt(request.getParameter(PARAM_YUGIOH_DEF)));
                } else {
                    errors.add("The DEF must be greater than or equal to zero for the Yu-gi-oh card.");
                }
            }

            if(request.getParameter(PARAM_YUGIOH_ATTRIBUTE) != null &&
                    !request.getParameter(PARAM_YUGIOH_ATTRIBUTE).equals("") &&
                    request.getParameter(PARAM_YUGIOH_ATTRIBUTE).length() <= 25)  {
                yugiohCard.setYcrAttribute(request.getParameter(PARAM_YUGIOH_ATTRIBUTE));
            } else  {
                errors.add("You must enter an attribute for the Yu-gi-oh card.");
            }

            if(request.getParameter(PARAM_YUGIOH_CARD_TYPE) != null &&
                    !request.getParameter(PARAM_YUGIOH_CARD_TYPE).equals("") &&
                    request.getParameter(PARAM_YUGIOH_CARD_TYPE).length() <= 50)  {
                yugiohCard.setYcrCardType(request.getParameter(PARAM_YUGIOH_CARD_TYPE));
            } else  {
                errors.add("You must enter a card type for the Yu-gi-oh card.");
            }

            if(request.getParameter(PARAM_YUGIOH_LEVEL) != null && !request.getParameter(PARAM_YUGIOH_LEVEL).equals(""))  {
                yugiohCard.setYcrLevel(Integer.parseInt(request.getParameter(PARAM_YUGIOH_LEVEL)));
            } else  {
                errors.add("You must enter the level for the Yu-gi-oh card.");
            }

            if(request.getParameter(PARAM_YUGIOH_NAME) != null && !request.getParameter(PARAM_YUGIOH_NAME).equals("") &&
                    request.getParameter(PARAM_YUGIOH_NAME).length() <=255)  {
                yugiohCard.setYcrName(request.getParameter(PARAM_YUGIOH_NAME));
            } else  {
                errors.add("You must enter the name for the Yu-gi-oh card.");
            }

            if(request.getParameter(PARAM_YUGIOH_TYPE) != null && !request.getParameter(PARAM_YUGIOH_TYPE).equals("") &&
                    request.getParameter(PARAM_YUGIOH_TYPE).length() <= 25)  {
                yugiohCard.setYcrType(request.getParameter(PARAM_YUGIOH_TYPE));
            } else  {
                errors.add("You must enter the type for the Yu-gi-oh card.");
            }

            //check if it is a magic card, if it is validate and set the magic fields
        } else if(request.getParameter(PARAM_TYPE) != null && !request.getParameter(PARAM_TYPE).equals("") &&
                request.getParameter(PARAM_TYPE).equals("MAGIC"))  {
            //check if it is a new record
            if(magicCard == null)  {
                magicCard = new MagicCard();
            }

            //all fields required except flavor, text, power, toughness, hand, life
            magicCard.setMcrFlavor(request.getParameter(PARAM_MAGIC_FLAVOR));
            magicCard.setMcrPower(request.getParameter(PARAM_MAGIC_POWER));
            magicCard.setMcrToughness(request.getParameter(PARAM_MAGIC_TOUGHNESS));
            magicCard.setMcrText(request.getParameter(PARAM_MAGIC_TEXT));

            if(request.getParameter(PARAM_MAGIC_HAND) != null && !request.getParameter(PARAM_MAGIC_HAND).equals(""))  {
                magicCard.setMcrHand(Integer.parseInt(request.getParameter(PARAM_MAGIC_HAND)));
            }

            if(request.getParameter(PARAM_MAGIC_LIFE) != null && !request.getParameter(PARAM_MAGIC_LIFE).equals(""))  {
                magicCard.setMcrLife(Integer.parseInt(request.getParameter(PARAM_MAGIC_LIFE)));
            }

            //required fields
            if(request.getParameter(PARAM_MAGIC_ARTIST) != null && !request.getParameter(PARAM_MAGIC_ARTIST).equals(""))  {
                magicCard.setMcrArtist(request.getParameter(PARAM_MAGIC_ARTIST));
            } else  {
                errors.add("You must enter the artist of the Magic Card.");
            }

            if(request.getParameter(PARAM_MAGIC_BORDER) != null && !request.getParameter(PARAM_MAGIC_BORDER).equals(""))  {
                magicCard.setMcrBorder(request.getParameter(PARAM_MAGIC_BORDER));
            } else  {
                errors.add("You must enter the border of the Magic Card");
            }

            if(request.getParameter(PARAM_MAGIC_CARD_NAME) != null && !request.getParameter(PARAM_MAGIC_CARD_NAME).equals(""))  {
                magicCard.setMcrCardName(request.getParameter(PARAM_MAGIC_CARD_NAME));
            } else  {
                errors.add("You must enter the card name of the Magic Card.");
            }

            if(request.getParameter(PARAM_MAGIC_CMC) != null && !request.getParameter(PARAM_MAGIC_CMC).equals(""))  {
                magicCard.setMcrCmc(Double.parseDouble(request.getParameter(PARAM_MAGIC_CMC)));
            } else  {
                errors.add("You must enter the CMC of the Magic Card.");
            }

            if(request.getParameter(PARAM_MAGIC_COLORS) != null && !request.getParameter(PARAM_MAGIC_COLORS).equals(""))  {
                magicCard.setMcrColors(request.getParameter(PARAM_MAGIC_COLORS));
            } else  {
                errors.add("You must enter the color of the Magic Card.");
            }

            //todo: discuss if we can move these images to the image field in inventory
            if(request.getParameter(PARAM_MAGIC_IMAGE_NAME) != null &&
                    !request.getParameter(PARAM_MAGIC_IMAGE_NAME).equals("") &&
                    request.getParameter(PARAM_MAGIC_IMAGE_NAME).length() <= 150)  {
                magicCard.setMcrImageName(request.getParameter(PARAM_MAGIC_IMAGE_NAME));
            } else  {
                errors.add("You must select an image for the magic card.");
            }

            if(request.getParameter(PARAM_MAGIC_LAYOUT) != null && !request.getParameter(PARAM_MAGIC_LAYOUT).equals("") &&
                    request.getParameter(PARAM_MAGIC_LAYOUT).length() <= 50)  {
                magicCard.setMcrLayout(request.getParameter(PARAM_MAGIC_LAYOUT));
            } else  {
                errors.add("You must select the layout of the Magic card.");
            }

            //todo: ask if it can be 0 or less
            if(request.getParameter(PARAM_MAGIC_LOYALTY) != null && !request.getParameter(PARAM_MAGIC_LOYALTY).equals(""))  {
                magicCard.setMcrLoyalty(Integer.parseInt(request.getParameter(PARAM_MAGIC_LOYALTY)));
            } else  {
                errors.add("You must select the life of the Magic card.");
            }

            if(request.getParameter(PARAM_MAGIC_MANA_COST) != null &&
                    !request.getParameter(PARAM_MAGIC_MANA_COST).equals("") &&
                    request.getParameter(PARAM_MAGIC_MANA_COST).length() <= 50)  {
                magicCard.setMcrManaCost(request.getParameter(PARAM_MAGIC_MANA_COST));
            } else  {
                errors.add("You must enter the mana cost of a Magic card.");
            }

            if(request.getParameter(PARAM_MAGIC_MULTIVERSE_ID) != null &&
                    !request.getParameter(PARAM_MAGIC_MULTIVERSE_ID).equals("") &&
                    Integer.parseInt(request.getParameter(PARAM_MAGIC_MULTIVERSE_ID)) >= 0)  {
                magicCard.setMcrMultiverseId(Integer.parseInt(request.getParameter(PARAM_MAGIC_MULTIVERSE_ID)));
            } else  {
                errors.add("You must enter the mutiverse id of the Magic card.");
            }

            if(request.getParameter(PARAM_MAGIC_NAMES) != null && !request.getParameter(PARAM_MAGIC_NAMES).equals("") &&
                    request.getParameter(PARAM_MAGIC_NAMES).length() <= 150)  {
                magicCard.setMcrNames(request.getParameter(PARAM_MAGIC_NAMES));
            } else  {
                errors.add("You must enter the names of the Magic card.");
            }

            if(request.getParameter(PARAM_MAGIC_NUMBER) != null && !request.getParameter(PARAM_MAGIC_NUMBER).equals("") &&
                    request.getParameter(PARAM_MAGIC_NUMBER).length() <= 5)  {
                magicCard.setMcrNumber(request.getParameter(PARAM_MAGIC_NUMBER));
            } else  {
                errors.add("You must enter the number of the Magic card.");
            }

            if(request.getParameter(PARAM_MAGIC_RARITY) != null && !request.getParameter(PARAM_MAGIC_RARITY).equals("") &&
                    request.getParameter(PARAM_MAGIC_RARITY).length() <= 50)  {
                magicCard.setMcrRarity(request.getParameter(PARAM_MAGIC_RARITY));
            } else  {
                errors.add("You must enter the rarity of the Magic card.");
            }

            if(request.getParameter(PARAM_MAGIC_RELEASE_DATE) != null &&
                    !request.getParameter(PARAM_MAGIC_RELEASE_DATE).equals("") &&
                    request.getParameter(PARAM_MAGIC_RELEASE_DATE).length() <= 50)  {
                magicCard.setMcrReleaseDate(request.getParameter(PARAM_MAGIC_RELEASE_DATE));
            } else  {
                errors.add("You must enter the release date of the magic card.");
            }

            if(request.getParameter(PARAM_MAGIC_RESERVED) != null && !request.getParameter(PARAM_MAGIC_RESERVED).equals("") &&
                    request.getParameter(PARAM_MAGIC_RESERVED).equals("true"))  {
                magicCard.setMcrReserved(Byte.parseByte("1"));
            } else  {
                magicCard.setMcrReserved(Byte.parseByte("0"));
            }

            if(request.getParameter(PARAM_MAGIC_SET_ID) != null && !request.getParameter(PARAM_MAGIC_SET_ID).equals(""))  {
                MagicSet magicSet = (MagicSet) session.get(MagicSet.class, request.getParameter(PARAM_MAGIC_SET_ID));
                if(magicSet != null)  {
                    magicCard.setMagicSet(magicSet);
                } else  {
                    errors.add("You must select a magic set.");
                }

            } else  {
                errors.add("You must select a magic set.");
            }

            if(request.getParameter(PARAM_MAGIC_SUB_TYPES) != null && !request.getParameter(PARAM_MAGIC_SUB_TYPES).equals("") &&
                    request.getParameter(PARAM_MAGIC_SUB_TYPES).length() <= 100)  {
                magicCard.setMcrSubTypes(request.getParameter(PARAM_MAGIC_SUB_TYPES));
            } else  {
                errors.add("You must enter a magic sub type.");
            }

            if(request.getParameter(PARAM_MAGIC_SUPER_TYPES) != null && !request.getParameter(PARAM_MAGIC_SUPER_TYPES).equals("") &&
                    request.getParameter(PARAM_MAGIC_SUPER_TYPES).length() <= 100)  {
                magicCard.setMcrSuperTypes(request.getParameter(PARAM_MAGIC_SUPER_TYPES));
            }

            if(request.getParameter(PARAM_MAGIC_TIMESHIFTED) != null &&
                    request.getParameter(PARAM_MAGIC_TIMESHIFTED).equals("true"))  {
                magicCard.setMcrTimeshifted(Byte.parseByte("1"));
            } else  {
                magicCard.setMcrTimeshifted(Byte.parseByte("0"));
            }

            if(request.getParameter(PARAM_MAGIC_TYPE) != null && !request.getParameter(PARAM_MAGIC_TYPE).equals("") &&
                    request.getParameter(PARAM_MAGIC_TYPE).length() <= 500)  {
                magicCard.setMcrType(request.getParameter(PARAM_MAGIC_TYPE));
            } else  {
                errors.add("You must enter the type of the magic card,");
            }

            if(request.getParameter(PARAM_MAGIC_TYPES) != null && !request.getParameter(PARAM_MAGIC_TYPES).equals("") &&
                    request.getParameter(PARAM_MAGIC_TYPES).length() <= 100)  {
                magicCard.setMcrTypes(request.getParameter(PARAM_MAGIC_TYPES));
            } else  {
                errors.add("You must enter the types of the magic card.");
            }

            if(request.getParameter(PARAM_MAGIC_VARIATIONS) != null &&
                    !request.getParameter(PARAM_MAGIC_VARIATIONS).equals("") &&
                    request.getParameter(PARAM_MAGIC_VARIATIONS).length() <= 100)  {
                magicCard.setMcrVariations(request.getParameter(PARAM_MAGIC_VARIATIONS));
            } else  {
                errors.add("You must enter the variations of the magic card.");
            }

            if(request.getParameter(PARAM_MAGIC_WATERMARK) != null &&
                    !request.getParameter(PARAM_MAGIC_WATERMARK).equals("") &&
                    request.getParameter(PARAM_MAGIC_WATERMARK).length() <= 50)  {
                magicCard.setMcrWatermark(request.getParameter(PARAM_MAGIC_WATERMARK));
            } else  {
                errors.add("You must enter the watermark of the magic card.");
            }

            //check if it is a event, if it is validate and set the event fields
        } else if(request.getParameter(PARAM_TYPE) != null && !request.getParameter(PARAM_TYPE).equals("") &&
                request.getParameter(PARAM_TYPE).equals("EVENT"))  {
            if(request.getParameter(PARAM_EVENT_DATE) != null && !request.getParameter(PARAM_EVENT_DATE).equals(""))  {
                event.setDate(ServletUtil.getDateTimeFromString(request.getParameter(PARAM_EVENT_DATE)));
            } else  {
                errors.add("You must select a date for the event.");
            }

            if(request.getParameter(PARAM_EVENT_DESCRIPTION) != null &&
                    !request.getParameter(PARAM_EVENT_DESCRIPTION).equals(""))  {
                event.setDescription(request.getParameter(PARAM_EVENT_DESCRIPTION));
            } else  {
                errors.add("You must enter a description for the event.");
            }

            if(request.getParameter(PARAM_EVENT_NUMBER_IN_STORE_USERS) != null &&
                    !request.getParameter(PARAM_EVENT_NUMBER_IN_STORE_USERS).equals(""))  {
                event.setNumberInStoreUsers(Integer.parseInt(request.getParameter(PARAM_EVENT_NUMBER_IN_STORE_USERS)));
            } else  {
                errors.add("You must enter the number of in store users.");
            }

            if(request.getParameter(PARAM_EVENT_PLAYER_LIMIT) != null &&
                    !request.getParameter(PARAM_EVENT_PLAYER_LIMIT).equals(""))  {
                event.setPlayerLimit(Integer.parseInt(request.getParameter(PARAM_EVENT_PLAYER_LIMIT)));
            } else  {
                errors.add("You must enter the player limit.");
            }

            if(request.getParameter(PARAM_EVENT_TITLE) != null && !request.getParameter(PARAM_EVENT_TITLE).equals("") &&
                    request.getParameter(PARAM_EVENT_TITLE).length() <= 200)  {
                event.setTitle(request.getParameter(PARAM_EVENT_TITLE));
            } else  {
                errors.add("You must enter a title for the event.");
            }

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
