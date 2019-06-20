package com.edu.unq.tpi.dapp.grupoB.Eventeando;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@Profile({"test", "dev"})
public class TestConfiguration {

    @Bean
    public JavaMailSender mailSender() {
        return new FakeMailSender();
    }
}