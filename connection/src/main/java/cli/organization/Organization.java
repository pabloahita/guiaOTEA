package cli.organization;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cli.organization.data.Address;

/**
 * Model class for the organizations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 *
 * */
public class Organization implements Serializable {

    /**Organization identifier*/
    @SerializedName("idOrganization")
    public int idOrganization;

    /**Organization type*/
    @SerializedName("orgType")
    public String orgType;

    /**Organization illness or syndrome*/
    @SerializedName("illness")
    public String illness;

    /**Organization name*/
    @SerializedName("nameOrg")
    public String nameOrg;

    /**Address identifier*/
    @SerializedName("idAddress")
    public int idAddress;

    /**Organization email*/
    @SerializedName("email")
    public String email;

    /**Organization telephone*/
    @SerializedName("telephone")
    public String telephone;

    /**Organization information in Spanish*/
    @SerializedName("informationSpanish")
    public String informationSpanish;

    /**Organization information in English*/
    @SerializedName("informationEnglish")
    public String informationEnglish;

    /**Organization information in French*/
    @SerializedName("informationFrench")
    public String informationFrench;

    /**Organization information in Basque*/
    @SerializedName("informationBasque")
    public String informationBasque;

    /**Organization information in Catalan*/
    @SerializedName("informationCatalan")
    public String informationCatalan;

    /**Organization information in Dutch*/
    @SerializedName("informationDutch")
    public String informationDutch;

    /**Organization information in Galician*/
    @SerializedName("informationGalician")
    public String informationGalician;

    /**Organization information in German*/
    @SerializedName("informationGerman")
    public String informationGerman;

    /**Organization information in Italian*/
    @SerializedName("informationItalian")
    public String informationItalian;

    /**Organization information in Portuguese*/
    @SerializedName("informationPortuguese")
    public String informationPortuguese;

    /**Organization principal email*/
    @SerializedName("emailOrgPrincipal")
    public String emailOrgPrincipal;

    /**Profile photo*/
    @SerializedName("profilePhoto")
    public String profilePhoto;

    /**
     * Class constructor
     *
     * @param idOrganization - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @param nameOrg - Organization name
     * @param idAddress - Address identifier
     * @param email - Organization email
     * @param telephone - Organization telephone
     * @param informationSpanish - Organization information in Spanish
     * @param informationEnglish - Organization information in English
     * @param informationFrench - Organization information in French
     * @param informationBasque - Organization information in Basque
     * @param informationCatalan - Organization information in Catalan
     * @param informationDutch - Organization information in Dutch
     * @param informationGalician - Organization information in Galician
     * @param informationGerman - Organization information in German
     * @param informationItalian - Organization information in Italian
     * @param informationPortuguese - Organization information in Portuguese
     * @param emailOrgPrincipal - Organization principal email
     * @param profilePhoto - Profile photo
     * */
    public Organization(int idOrganization, String orgType, String illness, String nameOrg, int idAddress, String email, String telephone, String informationSpanish, String informationEnglish,String informationFrench, String informationBasque, String informationCatalan, String informationDutch, String informationGalician, String informationGerman, String informationItalian, String informationPortuguese, String emailOrgPrincipal, String profilePhoto){
        setIdOrganization(idOrganization);
        setOrgType(orgType);
        setIllness(illness);
        setNameOrg(nameOrg);
        setIdAddress(idAddress);
        setTelephone(telephone);
        setEmail(email);
        setInformationEnglish(informationEnglish);
        setInformationSpanish(informationSpanish);
        setInformationFrench(informationFrench);
        setInformationBasque(informationBasque);
        setInformationCatalan(informationCatalan);
        setInformationDutch(informationDutch);
        setInformationGalician(informationGalician);
        setInformationGerman(informationGerman);
        setInformationItalian(informationItalian);
        setInformationPortuguese(informationPortuguese);
        setEmailOrgPrincipal(emailOrgPrincipal);
        setProfilePhoto(profilePhoto);
    }



    /**
     * Method that obtains the organization identifier
     *
     * @return Organization identifier
     * */
    public int getIdOrganization() {
        return idOrganization;
    }

    /**
     * Method that sets the new organization identifier
     *
     * @param idOrganization - Organization identifier
     * */
    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
    }

    /**
     * Method that obtains the organization type
     *
     * @return Organization type
     * */
    public String getOrganizationType() {
        return orgType;
    }

    /**
     * Method that sets the new organization type
     *
     * @param orgType - Organization type
     * */
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }


    /**
     * Method that obtains the organization telephone
     *
     * @return Organization telephone
     * */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Method that obtains the organization email
     *
     * @return Organization email
     * */
    public String getEmail() {
        return email;
    }


    /**
     * Method that sets the new organization principal email
     *
     * @param emailOrgPrincipal - Organization principal email
     * */
    public void setEmailOrgPrincipal(String emailOrgPrincipal) {
        this.emailOrgPrincipal=emailOrgPrincipal;
    }

    /**
     * Method that obtains the organization information in English
     *
     * @return Organization information in English
     * */
    public String getInformationEnglish() {
        return informationEnglish;
    }

    /**
     * Method that sets the new organization information in English
     *
     * @param informationEnglish - Organization information in English
     * */
    public void setInformationEnglish(String informationEnglish) {
        this.informationEnglish = informationEnglish;
    }

    /**
     * Method that obtains the organization information in English
     *
     * @return Organization information in English
     * */
    public String getInformationSpanish() {
        return informationSpanish;
    }

    /**
     * Method that sets the new organization information in Spanish
     *
     * @param informationSpanish - Organization information in Spanish
     * */
    public void setInformationSpanish(String informationSpanish) {
        this.informationSpanish = informationSpanish;
    }

    /**
     * Method that obtains the organization information in French
     *
     * @return Organization information in French
     * */
    public String getInformationFrench() {
        return informationFrench;
    }

    /**
     * Method that sets the new organization information in French
     *
     * @param informationFrench - Organization information in French
     * */
    public void setInformationFrench(String informationFrench) {
        this.informationFrench = informationFrench;
    }

    /**
     * Method that obtains the organization information in Basque
     *
     * @return Organization information in Basque
     * */
    public String getInformationBasque() {
        return informationBasque;
    }

    /**
     * Method that sets the new organization information in Basque
     *
     * @param informationBasque - Organization information in Basque
     * */
    public void setInformationBasque(String informationBasque) {
        this.informationBasque = informationBasque;
    }

    /**
     * Method that obtains the organization information in Catalan
     *
     * @return Organization information in Catalan
     * */
    public String getInformationCatalan() {
        return informationCatalan;
    }

    /**
     * Method that sets the new organization information in Catalan
     *
     * @param informationCatalan - Organization information in Catalan
     * */
    public void setInformationCatalan(String informationCatalan) {
        this.informationCatalan = informationCatalan;
    }

    /**
     * Method that obtains the organization information in Dutch
     *
     * @return Organization information in Dutch
     * */
    public String getInformationDutch() {
        return informationDutch;
    }

    /**
     * Method that sets the new organization information in Dutch
     *
     * @param informationDutch - Organization information in Dutch
     * */
    public void setInformationDutch(String informationDutch) {
        this.informationDutch = informationDutch;
    }

    /**
     * Method that obtains the organization information in Galician
     *
     * @return Organization information in Galician
     * */
    public String getInformationGalician() {
        return informationGalician;
    }

    /**
     * Method that sets the new organization information in Galician
     *
     * @param informationGalician - Organization information in Galician
     * */
    public void setInformationGalician(String informationGalician) {
        this.informationGalician = informationGalician;
    }

    /**
     * Method that obtains the organization information in German
     *
     * @return Organization information in German
     * */
    public String getInformationGerman() {
        return informationGerman;
    }

    /**
     * Method that sets the new organization information in German
     *
     * @param informationGerman - Organization information in German
     * */
    public void setInformationGerman(String informationGerman) {
        this.informationGerman = informationGerman;
    }

    /**
     * Method that obtains the organization information in Italian
     *
     * @return Organization information in Italian
     * */
    public String getInformationItalian() {
        return informationItalian;
    }

    /**
     * Method that sets the new organization information in Italian
     *
     * @param informationItalian - Organization information in Italian
     * */
    public void setInformationItalian(String informationItalian) {
        this.informationItalian = informationItalian;
    }

    /**
     * Method that obtains the organization information in Portuguese
     *
     * @return Organization information in Portuguese
     * */
    public String getInformationPortuguese() {
        return informationPortuguese;
    }

    /**
     * Method that sets the new organization information in Portuguese
     *
     * @param informationPortuguese - Organization information in Portuguese
     * */
    public void setInformationPortuguese(String informationPortuguese) {
        this.informationPortuguese = informationPortuguese;
    }


    /**
     * Method that sets the new organization name
     *
     * @param name - Organization name
     * */
    public void setName(String name) {
        this.nameOrg=name;
    }

    /**
     * Method that obtains the address identifier
     *
     * @return Address identifier
     * */
    public int getIdAddress(){return idAddress;}

    /**
     * Method that sets the new address identifier
     *
     * @param idAddress - Address identifier
     * */
    public void setIdAddress(int idAddress) {this.idAddress=idAddress;}

    /**
     * Method that sets the new organization telephone
     *
     * @param telephone - Organization telephone
     * */
    public void setTelephone(String telephone) {
        this.telephone=telephone;
    }

    /**
     * Method that sets the new organization email
     *
     * @param email - Organization email
     * */
    public void setEmail(String email) {
        this.email=email;
    }

    /**
     * Method that obtains the organization illness or syndrome
     *
     * @return Organization illness or syndrome
     * */
    public String getIllness() {
        return illness;
    }

    /**
     * Method that sets the new organization illness or syndrome
     *
     * @param illness - Organization illness or syndrome
     * */
    public void setIllness(String illness) {
        this.illness = illness;
    }

    /**
     * Method that obtains the organization principal email
     *
     * @return Organization principal email
     * */
    public String getEmailOrgPrincipal() {
        return emailOrgPrincipal;
    }

    /**
     * Method that obtains the organization type
     *
     * @return Organization type
     * */
    public String getOrgType() {
        return orgType;
    }

    /**
     * Method that obtains the organization name
     *
     * @return Organization name
     * */
    public String getNameOrg() {
        return nameOrg;
    }

    /**
     * Method that sets the new organization name
     *
     * @param nameOrg - Organization name
     * */
    public void setNameOrg(String nameOrg) {
        this.nameOrg = nameOrg;
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

}
