package Exceptions;

public class TagNameMapException extends Exception{
    /**
     * Exception lancée lorsque le fichier xml ne contient pas le tag nome
     * @param message: message emis lors de la découverte de l'exception
     */
    public TagNameMapException(String message) {
        super(message);
        System.out.println(message);
    }
}
