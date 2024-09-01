package session;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cli.organization.data.Center;
import misc.ListCallback;
import otea.connection.controller.CentersController;

public class SelectToEditCenterUtil {

    private List<Center> centers;

    private static SelectToEditCenterUtil instance;

    private SelectToEditCenterUtil(){
        centers=CentersController.GetAditionalCentersByOrganization(Session.getInstance().getOrganization());

    }

    public static synchronized void createInstance(){
        setInstance(new SelectToEditCenterUtil());
    }

    public static synchronized void removeInstance(){
        setInstance(null);
    }


    public List<Center> getCenters() {
        return centers;
    }

    public void setCenters(List<Center> centers) {
        this.centers = centers;
    }

    public static SelectToEditCenterUtil getInstance() {
        return instance;
    }

    public static void setInstance(SelectToEditCenterUtil instance) {
        SelectToEditCenterUtil.instance = instance;
    }
}
