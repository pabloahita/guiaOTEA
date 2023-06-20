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
    private long telephone;

    private boolean connected;

    @SerializedName("password")
    private String password;

    @SerializedName("idOrganization")
    private int idOrganization;

    @SerializedName("orgType")
    private String orgType;

    @SerializedName("illness")
    private String illness;

    public AbstractUser(String first_name, String last_name, String email, String password, long telephone, int idOrganization, String orgType, String illness){
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
    public long getTelephone(){
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
    public void setTelephone(long telephone){
        this.telephone=telephone;
    }

    @Override
    public int getIdOrganization() {
        return idOrganization;
    }

    @Override
    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
    }

    @Override
    public String getOrgType() {
        return orgType;
    }

    @Override
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Override
    public String getIllness() {
        return illness;
    }

    @Override
    public void setIllness(String illness) {
        this.illness = illness;
    }

    @Override
    public void connect(){this.connected=true;}

    @Override
    public void disconnect(){this.connected=false;}
}
