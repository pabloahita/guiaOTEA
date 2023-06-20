package cli.user;

public class Administrator extends AbstractUser {
    public Administrator(String first_name, String last_name, String email, String password, long telephone) {
        super(first_name, last_name, email, password, telephone,-1,"","");
    }
}
