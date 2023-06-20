package cli.organization;

import cli.organization.data.EvaluatorTeam;

public class AutisticEvaluatorOrganization extends AbstractEvaluatorOrganization {
    public AutisticEvaluatorOrganization(int idOrganization, String orgType, String illness, String name, int idAddress, long telephone, String email, String information) {
        super(idOrganization, orgType, illness, name, idAddress, telephone, email, information);
    }

    @Override
    public void setEvaluatorTeams() {

    }

    @Override
    public void addEvaluatorTeam(EvaluatorTeam evaluatorTeam){

    }

}
