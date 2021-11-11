package Exceptions;

public class AttributsDepotException extends Exception{
    /**
     * Exception lancée lorsque la balise depot à un soucis dans le nombre d'attribut ou dans le contenu des attributs
     * @param message: message emis lors de la découverte de l'exception
     */
    public AttributsDepotException(String message) {
        super(message);
        System.out.println(message);
    }

}

