package ar.edu.unahur.obj2.catering.propuestas;

import ar.edu.unahur.obj2.catering.excepciones.PrecioNegativoException;

public class Servicio implements Propuesta {
    

    private final String nombre;
    private Double precio;

    public Servicio(String nombre, Double precio) {
        if (precio < 0){
            throw new PrecioNegativoException("El precio de un Servicio no puede ser negativo");
        }
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override 
    public String getDescripcion() { 
        return nombre;
    }

    @Override 
    public Double getPrecio() { 
        return precio;
    }

    @Override 
    public Integer getCantidadDeServicios() { 
        return 1;
    }

    @Override 
    public Boolean incluye(String nombreServicio) { 
        return nombre.equals(nombreServicio);
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new PrecioNegativoException("No se puede asignar un precio menor a 0");
        }
        this.precio = precio;
    }
}
