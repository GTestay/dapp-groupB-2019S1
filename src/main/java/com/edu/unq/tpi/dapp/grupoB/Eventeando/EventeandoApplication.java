package com.edu.unq.tpi.dapp.grupoB.Eventeando;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventeandoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventeandoApplication.class, args);
	}

}
