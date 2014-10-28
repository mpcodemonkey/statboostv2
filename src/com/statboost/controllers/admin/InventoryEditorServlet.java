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
import java.util.Calendar;

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
    public static final String PARAM_HEAVILY_PLAYED_PRICE = "heavilyPlayedPrice";
    public static final String PARAM_LIGHTLY_PLAYED_PRICE = "lightlyPlayedPrice";
    public static final String PARAM_MODERATELY_PLAYED_PRICE = "moderatelyPlayedPrice";
    public static final String PARAM_NEAR_MINT_PRICE = "nearMintPrice";
    public static final String PARAM_NEW_PRICE = "newPrice";
    public static final String PARAM_NUM_DAMAGED_IN_STOCK = "numDamagedInStock";
    public static final String PARAM_NUM_HEAVILY_PLAYED_IN_STOCK = "numHeavilyDamagedInStock";
    public static final String PARAM_NUM_LIGHTLY_PLAYED_IN_STOCK = "numLightlyPlayedInStock";
    public static final String PARAM_NUM_MODERATELY_PLAYED_IN_STOCK = "numModeratelyPlayedInStock";
    public static final String PARAM_NUM_NEAR_MINT_IN_STOCK = "nearMintInStock";
    public static final String PARAM_NUM_NEW_IN_STOCK = "numNewInStock";

    //determines if it is a magic card, yugioh card, event, or generic item
    public static final String PARAM_TYPE = "type";
    public static final String ATTR_TYPE = "attrtype";

    public static final String ATTR_INVENTORY = "inventory";
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



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Inventory inventory = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        MagicCard magicCard = null;
        YugiohCard yugiohCard = null;
        ResultSet magicSets = null;
        Event event = null;
        String typeToPass = "GENERIC";
        if(request.getParameter(PARAM_INVENTORY_UID) == null || request.getParameter(PARAM_INVENTORY_UID).equals("0") ||
                request.getParameter(PARAM_INVENTORY_UID).equals("0"))  {
            inventory = new Inventory();
            magicCard = new MagicCard();
            yugiohCard = new YugiohCard();
            event = new Event();
        } else  {
            inventory = (Inventory) session.load(Inventory.class, Integer.parseInt(request.getParameter(PARAM_INVENTORY_UID)));
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
            } else  {
                event = new Event();
            }
        }

        magicSets = ServletUtil.getResultSetFromSql("select * from stt_magic_set");

        forwardToEditor(request, response, null, "", inventory, magicCard, event, yugiohCard, "", magicSets, typeToPass);
        session.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Inventory inventory = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        MagicCard magicCard = null;
        YugiohCard yugiohCard = null;
        Event event = null;
        ResultSet magicSets = null;
        if(request.getParameter(PARAM_INVENTORY_UID) == null || request.getParameter(PARAM_INVENTORY_UID).equals("0") ||
                request.getParameter(PARAM_INVENTORY_UID).equals("0"))  {
            inventory = new Inventory();
            //do not initialize the magic card, yugio, card, or event, will do this only if necessary
        } else  {
            inventory = (Inventory) session.load(Inventory.class, Integer.parseInt(request.getParameter(PARAM_INVENTORY_UID)));
            if(request.getParameter(PARAM_MAGIC_CARD_UID) != null && ! request.getParameter(PARAM_MAGIC_CARD_UID).equals("0") &&
                    ! request.getParameter(PARAM_MAGIC_CARD_UID).equals(""))  {
                magicCard = (MagicCard) session.load(MagicCard.class, Integer.parseInt(request.getParameter(PARAM_MAGIC_CARD_UID)));
            } else if(request.getParameter(PARAM_YUGIOH_UID) != null && !request.getParameter(PARAM_YUGIOH_UID).equals("") &&
                    !request.getParameter(PARAM_YUGIOH_UID).equals("0"))  {
                yugiohCard = (YugiohCard) session.load(YugiohCard.class, Integer.parseInt(request.getParameter(PARAM_YUGIOH_UID)));
            } else if(request.getParameter(PARAM_EVENT_UID) != null && !request.getParameter(PARAM_EVENT_UID).equals("")  &&
                    !request.getParameter(PARAM_EVENT_UID).equals("0"))  {
                event = (Event) session.load(Event.class, Integer.parseInt(request.getParameter(PARAM_EVENT_UID)));
            }
        }

        magicSets = ServletUtil.getResultSetFromSql("select * from stt_magic_set");

        ArrayList<String> errors = new ArrayList<String>();
        String warning = "";

        //inventory validation & setting
        if(request.getParameter(PARAM_INVENTORY_PRE_ORDER) != null && !request.getParameter(PARAM_INVENTORY_PRE_ORDER).equals(""))  {
            inventory.setPreOrder(true);
        } else  {
            inventory.setPreOrder(false);
        }

        //warning about not filling price
        inventory.setName(request.getParameter(PARAM_INVENTORY_NAME));
        if(request.getParameter(PARAM_INVENTORY_NAME) == null || request.getParameter(PARAM_INVENTORY_NAME).equals(""))  {
            errors.add("You must enter the name of the inventory.");
        }

        //fields not required
        inventory.setDescription(request.getParameter(PARAM_INVENTORY_DESCRIPTION));
        inventory.setEvent(event);
        //todo change how image works
        inventory.setImage(request.getParameter(PARAM_INVENTORY_IMAGE));
        inventory.setMagicCard(magicCard);
        inventory.setYugiohCard(yugiohCard);
/* COMMENTED OUT BECAUSE FIELDS HAVE CHANGED AND BROKEN
        if(request.getParameter(PARAM_NUM_DAMAGED_IN_STOCK) != null && !request.getParameter(PARAM_NUM_DAMAGED_IN_STOCK).equals(""))  {
            inventory.setDamagedInStock(Integer.parseInt(request.getParameter(PARAM_NUM_DAMAGED_IN_STOCK)));
        }

        if(request.getParameter(PARAM_NUM_HEAVILY_PLAYED_IN_STOCK) != null && !request.getParameter(PARAM_NUM_HEAVILY_PLAYED_IN_STOCK).equals(""))  {
            inventory.setHeavilyPlayedInStock(Integer.parseInt(request.getParameter(PARAM_NUM_HEAVILY_PLAYED_IN_STOCK)));
        }

        if(request.getParameter(PARAM_NUM_LIGHTLY_PLAYED_IN_STOCK) != null && !request.getParameter(PARAM_NUM_LIGHTLY_PLAYED_IN_STOCK).equals(""))  {
            inventory.setLightlyPlayedInStock(Integer.parseInt(request.getParameter(PARAM_NUM_LIGHTLY_PLAYED_IN_STOCK)));
        }

        if(request.getParameter(PARAM_NUM_MODERATELY_PLAYED_IN_STOCK) != null && !request.getParameter(PARAM_NUM_MODERATELY_PLAYED_IN_STOCK).equals(""))  {
            inventory.setModeratelyPlayedInStock(Integer.parseInt(request.getParameter(PARAM_NUM_MODERATELY_PLAYED_IN_STOCK)));
        }

        if(request.getParameter(PARAM_NUM_NEAR_MINT_IN_STOCK) != null && !request.getParameter(PARAM_NUM_NEAR_MINT_IN_STOCK).equals(""))  {
            inventory.setNearMintInStock(Integer.parseInt(request.getParameter(PARAM_NUM_NEAR_MINT_IN_STOCK)));
        }

        if(request.getParameter(PARAM_NUM_NEW_IN_STOCK) != null && !request.getParameter(PARAM_NUM_NEW_IN_STOCK).equals(""))  {
            inventory.setNumNewInStock(Integer.parseInt(request.getParameter(PARAM_NUM_NEW_IN_STOCK)));
        }

        if(request.getParameter(PARAM_DAMAGED_PRICE) != null && !request.getParameter(PARAM_DAMAGED_PRICE).equals(""))  {
            inventory.setDamagedPrice(Double.parseDouble(request.getParameter(PARAM_DAMAGED_PRICE)));
        }

        if(request.getParameter(PARAM_NEW_PRICE) != null && !request.getParameter(PARAM_NEW_PRICE).equals(""))  {
            inventory.setNewPrice(Double.parseDouble(request.getParameter(PARAM_NEW_PRICE)));
        }

        if(request.getParameter(PARAM_HEAVILY_PLAYED_PRICE) != null && !request.getParameter(PARAM_HEAVILY_PLAYED_PRICE).equals(""))  {
            inventory.setHeavilyPlayedPrice(Double.parseDouble(request.getParameter(PARAM_HEAVILY_PLAYED_PRICE)));
        }

        if(request.getParameter(PARAM_LIGHTLY_PLAYED_PRICE) != null && !request.getParameter(PARAM_LIGHTLY_PLAYED_PRICE).equals(""))  {
            inventory.setLightlyPlayedPrice(Double.parseDouble(request.getParameter(PARAM_LIGHTLY_PLAYED_PRICE)));
        }

        if(request.getParameter(PARAM_MODERATELY_PLAYED_PRICE) != null && !request.getParameter(PARAM_MODERATELY_PLAYED_PRICE).equals(""))  {
            inventory.setModeratelyPlayedPrice(Double.parseDouble(request.getParameter(PARAM_MODERATELY_PLAYED_PRICE)));
        }

        if(request.getParameter(PARAM_NEAR_MINT_PRICE) != null && !request.getParameter(PARAM_NEAR_MINT_PRICE).equals(""))  {
            inventory.setNearMintPrice(Double.parseDouble(request.getParameter(PARAM_NEAR_MINT_PRICE)));
        }
*/
        //not going to make the price required since some they will not have inventory for

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

            yugiohCard.setYcrAttribute(request.getParameter(PARAM_YUGIOH_ATTRIBUTE));
            if(request.getParameter(PARAM_YUGIOH_ATTRIBUTE) == null ||
                    request.getParameter(PARAM_YUGIOH_ATTRIBUTE).equals("") ||
                    request.getParameter(PARAM_YUGIOH_ATTRIBUTE).length() > 25)  {
                errors.add("You must enter an attribute for the Yu-gi-oh card.");
            }

            yugiohCard.setYcrCardType(request.getParameter(PARAM_YUGIOH_CARD_TYPE));
            if(request.getParameter(PARAM_YUGIOH_CARD_TYPE) == null ||
                    request.getParameter(PARAM_YUGIOH_CARD_TYPE).equals("") ||
                    request.getParameter(PARAM_YUGIOH_CARD_TYPE).length() > 50)  {
                errors.add("You must enter a card type for the Yu-gi-oh card.");
            }

            if(request.getParameter(PARAM_YUGIOH_LEVEL) != null && !request.getParameter(PARAM_YUGIOH_LEVEL).equals(""))  {
                yugiohCard.setYcrLevel(Integer.parseInt(request.getParameter(PARAM_YUGIOH_LEVEL)));
            } else  {
                errors.add("You must enter the level for the Yu-gi-oh card.");
            }

            yugiohCard.setYcrName(request.getParameter(PARAM_YUGIOH_NAME));
            if(request.getParameter(PARAM_YUGIOH_NAME) == null || request.getParameter(PARAM_YUGIOH_NAME).equals("") ||
                    request.getParameter(PARAM_YUGIOH_NAME).length() > 255)  {
                errors.add("You must enter the name for the Yu-gi-oh card.");
            }

            yugiohCard.setYcrType(request.getParameter(PARAM_YUGIOH_TYPE));
            if(request.getParameter(PARAM_YUGIOH_TYPE) == null || request.getParameter(PARAM_YUGIOH_TYPE).equals("") ||
                    request.getParameter(PARAM_YUGIOH_TYPE).length() > 25)  {
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
            if(magicCard.getMcrPower().length() > 5)  {
                errors.add("The power cannot be more than 5 characters long");
            }
            magicCard.setMcrToughness(request.getParameter(PARAM_MAGIC_TOUGHNESS));
            magicCard.setMcrText(request.getParameter(PARAM_MAGIC_TEXT));

            if(request.getParameter(PARAM_MAGIC_HAND) != null && !request.getParameter(PARAM_MAGIC_HAND).equals(""))  {
                magicCard.setMcrHand(Integer.parseInt(request.getParameter(PARAM_MAGIC_HAND)));
            }

            if(request.getParameter(PARAM_MAGIC_LIFE) != null && !request.getParameter(PARAM_MAGIC_LIFE).equals(""))  {
                magicCard.setMcrLife(Integer.parseInt(request.getParameter(PARAM_MAGIC_LIFE)));
            }

            //required fields
            magicCard.setMcrArtist(request.getParameter(PARAM_MAGIC_ARTIST));
            if(request.getParameter(PARAM_MAGIC_ARTIST) == null || request.getParameter(PARAM_MAGIC_ARTIST).equals(""))  {
                errors.add("You must enter the artist of the Magic Card.");
            }

            magicCard.setMcrBorder(request.getParameter(PARAM_MAGIC_BORDER));
            if(request.getParameter(PARAM_MAGIC_BORDER) == null || request.getParameter(PARAM_MAGIC_BORDER).equals(""))  {
                errors.add("You must enter the border of the Magic Card");
            }

            magicCard.setMcrCardName(request.getParameter(PARAM_MAGIC_CARD_NAME));
            if(request.getParameter(PARAM_MAGIC_CARD_NAME) == null || request.getParameter(PARAM_MAGIC_CARD_NAME).equals(""))  {
                errors.add("You must enter the card name of the Magic Card.");
            }

            magicCard.setMcrCmc(Double.parseDouble(request.getParameter(PARAM_MAGIC_CMC)));
            if(request.getParameter(PARAM_MAGIC_CMC) == null || request.getParameter(PARAM_MAGIC_CMC).equals(""))  {
                errors.add("You must enter the CMC of the Magic Card.");
            }

            magicCard.setMcrColors(request.getParameter(PARAM_MAGIC_COLORS));
            if(request.getParameter(PARAM_MAGIC_COLORS) == null || request.getParameter(PARAM_MAGIC_COLORS).equals(""))  {
                errors.add("You must enter the color of the Magic Card.");
            }

            //todo: discuss if we can move these images to the image field in inventory
            magicCard.setMcrImageName(request.getParameter(PARAM_MAGIC_IMAGE_NAME));
            if(request.getParameter(PARAM_MAGIC_IMAGE_NAME) == null ||
                    request.getParameter(PARAM_MAGIC_IMAGE_NAME).equals("") ||
                    request.getParameter(PARAM_MAGIC_IMAGE_NAME).length() > 150)  {
                errors.add("You must select an image for the magic card.");
            }

            magicCard.setMcrLayout(request.getParameter(PARAM_MAGIC_LAYOUT));
            if(request.getParameter(PARAM_MAGIC_LAYOUT) == null || request.getParameter(PARAM_MAGIC_LAYOUT).equals("") ||
                    request.getParameter(PARAM_MAGIC_LAYOUT).length() > 50)  {
                errors.add("You must select the layout of the Magic card.");
            }

            magicCard.setMcrLoyalty(Integer.parseInt(request.getParameter(PARAM_MAGIC_LOYALTY)));
            if(request.getParameter(PARAM_MAGIC_LOYALTY) == null || request.getParameter(PARAM_MAGIC_LOYALTY).equals("")
                    || Integer.parseInt(request.getParameter(PARAM_MAGIC_LOYALTY)) < 0)  {
                errors.add("You must select the life of the Magic card.");
            }

            magicCard.setMcrManaCost(request.getParameter(PARAM_MAGIC_MANA_COST));
            if(request.getParameter(PARAM_MAGIC_MANA_COST) == null ||
                    request.getParameter(PARAM_MAGIC_MANA_COST).equals("") ||
                    request.getParameter(PARAM_MAGIC_MANA_COST).length() > 50)  {
                errors.add("You must enter the mana cost of a Magic card.");
            }

            magicCard.setMcrMultiverseId(Integer.parseInt(request.getParameter(PARAM_MAGIC_MULTIVERSE_ID)));
            if(request.getParameter(PARAM_MAGIC_MULTIVERSE_ID) == null ||
                    request.getParameter(PARAM_MAGIC_MULTIVERSE_ID).equals("") ||
                    Integer.parseInt(request.getParameter(PARAM_MAGIC_MULTIVERSE_ID)) < 0)  {
                errors.add("You must enter the mutiverse id of the Magic card.");
            }

            magicCard.setMcrNames(request.getParameter(PARAM_MAGIC_NAMES));
            if(request.getParameter(PARAM_MAGIC_NAMES) == null || request.getParameter(PARAM_MAGIC_NAMES).equals("") ||
                    request.getParameter(PARAM_MAGIC_NAMES).length() > 150)  {
                errors.add("You must enter the names of the Magic card.");
            }

            magicCard.setMcrNumber(request.getParameter(PARAM_MAGIC_NUMBER));
            if(request.getParameter(PARAM_MAGIC_NUMBER) == null || request.getParameter(PARAM_MAGIC_NUMBER).equals("") ||
                    request.getParameter(PARAM_MAGIC_NUMBER).length() > 5)  {
                errors.add("You must enter the number of the Magic card.");
            }

            magicCard.setMcrRarity(request.getParameter(PARAM_MAGIC_RARITY));
            if(request.getParameter(PARAM_MAGIC_RARITY) == null || request.getParameter(PARAM_MAGIC_RARITY).equals("") ||
                    request.getParameter(PARAM_MAGIC_RARITY).length() > 50)  {
                errors.add("You must enter the rarity of the Magic card.");
            }

            magicCard.setMcrReleaseDate(request.getParameter(PARAM_MAGIC_RELEASE_DATE));
            if(request.getParameter(PARAM_MAGIC_RELEASE_DATE) == null ||
                    request.getParameter(PARAM_MAGIC_RELEASE_DATE).equals("") ||
                    request.getParameter(PARAM_MAGIC_RELEASE_DATE).length() > 50)  {
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

            magicCard.setMcrSubTypes(request.getParameter(PARAM_MAGIC_SUB_TYPES));
            if(request.getParameter(PARAM_MAGIC_SUB_TYPES) == null || request.getParameter(PARAM_MAGIC_SUB_TYPES).equals("") ||
                    request.getParameter(PARAM_MAGIC_SUB_TYPES).length() > 100)  {
                errors.add("You must enter a magic sub type.");
            }

            magicCard.setMcrSuperTypes(request.getParameter(PARAM_MAGIC_SUPER_TYPES));
            if(request.getParameter(PARAM_MAGIC_SUPER_TYPES) == null || request.getParameter(PARAM_MAGIC_SUPER_TYPES).equals("") ||
                    request.getParameter(PARAM_MAGIC_SUPER_TYPES).length() > 100)  {
                errors.add("You must enter the super type of the Magic Card.");
            }

            if(request.getParameter(PARAM_MAGIC_TIMESHIFTED) != null &&
                    request.getParameter(PARAM_MAGIC_TIMESHIFTED).equals("true"))  {
                magicCard.setMcrTimeshifted(Byte.parseByte("1"));
            } else  {
                magicCard.setMcrTimeshifted(Byte.parseByte("0"));
            }

            magicCard.setMcrType(request.getParameter(PARAM_MAGIC_TYPE));
            if(request.getParameter(PARAM_MAGIC_TYPE) == null || request.getParameter(PARAM_MAGIC_TYPE).equals("") ||
                    request.getParameter(PARAM_MAGIC_TYPE).length() > 500)  {
                errors.add("You must enter the type of the magic card,");
            }

            magicCard.setMcrTypes(request.getParameter(PARAM_MAGIC_TYPES));
            if(request.getParameter(PARAM_MAGIC_TYPES) == null || request.getParameter(PARAM_MAGIC_TYPES).equals("") ||
                    request.getParameter(PARAM_MAGIC_TYPES).length() > 100)  {
                errors.add("You must enter the types of the magic card.");
            }

            magicCard.setMcrVariations(request.getParameter(PARAM_MAGIC_VARIATIONS));
            if(request.getParameter(PARAM_MAGIC_VARIATIONS) == null ||
                    request.getParameter(PARAM_MAGIC_VARIATIONS).equals("") ||
                    request.getParameter(PARAM_MAGIC_VARIATIONS).length() > 100)  {
                errors.add("You must enter the variations of the magic card.");
            }

            magicCard.setMcrWatermark(request.getParameter(PARAM_MAGIC_WATERMARK));
            if(request.getParameter(PARAM_MAGIC_WATERMARK) == null ||
                    request.getParameter(PARAM_MAGIC_WATERMARK).equals("") ||
                   request.getParameter(PARAM_MAGIC_WATERMARK).length() > 50)  {
                errors.add("You must enter the watermark of the magic card.");
            }

            //check if it is a event, if it is validate and set the event fields
        } else if(request.getParameter(PARAM_TYPE) != null && !request.getParameter(PARAM_TYPE).equals("") &&
                request.getParameter(PARAM_TYPE).equals("EVENT"))  {

            event = new Event();

            Calendar fromDate = Calendar.getInstance();
            if(request.getParameter(PARAM_FROM_EVENT_DATE) != null && !request.getParameter(PARAM_FROM_EVENT_DATE).equals(""))  {
                fromDate.setTime(ServletUtil.getDateFromString(request.getParameter(PARAM_FROM_EVENT_DATE)));
            } else  {
                errors.add("You must select a start date for the event.");
            }

            if(request.getParameter(PARAM_FROM_EVENT_HOUR) != null && !request.getParameter(PARAM_FROM_EVENT_HOUR).equals(""))  {
                //sets it on a 12 hr basis
                fromDate.set(Calendar.HOUR, Integer.parseInt(request.getParameter(PARAM_FROM_EVENT_HOUR)));
            }

            if(request.getParameter(PARAM_FROM_EVENT_MIN) != null && !request.getParameter(PARAM_FROM_EVENT_MIN).equals(""))  {
                fromDate.set(Calendar.MINUTE, Integer.parseInt(request.getParameter(PARAM_FROM_EVENT_MIN)));
            }

            if(request.getParameter(PARAM_FROM_EVENT_AM_PM) != null && !request.getParameter(PARAM_FROM_EVENT_AM_PM).equals(""))  {
                if(request.getParameter(PARAM_FROM_EVENT_AM_PM).equalsIgnoreCase("AM"))  {
                    fromDate.set(Calendar.AM_PM, Calendar.AM);
                } else  {
                    fromDate.set(Calendar.AM_PM, Calendar.PM);
                }
            }

            event.setFromDate(fromDate.getTime());

            Calendar toDate = Calendar.getInstance();
            if(request.getParameter(PARAM_TO_EVENT_DATE) != null && !request.getParameter(PARAM_TO_EVENT_DATE).equals(""))  {
                toDate.setTime(ServletUtil.getDateFromString(request.getParameter(PARAM_TO_EVENT_DATE)));
            } else  {
                errors.add("You must select an end date for the event.");
            }

            if(request.getParameter(PARAM_TO_EVENT_HOUR) != null && !request.getParameter(PARAM_TO_EVENT_HOUR).equals(""))  {
                //sets it on a 12 hr basis
                toDate.set(Calendar.HOUR, Integer.parseInt(request.getParameter(PARAM_TO_EVENT_HOUR)));
            }

            if(request.getParameter(PARAM_TO_EVENT_MIN) != null && !request.getParameter(PARAM_TO_EVENT_MIN).equals(""))  {
                toDate.set(Calendar.MINUTE, Integer.parseInt(request.getParameter(PARAM_TO_EVENT_MIN)));
            }

            if(request.getParameter(PARAM_TO_EVENT_AM_PM) != null && !request.getParameter(PARAM_TO_EVENT_AM_PM).equals(""))  {
                if(request.getParameter(PARAM_TO_EVENT_AM_PM).equalsIgnoreCase("AM"))  {
                    toDate.set(Calendar.AM_PM, Calendar.AM);
                } else  {
                    toDate.set(Calendar.AM_PM, Calendar.PM);
                }
            }

            event.setToDate(toDate.getTime());

            if(event.getFromDate() != null && event.getToDate() != null && event.getFromDate().after(event.getToDate()))  {
                errors.add("The start date must fall before the end date.");
            }

            event.setDescription(request.getParameter(PARAM_EVENT_DESCRIPTION));
            if(request.getParameter(PARAM_EVENT_DESCRIPTION) == null ||
                    request.getParameter(PARAM_EVENT_DESCRIPTION).equals(""))  {
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

            event.setTitle(request.getParameter(PARAM_EVENT_TITLE));
            if(request.getParameter(PARAM_EVENT_TITLE) == null || request.getParameter(PARAM_EVENT_TITLE).equals("") ||
                    request.getParameter(PARAM_EVENT_TITLE).length() > 200)  {
                errors.add("You must enter a title for the event.");
            }

        }
        //otherwise it is just a generic item

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

            session.getTransaction().commit();
            forwardToEditor(request, response, null, "The transaction was saved successfully", inventory,
                    magicCard, event, yugiohCard, warning, magicSets, request.getParameter(PARAM_TYPE));
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
            forwardToEditor(request, response, errors, "", inventory, magicCard, event, yugiohCard, "", magicSets,
                    request.getParameter(PARAM_TYPE));
        }

        session.close();
    }

    private static void forwardToEditor(HttpServletRequest request, HttpServletResponse response,ArrayList<String> errors,
                                        String info, Inventory inventory, MagicCard magicCard,
                                        Event event, YugiohCard yugiohCard, String warning, ResultSet magicSets,
                                        String type) throws IOException,
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
        request.getRequestDispatcher("/admin/InventoryEditor.jsp").forward(request, response);
    }

    public static String getEditUrl(int inventoryUid)  {
        return SRV_MAP + "?" + PARAM_INVENTORY_UID + "=" + inventoryUid;
    }
}
