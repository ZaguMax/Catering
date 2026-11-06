package ar.edu.unahur.obj2.catering.excepciones;

/*
No verificada = 
    El llamador puede evitarlo y no hay forma razonable de recuperarse en tiempo de ejecución
*/

public class PrecioNegativoException extends RuntimeException {

    public PrecioNegativoException() {
        super("El precio no puede ser Negativo");
    }

    public PrecioNegativoException(String message) {
        super(message);
    }

}
