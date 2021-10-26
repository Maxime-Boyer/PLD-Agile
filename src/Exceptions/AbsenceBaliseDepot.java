package Exceptions;

public class AbsenceBaliseDepot extends Exception{

    public AbsenceBaliseDepot(String message) {
        super(message);
        System.out.println(message);
    }

}
