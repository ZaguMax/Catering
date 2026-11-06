package ar.edu.unahur.obj2.catering.modificaciones;

import ar.edu.unahur.obj2.catering.propuestas.Servicio;

public class ActualizarPrecio implements Modificacion {
    private final Servicio servicio;
    private final Double nuevoPrecio;
    private Double precioAnterior;

    public ActualizarPrecio(Servicio servicio, double nuevoPrecio) {
        this.servicio = servicio;
        this.nuevoPrecio = nuevoPrecio;
    }

    @Override
    public void aplicar() {
        Double anterior = servicio.getPrecio();   
        servicio.setPrecio(nuevoPrecio);          
        precioAnterior = anterior;
    }

    @Override
    public void revertir() {
        servicio.setPrecio(precioAnterior);
    }
}