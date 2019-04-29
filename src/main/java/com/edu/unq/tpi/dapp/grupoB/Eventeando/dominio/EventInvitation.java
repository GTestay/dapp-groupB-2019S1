package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDateTime;

public class EventInvitation {
    private final Event party;
    private final User user;

    public EventInvitation(Event anEvent, User invitedUser) {
        this.party = anEvent;
        this.user = invitedUser;
        invitedUser.receiveEventInvitation(this);
    }

    public boolean isConfirmed() {
        return party.guestHasConfirmed(user);
    }

    public void confirm(LocalDateTime confirmationDate) {
        party.confirmAssistance(this.user.email(), confirmationDate);
    }
}
