package Exceptions;

public class AttributsRequestsException extends Exception{
    /**
     * Exception lancée lorsque la balise request à un soucis dans le nombre d'attribut ou dans le contenu des attributs
     * @param message: message emis lors de la découverte de l'exception
     */
    public AttributsRequestsException(String message) {
        super(message);
        System.out.println(message);
    }

}

