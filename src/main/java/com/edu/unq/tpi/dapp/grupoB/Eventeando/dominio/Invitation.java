package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Invitation {
    private final Event party;
    private final User user;

    public Invitation(Event anEvent, User invitedUser) {
        this.party = anEvent;
        this.user = invitedUser;
        invitedUser.receiveEventInvitation(this);
    }

    public static List<Invitation> createListOfInvitationsWith(Event event) {
        return event.guests().stream()
                .map(user -> new Invitation(event, user))
                .collect(Collectors.toList());
    }

    public boolean isConfirmed() {
        return party.guestHasConfirmed(user);
    }

    public void confirm(LocalDateTime confirmationDate) {
        party.confirmAssistance(this.user.email(), confirmationDate);
    }
}
