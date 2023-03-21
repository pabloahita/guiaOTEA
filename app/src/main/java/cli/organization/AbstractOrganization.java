package cli.organization;

import cli.organization.data.Address;
import connection.ConnectionToLocalDatabase;

public class AbstractOrganization implements Organization{
    private int idOrganization;

    private String orgType;

    private String illness;
    private String name;
    private Address address;
    private int telephone;
    private String email;
    private String information;

    private static ConnectionToLocalDatabase con;

    public AbstractOrganization(int idOrganization, String orgType, String illness, String name, Address address, int telephone, String email, String information){
        setIdOrganization(idOrganization);
        setOrgType(orgType);
        setIllness(illness);
        setName(name);
        setAddress(address);
        setTelephone(telephone);
        setEmail(email);
        setInformation(information);
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public int getTelephone() {
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


    public void setName(String name) {
        this.name=name;
    }

    public void setAddress(Address address) {
        this.address=address;
    }

    public void setTelephone(int telephone) {
        this.telephone=telephone;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setInformation(String information) {
        this.information=information;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public ConnectionToLocalDatabase getConnection(){return con;}
    public void setConnection(ConnectionToLocalDatabase con){AbstractOrganization.con=con;}
}
