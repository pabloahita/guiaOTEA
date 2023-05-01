package cli.organization.data;

import cli.organization.Organization;

public class Center {

    Organization organization;

    int idCenter;

    String centerDescription;

    Address address;

    public Center(Organization organization,int idCenter, String centerDescription, Address address){
        setOrganization(organization);
        setIdCenter(idCenter);
        setCenterDescription(centerDescription);
        setAddress(address);
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
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
}
