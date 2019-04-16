package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.DoubleStream;

public class Party {

    public static final String ERROR_CAN_NOT_BE_CREATED_WITHOUT_GUESTS = "error, can not be created without guests";
    private static final String ERROR_CAN_NOT_ADD_SUPPLIES_WHOSE_PRICE_IS_NEGATIVE = "error, can't not add a supplies whose price is negative";
    public static final String ERROR_THE_USER_WAS_NOT_INVITED = "error, the user was not invited";
    private String organizer;
    private HashMap<String, String> guests;
    private LocalDateTime invitationLimitDate;
    private Double pricePerAssistant;
    private HashMap<String, Double> supplies;
    private List<String> guestConfirmations;

    public Party(String organizer, HashMap<String, String> guests, LocalDateTime invitationLimitDate, Double pricePerAssistant) {
        this.invitationLimitDate = invitationLimitDate;
        this.pricePerAssistant = pricePerAssistant;
        if (guests.isEmpty()) {
            throw new RuntimeException(ERROR_CAN_NOT_BE_CREATED_WITHOUT_GUESTS);
        }
        this.organizer = organizer;
        this.guests = guests;
        supplies = new HashMap<>();
        guestConfirmations = new ArrayList<>();
    }


    public String organizer() {
        return organizer;
    }

    public HashMap<String, String> guests() {
        return guests;
    }

    public LocalDateTime invitationLimitDate() {
        return invitationLimitDate;
    }

    public void addSupply(String supplyName, Double supplyPrice) {
        if (supplyPrice < 0) {
            throw new RuntimeException(ERROR_CAN_NOT_ADD_SUPPLIES_WHOSE_PRICE_IS_NEGATIVE);
        }
        supplies.put(supplyName, supplyPrice);
    }

    public Double totalPriceOfSupplies() {
        return allSuppliesPrices().sum();
    }

    private DoubleStream allSuppliesPrices() {
        return supplies.values().stream().mapToDouble(price -> price);
    }

    public void confirmAssistance(String anEmail) {
        validateThatTheUserWasInvited(anEmail);
        guestConfirmations.add(anEmail);
    }

    private void validateThatTheUserWasInvited(String anEmail) {
        if (!isInvited(anEmail)) {
            throw new RuntimeException(ERROR_THE_USER_WAS_NOT_INVITED);
        }
    }

    private boolean isInvited(String anEmail) {
        return this.guests.containsKey(anEmail);
    }

    public Double totalCost() {
        return suppliesCostPerAssistant() + costPerConfirmedAssistance();
    }

    private double suppliesCostPerAssistant() {
        return sizeOfConfirmations() * totalPriceOfSupplies();
    }

    private Double costPerConfirmedAssistance() {
        return pricePerAssistant * this.sizeOfConfirmations();
    }

    private Integer sizeOfConfirmations() {
        return guestConfirmations.size();
    }
}
