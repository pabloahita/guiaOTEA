package cli.user;

public abstract class AbstractUser implements User{

    private String first_name;
    private String last_name;
    private String email;
    private int telephone;

    private boolean connected;

    private String password; //Pending to change to a password datatype

    public AbstractUser(String first_name, String last_name, String email, String password, int telephone){
        setFirstName(first_name);
        setLastName(last_name);
        setEmail(email);
        setPassword(password);
        setTelephone(telephone);
    }

    @Override
    public String getFirstName(){
        return first_name;
    }

    @Override
    public String getLastName(){
        return last_name;
    }

    @Override
    public String getEmail(){
        return email;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public int getTelephone(){
        return telephone;
    }
    @Override
    public void setFirstName(String first_name){
        this.first_name=first_name;
    }

    @Override
    public void setLastName(String last_name){
        this.last_name=last_name;
    }

    @Override
    public void setEmail(String email){
        this.email=email;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setTelephone(int telephone){
        this.telephone=telephone;
    }

    @Override
    public void connect(){this.connected=true;}

    @Override
    public void disconnect(){this.connected=false;}
}
