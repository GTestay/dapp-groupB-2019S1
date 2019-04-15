package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.DoubleStream;

public class Fiesta {

    public static final String ERROR_NO_SE_PUEDE_CREAR_SIN_INVITADOS = "ERROR_NO_SE_PUEDE_CREAR_SIN_INVITADOS";
    private static final String ERROR_NO_SE_PUEDE_CREAR_AGREGAR_UN_INSUMO_CUYO_PRECIO_ES_NEGATIVO = "ERROR_NO_SE_PUEDE_CREAR_AGREGAR_UN_INSUMO_CUYO_PRECIO_ES_NEGATIVO";
    public static final String ERROR_EL_USUARIO_NO_FUE_INVITADO_A_LA_FIESTA = "ERROR_EL_USUARIO_NO_FUE_INVITADO_A_LA_FIESTA";
    private String organizador;
    private HashMap<String, String> invitados;
    private LocalDateTime fechaLimiteDeInvitacion;
    private Double precioPorAsistente;
    private HashMap<String, Double> insumos;
    private List<String> confirmaciones;

    public Fiesta(String organizador, HashMap<String, String> invitados, LocalDateTime fechaLimiteDeInvitacion, Double precioPorAsistente) {
        this.fechaLimiteDeInvitacion = fechaLimiteDeInvitacion;
        this.precioPorAsistente = precioPorAsistente;
        if (invitados.isEmpty()) {
            throw new RuntimeException(ERROR_NO_SE_PUEDE_CREAR_SIN_INVITADOS);
        }
        this.organizador = organizador;
        this.invitados = invitados;
        insumos = new HashMap<>();
        confirmaciones = new ArrayList<>();
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

    public void confirmarAsistenciaDe(String emailDelUsuarioQueConfirmo) {
        validarQueElUsuarioEsteInvitado(emailDelUsuarioQueConfirmo);
        confirmaciones.add(emailDelUsuarioQueConfirmo);
    }

    private void validarQueElUsuarioEsteInvitado(String emailDelUsuarioQueConfirmo) {
        if (!estaInvitado(emailDelUsuarioQueConfirmo)) {
            throw new RuntimeException(ERROR_EL_USUARIO_NO_FUE_INVITADO_A_LA_FIESTA);
        }
    }

    private boolean estaInvitado(String emailDelUsuarioQueConfirmo) {
        return this.invitados.containsKey(emailDelUsuarioQueConfirmo);
    }

    public Double costeTotal() {
        return costeDeLosInsumosPorAsistente() + costePorInvitadoConfirmado();
    }

    private double costeDeLosInsumosPorAsistente() {
        return cantidadDeConfirmaciones() * this.precioTotalDeLosInsumos();
    }

    private Double costePorInvitadoConfirmado() {
        return precioPorAsistente * this.cantidadDeConfirmaciones();
    }

    private Integer cantidadDeConfirmaciones() {
        return confirmaciones.size();
    }
}
