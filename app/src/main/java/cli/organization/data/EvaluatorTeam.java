package cli.organization.data;

import java.sql.Date;
import java.util.List;

import cli.organization.EvaluatorOrganization;
import cli.user.EvaluatorOrganizationUser;

public class EvaluatorTeam {

    private Date creation_date;

    private EvaluatorOrganizationUser external_consultant;
    private List<EvaluatorOrganizationUser> members;
    private EvaluatorOrganization organization;
    public EvaluatorTeam(Date creation_date, EvaluatorOrganizationUser external_consultant, List<EvaluatorOrganizationUser> members, EvaluatorOrganization organization){
        setCreationDate(creation_date);
        setExternalConsultant(external_consultant);
        setMembers(members);
        setOrganization(organization);
    }

    public EvaluatorTeam(Date creation_date, EvaluatorOrganizationUser external_consultant){
        setCreationDate(creation_date);
        setExternalConsultant(external_consultant);
    }

    public Date getCreationDate() {
        return creation_date;
    }

    public void setCreationDate(Date creation_date) {
        this.creation_date = creation_date;
    }

    public EvaluatorOrganizationUser getExternalConsultant() {
        return external_consultant;
    }

    public void setExternalConsultant(EvaluatorOrganizationUser external_consultant) {
        this.external_consultant = external_consultant;
    }

    public List<EvaluatorOrganizationUser> getMembers() {
        return members;
    }

    public void setMembers(List<EvaluatorOrganizationUser> members) {
        this.members = members;
    }

    public EvaluatorOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(EvaluatorOrganization organization) {
        this.organization = organization;
    }
}
