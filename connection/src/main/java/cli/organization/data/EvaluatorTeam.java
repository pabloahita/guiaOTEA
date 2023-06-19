package cli.organization.data;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import cli.organization.EvaluatorOrganization;
import cli.user.EvaluatorOrganizationUser;
import otea.connection.ConnectionClient;


public class EvaluatorTeam {

    private int id;
    private Date creation_date;

    private EvaluatorOrganizationUser external_consultant;
    private List<EvaluatorOrganizationUser> members;
    private EvaluatorOrganization organization;

    private String pacient_name="";

    private String relative_name="";
    private static Connection con;
    public EvaluatorTeam(int id, Date creation_date, String emailExternalConsultant, int idOrganization, String illness, String pacient_name, String relative_name){
        setId(id);
        setCreationDate(creation_date);
        obtainExternalConsultant(emailExternalConsultant,idOrganization,illness);
        obtainEvalTeamMembers(id,idOrganization,illness);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setConnection(Connection con){EvaluatorTeam.con=con;}

    public void obtainExternalConsultant(String email, int id, String illness){
        //external_consultant= ConnectionClient.obtainEvaluatorOrganizationUser(email,id,illness);
    }

    public void obtainEvalTeamMembers(int id, int idOrg, String illness){
        //members=ConnectionClient.obtainEvalTeamMembers(id,idOrg,illness);
    }
}
