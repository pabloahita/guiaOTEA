package cli.user;

import cli.organization.EvaluatorOrganization;

public class EvaluatorOrganizationUser extends OrganizationUser {

    public EvaluatorOrganizationUser(String first_name, String last_name, String userType, String email, String password, long telephone, int idOrganization, String orgType, String illness) {
        super(first_name, last_name, userType, email, password, telephone, idOrganization, orgType, illness);
    }

    public EvaluatorOrganizationUser(String first_name, String last_name, String userType, String email, String password, long telephone, EvaluatorOrganization organization) {
        super(first_name, last_name, userType, email, password, telephone, organization);
    }
}
