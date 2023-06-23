package cli.organization;

//import android.util.Log;

import java.util.List;

import cli.organization.data.EvaluatorTeam;

public class AutisticEvaluatedOrganization extends EvaluatedOrganization {


    public AutisticEvaluatedOrganization(int idOrganization, String orgType, String illness, String name, int idAddress, long telephone, String email, String information, String emailOrgPrincipal, String emailOrgConsultant) {
        super(idOrganization, orgType, illness, name, idAddress, telephone, email, information, emailOrgPrincipal, emailOrgConsultant);
    }


}
