package Exceptions;

/**
 * Exception levée lorsqu'il n'y a pas de DTD dans l'entête du xml
 */

public class PresenceEncodingEtVersionException extends Exception {

    public PresenceEncodingEtVersionException(String message) {
        super(message);
        System.out.println(message);

    }
}
