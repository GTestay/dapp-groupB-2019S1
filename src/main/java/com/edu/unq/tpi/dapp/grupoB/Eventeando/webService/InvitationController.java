package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Invitation;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InvitationController {

    private InvitationService invitationService;

    @Autowired
    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}/invitations")
    public List<Invitation> userInvitations(@PathVariable("id") Long userId) {
        return invitationService.userInvitations(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/invitations/{invitation_id}/confirm")
    public void confirmInvitation(@PathVariable("invitation_id") Long invitationId) {
        invitationService.confirmInvitation(invitationId);
    }


}
