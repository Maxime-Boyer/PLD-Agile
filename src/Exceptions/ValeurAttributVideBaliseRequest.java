package Exceptions;

public class ValeurAttributVideBaliseRequest extends Exception{

    public ValeurAttributVideBaliseRequest(String message) {
        super(message);
        System.out.println(message);
    }

}

