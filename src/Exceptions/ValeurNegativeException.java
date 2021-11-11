package Exceptions;

public class ValeurNegativeException extends Exception{
    public ValeurNegativeException(String message) {
        super(message);
        System.out.println(message);
    }
}
