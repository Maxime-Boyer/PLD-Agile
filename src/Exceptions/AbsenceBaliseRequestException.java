package Exceptions;

public class AbsenceBaliseRequestException extends Exception{

    public AbsenceBaliseRequestException(String message) {
        super(message);
        System.out.println(message);
    }

}
