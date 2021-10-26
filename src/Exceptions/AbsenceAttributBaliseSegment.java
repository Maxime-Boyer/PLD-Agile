package Exceptions;

public class AbsenceAttributBaliseSegment extends Exception{

    public AbsenceAttributBaliseSegment(String message) {
        super(message);
        System.out.println(message);
    }

}
