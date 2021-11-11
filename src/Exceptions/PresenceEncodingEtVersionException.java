package Exceptions;

/**
 * Exception levée lorsqu'il n'y a pas de DTD dans l'entête du xml
 */

public class PresenceEncodingEtVersionException extends Exception {
    /**
     * Exception lancée lorsque le fichier xml ne contient pas l'encodage en première ligne
     * @param message: message emis lors de la découverte de l'exception
     */
    public PresenceEncodingEtVersionException(String message) {
        super(message);
        System.out.println(message);

    }
}
