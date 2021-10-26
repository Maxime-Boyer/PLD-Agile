package Exceptions;

public class AbsenceAttributBaliseRequest extends Exception{

    public AbsenceAttributBaliseRequest(String message) {
        super(message);
        System.out.println(message);
    }

}

