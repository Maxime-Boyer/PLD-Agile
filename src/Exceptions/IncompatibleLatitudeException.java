package Exceptions;

public class IncompatibleLatitudeException extends Exception {

    public IncompatibleLatitudeException(String message) {
        super(message);
        System.out.println(message);

    }
}
