package cli.indicators;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import otea.connection.caller.EvidencesCaller;


public class Indicator implements Serializable {

    @SerializedName("indicatorId")
    public int indicatorId;

    @SerializedName("indicatorType")
    public String indicatorType;

    @SerializedName("descriptionEnglish")
    public String descriptionEnglish;
    @SerializedName("descriptionSpanish")
    public String descriptionSpanish;
    @SerializedName("descriptionFrench")
    public String descriptionFrench;

    public List<Evidence> evidences;

    @SerializedName("indicatorPriority")
    public int indicatorPriority;

    @SerializedName("indicatorVersion")
    public int indicatorVersion;

    public int numFilledEvidences=0;

    public Indicator(int indicatorId, String indicatorType, String descriptionEnglish,String descriptionSpanish,String descriptionFrench, int indicatorPriority, int indicatorVersion) {
        setIdIndicator(indicatorId);
        setIndicatorType(indicatorType);
        setDescriptionEnglish(descriptionEnglish);
        setDescriptionSpanish(descriptionSpanish);
        setDescriptionFrench(descriptionFrench);
        setPriority(indicatorPriority);
        setIndicatorVersion(indicatorVersion);
    }

    public int getIdIndicator() {
        return indicatorId;
    }

    public void setIdIndicator(int indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public void setEvidences(List<Evidence> evidences) {
        this.evidences=evidences;
    }

    public List<Evidence> getEvidences() {
        return evidences;
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

    public int getIndicatorValue(){
        int value=0;
        for(Evidence e : evidences){
            value+=e.getEvidenceValue();
        }
        return value;
    }


    public void addEvidence(Evidence evidence){
        this.evidences.add(evidence);
        EvidencesCaller.getInstance().addEvidence(evidence);
    }

    public void updateEvidence(Evidence evidence){
        this.evidences.add(evidence);
        EvidencesCaller.getInstance().updateEvidence(evidence.getIdEvidence(),evidence.getIdIndicator(),evidence.getIndicatorType(),evidence.getIndicatorVersion(),evidence);
    }

    public void deleteEvidence(Evidence evidence){
        this.evidences.add(evidence);
        EvidencesCaller.getInstance().deleteEvidence(evidence.getIdEvidence(),evidence.getIdIndicator(),evidence.getIndicatorType(),evidence.getIndicatorVersion());
    }

    public Evidence getEvidenceById(int i){
        Evidence evidence=evidences.get(0);
        int c=0;
        while(c<evidences.size() && i!=evidence.getIdEvidence()){
            evidence=evidences.get(c);
            c++;
        }
        return evidence;
    }


    public int getPriority() {
        return indicatorPriority;
    }

    public void setPriority(int indicatorPriority) {
        this.indicatorPriority = indicatorPriority;
    }

    public int getMultiplicator(){
        if(numFilledEvidences==0||numFilledEvidences==1){return 0;}
        else if (numFilledEvidences==2||numFilledEvidences==3){
            if (this.indicatorPriority==1){return 1;}
            else if (this.indicatorPriority==2){return 2;}
            else if (this.indicatorPriority==3){return 3;}
            else{return 4;}
        }
        else{
            if (this.indicatorPriority==1){return 2;}
            else if (this.indicatorPriority==2){return 3;}
            else if (this.indicatorPriority==3){return 4;}
            else{return 5;}
        }
    }

    public void setNumFilledEvidences(int numFilledEvidences){this.numFilledEvidences=numFilledEvidences;}

    public int getNumFilledEvidences(){return numFilledEvidences;}

    public int getIndicatorVersion() {
        return indicatorVersion;
    }

    public void setIndicatorVersion(int indicatorVersion) {
        this.indicatorVersion = indicatorVersion;
    }
}
