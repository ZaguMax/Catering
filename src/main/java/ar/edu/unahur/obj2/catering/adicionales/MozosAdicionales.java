package ar.edu.unahur.obj2.catering.adicionales;

import ar.edu.unahur.obj2.catering.propuestas.Propuesta;

public class MozosAdicionales extends  Adicional {

    public MozosAdicionales(Propuesta propuesta) {
        super(propuesta);
    }

    @Override 
    public Double getPrecio(){
        return propuesta.getPrecio() + 18_000;
    }

    @Override 
    public  String getDescripcion(){
        return propuesta.getDescripcion() + " + Mozos adicionales";
    }
}
