package cli.user;

public abstract class AbstractUser implements User{

    private String first_name;
    private String last_name;
    private String email;
    private int telephone;

    private String password; //Pending to change to a password datatype

    public AbstractUser(String first_name, String last_name, String email, String password, int telephone){
        setFirstName(first_name);
        setLastName(last_name);
        setEmail(email);
        setPassword(password);
        setTelephone(telephone);
    }

    public String getFirstName(){
        return first_name;
    }

    public String getLastName(){
        return last_name;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public int getTelephone(){
        return telephone;
    }
    public void setFirstName(String first_name){
        this.first_name=first_name;
    }

    public void setLastName(String last_name){
        this.last_name=last_name;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setTelephone(int telephone){
        this.telephone=telephone;
    }
}
