package ar.edu.unahur.obj2.catering.propuestas;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unahur.obj2.catering.excepciones.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.excepciones.PropuestaNoIncluidaException;

public class Menu implements Propuesta {

    private final String nombre;
    private final double descuento;
    private final List<Propuesta> contenido = new ArrayList<>();

    @Override
    public String getDescripcion() {
        return  nombre;
    }

    @Override
    public Double getPrecio() {
        double suma = 0;
        for (Propuesta propuesta : contenido) {
            suma += propuesta.getPrecio();
        }
        return suma * (100 - descuento) / 100;
    }

    @Override
    public Integer getCantidadDeServicios() {
        int total = 0;
        for (Propuesta propuesta : contenido) {
            total += propuesta.getCantidadDeServicios();
        }
        return total;
    }

    @Override
    public Boolean incluye(String nombreServicio) {
        for (Propuesta propuesta : contenido) {
            if (propuesta.incluye(nombreServicio)) {
                return true;
            }
        }
        return false;
    }

    public Menu(String nombre, double descuento) {
        if (descuento < 0 || descuento > 100){
            throw new DescuentoInvalidoException("El descuento debe estar entre 0 y 100");
        }
        this.nombre = nombre;
        this.descuento = descuento;
    }

    public void agregar(Propuesta propuesta) {   
        contenido.add(propuesta);
    }

    public void quitar(Propuesta propuesta) {
        if (!contenido.remove(propuesta)) {
            throw new PropuestaNoIncluidaException("La propuesta no es componente directo del menú");
        }
    }
}
