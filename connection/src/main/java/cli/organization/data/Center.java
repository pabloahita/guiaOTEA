package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Center implements Serializable {


    Address address;

    @SerializedName("IdOrganization")
    public int IdOrganization;

    @SerializedName("orgType")
    public String orgType;

    @SerializedName("illness")
    public String illness;
    @SerializedName("idCenter")
    public int idCenter;

    @SerializedName("centerDescription")
    public String centerDescription;

    @SerializedName("idAddress")
    public int idAddress;

    @SerializedName("telephone")
    public long telephone;


    public Center(int idOrganization, String orgType, String illness,int idCenter, String centerDescription, int idAddress, long telephone){
        setIdOrganization(idOrganization);
        setOrgType(orgType);
        setIllness(illness);
        setIdCenter(idCenter);
        setCenterDescription(centerDescription);
        setIdAddress(idAddress);
        setTelephone(telephone);
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

}
