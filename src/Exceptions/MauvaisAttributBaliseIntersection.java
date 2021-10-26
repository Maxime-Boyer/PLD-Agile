package Exceptions;

public class MauvaisAttributBaliseIntersection extends Exception{

    public MauvaisAttributBaliseIntersection(String message) {
        super(message);
        System.out.println(message);
    }

}
