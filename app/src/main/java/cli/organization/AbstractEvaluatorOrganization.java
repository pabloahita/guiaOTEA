package cli.organization;

import java.util.LinkedList;
import java.util.List;

import cli.organization.data.Address;
import cli.organization.data.EvaluatorTeam;

public abstract class AbstractEvaluatorOrganization extends AbstractOrganization implements EvaluatorOrganization{


    private List<EvaluatorTeam> evaluator_teams;

    public AbstractEvaluatorOrganization(int idOrganization, String orgType, String illness, String name, Address address, int telephone, String email, String information){
        super(idOrganization, orgType, illness, name, address, telephone, email,information);
        setEvaluatorTeams(new LinkedList<EvaluatorTeam>());
    }

    public void setEvaluatorTeams(List<EvaluatorTeam> evaluator_teams){
        this.evaluator_teams=evaluator_teams;
    }


    public List<EvaluatorTeam> getEvaluatorTeams(){
        return evaluator_teams;
    }






}
