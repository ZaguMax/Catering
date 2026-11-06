package ar.edu.unahur.obj2.catering.adicionales;

import ar.edu.unahur.obj2.catering.propuestas.Propuesta;

public class VajillaPremium extends Adicional {
    
    public VajillaPremium(Propuesta propuesta) {
        super(propuesta);
    }

    @Override 
    public Double getPrecio(){
        return propuesta.getPrecio() + 12_000;
    }

    @Override 
    public  String getDescripcion(){
        return propuesta.getDescripcion() + " + Vajilla premium";
    }
}
