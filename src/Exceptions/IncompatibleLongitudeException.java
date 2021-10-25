package Exceptions;

public class IncompatibleLongitudeException extends Exception{

    public IncompatibleLongitudeException(String message) {
        super(message);
        System.out.println(message);

    }
}
