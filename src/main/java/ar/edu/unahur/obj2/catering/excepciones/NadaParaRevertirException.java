package ar.edu.unahur.obj2.catering.excepciones;

/*
No verificada = 
    El llamador puede evitarla consultando antes si hay historial
*/
public class NadaParaRevertirException extends RuntimeException {

    public NadaParaRevertirException() {
        super("No hay nada para revertir");
    }

    public NadaParaRevertirException(String message) {
        super(message);
    }

}
