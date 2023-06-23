package cli.user;

import cli.organization.Organization;

public class OrganizationUser extends User {

    public Organization organization;


    public OrganizationUser(String first_name, String last_name, String userType, String email, String password, long telephone, int idOrganization, String orgType, String illness) {
        super(first_name, last_name, userType, email, password, telephone,idOrganization,orgType,illness);
        setOrganization(getCaller().obtainOrganization(idOrganization,orgType,illness));
    }

    public OrganizationUser(String first_name, String last_name, String userType, String email, String password, long telephone, Organization organization) {
        super(first_name, last_name, userType, email, password, telephone,organization.getIdOrganization(),organization.getOrganizationType(),organization.getIllness());
        setOrganization(organization);
    }



    public void setOrganization(Organization organization){
        this.organization=organization;
    }

    public Organization getOrganization(){
        return organization;
    }
}
