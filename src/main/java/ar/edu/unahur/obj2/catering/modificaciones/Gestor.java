package ar.edu.unahur.obj2.catering.modificaciones;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unahur.obj2.catering.excepciones.NadaParaRevertirException;

public class Gestor {
    private final List<Modificacion> historial = new ArrayList<>();

    public void aplicar(Modificacion modificacion) {
        modificacion.aplicar();          
        historial.add(modificacion);
    }

    public void revertir() {
        if (historial.isEmpty()) {
            throw new NadaParaRevertirException("No hay modificaciones para revertir");
        }
        Modificacion ultima = historial.remove(historial.size() - 1);
        ultima.revertir();
    }

    public void aplicarLote(List<Modificacion> lote) {
        int aplicadas = 0;
        try {
            for (Modificacion modificacion : lote) {
                aplicar(modificacion);
                aplicadas++;
            }
        } catch (RuntimeException e) {
            for (int i = 0; i < aplicadas; i++) {
                revertir();
            }
            throw e;
        }
    }
}