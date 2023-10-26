package cli.user;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class User implements Serializable{

    @SerializedName("first_name")
    public String first_name;
    @SerializedName("userType")
    public String userType;

    @SerializedName("last_name")
    public String last_name;

    @SerializedName("emailUser")
    public String emailUser;

    @SerializedName("passwordUser")
    public String passwordUser;

    @SerializedName("telephone")
    public String telephone;

    public boolean connected;

    @SerializedName("idOrganization")
    public int idOrganization;

    @SerializedName("orgType")
    public String orgType;

    @SerializedName("illness")
    public String illness;


    public User(String emailUser, String userType, String first_name, String last_name, String passwordUser, String telephone, int idOrganization, String orgType, String illness){
        setEmailUser(emailUser);
        setUserType(userType);
        setFirst_name(first_name);
        setLast_name(last_name);
        setPassword(passwordUser);
        setTelephone(telephone);
        setIdOrganization(idOrganization);
        setOrganizationType(orgType);
        setIllness(illness);
    }

    public String getFirst_name(){
        return first_name;
    }

    public String getLast_name(){
        return last_name;
    }

    public String getEmailUser(){
        return emailUser;
    }

    public String getTelephone(){
        return telephone;
    }
    public void setFirst_name(String first_name){
        this.first_name=first_name;
    }

    public void setLast_name(String last_name){
        this.last_name=last_name;
    }

    public void setEmailUser(String emailUser){
        this.emailUser=emailUser;
    }

    public void setTelephone(String telephone){
        this.telephone=telephone;
    }

    public int getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
    }

    public String getOrganizationType() {
        return orgType;
    }

    public void setOrganizationType(String orgType) {
        this.orgType = orgType;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public void connect(){this.connected=true;}

    public void disconnect(){this.connected=false;}

    public String getPassword() {
        return passwordUser;
    }

    public void setPassword(String passwordUser) {
        this.passwordUser=passwordUser;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }



}
