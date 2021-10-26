package Exceptions;

public class NameFile extends Exception{

    public NameFile(String message) {
        super(message);
        System.out.println(message);
    }
}
