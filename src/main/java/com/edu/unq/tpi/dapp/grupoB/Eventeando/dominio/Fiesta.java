package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.util.HashMap;

public class Fiesta {

    private String organizador;
    private HashMap<String, String> invitados;

    public Fiesta(String pepito, HashMap<String, String> invitados) {

        organizador = pepito;
        this.invitados = invitados;
    }


    public String organizador() {
        return organizador;
    }

    public HashMap<String, String> invitados() {
        return invitados;
    }
}
