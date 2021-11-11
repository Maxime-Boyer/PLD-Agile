package Exceptions;

public class AttributsSegmentsException extends Exception{
    /**
     * Exception lancée lorsque la balise segment à un soucis dans le nombre d'attribut ou dans le contenu des attributs
     * @param message: message emis lors de la découverte de l'exception
     */
    public AttributsSegmentsException(String message) {
        super(message);
        System.out.println(message);
    }
}
