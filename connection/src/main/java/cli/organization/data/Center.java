package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class for the external organizations centers
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 *
 * */
public class Center implements Serializable {

    /**Organization identifier*/
    @SerializedName("idOrganization")
    public int idOrganization;

    /**Organization type*/
    @SerializedName("orgType")
    public String orgType;

    /**Organization illness or syndrome*/
    @SerializedName("illness")
    public String illness;

    /**Center identifier*/
    @SerializedName("idCenter")
    public int idCenter;

    /**Center description in English*/
    @SerializedName("descriptionEnglish")
    public String descriptionEnglish;

    /**Center description in Spanish*/
    @SerializedName("descriptionSpanish")
    public String descriptionSpanish;

    /**Center description in French*/
    @SerializedName("descriptionFrench")
    public String descriptionFrench;

    /**Center description in Basque*/
    @SerializedName("descriptionBasque")
    public String descriptionBasque;

    /**Center description in Catalan*/
    @SerializedName("descriptionCatalan")
    public String descriptionCatalan;

    /**Center description in Dutch*/
    @SerializedName("descriptionDutch")
    public String descriptionDutch;

    /**Center description in Galician*/
    @SerializedName("descriptionGalician")
    public String descriptionGalician;

    /**Center description in German*/
    @SerializedName("descriptionGerman")
    public String descriptionGerman;

    /**Center description in Italian*/
    @SerializedName("descriptionItalian")
    public String descriptionItalian;

    /**Center description in Portuguese*/
    @SerializedName("descriptionPortuguese")
    public String descriptionPortuguese;

    /**Address identifier*/
    @SerializedName("idAddress")
    public int idAddress;

    /**Center telephone*/
    @SerializedName("telephone")
    public String telephone;

    /**Center email*/
    @SerializedName("email")
    public String email;

    /**
     * Class constructor
     *
     * @param idOrganization - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @param idCenter - Center identifier
     * @param descriptionEnglish - Center description in English
     * @param descriptionSpanish - Center description in Spanish
     * @param descriptionFrench - Center description in French
     * @param descriptionBasque - Center description in Basque
     * @param descriptionCatalan - Center description in Catalan
     * @param descriptionDutch - Center description in Dutch
     * @param descriptionGalician - Center description in Galician
     * @param descriptionGerman - Center description in German
     * @param descriptionItalian - Center description in Italian
     * @param descriptionPortuguese - Center description in Portuguese
     * @param idAddress - Address identifier
     * @param telephone - Center telephone
     * @param email - Center email
     * */
    public Center(int idOrganization, String orgType, String illness,int idCenter, String descriptionEnglish, String descriptionSpanish, String descriptionFrench, String descriptionBasque, String descriptionCatalan, String descriptionDutch, String descriptionGalician, String descriptionGerman, String descriptionItalian, String descriptionPortuguese, int idAddress, String telephone, String email){
        setIdOrganization(idOrganization);
        setOrgType(orgType);
        setIllness(illness);
        setIdCenter(idCenter);
        setDescriptionEnglish(descriptionEnglish);
        setDescriptionSpanish(descriptionSpanish);
        setDescriptionFrench(descriptionFrench);
        setDescriptionBasque(descriptionBasque);
        setDescriptionCatalan(descriptionCatalan);
        setDescriptionDutch(descriptionDutch);
        setDescriptionGalician(descriptionGalician);
        setDescriptionGerman(descriptionGerman);
        setDescriptionItalian(descriptionItalian);
        setDescriptionPortuguese(descriptionPortuguese);
        setIdAddress(idAddress);
        setTelephone(telephone);
        setEmail(email);
    }

    /**
     * Method that obtains the center identifier
     *
     * @return Center identifier
     * */
    public int getIdCenter() {
        return idCenter;
    }


    /**
     * Method that sets the new center identifier
     *
     * @param idCenter - Center identifier
     * */
    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }

    /**
     * Method that obtains the center description in English
     *
     * @return Center description in English
     * */
    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    /**
     * Method that sets the new description in English
     *
     * @param descriptionEnglish - Center description in English
     * */

    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    /**
     * Method that obtains the center description in Spanish
     *
     * @return Center description in Spanish
     * */
    public String getDescriptionSpanish() {
        return descriptionSpanish;
    }

    /**
     * Method that sets the new description in Spanish
     *
     * @param descriptionSpanish - Center description in English
     * */
    public void setDescriptionSpanish(String descriptionSpanish) {
        this.descriptionSpanish = descriptionSpanish;
    }

    /**
     * Method that obtains the center description in French
     *
     * @return Center description in French
     * */
    public String getDescriptionFrench() {
        return descriptionFrench;
    }

    /**
     * Method that sets the new description in French
     *
     * @param descriptionFrench - Center description in French
     * */
    public void setDescriptionFrench(String descriptionFrench) {
        this.descriptionFrench = descriptionFrench;
    }

    /**
     * Method that obtains the center description in Basque
     *
     * @return Center description in Basque
     * */
    public String getDescriptionBasque() {
        return descriptionBasque;
    }

    /**
     * Method that sets the new description in Basque
     *
     * @param descriptionBasque - Center description in English
     * */
    public void setDescriptionBasque(String descriptionBasque) {
        this.descriptionBasque = descriptionBasque;
    }

    /**
     * Method that obtains the center description in Catalan
     *
     * @return Center description in Catalan
     * */
    public String getDescriptionCatalan() {
        return descriptionCatalan;
    }

    /**
     * Method that sets the new description in Catalan
     *
     * @param descriptionCatalan - Center description in Catalan
     * */
    public void setDescriptionCatalan(String descriptionCatalan) {
        this.descriptionCatalan = descriptionCatalan;
    }

    /**
     * Method that obtains the center description in Dutch
     *
     * @return Center description in Dutch
     * */
    public String getDescriptionDutch() {
        return descriptionDutch;
    }

    /**
     * Method that sets the new description in Dutch
     *
     * @param descriptionDutch - Center description in Dutch
     * */
    public void setDescriptionDutch(String descriptionDutch) {
        this.descriptionDutch = descriptionDutch;
    }

    /**
     * Method that obtains the center description in Galician
     *
     * @return Center description in Galician
     * */
    public String getDescriptionGalician() {
        return descriptionGalician;
    }

    /**
     * Method that sets the new description in Galician
     *
     * @param descriptionGalician - Center description in Galician
     * */
    public void setDescriptionGalician(String descriptionGalician) {
        this.descriptionGalician = descriptionGalician;
    }

    /**
     * Method that obtains the center description in German
     *
     * @return Center description in German
     * */
    public String getDescriptionGerman() {
        return descriptionGerman;
    }

    /**
     * Method that sets the new description in German
     *
     * @param descriptionGerman - Center description in German
     * */
    public void setDescriptionGerman(String descriptionGerman) {
        this.descriptionGerman = descriptionGerman;
    }

    /**
     * Method that obtains the center description in Italian
     *
     * @return Center description in Italian
     * */
    public String getDescriptionItalian() {
        return descriptionItalian;
    }

    /**
     * Method that sets the new description in Italian
     *
     * @param descriptionItalian - Center description in Italian
     * */
    public void setDescriptionItalian(String descriptionItalian) {
        this.descriptionItalian = descriptionItalian;
    }

    /**
     * Method that obtains the center description in Portuguese
     *
     * @return Center description in Italian
     * */
    public String getDescriptionPortuguese() {
        return descriptionPortuguese;
    }

    /**
     * Method that sets the new description in Portuguese
     *
     * @param descriptionPortuguese - Center description in Portuguese
     * */
    public void setDescriptionPortuguese(String descriptionPortuguese) {
        this.descriptionPortuguese = descriptionPortuguese;
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
    public String getOrgType() {
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
     * Method that obtains the organization identifier
     *
     * @return Organization identifier
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
     * Method that obtains the address identifier
     *
     * @return Address identifier
     * */
    public int getIdAddress() {
        return idAddress;
    }

    /**
     * Method that sets the new address identifier
     *
     * @param idAddress - Address identifier
     * */
    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    /**
     * Method that obtains the center telephone
     *
     * @return Center telephone
     * */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Method that sets the new center telephone
     *
     * @param telephone - Center telephone
     * */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Method that obtains the center email
     *
     * @return Center email
     * */
    public String getEmail() {
        return email;
    }


    /**
     * Method that sets the new center email
     *
     * @param email - Center email
     * */
    public void setEmail(String email) {
        this.email = email;
    }
}
