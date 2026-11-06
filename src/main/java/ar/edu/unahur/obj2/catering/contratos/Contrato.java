package ar.edu.unahur.obj2.catering.contratos;

import ar.edu.unahur.obj2.catering.propuestas.Propuesta;

public class Contrato {
    private final String cliente;
    private final String getDescripcion;
    private final Double importe;

    public Contrato(String cliente, Propuesta propuesta) {
        this.cliente = cliente;
        this.getDescripcion = propuesta.getDescripcion();
        this.importe = propuesta.getPrecio();
    }

    public String getCliente() {
        return cliente;
    }

    public String getDescripcion() {
        return getDescripcion;
    }

    public Double getImporte() {
        return importe;
    }
}
