package Exceptions;

public class AbsenceBaliseRequestException extends Exception{
    /**
     * Exception lancée lorsque la balise request est absente dans les fichiers de requêtes
     * @param message: message emis lors de la découverte de l'exception
     */
    public AbsenceBaliseRequestException(String message) {
        super(message);
        System.out.println(message);
    }

}
