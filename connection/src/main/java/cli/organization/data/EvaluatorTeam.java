package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import cli.organization.EvaluatorOrganization;
import cli.user.EvaluatedOrganizationUser;
import cli.user.EvaluatorOrganizationUser;
import otea.connection.caller.OrganizationsCaller;
import otea.connection.caller.UsersCaller;


public class EvaluatorTeam {

    @SerializedName("idEvaluatorTeam")
    public int id;
    @SerializedName("creationDate")
    public Timestamp creation_date;

    @SerializedName("idOrganization")
    public int idOrganization;
    @SerializedName("orgType")
    public String orgType;
    @SerializedName("illness")
    public String illness;
    @SerializedName("patientName")
    public String patient_name="";
    @SerializedName("relativeName")
    public String relative_name="";
    @SerializedName("emailConsultant")
    public String emailConsultant;
    @SerializedName("emailResponsible")
    public String emailResponsible;
    @SerializedName("emailProfessional")
    public String emailProfessional;
    public EvaluatorOrganizationUser external_consultant;
    public EvaluatedOrganizationUser professional;
    public EvaluatedOrganizationUser direct_attendance_responsible;
    public List<EvaluatorTeamMember> members;
    public EvaluatorOrganization organization;


    public EvaluatorTeam(int id, Timestamp creation_date, int idOrganization, String orgType, String illness, String emailConsultant, String emailProfessional, String emailResponsible, String patient_name, String relative_name){
        setId(id);
        setCreationDate(creation_date);
        setIdOrganization(idOrganization);
        setEmailConsultant(emailConsultant);
        setEmailProfessional(emailProfessional);
        setEmailResponsible(emailResponsible);
        setPatient_name(patient_name);
        setRelative_name(relative_name);
        setOrganization((EvaluatorOrganization) OrganizationsCaller.getInstance().obtainOrganization(idOrganization,orgType,illness));
        setExternalConsultant((EvaluatorOrganizationUser) UsersCaller.getInstance().obtainOrgUser(emailConsultant,organization));
        setProfessional((EvaluatedOrganizationUser) UsersCaller.getInstance().obtainUser(emailProfessional));
        setDirect_attendance_responsible((EvaluatedOrganizationUser) UsersCaller.getInstance().obtainUser(emailResponsible));
        setMembers(new LinkedList<>());
    }

    public Timestamp getCreationTimestamp() {
        return creation_date;
    }

    public void setCreationDate(Timestamp creation_date) {
        this.creation_date = creation_date;
    }

    public EvaluatorOrganizationUser getExternalConsultant() {
        return external_consultant;
    }

    public void setExternalConsultant(EvaluatorOrganizationUser external_consultant) {
        this.external_consultant = external_consultant;
    }

    public List<EvaluatorTeamMember> getMembers() {
        return members;
    }

    public void setMembers(List<EvaluatorTeamMember> members) {
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

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getRelative_name() {
        return relative_name;
    }

    public void setRelative_name(String relative_name) {
        this.relative_name = relative_name;
    }

    public String getEmailConsultant() {
        return emailConsultant;
    }

    public void setEmailConsultant(String emailConsultant) {
        this.emailConsultant = emailConsultant;
    }

    public String getEmailResponsible() {
        return emailResponsible;
    }

    public void setEmailResponsible(String emailResponsible) {
        this.emailResponsible = emailResponsible;
    }

    public String getEmailProfessional() {
        return emailProfessional;
    }

    public void setEmailProfessional(String emailProfessional) {
        this.emailProfessional = emailProfessional;
    }

    public int getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
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

    public EvaluatorOrganizationUser getExternal_consultant() {
        return external_consultant;
    }

    public void setExternal_consultant(EvaluatorOrganizationUser external_consultant) {
        this.external_consultant = external_consultant;
    }

    public EvaluatedOrganizationUser getProfessional() {
        return professional;
    }

    public void setProfessional(EvaluatedOrganizationUser professional) {
        this.professional = professional;
    }

    public EvaluatedOrganizationUser getDirect_attendance_responsible() {
        return direct_attendance_responsible;
    }

    public void setDirect_attendance_responsible(EvaluatedOrganizationUser direct_attendance_responsible) {
        this.direct_attendance_responsible = direct_attendance_responsible;
    }
}
