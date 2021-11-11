package Exceptions;

public class AbsenceBaliseDepotException extends Exception{
    /**
     * Exception lancée lorsque la balise depot est absente dans les fichiers de requêtes
     * @param message: message emis lors de la découverte de l'exception
     */
    public AbsenceBaliseDepotException(String message) {
        super(message);
        System.out.println(message);
    }

}
