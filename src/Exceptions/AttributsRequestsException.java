package Exceptions;

public class AttributsRequestsException extends Exception{

    public AttributsRequestsException(String message) {
        super(message);
        System.out.println(message);
    }

}

