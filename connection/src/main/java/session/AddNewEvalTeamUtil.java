package session;

import java.util.List;

import cli.organization.Organization;
import cli.organization.data.Center;
import cli.user.User;
import otea.connection.controller.CentersController;
import otea.connection.controller.UsersController;

public class AddNewEvalTeamUtil {

    List<User> evaluatedUsers;

    List<Center> centers;

    private static AddNewEvalTeamUtil instance;

    private AddNewEvalTeamUtil(){
        Organization org=Session.getInstance().getOrganization();
        setEvaluatedUsers(UsersController.GetAllOrgUsersByOrganization(org.getIdOrganization(),org.getOrgType(),org.getIllness()));
        setCenters(CentersController.GetAllByOrganization(org));
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

    public List<Center> getCenters() {
        return centers;
    }

    public void setCenters(List<Center> centers) {
        this.centers = centers;
    }
}
