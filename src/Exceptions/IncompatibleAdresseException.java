package Exceptions;

public class IncompatibleAdresseException extends Exception{

    public IncompatibleAdresseException(String message) {
        super(message);
        System.out.println(message);
    }
}
