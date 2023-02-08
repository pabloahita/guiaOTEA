package cli.user;

import cli.organization.Organization;

public class OrganizationUser extends AbstractUser{

    private Organization organization;

    public OrganizationUser(String first_name, String last_name, String email, String password, int telephone, Organization organization) {
        super(first_name, last_name, email, password, telephone);
        setOrganization(organization);
    }

    public OrganizationUser(String first_name, String last_name, String email, String password, int telephone) {
        super(first_name, last_name, email, password, telephone);
    }
    public void setOrganization(Organization organization){
        this.organization=organization;
    }

    public Organization getOrganization(){
        return organization;
    }
}
