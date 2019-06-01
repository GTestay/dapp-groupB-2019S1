package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Invitation {
    private final Event event;
    private final User guest;

    public Invitation(Event anEvent, User guest) {
        this.event = anEvent;
        this.guest = guest;
        guest.receiveEventInvitation(this);
    }

    public static List<Invitation> createListOfInvitationsWith(Event event) {
        return event.guests().stream()
                .map(user -> new Invitation(event, user))
                .collect(Collectors.toList());
    }

    public boolean isConfirmed() {
        return event.guestHasConfirmed(guest);
    }

    public void confirm(LocalDateTime confirmationDate) {
        event.confirmAssistance(this.guest.email(), confirmationDate);
    }

    public String guestEmail() {
        return guest.email();
    }

    public String organizerFullName() {
        return event.organizer().fullName();
    }

    public String eventDescription() {
        return event.description();
    }

    public String guestFullName() {
        return guest.fullName();
    }
}
