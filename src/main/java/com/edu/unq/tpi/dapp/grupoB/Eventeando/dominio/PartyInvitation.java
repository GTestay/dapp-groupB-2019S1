package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

public class PartyInvitation {
    private final Party party;
    private final User user;
    private boolean confirmation;

    public PartyInvitation(Party anEvent, User invitedUser) {
        this.party = anEvent;
        this.user = invitedUser;
        invitedUser.receiveEventInvitation(this);
        confirmation = false;
    }

    public boolean isConfirmed() {
        return confirmation;
    }

    public void confirm() {
        party.confirmAssistance(this.user.email());
        confirmation = true;
    }
}
