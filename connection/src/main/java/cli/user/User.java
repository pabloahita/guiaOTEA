package cli.user;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class User implements IUser, Serializable{

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
    public long telephone;

    public boolean connected;

    @SerializedName("idOrganization")
    public int idOrganization;

    @SerializedName("organizationType")
    public String orgType;

    @SerializedName("illness")
    public String illness;


    public User(String emailUser, String userType, String first_name, String last_name, String passwordUser, long telephone, int idOrganization, String orgType, String illness){
        setEmailUser(emailUser);
        setUserType(userType);
        setFirst_name(first_name);
        setLast_name(last_name);
        setPassword(passwordUser);
        setTelephone(telephone);
        setIdOrganization(idOrganization);
        setOrgType(orgType);
        setIllness(illness);
    }

    @Override
    public String getFirst_name(){
        return first_name;
    }

    @Override
    public String getLast_name(){
        return last_name;
    }

    @Override
    public String getEmailUser(){
        return emailUser;
    }

    @Override
    public long getTelephone(){
        return telephone;
    }
    @Override
    public void setFirst_name(String first_name){
        this.first_name=first_name;
    }

    @Override
    public void setLast_name(String last_name){
        this.last_name=last_name;
    }

    @Override
    public void setEmailUser(String emailUser){
        this.emailUser=emailUser;
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

    @Override
    public String getPassword() {
        return passwordUser;
    }

    @Override
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
