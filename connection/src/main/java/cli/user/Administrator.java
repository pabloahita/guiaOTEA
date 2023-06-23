package cli.user;

public class Administrator extends User {
    public Administrator(String first_name, String last_name, String userType, String email, String password, long telephone) {
        super(first_name, last_name, userType, email, password, telephone,-1,"","");
    }
}
