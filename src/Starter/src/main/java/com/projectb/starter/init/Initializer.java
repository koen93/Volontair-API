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

    //Offer constants
    private String DRINKING_CHAT_TITLE = "Chat with some coffee";
    private String HELPING_WITH_GARDEN_TITLE = "Helping in your garden";
    private String DRINKING_CHAT_DESC = "Anybody who likes a simple chat with some fresh coffee :)";
    private String HELPING_WITH_GARDEN_DESC = "Anybody who needs help in their garden? I could help if you like!";

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
    private Category categoryHousekeeping;
    private Category categoryTransportation;
    private Category categoryRepairingAndReplacing;
    private Category categoryEvents;

    private User userOne;
    private User userTwo;
    private User userThree;
    private User userFour;

    private Request requestComputer;
    private Request requestGrocery;
    private Request requestLamp;

    private Offer offerChatAndDrink;
    private Offer offerHelpGarden;

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
        userOne = new User();

        userOne.setUsername("kotterdijk91@example.com");
        userOne.setPassword("password");
        userOne.setName("Mark Suikerberg");
        userOne.setSummary("Hallo, ik ben Mark Suikerberg.");
        userOne.setEnabled(true);
        userOne.getRoles().add(roleRepo.findByName("ROLE_USER"));
        userOne.getCategories().add(categorytechnicalQuestions);
        userOne.getCategories().add(categorySocialActivities);
        userOne.getRequests().add(requestComputer);
        userOne.getOffers().add(offerChatAndDrink);
        userOne.setLatitude(51.441642);
        userOne.setLongitude(5.469722);
        userOne.setImageUrl("https://graph.facebook.com/4/picture");
        userOne.setGoal(Goal.GIVE_AND_GET_HELP);

        userRepo.save(userOne);

        userTwo = new User();

        userTwo.setUsername("annaliebherr@example.com");
        userTwo.setPassword("password");
        userTwo.setName("Anna Liebherr");
        userTwo.setSummary("Hallo, ik ben Anna Liebherr.");
        userTwo.setEnabled(true);
        userTwo.getRoles().add(roleRepo.findByName("ROLE_USER"));
        userTwo.getCategories().add(categorytechnicalQuestions);
        userTwo.getCategories().add(categorySocialActivities);
        userTwo.getRequests().add(requestGrocery);
        userTwo.setLatitude(51.615789);
        userTwo.setLongitude(5.539240);
        userTwo.setGoal(Goal.GET_HELP);
        userTwo.setImageUrl("https://graph.facebook.com/100002153883826/picture");

        userRepo.save(userTwo);


        userThree = new User();

        userThree.setUsername("janjanssen@example.com");
        userThree.setPassword("password");
        userThree.setName("Jan Janssen");
        userThree.setSummary("Hallo, ik ben Jan Janssen.");
        userThree.setEnabled(true);
        userThree.getRoles().add(roleRepo.findByName("ROLE_USER"));
        userThree.getCategories().add(categoryRepairingAndReplacing);
        userThree.getRequests().add(requestLamp);
        userThree.setLatitude(51.653306);
        userThree.setLongitude(5.294347);
        userThree.setGoal(Goal.GET_HELP);
        userThree.setImageUrl("https://graph.facebook.com/1435737693304473/picture");

        userRepo.save(userThree);

        userFour = new User();

        userFour.setUsername("pietpietersen@example.com");
        userFour.setPassword("password");
        userFour.setName("Piet Pietersen");
        userFour.setSummary("Hallo, ik ben Piet Pietersen.");
        userFour.setEnabled(true);
        userFour.getRoles().add(roleRepo.findByName("ROLE_USER"));
        userFour.getCategories().add(categoryRepairingAndReplacing);
        userFour.getOffers().add(offerHelpGarden);
        userFour.setLatitude(52.090737);
        userFour.setLongitude(5.121420);
        userFour.setGoal(Goal.GIVE_HELP);
        userFour.setImageUrl("https://graph.facebook.com/1264306426/picture");

        userRepo.save(userFour);
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

        categoryHousekeeping = new Category();
        categoryHousekeeping.setName(CATEGORY_NAME_HOUSEKEEPING);
        categoryHousekeeping.setColorHex(CATEGORY_COLOR_HOUSEKEEPING);
        categoryHousekeeping.setIconKey(CATEGORY_ICON_HOUSEKEEPING);
        categoryRepo.save(categoryHousekeeping);

        categoryTransportation = new Category();
        categoryTransportation.setName(CATEGORY_NAME_TRANSPORTATION);
        categoryTransportation.setColorHex(CATEGORY_COLOR_TRANSPORTATION);
        categoryTransportation.setIconKey(CATEGORY_ICON_TRANSPORTATION);
        categoryRepo.save(categoryTransportation);

        categoryRepairingAndReplacing = new Category();
        categoryRepairingAndReplacing.setName(CATEGORY_NAME_REPAIRING_AND_REPLACING);
        categoryRepairingAndReplacing.setColorHex(CATEGORY_COLOR_REPAIRING_AND_REPLACING);
        categoryRepairingAndReplacing.setIconKey(CATEGORY_ICON_REPAIRING_AND_REPLACING);
        categoryRepo.save(categoryRepairingAndReplacing);

        categoryEvents = new Category();
        categoryEvents.setName(CATEGORY_NAME_EVENTS);
        categoryEvents.setColorHex(CATEGORY_COLOR_EVENTS);
        categoryEvents.setIconKey(CATEGORY_ICON_EVENTS);
        categoryRepo.save(categoryEvents);
    }

    private void initRequests() {
        GeometryFactory geometryFactory = new GeometryFactory();

        requestComputer = new Request();
        requestComputer.setCreator(userOne);
        requestComputer.setTitle(COMPUTER_HELP_TITLE);
        requestComputer.setCategory(categorytechnicalQuestions);
        requestComputer.setDescription(COMPUTER_HELP_DESC);

        requestRepo.save(requestComputer);

        requestGrocery = new Request();
        requestGrocery.setCreator(userTwo);
        requestGrocery.setTitle(GROCERY_TITLE);
        requestGrocery.setCategory(categorySocialActivities);
        requestGrocery.setDescription(GROCERY_DESC);

        requestRepo.save(requestGrocery);

        requestLamp = new Request();
        requestLamp.setCreator(userThree);
        requestLamp.setTitle(FIX_LAMP_TITLE);
        requestLamp.setCategory(categorySocialActivities);
        requestLamp.setDescription(FIX_LAMP_DESC);

        requestRepo.save(requestLamp);
    }

    private void initOffers() {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(52.3702157, 4.895167899999933));

        offerChatAndDrink = new Offer();
        offerChatAndDrink.setCreator(userOne);
        offerChatAndDrink.setCategory(categorySocialActivities);
        offerChatAndDrink.setTitle(DRINKING_CHAT_TITLE);
        offerChatAndDrink.setDescription(DRINKING_CHAT_DESC);

        offerRepo.save(offerChatAndDrink);

        offerHelpGarden = new Offer();
        offerHelpGarden.setCreator(userFour);
        offerHelpGarden.setTitle(HELPING_WITH_GARDEN_TITLE);
        offerHelpGarden.setCategory(categorySocialActivities);
        offerHelpGarden.setDescription(HELPING_WITH_GARDEN_DESC);

        offerRepo.save(offerHelpGarden);
    }

    private void initConversations() {
        Conversation conversation = new Conversation();
        conversation.setStarter(userOne);
        conversation.setListener(userTwo);
        conversationRepo.save(conversation);

        Message message1 = new Message(userOne, "Hallo!");
        conversation.addMessage(message1);
        messageRepo.save(message1);

        Message message2 = new Message(userTwo, "Hej!");
        conversation.addMessage(message2);
        messageRepo.save(message2);

        Message message3 = new Message(userOne, "Hoe gaat het?");
        conversation.addMessage(message3);
        messageRepo.save(message3);

        Message message4 = new Message(userTwo, "Goed hoor! Met jou?");
        conversation.addMessage(message4);
        messageRepo.save(message4);

        Conversation conversation2 = new Conversation();
        conversation2.setStarter(userThree);
        conversation2.setListener(userFour);
        conversationRepo.save(conversation2);

        Message conversation2message1 = new Message(userOne, "Hallo!");
        conversation2.addMessage(conversation2message1);
        messageRepo.save(conversation2message1);

        Message conversation2message2 = new Message(userTwo, "Hej!");
        conversation2.addMessage(conversation2message2);
        messageRepo.save(conversation2message2);

        Message conversation2message3 = new Message(userOne, "Hoe gaat het?");
        conversation2.addMessage(conversation2message3);
        messageRepo.save(conversation2message3);

        Message conversation2message4 = new Message(userTwo, "Goed hoor! Met jou?");
        conversation2.addMessage(conversation2message4);
        messageRepo.save(conversation2message4);
    }
}
