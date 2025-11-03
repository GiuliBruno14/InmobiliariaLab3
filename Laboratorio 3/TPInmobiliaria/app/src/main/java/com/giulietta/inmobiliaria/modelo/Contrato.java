package com.giulietta.inmobiliaria.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Contrato implements Serializable {
    private int id;
    private Date fechaInicio;
    private Date fechaTerm;
    private double montoMensual;
    private int idInquilino;
    private int idInmueble;

    private Inquilino arrendatario; // Datos del inquilino
    private Inmueble datos;         // Datos del inmueble

    public Contrato() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTerm() {
        return fechaTerm;
    }

    public void setFechaTerm(Date fechaTerm) {
        this.fechaTerm = fechaTerm;
    }

    public double getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(double montoMensual) {
        this.montoMensual = montoMensual;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public Inquilino getArrendatario() {
        return arrendatario;
    }

    public void setArrendatario(Inquilino arrendatario) {
        this.arrendatario = arrendatario;
    }

    public Inmueble getDatos() {
        return datos;
    }

    public void setDatos(Inmueble datos) {
        this.datos = datos;
    }
}
