package cli.organization;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cli.organization.data.Address;

public class Organization implements IOrganization, Serializable {

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

    @SerializedName("telephone")
    public long telephone;

    @SerializedName("email")
    public String email;

    @SerializedName("informationEnglish")
    public String informationEnglish;
    @SerializedName("informationSpanish")
    public String informationSpanish;
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


    private Address address;
    public Organization(int idOrganization, String orgType, String illness, String name, int idAddress, long telephone, String email, String informationEnglish, String informationSpanish, String informationFrench, String informationBasque, String informationCatalan, String informationDutch, String informationGalician, String informationGerman, String informationItalian, String informationPortuguese, String emailOrgPrincipal){
        setIdOrganization(idOrganization);
        setOrgType(orgType);
        setIllness(illness);
        setName(name);
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



    @Override
    public int getIdOrganization() {
        return idOrganization;
    }

    @Override
    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
    }

    @Override
    public String getOrganizationType() {
        return orgType;
    }

    @Override
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Override
    public String getName() {
        return nameOrg;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public long getTelephone() {
        return telephone;
    }

    @Override
    public String getEmail() {
        return email;
    }


    @Override
    public void setEmailOrgPrincipal(String emailOrgPrincipal) {
        this.emailOrgPrincipal=emailOrgPrincipal;
    }

    @Override
    public String getInformationEnglish() {
        return informationEnglish;
    }

    @Override
    public void setInformationEnglish(String informationEnglish) {
        this.informationEnglish = informationEnglish;
    }

    @Override
    public String getInformationSpanish() {
        return informationSpanish;
    }

    @Override
    public void setInformationSpanish(String informationSpanish) {
        this.informationSpanish = informationSpanish;
    }

    @Override
    public String getInformationFrench() {
        return informationFrench;
    }

    @Override
    public void setInformationFrench(String informationFrench) {
        this.informationFrench = informationFrench;
    }

    @Override
    public String getInformationBasque() {
        return informationBasque;
    }

    @Override
    public void setInformationBasque(String informationBasque) {
        this.informationBasque = informationBasque;
    }

    @Override
    public String getInformationCatalan() {
        return informationCatalan;
    }

    @Override
    public void setInformationCatalan(String informationCatalan) {
        this.informationCatalan = informationCatalan;
    }

    @Override
    public String getInformationDutch() {
        return informationDutch;
    }

    @Override
    public void setInformationDutch(String informationDutch) {
        this.informationDutch = informationDutch;
    }

    @Override
    public String getInformationGalician() {
        return informationGalician;
    }

    @Override
    public void setInformationGalician(String informationGalician) {
        this.informationGalician = informationGalician;
    }

    @Override
    public String getInformationGerman() {
        return informationGerman;
    }

    @Override
    public void setInformationGerman(String informationGerman) {
        this.informationGerman = informationGerman;
    }

    @Override
    public String getInformationItalian() {
        return informationItalian;
    }

    @Override
    public void setInformationItalian(String informationItalian) {
        this.informationItalian = informationItalian;
    }

    @Override
    public String getInformationPortuguese() {
        return informationPortuguese;
    }

    @Override
    public void setInformationPortuguese(String informationPortuguese) {
        this.informationPortuguese = informationPortuguese;
    }

    @Override
    public void setName(String name) {
        this.nameOrg=name;
    }

    @Override
    public void setAddress(Address address) {this.address=address;}

    @Override
    public int getIdAddress(){return idAddress;}
    @Override
    public void setIdAddress(int idAddress) {this.idAddress=idAddress;}

    @Override
    public void setTelephone(long telephone) {
        this.telephone=telephone;
    }

    @Override
    public void setEmail(String email) {
        this.email=email;
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
    public String getEmailOrgPrincipal() {
        return emailOrgPrincipal;
    }


    @Override
    public String getOrgType() {
        return orgType;
    }

    @Override
    public String getNameOrg() {
        return nameOrg;
    }

    @Override
    public void setNameOrg(String nameOrg) {
        this.nameOrg = nameOrg;
    }


}
