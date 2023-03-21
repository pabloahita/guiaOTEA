package cli.organization;

import cli.organization.data.EvaluatorTeam;

public interface EvaluatorOrganization {
    void addEvaluatorTeam(EvaluatorTeam evaluatorTeam);
    //Common methods for every evaluator organization
    int getIdOrganization();
    void setEvaluatorTeams();
}
