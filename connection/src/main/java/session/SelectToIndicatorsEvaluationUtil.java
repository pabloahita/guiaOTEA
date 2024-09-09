package session;

import java.util.List;

import cli.organization.Organization;
import cli.organization.data.Center;
import otea.connection.controller.CentersController;
import otea.connection.controller.OrganizationsController;

public class SelectToIndicatorsEvaluationUtil {

    List<Organization> organizations;

    List<Center> centers;

    private static SelectToIndicatorsEvaluationUtil instance;
    private SelectToIndicatorsEvaluationUtil(){
        if(Session.getInstance().getUser().getUserType().equals("ADMIN")) {
            this.organizations = OrganizationsController.GetAllEvaluatedOrganizations();
        }else if(Session.getInstance().getUser().getUserType().equals("DIRECTOR")){
            this.centers= CentersController.GetAllByOrganization(Session.getInstance().getOrganization());
        }
    }

    public static synchronized SelectToIndicatorsEvaluationUtil getInstance(){
        if(instance==null){
            instance=new SelectToIndicatorsEvaluationUtil();
        }
        return instance;
    }

    public static synchronized void removeInstance(){
        instance=null;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public List<Center> getCenters() {
        return centers;
    }

    public void setCenters(List<Center> centers) {
        this.centers = centers;
    }
}
