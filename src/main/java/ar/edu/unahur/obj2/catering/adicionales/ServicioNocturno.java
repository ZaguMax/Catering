package ar.edu.unahur.obj2.catering.adicionales;

import ar.edu.unahur.obj2.catering.propuestas.Propuesta;

public class ServicioNocturno extends  Adicional {
    
    public ServicioNocturno(Propuesta propuesta) {
        super(propuesta);
    }

    @Override 
    public Double getPrecio(){
        return propuesta.getPrecio() * 1.2;
    }

    @Override 
    public  String getDescripcion(){
        return propuesta.getDescripcion() + " + Servicio nocturno";
    }
}
