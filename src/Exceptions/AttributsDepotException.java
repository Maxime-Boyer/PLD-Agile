package Exceptions;

public class AttributsDepotException extends Exception{

    public AttributsDepotException(String message) {
        super(message);
        System.out.println(message);
    }

}

