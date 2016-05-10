package com.projectb.starter.init;

import com.projectb.entities.*;
import com.projectb.repositories.*;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Initializer {

    private static final Logger logger = LogManager.getLogger(Initializer.class.getSimpleName());

    // Categories constants
    private static final String CATEGORY_NAME_TECHINCAL_QUESTIONS = "Technische vragen";
    private static final String CATEGORY_COLOR_TECHNICAL_QUESTIONS = "CC6031";
    private static final String CATEGORY_ICON_TECHINCAL_QUESTIONS = "technical_questions";

    private static final String CATEGORY_NAME_SOCIAL_ACTIVITIES = "Sociale activiteiten";
    private static final String CATEGORY_COLOR_HOUSEWORK_SOCIAL_ACTIVITES = "997D71";
    private static final String CATEGORY_ICON_SOCIAL_ACTIVITIES = "social_activities";

    private static final String CATEGORY_NAME_HOUSEKEEPING = "Huis en tuin";
    private static final String CATEGORY_COLOR_HOUSEKEEPING = "FF5956";
    private static final String CATEGORY_ICON_HOUSEKEEPING = "housekeeping";

    private static final String CATEGORY_NAME_TRANSPORTATION = "Vervoer";
    private static final String CATEGORY_COLOR_TRANSPORTATION = "96FF99";
    private static final String CATEGORY_ICON_TRANSPORTATION = "transportation";

    private static final String CATEGORY_NAME_REPAIRING_AND_REPLACING = "Reparaties en vervangen";
    private static final String CATEGORY_COLOR_REPAIRING_AND_REPLACING = "61CC31";
    private static final String CATEGORY_ICON_REPAIRING_AND_REPLACING = "repairing_and_replacing";

    private static final String CATEGORY_NAME_EVENTS = "Evenementen";
    private static final String CATEGORY_COLOR_EVENTS = "56BAFF";
    private static final String CATEGORY_ICON_EVENTS = "evenementen";

    //Request constants
    private String COMPUTER_HELP_TITLE = "Helping with some computer problems";
    private String GROCERY_TITLE = "Doing help with storing grocery's";
    private String FIX_LAMP_TITLE = "Helping to fix my lamp";
    private String FIX_LAMP_DESC = "Two light bulbs are broken. The problem is that I am in a wheelchair and cannot change them by myself...";
    private String GROCERY_DESC = "I just want someone to help when I want to store my grocery's delivered by AH. Maybe a cup of coffee afterwards?";
    private String COMPUTER_HELP_DESC = "I have some problems with viruses and malware.. need assistance to install some anti-virus/malware tools.";
    private Double COMPUTER_HELP_LAT = 51.7889006;
    private Double COMPUTER_HELP_LONG = 5.2848906;
    private Double GROCERY_LAT = 52.6889006;
    private Double GROCERY_LONG = 5.1848906;
    private Double FIX_LAMP_LAT = 51.5889006;
    private Double FIX_LAMP_LONG = 5.3448906;

    //Offer constants
    private String DRINKING_CHAT_TITLE = "Chat with some coffee";
    private String HELPING_WITH_GARDEN_TITLE = "Helping in your garden";
    private String DRINKING_CHAT_DESC = "Anybody who likes a simple chat with some fresh coffee :)";
    private String HELPING_WITH_GARDEN_DESC = "Anybody who needs help in their garden? I could help if you like!";

    private Double DRINKING_CHAT_LAT = 50.6889006;
    private Double DRINKING_CHAT_LONG = 5.0848906;
    private Double HELPING_WITH_GARDEN_LAT = 51.6999006;
    private Double HELPING_WITH_GARDEN_LONG = 5.2868906;

    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private OfferRepo offerRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ConversationRepo conversationRepo;

    @Autowired
    private MessageRepo messageRepo;

    private Category categorytechnicalQuestions;
    private Category categorySocialActivities;

    private Account accountOne;
    private Account accountTwo;

    private Request requestComputer;
    private Offer offerChatAndDrink;

    // Call to create dummy data
    @PostConstruct
    public void init() {
        //Creating categories
        initCategories();

        //Creating users
        initUsers();

        //Creating requests
        initRequests();

        //Creating offers
        initOffers();

        //Creating conversations
        initConversations();
    }

    private void initUsers() {
        accountOne = new Account();

        accountOne.setUsername("kotterdijk91");
        accountOne.setPassword("password");
        accountOne.setName("Karel Otterdijk");
        accountOne.setSummary("Hallo, ik ben Karel Otterdijk.");
        accountOne.setEnabled(true);
        accountOne.getRoles().add(roleRepo.findByName("ROLE_USER"));

        accountOne.getCategories().add(categorytechnicalQuestions);
        accountOne.getCategories().add(categorySocialActivities);

        accountOne.getRequests().add(requestComputer);
        accountOne.getOffers().add(offerChatAndDrink);

        userRepo.save(accountOne);

        accountTwo = new Account();

        accountTwo.setUsername("annaliebherr");
        accountTwo.setPassword("password");
        accountTwo.setName("Anna Liebherr");
        accountTwo.setSummary("Hallo, ik ben Karel Otterdijk.");
        accountTwo.setEnabled(true);
        accountTwo.getRoles().add(roleRepo.findByName("ROLE_USER"));

        userRepo.save(accountTwo);
    }

    private void initCategories() {
        categorytechnicalQuestions = new Category();
        categorytechnicalQuestions.setName(CATEGORY_NAME_TECHINCAL_QUESTIONS);
        categorytechnicalQuestions.setColorHex(CATEGORY_COLOR_TECHNICAL_QUESTIONS);
        categorytechnicalQuestions.setIconKey(CATEGORY_ICON_TECHINCAL_QUESTIONS);
        categoryRepo.save(categorytechnicalQuestions);

        categorySocialActivities = new Category();
        categorySocialActivities.setName(CATEGORY_NAME_SOCIAL_ACTIVITIES);
        categorySocialActivities.setColorHex(CATEGORY_COLOR_HOUSEWORK_SOCIAL_ACTIVITES);
        categorySocialActivities.setIconKey(CATEGORY_ICON_SOCIAL_ACTIVITIES);
        categoryRepo.save(categorySocialActivities);

        Category categoryHousekeeping = new Category();
        categoryHousekeeping.setName(CATEGORY_NAME_HOUSEKEEPING);
        categoryHousekeeping.setColorHex(CATEGORY_COLOR_HOUSEKEEPING);
        categoryHousekeeping.setIconKey(CATEGORY_ICON_HOUSEKEEPING);
        categoryRepo.save(categoryHousekeeping);

        Category categoryTransportation = new Category();
        categoryTransportation.setName(CATEGORY_NAME_TRANSPORTATION);
        categoryTransportation.setColorHex(CATEGORY_COLOR_TRANSPORTATION);
        categoryTransportation.setIconKey(CATEGORY_ICON_TRANSPORTATION);
        categoryRepo.save(categoryTransportation);

        Category categoryRepairingAndReplacing = new Category();
        categoryRepairingAndReplacing.setName(CATEGORY_NAME_REPAIRING_AND_REPLACING);
        categoryRepairingAndReplacing.setColorHex(CATEGORY_COLOR_REPAIRING_AND_REPLACING);
        categoryRepairingAndReplacing.setIconKey(CATEGORY_ICON_REPAIRING_AND_REPLACING);
        categoryRepo.save(categoryRepairingAndReplacing);

        Category categoryEvents = new Category();
        categoryEvents.setName(CATEGORY_NAME_EVENTS);
        categoryEvents.setColorHex(CATEGORY_COLOR_EVENTS);
        categoryEvents.setIconKey(CATEGORY_ICON_EVENTS);
        categoryRepo.save(categoryEvents);
    }

    private void initRequests() {
        GeometryFactory geometryFactory = new GeometryFactory();

        requestComputer = new Request();
        requestComputer.setCreator(accountOne);
        requestComputer.setTitle(COMPUTER_HELP_TITLE);
        requestComputer.setCategory(categorytechnicalQuestions);
        requestComputer.setDescription(COMPUTER_HELP_DESC);
        requestComputer.setLatitude(COMPUTER_HELP_LAT);
        requestComputer.setLongitude(COMPUTER_HELP_LONG);

        requestRepo.save(requestComputer);

        Request requestGrocery = new Request();
        requestGrocery.setTitle(GROCERY_TITLE);
        requestGrocery.setCategory(categorySocialActivities);
        requestGrocery.setDescription(GROCERY_DESC);
        requestGrocery.setLatitude(GROCERY_LAT);
        requestGrocery.setLongitude(GROCERY_LONG);

        requestRepo.save(requestGrocery);

        Request requestLamp = new Request();
        requestLamp.setTitle(FIX_LAMP_TITLE);
        requestLamp.setCategory(categorySocialActivities);
        requestLamp.setDescription(FIX_LAMP_DESC);
        requestLamp.setLongitude(FIX_LAMP_LONG);
        requestLamp.setLatitude(FIX_LAMP_LAT);

        requestRepo.save(requestLamp);
    }

    private void initOffers() {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(52.3702157, 4.895167899999933));

        offerChatAndDrink = new Offer();
        offerChatAndDrink.setCreator(accountOne);
        offerChatAndDrink.setCategory(categorySocialActivities);
        offerChatAndDrink.setTitle(DRINKING_CHAT_TITLE);
        offerChatAndDrink.setDescription(DRINKING_CHAT_DESC);
        offerChatAndDrink.setLatitude(DRINKING_CHAT_LAT);
        offerChatAndDrink.setLongitude(DRINKING_CHAT_LONG);

        offerRepo.save(offerChatAndDrink);

        Offer offerHelpGarden = new Offer();
        offerHelpGarden.setTitle(HELPING_WITH_GARDEN_TITLE);
        offerHelpGarden.setCategory(categorySocialActivities);
        offerHelpGarden.setDescription(HELPING_WITH_GARDEN_DESC);
        offerHelpGarden.setLatitude(HELPING_WITH_GARDEN_LAT);
        offerHelpGarden.setLongitude(HELPING_WITH_GARDEN_LONG);

        offerRepo.save(offerHelpGarden);
    }

    private void initConversations() {
        Conversation conversation = new Conversation();
        conversation.setStarter(accountOne);
        conversation.setListener(accountTwo);
        conversationRepo.save(conversation);

        Message message1 = new Message(accountOne, "Hallo!");
        conversation.addMessage(message1);
        messageRepo.save(message1);

        Message message2 = new Message(accountTwo, "Hej!");
        conversation.addMessage(message2);
        messageRepo.save(message2);

        Message message3 = new Message(accountOne, "Hoe gaat het?");
        conversation.addMessage(message3);
        messageRepo.save(message3);

        Message message4 = new Message(accountTwo, "Goed hoor! Met jou?");
        conversation.addMessage(message4);
        messageRepo.save(message4);
    }
}
