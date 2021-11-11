package Exceptions;

public class CommandeImpossibleException extends Exception {
    /**
     * Exception lancée lorsque l'on tente d'ajouer une requete et qu'on place le prédécesseur du dépot avant celui de la collecte
     * @param message: message emis lors de la découverte de l'exception
     */
    public CommandeImpossibleException(String message) {
        super(message);
        System.out.println(message);

    }
}
