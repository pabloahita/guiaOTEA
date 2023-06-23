package cli.organization;

import java.util.List;

import cli.indicators.Indicator;
import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;

public class AutisticEvaluatorOrganization extends EvaluatorOrganization {
    public AutisticEvaluatorOrganization(int idOrganization, String orgType, String illness, String name, int idAddress, long telephone, String email, String information) {
        super(idOrganization, orgType, illness, name, idAddress, telephone, email, information,"","");
    }



}
