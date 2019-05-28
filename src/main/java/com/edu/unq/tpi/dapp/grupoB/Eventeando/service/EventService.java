package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
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
        List<User> guests = event.guests();

        sendEmails(guests);
    }

    private void sendEmails(List<User> guests) {
        guests.forEach(guest -> sendEmail(guest));
    }

    private void sendEmail(User guest) {
        try {
            service.sendEmail(guest.email(), "Venite a mi evento!", "Nueva Invitacion");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
