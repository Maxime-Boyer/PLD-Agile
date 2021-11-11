package Exceptions;

public class NegatifLongitudeException extends Exception{
    /**
     * Exception lancée lorsque une longitude est négative sachant que nous somme en France et donc au dessus de l'quateur donc elle doit être positive
     * @param message: message emis lors de la découverte de l'exception
     */
    public NegatifLongitudeException(String message) {
        super(message);
        System.out.println(message);
    }
}
