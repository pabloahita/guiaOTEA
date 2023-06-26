package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import cli.user.OrganizationUser;
import otea.connection.caller.OrganizationsCaller;
import otea.connection.caller.UsersCaller;

public class EvaluatorTeamMember {

    @SerializedName("emailUser")
    private String emailUser;

    @SerializedName("idEvaluatorTeam")
    private int idEvaluatorTeam;

    @SerializedName("idEvaluatorOrganization")
    private int idEvaluatorOrganization;

    @SerializedName("orgType")
    private String orgType;
    @SerializedName("illness")
    private String illness;

    private OrganizationUser user;

    public EvaluatorTeamMember(String emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, String orgType, String illness) {
        setEmailUser(emailUser);
        setIdEvaluatorTeam(idEvaluatorTeam);
        setIdEvaluatorOrganization(idEvaluatorOrganization);
        setOrgType(orgType);
        setIllness(illness);
        setUser(UsersCaller.getInstance().obtainOrgUser(emailUser, OrganizationsCaller.getInstance().obtainOrganization(idEvaluatorOrganization,orgType,illness)));
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public int getIdEvaluatorTeam() {
        return idEvaluatorTeam;
    }

    public void setIdEvaluatorTeam(int idEvaluatorTeam) {
        this.idEvaluatorTeam = idEvaluatorTeam;
    }

    public int getIdEvaluatorOrganization() {
        return idEvaluatorOrganization;
    }

    public void setIdEvaluatorOrganization(int idEvaluatorOrganization) {
        this.idEvaluatorOrganization = idEvaluatorOrganization;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public OrganizationUser getUser() {
        return user;
    }

    public void setUser(OrganizationUser user) {
        this.user = user;
    }

}
