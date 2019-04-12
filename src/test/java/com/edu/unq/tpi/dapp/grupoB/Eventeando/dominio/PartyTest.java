package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PartyTest {

    private String organizador;
    private LocalDateTime fechaLimiteDeInvitacion;

    @Before
    public void setUp() {
        organizador = "Pepito";
        fechaLimiteDeInvitacion = LocalDateTime.now();
    }

    @Test
    public void testSePuedeCrearUnaFiestaEstaTieneOrganizadorUnaListaDeInvitadosYUnaFechaLimiteDeAceptacionDeLaInvitacion() {
        HashMap<String, String> invitados = new HashMap<>();
        invitados.put("email@gmail.com", "Juan");
        Fiesta fiestaDePepito = new Fiesta(organizador, invitados, fechaLimiteDeInvitacion);

        assertEquals(organizador, fiestaDePepito.organizador());
        assertFalse(fiestaDePepito.invitados().isEmpty());
        assertEquals(fechaLimiteDeInvitacion, fiestaDePepito.fechaLimiteDeInvitacion());
    }

    @Test
    public void testNoSePuedeCrearUnaFiestaSinInvitados() {
        try {
            new Fiesta(organizador, new HashMap<>(), fechaLimiteDeInvitacion);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), Fiesta.ERROR_NO_SE_PUEDE_CREAR_SIN_INVITADOS);
        }

    }

}
