package Exceptions;

public class IncompatibleLatitudeException extends Exception {
    /**
     * Exception lancée lorsque une latitude n'a pas un format adéquat
     * @param message: message emis lors de la découverte de l'exception
     */
    public IncompatibleLatitudeException(String message) {
        super(message);
        System.out.println(message);

    }
}
