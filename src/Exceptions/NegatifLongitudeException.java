package Exceptions;

public class NegatifLongitudeException extends Exception{

    public NegatifLongitudeException(String message) {
        super(message);
        System.out.println(message);
    }
}
