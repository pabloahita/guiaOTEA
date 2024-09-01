package session;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cli.organization.data.EvaluatorTeam;
import misc.ListCallback;
import otea.connection.controller.EvaluatorTeamsController;

public class SelectToEditEvaluatorTeamUtil {

    private List<EvaluatorTeam> evaluatorTeams;

    private static SelectToEditEvaluatorTeamUtil instance;

    private SelectToEditEvaluatorTeamUtil(){
        evaluatorTeams=EvaluatorTeamsController.GetAllByOrganization(Session.getInstance().getOrganization().getIdOrganization(), Session.getInstance().getOrganization().getOrgType(), Session.getInstance().getOrganization().getIllness());

    }

    public static synchronized void createInstance(){
        setInstance(new SelectToEditEvaluatorTeamUtil());
    }

    public static synchronized void removeInstance(){
        setInstance(null);
    }

    public List<EvaluatorTeam> getEvaluatorTeams() {
        return evaluatorTeams;
    }

    public void setEvaluatorTeams(List<EvaluatorTeam> evaluatorTeams) {
        this.evaluatorTeams = evaluatorTeams;
    }

    public static SelectToEditEvaluatorTeamUtil getInstance() {
        return instance;
    }

    public static void setInstance(SelectToEditEvaluatorTeamUtil instance) {
        SelectToEditEvaluatorTeamUtil.instance = instance;
    }
}
