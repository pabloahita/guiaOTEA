package cli.indicators;

import com.google.gson.annotations.SerializedName;

public class Evidence {

    @SerializedName("idEvidence")
    public int idEvidence;
    @SerializedName("idIndicator")
    public int idIndicator;
    @SerializedName("indicatorType")
    public String indicatorType;
    @SerializedName("descriptionEnglish")
    public String descriptionEnglish;
    @SerializedName("descriptionSpanish")
    public String descriptionSpanish;
    @SerializedName("descriptionFrench")
    public String descriptionFrench;
    @SerializedName("evidenceValue")
    public int value;
    public Evidence(int idEvidence, int idIndicator, String indicatorType, String descriptionEnglish,String descriptionSpanish,String descriptionFrench, int value){
        setIdEvidence(idEvidence);
        setIdIndicator(idIndicator);
        setIndicatorType(indicatorType);
        setDescriptionEnglish(descriptionEnglish);
        setDescriptionSpanish(descriptionSpanish);
        setDescriptionFrench(descriptionFrench);
        setValue(value);
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

    public void setValue(int value) {
        this.value=value;
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

    public int getValue(){
        return value;
    }

}
