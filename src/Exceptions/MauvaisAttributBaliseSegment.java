package Exceptions;

public class MauvaisAttributBaliseSegment extends Exception{

    public MauvaisAttributBaliseSegment(String message) {
        super(message);
        System.out.println(message);
    }

}
