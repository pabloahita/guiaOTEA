package cli.indicators;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import otea.connection.caller.Caller;


public class Indicator {

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

    public List<Evidence> evidences;

    @SerializedName("indicatorPriority")
    public int priority;

    public int numFilledEvidences=0;

    public Caller caller;
    public Indicator(int idIndicator, String indicatorType, String descriptionEnglish,String descriptionSpanish,String descriptionFrench, int priority) {
        setIdIndicator(idIndicator);
        setIndicatorType(indicatorType);
        setDescriptionEnglish(descriptionEnglish);
        setDescriptionSpanish(descriptionSpanish);
        setDescriptionFrench(descriptionFrench);
        setPriority(priority);
        setCaller(new Caller());
        setEvidences(caller.obtainEvidences(idIndicator,indicatorType));
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
            value+=e.getValue();
        }
        return value;
    }


    public void addEvidence(Evidence evidence){
        this.evidences.add(evidence);
        caller.addEvidence(evidence);
    }

    public void updateEvidence(Evidence evidence){
        this.evidences.add(evidence);
        caller.updateEvidence(evidence.getIdEvidence(),idIndicator,indicatorType,evidence);
    }

    public void deleteEvidence(Evidence evidence){
        this.evidences.add(evidence);
        caller.deleteEvidence(evidence.getIdEvidence(),evidence.getIdIndicator(),evidence.getIndicatorType());
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
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getMultiplicator(){
        if(numFilledEvidences==0||numFilledEvidences==1){return 0;}
        else if (numFilledEvidences==2||numFilledEvidences==3){
            if (this.priority==1){return 1;}
            else if (this.priority==2){return 2;}
            else if (this.priority==3){return 3;}
            else{return 4;}
        }
        else{
            if (this.priority==1){return 2;}
            else if (this.priority==2){return 3;}
            else if (this.priority==3){return 4;}
            else{return 5;}
        }
    }

    public void setNumFilledEvidences(int numFilledEvidences){this.numFilledEvidences=numFilledEvidences;}

    public int getNumFilledEvidences(){return numFilledEvidences;}

    public Caller getCaller() {
        return caller;
    }

    public void setCaller(Caller caller) {
        this.caller = caller;
    }
}
