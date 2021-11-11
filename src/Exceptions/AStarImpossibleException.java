package Exceptions;

public class AStarImpossibleException extends Exception{
    /**
     * Exception lancée lorsque l'algorytme Astar ne peut pas être éxécuté
     * @param message: message emis lors de la découverte de l'exception
     */
    public AStarImpossibleException(String message) {
        super(message);
        System.out.println(message);
    }

}

