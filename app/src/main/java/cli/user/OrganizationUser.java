package cli.user;

import cli.ConnectionClient;
import cli.organization.EvaluatedOrganization;
import cli.organization.Organization;

public class OrganizationUser extends AbstractUser{

    private Organization organization;


    public OrganizationUser(String first_name, String last_name, String email, String password, int telephone, int idOrganization, String orgType, String illness) {
        super(first_name, last_name, email, password, telephone);
        obtainOrganization(idOrganization,orgType,illness);
    }

    public OrganizationUser(String first_name, String last_name, String email, String password, int telephone, EvaluatedOrganization organization) {
        super(first_name, last_name, email, password, telephone);
        setOrganization(organization);
    }

    private void obtainOrganization(int idOrganization, String orgType, String illness) {
        organization= ConnectionClient.obtainOrganization(idOrganization,orgType,illness);
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
