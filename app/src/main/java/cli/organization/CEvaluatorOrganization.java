package cli.organization;

import java.util.LinkedList;
import java.util.List;

import cli.organization.data.Address;
import cli.organization.data.EvaluatorTeam;

public class CEvaluatorOrganization extends AbstractOrganization implements EvaluatorOrganization{


    private List<EvaluatorTeam> evaluator_teams;

    public CEvaluatorOrganization(String name, Address address, int telephone, String email, String information, List<EvaluatorTeam> evaluator_teams){
        super(name,address,telephone,email,information);
        setEvaluatorTeams(evaluator_teams);
    }

    public CEvaluatorOrganization(String name, Address address, int telephone, String email, String information){
        super(name,address,telephone,email,information);
        this.evaluator_teams=new LinkedList<EvaluatorTeam>();
    }

    public void setEvaluatorTeams(List<EvaluatorTeam> evaluator_team){
        if(evaluator_team==null){
            this.evaluator_teams=new LinkedList<EvaluatorTeam>();
        }
        else {
            this.evaluator_teams = evaluator_teams;
        }
    }



    public List<EvaluatorTeam> getEvaluatorTeams(){
        return evaluator_teams;
    }

    @Override
    public void addEvaluatorTeam(EvaluatorTeam evaluatorTeam){
        this.evaluator_teams.add(evaluatorTeam);
    }


}
