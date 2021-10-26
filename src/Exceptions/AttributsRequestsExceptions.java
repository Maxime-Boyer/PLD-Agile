package Exceptions;

public class AttributsRequestsExceptions extends Exception{

    public AttributsRequestsExceptions(String message) {
        super(message);
        System.out.println(message);
    }

}

