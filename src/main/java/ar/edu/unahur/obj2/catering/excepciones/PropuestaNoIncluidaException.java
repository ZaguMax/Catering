package ar.edu.unahur.obj2.catering.excepciones;

/*
No verificada = 
    Al ser no verificada, no ensucia las firmas de aplicar/revertir con throws
*/
public class PropuestaNoIncluidaException extends RuntimeException {

    public PropuestaNoIncluidaException() {
        super("La propuesta no esta incluida");
    }

    public PropuestaNoIncluidaException(String message) {
        super(message);
    }

}
