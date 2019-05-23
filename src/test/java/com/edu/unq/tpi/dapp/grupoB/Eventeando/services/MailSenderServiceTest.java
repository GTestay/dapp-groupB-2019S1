package com.edu.unq.tpi.dapp.grupoB.Eventeando.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailSenderServiceTest {

    @Autowired
    private MailSenderService mailSenderService;

    @Test
    public void testingEmailSenderService() throws Exception {
        mailSenderService.sendEmail("martinegonzalez95@gmail.com",
                                    "Probando el body de un email",
                                    "Titulo del email");
    }
}

