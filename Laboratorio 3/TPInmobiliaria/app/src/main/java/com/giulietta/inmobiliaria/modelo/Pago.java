package com.giulietta.inmobiliaria.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Pago implements Serializable {
    private int id;
    private Date fecha;
    private String referencia;
    private double importe;
    private int idContrato;

    public Pago(int id, Date fecha, String referencia, double importe, int idContrato) {
        this.id = id;
        this.fecha = fecha;
        this.referencia = referencia;
        this.importe = importe;
        this.idContrato = idContrato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }
}
