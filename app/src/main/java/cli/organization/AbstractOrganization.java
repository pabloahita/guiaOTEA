package cli.organization;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import cli.organization.data.Address;
import cli.organization.data.Center;

public abstract class AbstractOrganization implements Organization{
    private int idOrganization;

    private String orgType;

    private String illness;
    private String name;
    private List<Center> centerList;
    private Address address;
    private int telephone;
    private String email;
    private String information;

    private static Connection con;

    public AbstractOrganization(int idOrganization, String orgType, String illness, String name, int idAddress, int telephone, String email, String information){
        setIdOrganization(idOrganization);
        setOrgType(orgType);
        setIllness(illness);
        setName(name);
        setAddress(idAddress);
        setTelephone(telephone);
        setEmail(email);
        setInformation(information);
        setCenterList(new LinkedList<>());
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

    public void setAddress(int idAddress) {

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

    @Override
    public Connection getConnection(){return con;}
    public static void setConnection(Connection con){AbstractOrganization.con=con;}

    @Override
    public List<Center> getCenterList(){return centerList;}

    @Override
    public void setCenterList(List<Center> centerList){this.centerList=centerList;}

    @Override
    public void addCenter(Center center){centerList.add(center);}
    @Override
    public void removeCenter(Center center){centerList.remove(center);}
}
