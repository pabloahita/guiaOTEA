package cli.organization.data;

import java.sql.Date;
import java.util.List;

import cli.organization.AbstractEvaluatedOrganization;
import cli.organization.AbstractEvaluatorOrganization;
import cli.user.RegisteredUser;

public class EvaluatorTeam {

    private Date creation_date;

    private RegisteredUser external_consultant;
    private List<RegisteredUser> evaluator_team;
    private AbstractEvaluatorOrganization organization;
    public EvaluatorTeam(Date creation_date, RegisteredUser external_consultant, List<RegisteredUser> evaluator_team, AbstractEvaluatorOrganization organization){
        setCreationDate(creation_date);
        setExternalConsultant(external_consultant);
        setEvaluatorTeam(evaluator_team);
        setOrganization(organization);
    }

    public Date getCreationDate() {
        return creation_date;
    }

    public void setCreationDate(Date creation_date) {
        this.creation_date = creation_date;
    }

    public RegisteredUser getExternalConsultant() {
        return external_consultant;
    }

    public void setExternalConsultant(RegisteredUser external_consultant) {
        this.external_consultant = external_consultant;
    }

    public List<RegisteredUser> getEvaluatorTeam() {
        return evaluator_team;
    }

    public void setEvaluatorTeam(List<RegisteredUser> evaluator_team) {
        this.evaluator_team = evaluator_team;
    }

    public AbstractEvaluatorOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(AbstractEvaluatorOrganization organization) {
        this.organization = organization;
    }
}
