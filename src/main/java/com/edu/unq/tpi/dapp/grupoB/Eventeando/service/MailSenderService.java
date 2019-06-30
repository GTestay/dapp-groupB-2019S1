package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Invitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.List;

@Component
public class MailSenderService {

    private final JavaMailSender sender;

    @Autowired
    public MailSenderService(JavaMailSender mailSender) {
        this.sender = mailSender;
    }

    public void sendEmail(String receiver, String body, String subject) throws Exception {
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(receiver);
        helper.setText(body);
        helper.setSubject(subject);

        sender.send(message);
    }

    @Async
    public void sendInvitationsEmails(List<Invitation> invitations) {
        invitations.forEach(invitation -> sendEmail(invitation));
    }

    private void sendEmail(Invitation invitation) {
        try {
            this.sendEmail(invitation.guestEmail(), bodyDelMail(invitation), subjectDelMail(invitation));
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
