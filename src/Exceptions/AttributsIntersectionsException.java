package Exceptions;

public class AttributsIntersectionsException extends Exception{
    /**
     * Exception lancée lorsque la balise intersection à un soucis dans le nombre d'attribut ou dans le contenu des attributs
     * @param message: message emis lors de la découverte de l'exception
     */
    public AttributsIntersectionsException(String message) {
        super(message);
        System.out.println(message);
    }
}
