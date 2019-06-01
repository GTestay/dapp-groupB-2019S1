package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Invitation;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.services.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventDao eventDao;

    private final MailSenderService service;

    @Autowired
    public EventService(EventDao eventDao, MailSenderService service) {
        this.eventDao = eventDao;
        this.service = service;
    }

    public List<Event> allEvents() {
        return eventDao.findAll();
    }

    public void createEvent(Event event) {
        List<Invitation> invitations = Invitation.createListOfInvitationsWith(event);
        sendInvitationsEmails(invitations);
    }

    private void sendInvitationsEmails(List<Invitation> invitations) {
        invitations.forEach(invitation -> sendEmail(invitation));

    }

    private void sendEmail(Invitation invitation) {
        try {
            service.sendEmail(invitation.guestEmail(), bodyDelMail(invitation), subjectDelMail(invitation));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private String subjectDelMail(Invitation invitation) {
        return "Nueva Invitacion de " + invitation.organizerFullName();
    }

    private String bodyDelMail(Invitation invitation) {
        return "Hola! " + invitation.guestFullName() + ". Venite a mi evento! \n" +
                invitation.eventDescription();
    }

}
