package ar.edu.unahur.obj2.catering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.catering.adicionales.MozosAdicionales;
import ar.edu.unahur.obj2.catering.adicionales.ServicioNocturno;
import ar.edu.unahur.obj2.catering.adicionales.VajillaPremium;
import ar.edu.unahur.obj2.catering.contratos.Contrato;
import ar.edu.unahur.obj2.catering.excepciones.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.excepciones.NadaParaRevertirException;
import ar.edu.unahur.obj2.catering.excepciones.PrecioNegativoException;
import ar.edu.unahur.obj2.catering.excepciones.PropuestaNoIncluidaException;
import ar.edu.unahur.obj2.catering.modificaciones.ActualizarPrecio;
import ar.edu.unahur.obj2.catering.modificaciones.AgregarPropuesta;
import ar.edu.unahur.obj2.catering.modificaciones.Gestor;
import ar.edu.unahur.obj2.catering.modificaciones.Modificacion;
import ar.edu.unahur.obj2.catering.modificaciones.QuitarPropuesta;
import ar.edu.unahur.obj2.catering.propuestas.Menu;
import ar.edu.unahur.obj2.catering.propuestas.Propuesta;
import ar.edu.unahur.obj2.catering.propuestas.Servicio;

class PropuestasTest {
    private Servicio entradas;
    private Servicio parrillada;
    private Servicio dulce;
    private Servicio barra;
    private Servicio dj;
    private Servicio brindis;
    private Servicio fotografo;
    private Menu parrillero;
    private Menu packFiesta;
    private Menu eventoCompleto;
    private Gestor gestor;

    @BeforeEach
    void setUp() {
        // Arrange (común)
        entradas = new Servicio("Mesa de entradas", 80_000.00);
        parrillada = new Servicio("Parrillada completa", 240_000.00);
        dulce = new Servicio("Mesa dulce", 60_000.00);
        barra = new Servicio("Barra de tragos", 150_000.00);
        dj = new Servicio("DJ y sonido", 120_000.00);
        brindis = new Servicio("Brindis de bienvenida", 50_000.00);
        fotografo = new Servicio("Fotógrafo", 100_000.00);

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

        gestor = new Gestor();
    }

    @Test
    void dadoMenuParrillero_alCalcularPrecio_devuelve342000() {
        // Arrange: parrillero armado en setUp()

        // Act
        double precio = parrillero.getPrecio();

        // Assert
        assertEquals(342_000, precio, 0.01);
    }

    @Test
    void dadoPackFiesta_alCalcularPrecio_devuelve256500() {
        // Arrange: packFiesta armado en setUp()

        // Act
        double precio = packFiesta.getPrecio();

        // Assert
        assertEquals(256_500, precio, 0.01);
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
    void dadoServicioSuelto_alContarServicios_devuelve1() {
        // Arrange: dj armado en setUp()

        // Act
        int cantidad = dj.getCantidadDeServicios();

        // Assert
        assertEquals(1, cantidad);
    }

    @Test
    void dadoMenuParrillero_alConsultarParrillada_laIncluye() {
        // Arrange: parrillero armado en setUp()

        // Act
        boolean incluye = parrillero.incluye("Parrillada completa");

        // Assert
        assertTrue(incluye);
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
    void dadoBrindisDeBienvenida_alConsultarSuNombre_loIncluye() {
        // Arrange: brindis armado en setUp()

        // Act
        boolean incluye = brindis.incluye("Brindis de bienvenida");

        // Assert
        assertTrue(incluye);
    }

    @Test
    void dadoUnServicioConPrecioNegativo_alCrearlo_lanzaPrecioNegativoException() {
        // Arrange
        double precioNegativo = -1.00;

        // Act + Assert
        assertThrows(PrecioNegativoException.class, () -> new Servicio("X", precioNegativo));
    }

    @Test
    void dadoUnMenuConDescuentoMayorACien_alCrearlo_lanzaDescuentoInvalidoException() {
        // Arrange
        double descuentoInvalido = 101;

        // Act + Assert
        assertThrows(DescuentoInvalidoException.class, () -> new Menu("X", descuentoInvalido));
    }

    @Test
    void dadoUnMenuConDescuentoNegativo_alCrearlo_lanzaDescuentoInvalidoException() {
        // Arrange
        double descuentoInvalido = -1;

        // Act + Assert
        assertThrows(DescuentoInvalidoException.class, () -> new Menu("X", descuentoInvalido));
    }

    @Test
    void dadoEventoCompleto_alCalcularPrecio_aplicaElDescuentoSobreLosMenusYaDescontados() {
        // Arrange: eventoCompleto armado en setUp()

        // Act
        double precio = eventoCompleto.getPrecio();

        // Assert
        assertEquals(518_800, precio, 0.01);
    }

    @Test
    void dadoUnMenuQueContieneOtrosMenus_alContarServicios_sumaLosDeTodosLosNiveles() {
        // Arrange: eventoCompleto armado en setUp()

        // Act
        int cantidad = eventoCompleto.getCantidadDeServicios();

        // Assert
        assertEquals(6, cantidad);
    }

    @Test
    void dadoEventoCompleto_alConsultarUnServicioDeUnMenuInterno_loIncluye() {
        // Arrange: eventoCompleto armado en setUp()

        // Act
        boolean incluye = eventoCompleto.incluye("DJ y sonido");

        // Assert
        assertTrue(incluye);
    }

    @Test
    void dadoEventoCompleto_alConsultarFotografo_noLoIncluye() {
        // Arrange: eventoCompleto armado en setUp()

        // Act
        boolean incluye = eventoCompleto.incluye("Fotógrafo");

        // Assert
        assertFalse(incluye);
    }

    @Test
    void dadoUnServicioConMozosAdicionales_alCalcularPrecio_suma18000() {
        // Arrange
        Propuesta propuesta = new MozosAdicionales(dulce);

        // Act
        double precio = propuesta.getPrecio();

        // Assert
        assertEquals(78_000, precio, 0.01);
    }

    @Test
    void dadoUnServicioConVajillaPremium_alCalcularPrecio_suma12000() {
        // Arrange
        Propuesta propuesta = new VajillaPremium(dulce);

        // Act
        double precio = propuesta.getPrecio();

        // Assert
        assertEquals(72_000, precio, 0.01);
    }

    @Test
    void dadoUnServicioConServicioNocturno_alCalcularPrecio_suma20PorCiento() {
        // Arrange
        Propuesta propuesta = new ServicioNocturno(dulce);

        // Act
        double precio = propuesta.getPrecio();

        // Assert
        assertEquals(72_000, precio, 0.01);
    }

    @Test
    void dadoUnServicioNocturnoAplicadoDespuesDeMozos_alCalcularPrecio_incluyeLosMozosEnElRecargo() {
        // Arrange
        Propuesta propuesta = new ServicioNocturno(new MozosAdicionales(parrillero));

        // Act
        double precio = propuesta.getPrecio();

        // Assert
        assertEquals(432_000, precio, 0.01);
    }

    @Test
    void dadoUnServicioNocturnoAplicadoAntesQueMozos_alCalcularPrecio_elOrdenAlteraElPrecioFinal() {
        // Arrange
        Propuesta propuesta = new MozosAdicionales(new ServicioNocturno(parrillero));

        // Act
        double precio = propuesta.getPrecio();

        // Assert
        assertEquals(428_400, precio, 0.01);
    }

    @Test
    void dadoUnAdicionalRepetido_alCalcularPrecio_seAplicaCadaVez() {
        // Arrange
        Propuesta propuesta = new VajillaPremium(new VajillaPremium(brindis));

        // Act
        double precio = propuesta.getPrecio();

        // Assert
        assertEquals(74_000, precio, 0.01);
    }

    @Test
    void dadoUnServicioNocturnoRepetido_alCalcularPrecio_elRecargoSeAcumula() {
        // Arrange
        Propuesta propuesta = new ServicioNocturno(new ServicioNocturno(dulce));

        // Act
        double precio = propuesta.getPrecio();

        // Assert
        assertEquals(86_400, precio, 0.01);
    }

    @Test
    void dadoUnAdicionalSobreUnMenuCompleto_alCalcularPrecio_sumaAlPrecioDelMenu() {
        // Arrange
        Propuesta propuesta = new VajillaPremium(eventoCompleto);

        // Act
        double precio = propuesta.getPrecio();

        // Assert
        assertEquals(530_800, precio, 0.01);
    }

    @Test
    void dadoUnaPropuestaConAdicionales_alConsultarDescripcion_listaLosAdicionalesEnOrden() {
        // Arrange
        Propuesta propuesta = new ServicioNocturno(new MozosAdicionales(parrillero));

        // Act
        String descripcion = propuesta.getDescripcion();

        // Assert
        assertEquals("Menú Parrillero + Mozos adicionales + Servicio nocturno", descripcion);
    }

    @Test
    void dadaUnaPropuestaConAdicional_alConsultarServiciosEInclusion_seMantienen() {
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
    void dadaUnaPropuestaConAdicional_alConsultarLaOriginal_noCambia() {
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
    void dadoUnContratoDeLucia_alConsultarImporteYDescripcion_losFijaAlCrearlo() {
        // Arrange
        Propuesta propuesta = new MozosAdicionales(parrillero);

        // Act
        Contrato contrato = new Contrato("Lucía", propuesta);

        // Assert
        assertEquals(360_000, contrato.getImporte(), 0.01);
        assertEquals("Menú Parrillero + Mozos adicionales", contrato.getDescripcion());
    }

    @Test
    void dadaUnaModificacionAgregar_alAplicarlaYRevertirla_agregaYLuegoQuitaLaPropuesta() {
        // Arrange
        gestor.aplicar(new AgregarPropuesta(parrillero, fotografo));

        // Act
        double precioConFotografo = parrillero.getPrecio();
        gestor.revertir();

        // Assert
        assertEquals(432_000, precioConFotografo, 0.01);
        assertEquals(342_000, parrillero.getPrecio(), 0.01);
        assertFalse(parrillero.incluye("Fotógrafo"));
    }

    @Test
    void dadaUnaModificacionQuitar_alAplicarlaYRevertirla_quitaYLuegoVuelveAAgregarLaPropuesta() {
        // Arrange
        gestor.aplicar(new QuitarPropuesta(parrillero, dulce));

        // Act
        double precioSinDulce = parrillero.getPrecio();
        gestor.revertir();

        // Assert
        assertEquals(288_000, precioSinDulce, 0.01);
        assertEquals(342_000, parrillero.getPrecio(), 0.01);
        assertTrue(parrillero.incluye("Mesa dulce"));
    }

    @Test
    void dadaUnaActualizacionDePrecio_alRevertirla_devuelveElPrecioAnterior() {
        // Arrange
        gestor.aplicar(new ActualizarPrecio(parrillada, 260_000));

        // Act
        double precioActualizado = parrillada.getPrecio();
        gestor.revertir();

        // Assert
        assertEquals(260_000, precioActualizado, 0.01);
        assertEquals(240_000, parrillada.getPrecio(), 0.01);
    }

    @Test
    void dadaUnaSecuenciaInmediata_alAplicarYRevertir_elPrecioSigueLosPasosEsperados() {
        // Arrange
        gestor.aplicar(new AgregarPropuesta(parrillero, fotografo));
        gestor.aplicar(new ActualizarPrecio(parrillada, 260_000));
        gestor.aplicar(new QuitarPropuesta(parrillero, dulce));

        // Act + Assert
        assertEquals(396_000, parrillero.getPrecio(), 0.01);
        gestor.revertir();
        assertEquals(450_000, parrillero.getPrecio(), 0.01);
        gestor.revertir();
        assertEquals(432_000, parrillero.getPrecio(), 0.01);
        gestor.revertir();
        assertEquals(342_000, parrillero.getPrecio(), 0.01);
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertir());
    }

    @Test
    void dadoUnHistorialVacio_alRevertir_lanzaNadaParaRevertirException() {
        // Arrange: gestor sin modificaciones aplicadas

        // Act + Assert
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertir());
    }

    @Test
    void dadasDosActualizacionesDelMismoServicio_alRevertirlas_vuelveAlPrecioPrevioDeCadaUna() {
        // Arrange
        Modificacion primera = new ActualizarPrecio(parrillada, 260_000);
        Modificacion segunda = new ActualizarPrecio(parrillada, 300_000);
        gestor.aplicar(primera);
        gestor.aplicar(segunda);

        // Act + Assert
        assertEquals(300_000, parrillada.getPrecio(), 0.01);
        gestor.revertir();
        assertEquals(260_000, parrillada.getPrecio(), 0.01);
        gestor.revertir();
        assertEquals(240_000, parrillada.getPrecio(), 0.01);
    }

    @Test
    void dadoUnPrecioNegativo_alAplicarLaActualizacion_lanzaPrecioNegativoExceptionYNoQuedaEnElHistorial() {
        // Arrange
        Modificacion invalida = new ActualizarPrecio(parrillada, -1);

        // Act + Assert
        assertThrows(PrecioNegativoException.class, () -> gestor.aplicar(invalida));
        assertEquals(240_000, parrillada.getPrecio(), 0.01);
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertir());
    }

    @Test
    void dadoUnMenuSinLaPropuesta_alQuitarla_lanzaPropuestaNoIncluidaExceptionYNoAlteraElMenu() {
        // Arrange: barra no es componente directo de parrillero

        // Act + Assert
        assertThrows(PropuestaNoIncluidaException.class, () -> parrillero.quitar(barra));
        assertEquals(342_000, parrillero.getPrecio(), 0.01);
        assertEquals(3, parrillero.getCantidadDeServicios());
    }

    @Test
    void dadaUnaModificacionQuitarInvalida_alAplicarla_lanzaPropuestaNoIncluidaExceptionYNoQuedaEnElHistorial() {
        // Arrange
        Modificacion invalida = new QuitarPropuesta(parrillero, barra);

        // Act + Assert
        assertThrows(PropuestaNoIncluidaException.class, () -> gestor.aplicar(invalida));
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertir());
    }


    @Test
    void dadoUnLoteExitoso_alAplicarlo_cadaModificacionQuedaEnElHistorialEnOrden() {
        // Arrange
        List<Modificacion> lote = List.of(
            new AgregarPropuesta(parrillero, fotografo),
            new ActualizarPrecio(parrillada, 260_000),
            new QuitarPropuesta(parrillero, dulce));

        // Act
        gestor.aplicarLote(lote);

        // Assert
        assertEquals(396_000, parrillero.getPrecio(), 0.01);
        gestor.revertir();
        assertEquals(450_000, parrillero.getPrecio(), 0.01);
        gestor.revertir();
        gestor.revertir();
        assertEquals(342_000, parrillero.getPrecio(), 0.01);
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertir());
    }

    @Test
    void dadoUnLoteConUnaModificacionInvalida_alAplicarlo_dejaElCatalogoComoEstaba() {
        // Arrange
        gestor.aplicar(new AgregarPropuesta(parrillero, fotografo));
        List<Modificacion> lote = List.of(
            new ActualizarPrecio(parrillada, 260_000),
            new QuitarPropuesta(parrillero, barra));   

        // Act
        assertThrows(PropuestaNoIncluidaException.class, () -> gestor.aplicarLote(lote));

        // Assert
        assertEquals(432_000, parrillero.getPrecio(), 0.01);
        assertEquals(240_000, parrillada.getPrecio(), 0.01);
        gestor.revertir();
        assertEquals(342_000, parrillero.getPrecio(), 0.01);
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertir());
    }

    @Test
    void dadoUnContratoCreado_alActualizarElPrecioDeUnServicio_elImporteContratadoNoCambia() {
        // Arrange
        Contrato contrato = new Contrato("Lucía", parrillero);

        // Act
        gestor.aplicar(new ActualizarPrecio(parrillada, 260_000));

        // Assert
        assertEquals(360_000, parrillero.getPrecio(), 0.01);
        assertEquals(342_000, contrato.getImporte(), 0.01);
    }
}