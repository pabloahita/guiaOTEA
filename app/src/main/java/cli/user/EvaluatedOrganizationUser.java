package cli.user;

import cli.organization.EvaluatedOrganization;
import cli.organization.Organization;

public class EvaluatedOrganizationUser extends OrganizationUser {


    public EvaluatedOrganizationUser(String first_name, String last_name, String email, String password, int telephone) {
        super(first_name, last_name, email, password, telephone);
    }

    public EvaluatedOrganizationUser(String first_name, String last_name, String email, String passwordUser, int telephone, EvaluatedOrganization organization) {
        super(first_name, last_name, email, passwordUser, telephone, organization);
    }
}