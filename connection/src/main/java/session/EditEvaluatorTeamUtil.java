package session;

import java.util.List;

import cli.organization.Organization;
import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
import cli.user.User;
import otea.connection.controller.UsersController;

public class EditEvaluatorTeamUtil {


    EvaluatorTeam evaluatorTeam;

    List<User> evaluatedUsers;

    private static EditEvaluatorTeamUtil instance;

    private EditEvaluatorTeamUtil(EvaluatorTeam evaluatorTeam){

        setEvaluatorTeam(evaluatorTeam);
        Organization org=Session.getInstance().getOrganization();
        this.evaluatedUsers= UsersController.GetAllOrgUsersByOrganization(org.getIdOrganization(),org.getOrgType(),org.getIllness());
    }

    public static synchronized void createInstance(EvaluatorTeam evaluatorTeam){
        instance=new EditEvaluatorTeamUtil(evaluatorTeam);
    }

    public static synchronized EditEvaluatorTeamUtil getInstance(){
        return instance;
    }

    public static synchronized void removeInstance(){
        instance=null;
    }

    public EvaluatorTeam getEvaluatorTeam() {
        return evaluatorTeam;
    }

    public void setEvaluatorTeam(EvaluatorTeam evaluatorTeam) {
        this.evaluatorTeam = evaluatorTeam;
    }

    public List<User> getEvaluatedUsers() {
        return evaluatedUsers;
    }

    public void setEvaluatedUsers(List<User> evaluatedUsers) {
        this.evaluatedUsers = evaluatedUsers;
    }
}
