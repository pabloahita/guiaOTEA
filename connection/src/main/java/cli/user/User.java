package cli.user;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class for users
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class User implements Serializable{

    /**User first name*/
    @SerializedName("first_name")
    public String first_name;

    /**User type*/
    @SerializedName("userType")
    public String userType;

    /**User last name*/
    @SerializedName("last_name")
    public String last_name;

    /**User email*/
    @SerializedName("emailUser")
    public String emailUser;

    /**User password*/
    @SerializedName("passwordUser")
    public String passwordUser;

    /**User telephone*/
    @SerializedName("telephone")
    public String telephone;

    /**User organization*/
    @SerializedName("idOrganization")
    public int idOrganization;

    /**User organization type*/
    @SerializedName("orgType")
    public String orgType;

    /**User organization illness or syndrome*/
    @SerializedName("illness")
    public String illness;

    @SerializedName("isDirector")
    public int isDirector;

    /**Profile photo*/
    @SerializedName("profilePhoto")
    public String profilePhoto;


    /**
     * Class constructor
     *
     * @param emailUser - User email
     * @param userType - User type
     * @param first_name - User first name
     * @param last_name - User last name
     * @param passwordUser - User password
     * @param telephone - User telephone
     * @param idOrganization - User organization identifier
     * @param orgType - User organization type
     * @param illness - User organization illness or syndrome
     * @param isDirector - User is the director of its organization or not
     * @param profilePhoto - Profile photo
     * */
    public User(String emailUser, String userType, String first_name, String last_name, String passwordUser, String telephone, int idOrganization, String orgType, String illness, int isDirector, String profilePhoto){
        setEmailUser(emailUser);
        setUserType(userType);
        setFirst_name(first_name);
        setLast_name(last_name);
        setPassword(passwordUser);
        setTelephone(telephone);
        setIdOrganization(idOrganization);
        setOrganizationType(orgType);
        setIllness(illness);
        setIsDirector(isDirector);
        setProfilePhoto(profilePhoto);
    }

    /**
     * Method that obtains the user first name
     *
     * @return User first name
     * */
    public String getFirst_name(){
        return first_name;
    }

    /**
     * Method that obtains the user last name
     *
     * @return User last name
     * */
    public String getLast_name(){
        return last_name;
    }

    /**
     * Method that obtains the user email
     *
     * @return User email
     * */
    public String getEmailUser(){
        return emailUser;
    }

    /**
     * Method that obtains the user telephone
     *
     * @return User telephone
     * */
    public String getTelephone(){
        return telephone;
    }

    /**
     * Method that sets the new user first name
     *
     * @param first_name - User first name
     * */
    public void setFirst_name(String first_name){
        this.first_name=first_name;
    }

    /**
     * Method that sets the new user first name
     *
     * @param last_name - User last name
     * */
    public void setLast_name(String last_name){
        this.last_name=last_name;
    }

    /**
     * Method that sets the new user email
     *
     * @param emailUser - User email
     * */
    public void setEmailUser(String emailUser){
        this.emailUser=emailUser;
    }

    /**
     * Method that sets the new user telephone
     *
     * @param telephone - User telephone
     * */
    public void setTelephone(String telephone){
        this.telephone=telephone;
    }

    /**
     * Method that obtains the user organization identifier
     *
     * @return User organization identifier
     * */
    public int getIdOrganization() {
        return idOrganization;
    }

    /**
     * Method that sets the new user organization identifier
     *
     * @param idOrganization - User organization identifier
     * */
    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
    }

    /**
     * Method that obtains the user organization type
     *
     * @return User organization type
     * */
    public String getOrganizationType() {
        return orgType;
    }

    /**
     * Method that sets the new user organization type
     *
     * @param orgType - User organization type
     * */
    public void setOrganizationType(String orgType) {
        this.orgType = orgType;
    }

    /**
     * Method that obtains the user organization illness or syndrome
     *
     * @return User organization illness or syndrome
     * */
    public String getIllness() {
        return illness;
    }

    /**
     * Method that sets the new user organization illness or syndrome
     *
     * @param illness - User organization illness or syndrome
     * */
    public void setIllness(String illness) {
        this.illness = illness;
    }

    /**
     * Method that obtains the user password
     *
     * @return User password
     * */
    public String getPassword() {
        return passwordUser;
    }

    /**
     * Method that sets the new user password
     *
     * @param passwordUser - User password
     * */
    public void setPassword(String passwordUser) {
        this.passwordUser=passwordUser;
    }

    /**
     * Method that obtains the user type
     *
     * @return User type
     * */
    public String getUserType() {
        return userType;
    }

    /**
     * Method that sets the new user type
     *
     * @param userType - User type
     * */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Method that obtains the profile photo URL
     *
     * @return Profile photo
     * */
    public String getProfilePhoto() {
        return profilePhoto;
    }

    /**
     * Method that sets the new profile photo URL
     *
     * @param profilePhoto - Profile photo
     * */
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    /**
     * Method that obtains if the user is the director of its organization or not
     *
     * @return 0 if is not director, 1 if is director
     * */
    public int getIsDirector() {
        return isDirector;
    }

    /**
     * Method that obtains if the user is the director of its organization or not
     *
     * @param isDirector - 0 if is not director, 1 if is director
     * */
    public void setIsDirector(int isDirector) {
        this.isDirector = isDirector;
    }
}
