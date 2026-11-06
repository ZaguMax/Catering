package ar.edu.unahur.obj2.catering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.catering.contratos.Contrato;
import ar.edu.unahur.obj2.catering.excepciones.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.excepciones.PrecioNegativoException;
import ar.edu.unahur.obj2.catering.propuestas.Menu;
import ar.edu.unahur.obj2.catering.adicionales.MozosAdicionales;
import ar.edu.unahur.obj2.catering.propuestas.Propuesta;
import ar.edu.unahur.obj2.catering.propuestas.Servicio;
import ar.edu.unahur.obj2.catering.adicionales.ServicioNocturno;
import ar.edu.unahur.obj2.catering.adicionales.VajillaPremium;

class PropuestasTest {
    private Servicio dulce;
    private Servicio brindis;
    private Menu parrillero;
    private Menu packFiesta;
    private Menu eventoCompleto;

    @BeforeEach
    void setUp() {
        // Arrange (común a los tests que usan menús)
        Servicio entradas = new Servicio("Mesa de entradas", 80_000.00);
        Servicio parrillada = new Servicio("Parrillada completa", 240_000.00);
        dulce = new Servicio("Mesa dulce", 60_000.00);
        Servicio barra = new Servicio("Barra de tragos", 150_000.00);
        Servicio dj = new Servicio("DJ y sonido", 120_000.00);
        brindis = new Servicio("Brindis de bienvenida", 50_000.00);

        parrillero = new Menu("Menú Parrillero", 10);
        parrillero.agregar(entradas);
        parrillero.agregar(parrillada);
        parrillero.agregar(dulce);

        packFiesta = new Menu("Pack Fiesta", 5);
        packFiesta.agregar(barra);
        packFiesta.agregar(dj);

        eventoCompleto = new Menu("Evento Completo", 20);
        eventoCompleto.agregar(parrillero);
        eventoCompleto.agregar(packFiesta);
        eventoCompleto.agregar(brindis);
    }

    // ---------- Etapa 1 ----------

    @Test
    void dadoMenuParrillero_alCalcularPrecio_devuelve342000() {
        // Arrange: parrillero armado en setUp()

        // Act
        double precio = parrillero.getPrecio();

        // Assert
        assertEquals(342_000, precio, 0.01);
    }

    @Test
    void dadoMenuParrillero_alContarServicios_devuelve3() {
        // Arrange: parrillero armado en setUp()

        // Act
        int cantidad = parrillero.getCantidadDeServicios();

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
        // Arrange: brindis armado en setUp()

        // Act
        boolean incluye = brindis.incluye("Brindis de bienvenida");

        // Assert
        assertTrue(incluye);
    }

    @Test
    void dadoEventoCompleto_alCalcularPrecio_devuelve518800() {
        // Arrange: eventoCompleto armado en setUp()

        // Act
        double precio = eventoCompleto.getPrecio();

        // Assert
        assertEquals(518_800, precio, 0.01);
    }

    // ---------- Etapa 2: adicionales ----------

    @Test
    void dadoParrilleroConMozosYNocturno_alConsultarPrecio_devuelve432000() {
        // Arrange
        Propuesta propuesta = new ServicioNocturno(new MozosAdicionales(parrillero));

        // Act
        double precio = propuesta.getPrecio();

        // Assert
        assertEquals(432_000, precio, 0.01);
    }

    @Test
    void dadoParrilleroConNocturnoYMozos_alConsultarPrecio_devuelve428400() {
        // Arrange
        Propuesta propuesta = new MozosAdicionales(new ServicioNocturno(parrillero));

        // Act
        double precio = propuesta.getPrecio();

        // Assert
        assertEquals(428_400, precio, 0.01);
    }

    @Test
    void dadoBrindisConVajillaRepetida_alConsultarPrecio_devuelve74000() {
        // Arrange
        Propuesta propuesta = new VajillaPremium(new VajillaPremium(brindis));

        // Act
        double precio = propuesta.getPrecio();

        // Assert
        assertEquals(74_000, precio, 0.01);
    }

    @Test
    void dadoMesaDulceConNocturnoRepetido_alConsultarPrecio_devuelve86400() {
        // Arrange
        Propuesta propuesta = new ServicioNocturno(new ServicioNocturno(dulce));

        // Act
        double precio = propuesta.getPrecio();

        // Assert
        assertEquals(86_400, precio, 0.01);
    }

    @Test
    void dadoEventoCompletoConVajilla_alConsultarPrecio_devuelve530800() {
        // Arrange
        Propuesta propuesta = new VajillaPremium(eventoCompleto);

        // Act
        double precio = propuesta.getPrecio();

        // Assert
        assertEquals(530_800, precio, 0.01);
    }

    @Test
    void dadoParrilleroConAdicionales_alConsultarDescripcion_listaLosAdicionalesEnOrden() {
        // Arrange
        Propuesta propuesta = new ServicioNocturno(new MozosAdicionales(parrillero));

        // Act
        String descripcion = propuesta.getDescripcion();

        // Assert
        assertEquals("Menú Parrillero + Mozos adicionales + Servicio nocturno", descripcion);
    }

    @Test
    void dadoPropuestaConAdicional_alConsultarServiciosEInclusion_seMantienen() {
        // Arrange
        Propuesta propuesta = new VajillaPremium(parrillero);

        // Act
        int cantidad = propuesta.getCantidadDeServicios();
        boolean incluye = propuesta.incluye("Parrillada completa");

        // Assert
        assertEquals(3, cantidad);
        assertTrue(incluye);
    }

    @Test
    void dadoPropuestaConAdicional_alConsultarLaOriginal_noCambia() {
        // Arrange
        new MozosAdicionales(parrillero);

        // Act
        double precio = parrillero.getPrecio();
        String descripcion = parrillero.getDescripcion();

        // Assert
        assertEquals(342_000, precio, 0.01);
        assertEquals("Menú Parrillero", descripcion);
    }

    @Test
    void dadoContratoDeLucia_alConsultarImporteYDescripcion_losFijaAlCrearlo() {
        // Arrange
        Propuesta propuesta = new MozosAdicionales(parrillero);

        // Act
        Contrato contrato = new Contrato("Lucía", propuesta);

        // Assert
        assertEquals(360_000, contrato.getImporte(), 0.01);
        assertEquals("Menú Parrillero + Mozos adicionales", contrato.getDescripcion());
    }
}