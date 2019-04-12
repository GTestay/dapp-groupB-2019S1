package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Fiesta {

    public static final String ERROR_NO_SE_PUEDE_CREAR_SIN_INVITADOS = "ERROR_NO_SE_PUEDE_CREAR_SIN_INVITADOS";
    private String organizador;
    private HashMap<String, String> invitados;
    private LocalDateTime fechaLimiteDeInvitacion;

    public Fiesta(String pepito, HashMap<String, String> invitados, LocalDateTime fechaLimiteDeInvitacion) {
        this.fechaLimiteDeInvitacion = fechaLimiteDeInvitacion;
        if (invitados.isEmpty()) {
            throw new RuntimeException(ERROR_NO_SE_PUEDE_CREAR_SIN_INVITADOS);
        }
        organizador = pepito;
        this.invitados = invitados;
    }


    public String organizador() {
        return organizador;
    }

    public HashMap<String, String> invitados() {
        return invitados;
    }

    public LocalDateTime fechaLimiteDeInvitacion() {
        return fechaLimiteDeInvitacion;
    }
}
