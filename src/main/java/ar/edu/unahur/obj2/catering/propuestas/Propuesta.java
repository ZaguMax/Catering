package ar.edu.unahur.obj2.catering.propuestas;

public interface Propuesta {
    String descripcion();
    Double precio();
    Integer cantidadDeServicios();
    Boolean  incluye(String servicio);
} 
