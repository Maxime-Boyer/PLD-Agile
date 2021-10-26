package Exceptions;

public class MauvaisAttributBaliseIntersectionException extends Exception{

    public MauvaisAttributBaliseIntersectionException(String message) {
        super(message);
        System.out.println(message);
    }

}
