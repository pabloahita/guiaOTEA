package cli.user;

import cli.organization.Organization;
import otea.connection.caller.OrganizationsCaller;

public class OrganizationUser extends User {

    public Organization organization;


    public OrganizationUser(String first_name, String last_name, String userType, String email, long telephone, int idOrganization, String orgType, String illness) {
        super(first_name, last_name, userType, email, telephone,idOrganization,orgType,illness);
        setOrganization(OrganizationsCaller.getInstance().obtainOrganization(idOrganization,orgType,illness));
    }

    public OrganizationUser(String first_name, String last_name, String userType, String email, long telephone, Organization organization) {
        super(first_name, last_name, userType, email, telephone,organization.getIdOrganization(),organization.getOrganizationType(),organization.getIllness());
        setOrganization(organization);
    }



    public void setOrganization(Organization organization){
        this.organization=organization;
    }

    public Organization getOrganization(){
        return organization;
    }
}
