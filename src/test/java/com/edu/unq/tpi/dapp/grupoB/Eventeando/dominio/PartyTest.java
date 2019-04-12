package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PartyTest {

    private String organizador;

    @Before
    public void setUp() throws Exception {
        organizador = "Pepito";
    }

    @Test
    public void testSePuedeCrearUnaFiestaEstaTieneOrganizadorYUnaListaDeInvitados() {
        HashMap<String, String> invitados = new HashMap<>();
        invitados.put("email@gmail.com", "Juan");

        Fiesta fiestaDePepito = new Fiesta(organizador, invitados);

        assertEquals(organizador, fiestaDePepito.organizador());
        assertFalse(fiestaDePepito.invitados().isEmpty());
    }

    @Test
    public void testNoSePuedeCrearUnaFiestaSinInvitados() {
        try {
            new Fiesta(organizador, new HashMap<>());
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), Fiesta.ERROR_NO_SE_PUEDE_CREAR_SIN_INVITADOS);
        }

    }


}
