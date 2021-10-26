package Exceptions;

public class MauvaisAttributBaliseSegmentException extends Exception{

    public MauvaisAttributBaliseSegmentException(String message) {
        super(message);
        System.out.println(message);
    }

}
