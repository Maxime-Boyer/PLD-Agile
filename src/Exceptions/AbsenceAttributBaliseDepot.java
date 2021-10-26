package Exceptions;

public class AbsenceAttributBaliseDepot extends Exception{

    public AbsenceAttributBaliseDepot(String message) {
        super(message);
        System.out.println(message);
    }

}

