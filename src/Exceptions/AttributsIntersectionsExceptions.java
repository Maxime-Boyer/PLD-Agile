package Exceptions;

public class AttributsIntersectionsExceptions extends Exception{
    public AttributsIntersectionsExceptions(String message) {
        super(message);
        System.out.println(message);
    }
}
