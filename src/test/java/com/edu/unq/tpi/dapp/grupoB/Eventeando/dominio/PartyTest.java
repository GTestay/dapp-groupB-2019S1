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
        Fiesta fiestaDePepito = fiestaConInvitados();

        assertEquals(organizador, fiestaDePepito.organizador());
        assertFalse(fiestaDePepito.invitados().isEmpty());
        assertEquals(fechaLimiteDeInvitacion, fiestaDePepito.fechaLimiteDeInvitacion());
    }

    private HashMap<String, String> invitados() {
        HashMap<String, String> invitados = new HashMap<>();
        invitados.put("email@gmail.com", "Juan");
        invitados.put("pepita@gmail.com", "Pepita");
        return invitados;
    }

    private Fiesta fiestaConInvitados() {
        return new Fiesta(organizador, invitados(), fechaLimiteDeInvitacion);
    }

    @Test
    public void testNoSePuedeCrearUnaFiestaSinInvitados() {
        try {
            new Fiesta(organizador, new HashMap<>(), fechaLimiteDeInvitacion);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), Fiesta.ERROR_NO_SE_PUEDE_CREAR_SIN_INVITADOS);
        }

    }

    @Test
    public void testUnaFiestaSinInsumosSuPrecioDeInsumosEsDe0() {
        assertEquals(0, fiestaConInvitados().precioTotalDeLosInsumos(), 0);
    }

    @Test
    public void testAUnaFiestaSeLeAgreganInsumos() {
        Fiesta fiestaDePepito = fiestaConInvitados();

        fiestaDePepito.agregarInsumo("Coca de 1 litro", 100.00);
        fiestaDePepito.agregarInsumo("Sanguches de Miga x 24", 200.00);

        assertEquals(300, fiestaDePepito.precioTotalDeLosInsumos(), 0);
    }
}
