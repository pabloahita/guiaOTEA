package session;

import java.util.List;

import cli.organization.Organization;
import otea.connection.controller.OrganizationsController;

public class SelectToIndicatorsEvaluationUtil {

    List<Organization> organizations;

    private static SelectToIndicatorsEvaluationUtil instance;
    private SelectToIndicatorsEvaluationUtil(){
        this.organizations=OrganizationsController.GetAllEvaluatedOrganizations();
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



}
