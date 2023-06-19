package cli.user;


import com.google.gson.annotations.SerializedName;

public abstract class AbstractUser implements User{

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("emailUser")
    private String email;

    @SerializedName("telephone")
    private int telephone;

    private boolean connected;

    @SerializedName("password")
    private String password;

    @SerializedName("idOrganization")
    private int idOrganization;

    @SerializedName("orgType")
    private String orgType;

    @SerializedName("illness")
    private String illness;

    public AbstractUser(String first_name, String last_name, String email, String password, int telephone, int idOrganization, String orgType, String illness){
        setFirstName(first_name);
        setLastName(last_name);
        setEmail(email);
        setPassword(password);
        setTelephone(telephone);
        setIdOrganization(idOrganization);
        setOrgType(orgType);
        setIllness(illness);
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

    public int getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    @Override
    public void connect(){this.connected=true;}

    @Override
    public void disconnect(){this.connected=false;}
}
