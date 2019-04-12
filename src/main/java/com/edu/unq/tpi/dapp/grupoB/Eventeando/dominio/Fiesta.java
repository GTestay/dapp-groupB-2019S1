package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.stream.DoubleStream;

public class Fiesta {

    public static final String ERROR_NO_SE_PUEDE_CREAR_SIN_INVITADOS = "ERROR_NO_SE_PUEDE_CREAR_SIN_INVITADOS";
    private static final String ERROR_NO_SE_PUEDE_CREAR_AGREGAR_UN_INSUMO_CUYO_PRECIO_ES_NEGATIVO = "ERROR_NO_SE_PUEDE_CREAR_AGREGAR_UN_INSUMO_CUYO_PRECIO_ES_NEGATIVO";
    private String organizador;
    private HashMap<String, String> invitados;
    private LocalDateTime fechaLimiteDeInvitacion;
    private HashMap<String, Double> insumos;

    public Fiesta(String pepito, HashMap<String, String> invitados, LocalDateTime fechaLimiteDeInvitacion) {
        this.fechaLimiteDeInvitacion = fechaLimiteDeInvitacion;
        if (invitados.isEmpty()) {
            throw new RuntimeException(ERROR_NO_SE_PUEDE_CREAR_SIN_INVITADOS);
        }
        organizador = pepito;
        this.invitados = invitados;
        insumos = new HashMap<>();
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

    public void agregarInsumo(String nombreDelInsumo, Double precioDelInsumo) {
        if (precioDelInsumo < 0) {
            throw new RuntimeException(ERROR_NO_SE_PUEDE_CREAR_AGREGAR_UN_INSUMO_CUYO_PRECIO_ES_NEGATIVO);
        }
        insumos.put(nombreDelInsumo, precioDelInsumo);
    }

    public Double precioTotalDeLosInsumos() {
        return preciosDeLosInsumos().sum();
    }

    private DoubleStream preciosDeLosInsumos() {
        return insumos.values().stream().mapToDouble(precio -> precio);
    }
}
