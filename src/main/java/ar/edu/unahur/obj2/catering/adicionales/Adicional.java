package ar.edu.unahur.obj2.catering.adicionales;

import ar.edu.unahur.obj2.catering.propuestas.Propuesta;

public abstract class Adicional implements Propuesta {
    protected final Propuesta propuesta;

    protected Adicional(Propuesta propuesta) {
        this.propuesta = propuesta;
    }


    @Override
    public Integer getCantidadDeServicios() {
        return propuesta.getCantidadDeServicios();
    }

    
    @Override
    public Boolean incluye(String servicio) {
        return propuesta.incluye(servicio);
    }
}