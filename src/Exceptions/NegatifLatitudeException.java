package Exceptions;

public class NegatifLatitudeException extends  Exception{
    public NegatifLatitudeException(String message) {
        super(message);
        System.out.println(message);
    }
}
