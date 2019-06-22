package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Invitation;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.EventeandoNotFound;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.InvitationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvitationService {

    private UserService userService;
    private InvitationDao invitationDao;
    private MailSenderService mailSenderService;

    @Autowired
    public InvitationService(UserService userService, InvitationDao invitationDao, MailSenderService mailSenderService) {
        this.userService = userService;
        this.invitationDao = invitationDao;
        this.mailSenderService = mailSenderService;
    }

    public List<Invitation> userInvitations(Long userId) {
        return findUser(userId)
                .invitations().stream()
                .filter(invitation -> !invitation.isConfirmed())
                .collect(Collectors.toList());
    }

    private User findUser(Long userId) {
        return userService.searchUser(userId);
    }

    public void sendInvitations(Event event) {
        List<Invitation> invitations = Invitation.createListOfInvitationsWith(event);
        invitationDao.saveAll(invitations);
        mailSenderService.sendInvitationsEmails(invitations);
    }

    public void confirmInvitation(Long invitationId) {
        Invitation invitation = findInvitation(invitationId);
        invitation.confirm(LocalDateTime.now());
        invitationDao.save(invitation);
    }

    private Invitation findInvitation(Long invitationId) {
        return this.invitationDao.findById(invitationId)
                .orElseThrow(() -> new EventeandoNotFound("The invitation don't exist!"));
    }
}
