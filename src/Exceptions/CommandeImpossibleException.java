package Exceptions;

public class CommandeImpossibleException extends Exception {

    public CommandeImpossibleException(String message) {
        super(message);
        System.out.println(message);

    }
}
