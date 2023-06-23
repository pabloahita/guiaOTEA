package cli.organization;

import java.util.List;

import cli.organization.data.EvaluatorTeam;

public interface IEvaluatorOrganization {

    void setEvaluatorTeams(List<EvaluatorTeam> evaluator_teams);

    List<EvaluatorTeam> getEvaluatorTeams();

    void addEvaluatorTeam(EvaluatorTeam evaluatorTeam);
}
