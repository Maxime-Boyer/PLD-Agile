package Exceptions;

public class NegatifLatitudeException extends  Exception{
    /**
     * Exception lancée lorsque une latitude est négative sachant que nous somme en France et donc au dessus de l'quateur donc elle doit être positive
     * @param message: message emis lors de la découverte de l'exception
     */
    public NegatifLatitudeException(String message) {
        super(message);
        System.out.println(message);
    }
}
