package ar.edu.unahur.obj2.catering.propuestas;

public interface Propuesta {
    String getDescripcion();
    Double getPrecio();
    Integer getCantidadDeServicios();
    Boolean  incluye(String servicio);
} 
