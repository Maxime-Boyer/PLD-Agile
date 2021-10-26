package Exceptions;

public class AbsenceBaliseRequest extends Exception{

    public AbsenceBaliseRequest(String message) {
        super(message);
        System.out.println(message);
    }

}
