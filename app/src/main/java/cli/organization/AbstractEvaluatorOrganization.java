package cli.organization;

import java.util.LinkedList;
import java.util.List;

import cli.organization.data.Address;
import cli.organization.data.EvaluatorTeam;

public abstract class AbstractEvaluatorOrganization implements EvaluatorOrganization{

    private String name;
    private Address address;
    private int telephone;
    private String email;
    private String information;
    private List<EvaluatorTeam> evaluator_teams;

    public AbstractEvaluatorOrganization(String name, Address address, int telephone, String email, String information, List<EvaluatorTeam> evaluator_teams){
        setName(name);
        setAddress(address);
        setTelephone(telephone);
        setEmail(email);
        setInformation(information);
        setEvaluatorTeams(evaluator_teams);
    }

    public void setEvaluatorTeams(List<EvaluatorTeam> evaluator_team){
        if(evaluator_team==null){
            this.evaluator_teams=new LinkedList<EvaluatorTeam>();
        }
        else {
            this.evaluator_teams = evaluator_teams;
        }
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setAddress(Address address) {
        this.address=address;
    }

    public void setTelephone(int telephone) {
        this.telephone=telephone;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setInformation(String information) {
        this.information=information;
    }

    public List<EvaluatorTeam> getEvaluatorTeams(){
        return evaluator_teams;
    }

    public void addEvaluatorTeam(EvaluatorTeam evaluatorTeam){
        this.evaluator_teams.add(evaluatorTeam);
    }


}
