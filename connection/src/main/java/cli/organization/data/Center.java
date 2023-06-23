package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import cli.organization.EvaluatedOrganization;

import otea.connection.caller.Caller;

public class Center {

    EvaluatedOrganization organization;

    Address address;

    @SerializedName("IdOrganization")
    public int IdOrganization;

    @SerializedName("orgType")
    public String orgType;

    @SerializedName("illness")
    public String illness;
    @SerializedName("idCenter")
    private int idCenter;

    @SerializedName("centerDescription")
    private String centerDescription;

    @SerializedName("idAddress")
    private int idAddress;

    @SerializedName("telephone")
    private long telephone;

    private Caller caller;

    public Center(EvaluatedOrganization organization,int idCenter, String centerDescription, Address address){
        setOrganization(organization);
        setIdCenter(idCenter);
        setCenterDescription(centerDescription);
        setAddress(address);
    }

    public Center(int idOrganization, String orgType, String illness,int idCenter, String centerDescription, int idAddress, long telephone){
        setIdOrganization(idOrganization);
        setOrgType(orgType);
        setIllness(illness);
        setIdCenter(idCenter);
        setCenterDescription(centerDescription);
        setIdAddress(idAddress);
        setTelephone(telephone);
        setCaller(new Caller());
        setOrganization((EvaluatedOrganization) caller.obtainOrganization(idOrganization,orgType,illness));
        setAddress(caller.obtainAddress(idAddress));
    }

    public EvaluatedOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(EvaluatedOrganization organization) {
        this.organization = organization;
    }

    public int getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }

    public String getCenterDescription() {
        return centerDescription;
    }

    public void setCenterDescription(String centerDescription) {
        this.centerDescription = centerDescription;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getIdOrganization() {
        return IdOrganization;
    }

    public void setIdOrganization(int idOrganization) {
        this.IdOrganization = idOrganization;
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

    public long getTelephone() {
        return telephone;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
    }

    public Caller getCaller() {
        return caller;
    }

    public void setCaller(Caller caller) {
        this.caller = caller;
    }
}
