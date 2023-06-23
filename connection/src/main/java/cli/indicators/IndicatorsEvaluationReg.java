package cli.indicators;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class IndicatorsEvaluationReg {

    @SerializedName("evaluationDate")
    public Date evaluationDate;
    @SerializedName("idEvaluatedOrganization")
    public int idEvaluatedOrganization;
    @SerializedName("orgTypeEvaluated")
    public String orgTypeEvaluated;
    @SerializedName("illness")
    public String illness;
    @SerializedName("evaluationDate")
    public int indicatorId;
    @SerializedName("evaluationDate")
    public int idEvidence;
    @SerializedName("evaluationDate")
    public int isMarked;

    public IndicatorsEvaluationReg(Date evaluationDate, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int indicatorId, int idEvidence, int isMarked) {
        setEvaluationDate(evaluationDate);
        setIdEvaluatedOrganization(idEvaluatedOrganization);
        setOrgTypeEvaluated(orgTypeEvaluated);
        setIllness(illness);
        setIndicatorId(indicatorId);
        setIdEvidence(idEvidence);
        setIsMarked(isMarked);
    }

    public Date getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public int getIdEvaluatedOrganization() {
        return idEvaluatedOrganization;
    }

    public void setIdEvaluatedOrganization(int idEvaluatedOrganization) {
        this.idEvaluatedOrganization = idEvaluatedOrganization;
    }

    public String getOrgTypeEvaluated() {
        return orgTypeEvaluated;
    }

    public void setOrgTypeEvaluated(String orgTypeEvaluated) {
        this.orgTypeEvaluated = orgTypeEvaluated;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public int getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(int indicatorId) {
        this.indicatorId = indicatorId;
    }

    public int getIdEvidence() {
        return idEvidence;
    }

    public void setIdEvidence(int idEvidence) {
        this.idEvidence = idEvidence;
    }

    public int getIsMarked() {
        return isMarked;
    }

    public void setIsMarked(int isMarked) {
        this.isMarked = isMarked;
    }
}
