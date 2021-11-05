package Exceptions;

public class AttributsSegmentsException extends Exception{
    public AttributsSegmentsException(String message) {
        super(message);
        System.out.println(message);
    }
}
