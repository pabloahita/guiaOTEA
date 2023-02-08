package cli.organization;

import cli.organization.data.Address;

public class AbstractOrganization implements Organization{
    private String name;
    private Address address;
    private int telephone;
    private String email;
    private String information;

    public AbstractOrganization(String name, Address address, int telephone, String email, String information){
        setName(name);
        setAddress(address);
        setTelephone(telephone);
        setEmail(email);
        setInformation(information);

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
}
