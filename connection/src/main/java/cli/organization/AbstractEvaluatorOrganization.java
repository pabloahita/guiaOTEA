package cli.organization;

import java.util.LinkedList;
import java.util.List;

import cli.organization.data.EvaluatorTeam;

public abstract class AbstractEvaluatorOrganization extends AbstractOrganization implements EvaluatorOrganization {


    private List<EvaluatorTeam> evaluator_teams;

    public AbstractEvaluatorOrganization(int idOrganization, String orgType, String illness, String name, int idAddress, long telephone, String email, String information){
        super(idOrganization, orgType, illness, name, idAddress, telephone, email,information,"","");
    }

    public void setEvaluatorTeams(List<EvaluatorTeam> evaluator_teams){
        this.evaluator_teams=new LinkedList<EvaluatorTeam>();

    }


    @Override
    public int getIdOrganization(){
        return super.getIdOrganization();
    }

    public List<EvaluatorTeam> getEvaluatorTeams(){
        return evaluator_teams;
    }






}
