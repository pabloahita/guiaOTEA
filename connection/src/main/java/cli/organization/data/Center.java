package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Center implements Serializable {


    @SerializedName("idOrganization")
    public int idOrganization;

    @SerializedName("orgType")
    public String orgType;

    @SerializedName("illness")
    public String illness;
    @SerializedName("idCenter")
    public int idCenter;

    @SerializedName("descriptionEnglish")
    public String descriptionEnglish;
    @SerializedName("descriptionSpanish")
    public String descriptionSpanish;
    @SerializedName("descriptionFrench")
    public String descriptionFrench;
    @SerializedName("descriptionBasque")
    public String descriptionBasque;
    @SerializedName("descriptionCatalan")
    public String descriptionCatalan;
    @SerializedName("descriptionDutch")
    public String descriptionDutch;
    @SerializedName("descriptionGalician")
    public String descriptionGalician;
    @SerializedName("descriptionGerman")
    public String descriptionGerman;
    @SerializedName("descriptionItalian")
    public String descriptionItalian;
    @SerializedName("descriptionPortuguese")
    public String descriptionPortuguese;

    @SerializedName("idAddress")
    public int idAddress;

    @SerializedName("telephone")
    public String telephone;

    @SerializedName("email")
    public String email;


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

    public int getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }

    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    public String getDescriptionSpanish() {
        return descriptionSpanish;
    }

    public void setDescriptionSpanish(String descriptionSpanish) {
        this.descriptionSpanish = descriptionSpanish;
    }

    public String getDescriptionFrench() {
        return descriptionFrench;
    }

    public void setDescriptionFrench(String descriptionFrench) {
        this.descriptionFrench = descriptionFrench;
    }

    public String getDescriptionBasque() {
        return descriptionBasque;
    }

    public void setDescriptionBasque(String descriptionBasque) {
        this.descriptionBasque = descriptionBasque;
    }

    public String getDescriptionCatalan() {
        return descriptionCatalan;
    }

    public void setDescriptionCatalan(String descriptionCatalan) {
        this.descriptionCatalan = descriptionCatalan;
    }

    public String getDescriptionDutch() {
        return descriptionDutch;
    }

    public void setDescriptionDutch(String descriptionDutch) {
        this.descriptionDutch = descriptionDutch;
    }

    public String getDescriptionGalician() {
        return descriptionGalician;
    }

    public void setDescriptionGalician(String descriptionGalician) {
        this.descriptionGalician = descriptionGalician;
    }

    public String getDescriptionGerman() {
        return descriptionGerman;
    }

    public void setDescriptionGerman(String descriptionGerman) {
        this.descriptionGerman = descriptionGerman;
    }

    public String getDescriptionItalian() {
        return descriptionItalian;
    }

    public void setDescriptionItalian(String descriptionItalian) {
        this.descriptionItalian = descriptionItalian;
    }

    public String getDescriptionPortuguese() {
        return descriptionPortuguese;
    }

    public void setDescriptionPortuguese(String descriptionPortuguese) {
        this.descriptionPortuguese = descriptionPortuguese;
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

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
