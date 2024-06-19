package session;

import java.util.List;

import cli.indicators.IndicatorsEvaluation;
import cli.organization.Organization;
import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
import cli.user.User;
import otea.connection.controller.UsersController;

public class IndicatorsEvaluationUtil {

    private static IndicatorsEvaluationUtil instance;

    private Organization evaluatedOrganization;

    private Center center;

    private User director;

    private User responsible;

    private User professional;

    private EvaluatorTeam evaluatorTeam;

    private IndicatorsEvaluation indicatorsEvaluation;






    private IndicatorsEvaluationUtil(Organization evaluatedOrganization, Center center, EvaluatorTeam evaluatorTeam, IndicatorsEvaluation indicatorsEvaluation){
        setEvaluatedOrganization(evaluatedOrganization);
        setCenter(center);
        setEvaluatorTeam(evaluatorTeam);
        setIndicatorsEvaluation(indicatorsEvaluation);
        List<User> users=UsersController.GetOrganizationAndEvalTeamStaff(evaluatedOrganization,evaluatorTeam);
        setDirector(users.get(0));
        setResponsible(users.get(1));
        setProfessional(users.get(2));
    }

    public static synchronized IndicatorsEvaluationUtil getInstance(){
        return instance;
    }

    public static void createInstance(Organization evaluatedOrganization,Center center,EvaluatorTeam evaluatorTeam,IndicatorsEvaluation indicatorsEvaluation){
        instance=new IndicatorsEvaluationUtil(evaluatedOrganization,center,evaluatorTeam,indicatorsEvaluation);
    }

    public static void removeInstance(){
        instance=null;
    }

    public Organization getEvaluatedOrganization() {
        return evaluatedOrganization;
    }

    public void setEvaluatedOrganization(Organization evaluatedOrganization) {
        this.evaluatedOrganization = evaluatedOrganization;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public User getDirector() {
        return director;
    }

    public void setDirector(User director) {
        this.director = director;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public User getProfessional() {
        return professional;
    }

    public void setProfessional(User professional) {
        this.professional = professional;
    }

    public EvaluatorTeam getEvaluatorTeam() {
        return evaluatorTeam;
    }

    public void setEvaluatorTeam(EvaluatorTeam evaluatorTeam) {
        this.evaluatorTeam = evaluatorTeam;
    }

    public IndicatorsEvaluation getIndicatorsEvaluation() {
        return indicatorsEvaluation;
    }

    public void setIndicatorsEvaluation(IndicatorsEvaluation indicatorsEvaluation) {
        this.indicatorsEvaluation = indicatorsEvaluation;
    }


}
