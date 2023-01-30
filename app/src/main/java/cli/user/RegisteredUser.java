package cli.user;

import cli.organization.Organization;

public class RegisteredUser extends AbstractUser{

    private Organization organization;

    public RegisteredUser(String first_name, String last_name, String email, String password, int telephone, Organization organization) {
        super(first_name, last_name, email, password, telephone);
        setOrganization(organization);
    }

    public void setOrganization(Organization organization){
        this.organization=organization;
    }

    public Organization getOrganization(){
        return organization;
    }
}
