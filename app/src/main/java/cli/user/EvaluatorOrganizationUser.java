package cli.user;

import cli.organization.Organization;

public class EvaluatorOrganizationUser extends OrganizationUser{
    public EvaluatorOrganizationUser(String first_name, String last_name, String email, String password, int telephone, Organization organization) {
        super(first_name, last_name, email, password, telephone, organization);
    }

    public EvaluatorOrganizationUser(String first_name, String last_name, String email, String password, int telephone) {
        super(first_name, last_name, email, password, telephone);
    }
}
