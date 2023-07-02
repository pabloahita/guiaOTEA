package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EvaluatorTeamMember implements Serializable {

    @SerializedName("emailUser")
    public String emailUser;

    @SerializedName("idEvaluatorTeam")
    public int idEvaluatorTeam;

    @SerializedName("idEvaluatorOrganization")
    public int idEvaluatorOrganization;

    @SerializedName("orgType")
    public String orgType;
    @SerializedName("illness")
    public String illness;


    public EvaluatorTeamMember(String emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, String orgType, String illness) {
        setEmailUser(emailUser);
        setIdEvaluatorTeam(idEvaluatorTeam);
        setIdEvaluatorOrganization(idEvaluatorOrganization);
        setOrgType(orgType);
        setIllness(illness);
        //setUser(UsersCaller.getInstance().obtainOrgUser(emailUser, OrganizationsCaller.getInstance().obtainOrganization(idEvaluatorOrganization,orgType,illness)));
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



}
