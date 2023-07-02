package cli.indicators;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;

public class IndicatorsEvaluationReg implements Serializable {

    @SerializedName("evaluationDate")
    public Timestamp evaluationDate;
    @SerializedName("idEvaluatedOrganization")
    public int idEvaluatedOrganization;
    @SerializedName("orgTypeEvaluated")
    public String orgTypeEvaluated;

    @SerializedName("illness")
    public String illness;
    @SerializedName("indicatorId")
    public int indicatorId;
    @SerializedName("idEvidence")
    public int idEvidence;
    @SerializedName("isMarked")
    public int isMarked;

    @SerializedName("indicatorVersion")
    public int indicatorVersion;

    public IndicatorsEvaluationReg(Timestamp evaluationDate, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int indicatorId, int idEvidence, int isMarked, int indicatorVersion) {
        setEvaluationDate(evaluationDate);
        setIdEvaluatedOrganization(idEvaluatedOrganization);
        setOrgTypeEvaluated(orgTypeEvaluated);
        setIllness(illness);
        setIndicatorId(indicatorId);
        setIdEvidence(idEvidence);
        setIsMarked(isMarked);
        setIndicatorVersion(indicatorVersion);
    }

    public Timestamp getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Timestamp evaluationDate) {
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

    public int getIndicatorVersion() {
        return indicatorVersion;
    }

    public void setIndicatorVersion(int indicatorVersion) {
        this.indicatorVersion = indicatorVersion;
    }
}
