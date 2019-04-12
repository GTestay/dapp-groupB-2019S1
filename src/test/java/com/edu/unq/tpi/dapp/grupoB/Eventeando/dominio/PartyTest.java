package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PartyTest {

    @Test
    public void testSePuedeCrearUnaFiestaEstaTieneOrganizadorYUnaListaDeInvitados() {
        String pepito = "Pepito";
        HashMap<String, String> invitados = new HashMap<>();
        invitados.put("email@gmail.com", "Juan");

        Fiesta fiestaDePepito = new Fiesta(pepito, invitados);

        assertEquals(pepito, fiestaDePepito.organizador());
        assertFalse(fiestaDePepito.invitados().isEmpty());
    }


}
