package gui.data.model;

public class WrongPasswordException extends Exception{
    public WrongPasswordException(){
        super("The written password is wrong. Please insert it again correctly");
    }
}
