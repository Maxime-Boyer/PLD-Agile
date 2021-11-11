package Exceptions;

public class IncompatibleAdresseException extends Exception{
    /**
     * Exception lancée lorsque l'id de l'adresse n'appartient pas à la carte chargée
     * @param message: message emis lors de la découverte de l'exception
     */
    public IncompatibleAdresseException(String message) {
        super(message);
        System.out.println(message);
    }
}
