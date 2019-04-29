package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class EventInvitation {
    private final Event party;
    private final User user;

    public EventInvitation(Event anEvent, User invitedUser) {
        this.party = anEvent;
        this.user = invitedUser;
        invitedUser.receiveEventInvitation(this);
    }

    public static List<EventInvitation> createListOfInvitationsWith(Event event) {
        return event.guests().stream()
                .map(user -> new EventInvitation(event, user))
                .collect(Collectors.toList());
    }

    public boolean isConfirmed() {
        return party.guestHasConfirmed(user);
    }

    public void confirm(LocalDateTime confirmationDate) {
        party.confirmAssistance(this.user.email(), confirmationDate);
    }
}
