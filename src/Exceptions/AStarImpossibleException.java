package Exceptions;

public class AStarImpossibleException extends Exception{

    public AStarImpossibleException(String message) {
        super(message);
        System.out.println(message);
    }

}

