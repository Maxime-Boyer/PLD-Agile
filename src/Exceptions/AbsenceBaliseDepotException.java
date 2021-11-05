package Exceptions;

public class AbsenceBaliseDepotException extends Exception{

    public AbsenceBaliseDepotException(String message) {
        super(message);
        System.out.println(message);
    }

}
