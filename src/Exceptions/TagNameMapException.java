package Exceptions;

public class TagNameMapException extends Exception{
    public TagNameMapException(String message) {
        super(message);
        System.out.println(message);
    }
}
