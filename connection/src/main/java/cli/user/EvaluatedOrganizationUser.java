package cli.user;

import cli.organization.EvaluatedOrganization;

public class EvaluatedOrganizationUser extends OrganizationUser {


    public EvaluatedOrganizationUser(String first_name, String last_name, String userType, String email, long telephone, EvaluatedOrganization organization) {
        super(first_name, last_name, userType, email, telephone, organization);
    }

    public EvaluatedOrganizationUser(String first_name, String last_name, String userType, String email, long telephone, int idOrganization, String organizationType, String illness) {
        super(first_name, last_name, userType, email, telephone, idOrganization, organizationType,illness);
    }
}