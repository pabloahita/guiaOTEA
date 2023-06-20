package cli.user;

import cli.organization.EvaluatedOrganization;

public class EvaluatedOrganizationUser extends OrganizationUser {


    public EvaluatedOrganizationUser(String first_name, String last_name, String email, String passwordUser, long telephone, EvaluatedOrganization organization) {
        super(first_name, last_name, email, passwordUser, telephone, organization);
    }

    public EvaluatedOrganizationUser(String first_name, String last_name, String email, String passwordUser, long telephone, int idOrganization, String organizationType, String illness) {
        super(first_name, last_name, email, passwordUser, telephone, idOrganization, organizationType,illness);
    }
}