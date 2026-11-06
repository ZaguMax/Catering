package ar.edu.unahur.obj2.catering.excepciones;

/*
No verificada = 
    El llamador puede evitarlo y no hay forma razonable de recuperarse en tiempo de ejecución
*/

public class DescuentoInvalidoException extends RuntimeException {

    public DescuentoInvalidoException() {
        super("El descuento es invalido");
    }

    public DescuentoInvalidoException(String message) {
        super(message);
    }

}
