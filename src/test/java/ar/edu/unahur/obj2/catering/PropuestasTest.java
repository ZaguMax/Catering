package ar.edu.unahur.obj2.catering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.catering.propuestas.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.propuestas.Menu;
import ar.edu.unahur.obj2.catering.propuestas.PrecioNegativoException;
import ar.edu.unahur.obj2.catering.propuestas.Servicio;

class PropuestasTest {
    private Menu parrillero;
    private Menu packFiesta;

    @BeforeEach
    void setUp() {
        // Arrange (común a los tests que usan menús)
        Servicio entradas = new Servicio("Mesa de entradas", 80_000.00);
        Servicio parrillada = new Servicio("Parrillada completa", 240_000.00);
        Servicio dulce = new Servicio("Mesa dulce", 60_000.00);
        Servicio barra = new Servicio("Barra de tragos", 150_000.00);
        Servicio dj = new Servicio("DJ y sonido", 120_000.00);

        parrillero = new Menu("Menú Parrillero", 10);
        parrillero.agregar(entradas);
        parrillero.agregar(parrillada);
        parrillero.agregar(dulce);

        packFiesta = new Menu("Pack Fiesta", 5);
        packFiesta.agregar(barra);
        packFiesta.agregar(dj);
    }

    @Test
    void dadoMenuParrillero_alCalcularPrecio_devuelve342000() {
        // Arrange: parrillero armado en setUp()

        // Act
        double precio = parrillero.precio();

        // Assert
        assertEquals(342_000, precio, 0.01);
    }

    @Test
    void dadoMenuParrillero_alContarServicios_devuelve3() {
        // Arrange: parrillero armado en setUp()

        // Act
        int cantidad = parrillero.cantidadDeServicios();

        // Assert
        assertEquals(3, cantidad);
    }

    @Test
    void dadoMenuParrillero_alConsultarDJ_noLoIncluye() {
        // Arrange: parrillero armado en setUp()

        // Act
        boolean incluye = parrillero.incluye("DJ y sonido");

        // Assert
        assertFalse(incluye);
    }

    @Test
    void dadoPrecioNegativo_alCrearServicio_lanzaPrecioNegativoException() {
        // Arrange
        double precioNegativo = -1.00;

        // Act + Assert
        assertThrows(PrecioNegativoException.class, () -> new Servicio("X", precioNegativo));
    }

    @Test
    void dadoDescuentoFueraDeRango_alCrearMenu_lanzaDescuentoInvalidoException() {
        // Arrange
        double descuentoInvalido = 101;

        // Act + Assert
        assertThrows(DescuentoInvalidoException.class, () -> new Menu("X", descuentoInvalido));
    }

    @Test
    void dadoBrindisDeBienvenida_alConsultarSuNombre_loIncluye() {
        // Arrange
        Servicio brindis = new Servicio("Brindis de bienvenida", 50_000.00);

        // Act
        boolean incluye = brindis.incluye("Brindis de bienvenida");

        // Assert
        assertTrue(incluye);
    }
}