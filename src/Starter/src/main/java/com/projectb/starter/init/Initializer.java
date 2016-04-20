package com.projectb.starter.init;

import com.projectb.entities.Offer;
import com.projectb.entities.Request;
import com.projectb.repositories.OfferRepo;
import com.projectb.repositories.RequestRepo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class Initializer {

    private static final Logger logger = LogManager.getLogger(Initializer.class.getSimpleName());

    //Request constants
    private String COMPUTER_HELP_TITLE = "Helping with some computer problems";
    private String GROCERY_TITLE = "Doing help with storing grocery's";
    private String FIX_LAMP_TITLE = "Helping to fix my lamp";
    private String FIX_LAMP_DESC = "Two light bulbs are broken. The problem is that I am in a wheelchair and cannot change them by myself...";
    private String GROCERY_DESC = "I just want someone to help when I want to store my grocery's delivered by AH. Maybe a cup of coffee afterwards?";
    private String COMPUTER_HELP_DESC = "I have some problems with viruses and malware.. need assistance to install some anti-virus/malware tools.";
    private Double COMPUTER_HELP_LAT = 51.6889006;
    private Double COMPUTER_HELP_LONG = 5.2848906;
    private Double GROCERY_LAT = 51.6889006;
    private Double GROCERY_LONG = 5.2848906;
    private Double FIX_LAMP_LAT = 51.6889006;
    private Double FIX_LAMP_LONG = 5.2848906;

    //Offer constants
    private String DRINKING_CHAT_TITLE = "Chat with some coffee";
    private String HELPING_WITH_GARDEN_TITLE = "Helping in your garden";
    private String DRINKING_CHAT_DESC = "Anybody who likes a simple chat with some fresh coffee :)";
    private String HELPING_WITH_GARDEN_DESC = "Anybody who needs help in their garden? I could help if you like!";

    private Double DRINKING_CHAT_LAT = 51.6889006;
    private Double DRINKING_CHAT_LONG = 5.2848906;
    private Double HELPING_WITH_GARDEN_LAT = 51.6889006;
    private Double HELPING_WITH_GARDEN_LONG = 5.2848906;

    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private OfferRepo offerRepo;

    // Call to create dummy data
    @PostConstruct
    public void init() {
        //Creating requests
        initRequests();

        //Creating offers
        initOffers();
    }

    private void initRequests() {
        Request requestComputer = new Request();
        requestComputer.setTitle(COMPUTER_HELP_TITLE);
        requestComputer.setDescription(COMPUTER_HELP_DESC);
        requestComputer.setLatitude(COMPUTER_HELP_LAT);
        requestComputer.setLongitude(COMPUTER_HELP_LONG);

        requestRepo.save(requestComputer);

        Request requestGrocery = new Request();
        requestGrocery.setTitle(GROCERY_TITLE);
        requestGrocery.setDescription(GROCERY_DESC);
        requestGrocery.setLatitude(GROCERY_LAT);
        requestGrocery.setLongitude(GROCERY_LONG);

        requestRepo.save(requestGrocery);

        Request requestLamp = new Request();
        requestLamp.setTitle(FIX_LAMP_TITLE);
        requestLamp.setDescription(FIX_LAMP_DESC);
        requestLamp.setLongitude(FIX_LAMP_LONG);
        requestLamp.setLatitude(FIX_LAMP_LAT);

        requestRepo.save(requestLamp);
    }

    private void initOffers() {
        Offer offerChatAndDrink = new Offer();
        offerChatAndDrink.setTitle(DRINKING_CHAT_TITLE);
        offerChatAndDrink.setDescription(DRINKING_CHAT_DESC);
        offerChatAndDrink.setLatitude(DRINKING_CHAT_LAT);
        offerChatAndDrink.setLongitude(DRINKING_CHAT_LONG);

        offerRepo.save(offerChatAndDrink);

        Offer offerHelpGarden = new Offer();
        offerHelpGarden.setTitle(HELPING_WITH_GARDEN_TITLE);
        offerHelpGarden.setDescription(HELPING_WITH_GARDEN_DESC);
        offerHelpGarden.setLatitude(HELPING_WITH_GARDEN_LAT);
        offerHelpGarden.setLongitude(HELPING_WITH_GARDEN_LONG);

        offerRepo.save(offerHelpGarden);
    }

}
