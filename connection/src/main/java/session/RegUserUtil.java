package session;

import cli.organization.Organization;
import cli.user.User;
import otea.connection.controller.OrganizationsController;

public class RegUserUtil {

    User user;

    Organization organization;

    private static RegUserUtil instance;

    private RegUserUtil(User user){
        setUser(user);
        setOrganization(OrganizationsController.Get(user.getIdOrganization(),user.getOrganizationType(),user.getIllness()));
    }


    public static synchronized void createInstance(User user){
        instance=new RegUserUtil(user);
    }

    public static synchronized RegUserUtil getInstance(){
        return instance;
    }

    public static synchronized void removeInstance(){
        instance=null;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
