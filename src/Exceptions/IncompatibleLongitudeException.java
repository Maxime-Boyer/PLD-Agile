package Exceptions;

public class IncompatibleLongitudeException extends Exception{
    /**
     * Exception lancée lorsque une longitude n'a pas un format adéquat
     * @param message: message emis lors de la découverte de l'exception
     */
    public IncompatibleLongitudeException(String message) {
        super(message);
        System.out.println(message);

    }
}
