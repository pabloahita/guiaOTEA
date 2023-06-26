package cli.organization;

import com.google.gson.annotations.SerializedName;

import cli.organization.data.Address;
import otea.connection.caller.AddressesCaller;

public class Organization implements IOrganization {

    @SerializedName("IdOrganization")
    private int IdOrganization;
    @SerializedName("orgType")
    private String orgType;
    @SerializedName("illness")
    private String illness;

    @SerializedName("nameOrg")
    private String nameOrg;

    @SerializedName("idAddress")
    private int idAddress;
    private Address address;

    @SerializedName("telephone")
    private long telephone;

    @SerializedName("email")
    private String email;

    @SerializedName("information")
    private String information;

    @SerializedName("emailOrgPrincipal")
    private String emailOrgPrincipal;

    @SerializedName("emailOrgConsultant")
    private String emailOrgConsultant;



    public Organization(int idOrganization, String orgType, String illness, String name, int idAddress, long telephone, String email, String information, String emailOrgPrincipal, String emailOrgConsultant){
        setIdOrganization(idOrganization);
        setOrgType(orgType);
        setIllness(illness);
        setName(name);
        setIdAddress(idAddress);
        setTelephone(telephone);
        setEmail(email);
        setInformation(information);
        setEmailOrgPrincipal(emailOrgPrincipal);
        setEmailOrgConsultant(emailOrgConsultant);
        setAddress(AddressesCaller.getInstance().obtainAddress(idAddress));
    }


    @Override
    public void setEmailOrgConsultant(String emailOrgPrincipal) {this.emailOrgPrincipal=emailOrgPrincipal;}

    @Override
    public void setEmailOrgPrincipal(String emailOrgConsultant) {this.emailOrgConsultant=emailOrgConsultant;}


    @Override
    public int getIdOrganization() {
        return IdOrganization;
    }

    @Override
    public void setIdOrganization(int idOrganization) {
        this.IdOrganization = idOrganization;
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
    public String getInformation() {
        return information;
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
    public void setInformation(String information) {
        this.information=information;
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
    public String getEmailOrgConsultant() {
        return emailOrgConsultant;
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
