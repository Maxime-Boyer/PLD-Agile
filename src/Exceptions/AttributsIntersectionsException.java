package Exceptions;

public class AttributsIntersectionsException extends Exception{
    public AttributsIntersectionsException(String message) {
        super(message);
        System.out.println(message);
    }
}
