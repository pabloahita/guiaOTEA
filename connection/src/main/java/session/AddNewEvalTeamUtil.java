package session;

import java.util.List;

import cli.organization.Organization;
import cli.user.User;
import otea.connection.controller.UsersController;

public class AddNewEvalTeamUtil {

    List<User> evaluatedUsers;

    private static AddNewEvalTeamUtil instance;

    private AddNewEvalTeamUtil(){
        Organization org=Session.getInstance().getOrganization();
        this.evaluatedUsers= UsersController.GetAllOrgUsersByOrganization(org.getIdOrganization(),org.getOrgType(),org.getIllness());
    }

    public static synchronized AddNewEvalTeamUtil getInstance(){
        if(instance==null){
            instance=new AddNewEvalTeamUtil();
        }
        return instance;
    }

    public static synchronized void removeInstance(){
        instance=null;
    }

    public List<User> getEvaluatedUsers() {
        return evaluatedUsers;
    }

    public void setEvaluatedUsers(List<User> evaluatedUsers) {
        this.evaluatedUsers = evaluatedUsers;
    }
}
