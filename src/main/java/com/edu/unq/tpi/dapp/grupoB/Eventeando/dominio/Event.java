package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.EventValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.EventValidator.ERROR_THE_USER_WAS_NOT_INVITED;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "event_type")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Party.class, name = "Party"),
        @JsonSubTypes.Type(value = BaquitaSharedExpensesEvent.class, name = "BaquitaSharedExpensesEvent"),
        @JsonSubTypes.Type(value = BaquitaCrowdFundingEvent.class, name = "BaquitaCrowdFundingEvent"),
        @JsonSubTypes.Type(value = PotluckEvent.class, name = "PotluckEvent"),
})
public abstract class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @JsonProperty
    protected Long id;

    @JsonProperty
    protected String description;

    @ManyToOne(cascade = ALL)
    @JsonProperty
    protected User organizer;

    @OneToMany(cascade = ALL)
    @JsonProperty
    protected List<Expense> expenses = new ArrayList<>();

    @OneToMany(cascade = ALL)
    @JsonProperty
    protected List<User> guests = new ArrayList<>();

    @ElementCollection
    @JsonProperty
    protected List<String> guestConfirmations = new ArrayList<>();

    protected static <EventType extends Event> EventType validateEvent(EventType newEvent, User organizer, String description, List<Expense> expenses, List<User> guests) {
        EventValidator eventValidator = new EventValidator();
        newEvent.organizer = eventValidator.validateOrganizer(organizer);
        newEvent.description = description;
        newEvent.expenses = expenses;
        newEvent.guests = eventValidator.validateGuests(guests);
        return newEvent;
    }

    public User organizer() {
        return organizer;
    }

    public String description() {
        return description;
    }

    public List<Expense> expenses() {
        return expenses;
    }

    public List<User> guests() {
        return guests;
    }

    public void confirmAssistance(String anEmail, LocalDateTime confirmationDate) {
        validateThatTheUserWasInvited(anEmail);
        guestConfirmations.add(anEmail);
    }

    public Double totalCost() {
        return expensesTotalCost();
    }

    public Double expensesTotalCost() {
        return allExpensesCost().sum();
    }

    private DoubleStream allExpensesCost() {
        return expenses().stream().mapToDouble(Expense::cost);
    }

    protected void validateThatTheUserWasInvited(String anEmail) {
        if (!isInvited(anEmail)) {
            throwEventException(ERROR_THE_USER_WAS_NOT_INVITED);
        }
    }

    protected boolean isInvited(String anEmail) {
        return guests.stream().anyMatch(user -> user.hasThisEmail(anEmail));
    }

    protected void throwEventException(String errorMessage) {
        throw new EventException(errorMessage);
    }

    protected Integer quantityOfConfirmations() {
        return guestConfirmations.size();
    }

    public boolean guestHasConfirmed(User guest) {
        return guestConfirmations.stream().anyMatch(guest::hasThisEmail);
    }

    public Long id() {
        return id;
    }

    public Boolean hasSameOrganizer(User organizer) {
        return this.organizer.hasThisEmail(organizer.email());
    }
}
