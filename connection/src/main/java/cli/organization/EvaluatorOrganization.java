package cli.organization;

import java.util.LinkedList;
import java.util.List;

import cli.organization.data.EvaluatorTeam;

public abstract class EvaluatorOrganization extends Organization implements IEvaluatorOrganization{


    public List<EvaluatorTeam> evaluator_teams;

    public EvaluatorOrganization(int idOrganization, String orgType, String illness, String name, int idAddress, long telephone, String email, String information, String emailOrgPrincipal, String emailOrgConsultant){
        super(idOrganization, orgType, illness, name, idAddress, telephone, email,information,emailOrgPrincipal,emailOrgConsultant);
    }
    @Override
    public void setEvaluatorTeams(List<EvaluatorTeam> evaluator_teams){
        this.evaluator_teams=new LinkedList<EvaluatorTeam>();

    }

    @Override
    public List<EvaluatorTeam> getEvaluatorTeams(){
        return evaluator_teams;
    }


    @Override
    public void addEvaluatorTeam(EvaluatorTeam evaluatorTeam){

    }






}
