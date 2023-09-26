package cli.indicators;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Evidence implements Serializable {

    @SerializedName("idEvidence")
    public int idEvidence;
    @SerializedName("idIndicator")
    public int idIndicator;
    @SerializedName("indicatorType")
    public String indicatorType;
    @SerializedName("idAmbit")
    public int idAmbit;
    @SerializedName("descriptionEnglish")
    public String descriptionEnglish;
    @SerializedName("descriptionSpanish")
    public String descriptionSpanish;
    @SerializedName("descriptionFrench")
    public String descriptionFrench;
    @SerializedName("descriptionBasque")
    public String descriptionBasque;
    @SerializedName("descriptionCatalan")
    public String descriptionCatalan;
    @SerializedName("descriptionDutch")
    public String descriptionDutch;
    @SerializedName("descriptionGalician")
    public String descriptionGalician;
    @SerializedName("descriptionGerman")
    public String descriptionGerman;
    @SerializedName("descriptionItalian")
    public String descriptionItalian;
    @SerializedName("descriptionPortuguese")
    public String descriptionPortuguese;
    @SerializedName("evidenceValue")
    public int evidenceValue;
    @SerializedName("indicatorVersion")
    public int indicatorVersion;
    public Evidence(int idEvidence, int idIndicator, String indicatorType, int idAmbit, String descriptionEnglish, String descriptionSpanish, String descriptionFrench, String descriptionBasque, String descriptionCatalan, String descriptionDutch, String descriptionGalician, String descriptionGerman, String descriptionItalian, String descriptionPortuguese, int evidenceValue, int indicatorVersion){
        setIdEvidence(idEvidence);
        setIdIndicator(idIndicator);
        setIndicatorType(indicatorType);
        setIdAmbit(idAmbit);
        setDescriptionEnglish(descriptionEnglish);
        setDescriptionSpanish(descriptionSpanish);
        setDescriptionFrench(descriptionFrench);
        setDescriptionBasque(descriptionBasque);
        setDescriptionCatalan(descriptionCatalan);
        setDescriptionDutch(descriptionDutch);
        setDescriptionGalician(descriptionGalician);
        setDescriptionGerman(descriptionGerman);
        setDescriptionItalian(descriptionItalian);
        setDescriptionPortuguese(descriptionPortuguese);
        setEvidenceValue(evidenceValue);
        setIndicatorVersion(indicatorVersion);
    }

    public int getIdEvidence() {
        return idEvidence;
    }

    public void setIdEvidence(int idEvidence) {
        this.idEvidence = idEvidence;
    }

    public int getIdIndicator() {
        return idIndicator;
    }

    public void setIdIndicator(int idIndicator) {
        this.idIndicator = idIndicator;
    }

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public void setValue(int evidenceValue) {
        this.evidenceValue=evidenceValue;
    }

    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    public String getDescriptionSpanish() {
        return descriptionSpanish;
    }

    public void setDescriptionSpanish(String descriptionSpanish) {
        this.descriptionSpanish = descriptionSpanish;
    }

    public String getDescriptionFrench() {
        return descriptionFrench;
    }

    public void setDescriptionFrench(String descriptionFrench) {
        this.descriptionFrench = descriptionFrench;
    }

    public String getDescriptionBasque() {
        return descriptionBasque;
    }

    public void setDescriptionBasque(String descriptionBasque) {
        this.descriptionBasque = descriptionBasque;
    }

    public String getDescriptionCatalan() {
        return descriptionCatalan;
    }

    public void setDescriptionCatalan(String descriptionCatalan) {
        this.descriptionCatalan = descriptionCatalan;
    }

    public String getDescriptionDutch() {
        return descriptionDutch;
    }

    public void setDescriptionDutch(String descriptionDutch) {
        this.descriptionDutch = descriptionDutch;
    }

    public String getDescriptionGalician() {
        return descriptionGalician;
    }

    public void setDescriptionGalician(String descriptionGalician) {
        this.descriptionGalician = descriptionGalician;
    }

    public String getDescriptionGerman() {
        return descriptionGerman;
    }

    public void setDescriptionGerman(String descriptionGerman) {
        this.descriptionGerman = descriptionGerman;
    }

    public String getDescriptionItalian() {
        return descriptionItalian;
    }

    public void setDescriptionItalian(String descriptionItalian) {
        this.descriptionItalian = descriptionItalian;
    }

    public String getDescriptionPortuguese() {
        return descriptionPortuguese;
    }

    public void setDescriptionPortuguese(String descriptionPortuguese) {
        this.descriptionPortuguese = descriptionPortuguese;
    }

    public int getValue(){
        return evidenceValue;
    }

    public int getEvidenceValue() {
        return evidenceValue;
    }

    public void setEvidenceValue(int evidenceValue) {
        this.evidenceValue = evidenceValue;
    }

    public int getIndicatorVersion() {
        return indicatorVersion;
    }

    public void setIndicatorVersion(int indicatorVersion) {
        this.indicatorVersion = indicatorVersion;
    }

    public int getIdAmbit() {
        return idAmbit;
    }

    public void setIdAmbit(int idAmbit) {
        this.idAmbit = idAmbit;
    }
}
