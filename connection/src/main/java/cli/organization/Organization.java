package cli.organization;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cli.organization.data.Address;

public class Organization implements Serializable {

    @SerializedName("idOrganization")
    public int idOrganization;

    @SerializedName("orgType")
    public String orgType;

    @SerializedName("illness")
    public String illness;

    @SerializedName("nameOrg")
    public String nameOrg;

    @SerializedName("idAddress")
    public int idAddress;

    @SerializedName("email")
    public String email;

    @SerializedName("telephone")
    public String telephone;

    @SerializedName("informationSpanish")
    public String informationSpanish;
    @SerializedName("informationEnglish")
    public String informationEnglish;
    @SerializedName("informationFrench")
    public String informationFrench;
    @SerializedName("informationBasque")
    public String informationBasque;
    @SerializedName("informationCatalan")
    public String informationCatalan;
    @SerializedName("informationDutch")
    public String informationDutch;
    @SerializedName("informationGalician")
    public String informationGalician;
    @SerializedName("informationGerman")
    public String informationGerman;
    @SerializedName("informationItalian")
    public String informationItalian;
    @SerializedName("informationPortuguese")
    public String informationPortuguese;

    @SerializedName("emailOrgPrincipal")
    public String emailOrgPrincipal;


    public Organization(int idOrganization, String orgType, String illness, String nameOrg, int idAddress, String email, String telephone, String informationSpanish, String informationEnglish,String informationFrench, String informationBasque, String informationCatalan, String informationDutch, String informationGalician, String informationGerman, String informationItalian, String informationPortuguese, String emailOrgPrincipal){
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

    
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    
    public String getName() {
        return nameOrg;
    }

    


    
    public String getTelephone() {
        return telephone;
    }

    
    public String getEmail() {
        return email;
    }


    
    public void setEmailOrgPrincipal(String emailOrgPrincipal) {
        this.emailOrgPrincipal=emailOrgPrincipal;
    }

    
    public String getInformationEnglish() {
        return informationEnglish;
    }

    
    public void setInformationEnglish(String informationEnglish) {
        this.informationEnglish = informationEnglish;
    }

    
    public String getInformationSpanish() {
        return informationSpanish;
    }

    
    public void setInformationSpanish(String informationSpanish) {
        this.informationSpanish = informationSpanish;
    }

    
    public String getInformationFrench() {
        return informationFrench;
    }

    
    public void setInformationFrench(String informationFrench) {
        this.informationFrench = informationFrench;
    }

    
    public String getInformationBasque() {
        return informationBasque;
    }

    
    public void setInformationBasque(String informationBasque) {
        this.informationBasque = informationBasque;
    }

    
    public String getInformationCatalan() {
        return informationCatalan;
    }

    
    public void setInformationCatalan(String informationCatalan) {
        this.informationCatalan = informationCatalan;
    }

    
    public String getInformationDutch() {
        return informationDutch;
    }

    
    public void setInformationDutch(String informationDutch) {
        this.informationDutch = informationDutch;
    }

    
    public String getInformationGalician() {
        return informationGalician;
    }

    
    public void setInformationGalician(String informationGalician) {
        this.informationGalician = informationGalician;
    }

    
    public String getInformationGerman() {
        return informationGerman;
    }

    
    public void setInformationGerman(String informationGerman) {
        this.informationGerman = informationGerman;
    }

    
    public String getInformationItalian() {
        return informationItalian;
    }

    
    public void setInformationItalian(String informationItalian) {
        this.informationItalian = informationItalian;
    }

    
    public String getInformationPortuguese() {
        return informationPortuguese;
    }

    
    public void setInformationPortuguese(String informationPortuguese) {
        this.informationPortuguese = informationPortuguese;
    }

    
    public void setName(String name) {
        this.nameOrg=name;
    }



    
    public int getIdAddress(){return idAddress;}
    
    public void setIdAddress(int idAddress) {this.idAddress=idAddress;}

    
    public void setTelephone(String telephone) {
        this.telephone=telephone;
    }

    
    public void setEmail(String email) {
        this.email=email;
    }


    
    public String getIllness() {
        return illness;
    }

    
    public void setIllness(String illness) {
        this.illness = illness;
    }

    
    public String getEmailOrgPrincipal() {
        return emailOrgPrincipal;
    }


    
    public String getOrgType() {
        return orgType;
    }

    
    public String getNameOrg() {
        return nameOrg;
    }

    
    public void setNameOrg(String nameOrg) {
        this.nameOrg = nameOrg;
    }


}
