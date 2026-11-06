package ar.edu.unahur.obj2.catering.propuestas;

public class Servicio implements Propuesta {
    

    private final String nombre;
    private final Double precio;

    public Servicio(String nombre, Double precio) {
        if (precio < 0){
            throw new PrecioNegativoException("El precio de un Servicio no puede ser negativo");
        }
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override 
    public String descripcion() { 
        return nombre;
    }

    @Override 
    public Double precio() { 
        return precio;
    }

    @Override 
    public Integer cantidadDeServicios() { 
        return 1;
    }

    @Override 
    public Boolean incluye(String nombreServicio) { 
        return nombre.equals(nombreServicio);
    }
}
