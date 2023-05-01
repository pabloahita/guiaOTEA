package gui.data.model;

public class UserNonExistingException extends Exception{
    public UserNonExistingException(){
        super("The user with the written username doesn't exist at the database");
    }
}
