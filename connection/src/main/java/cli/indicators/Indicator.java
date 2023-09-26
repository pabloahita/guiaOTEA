package cli.indicators;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


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

    public List<Evidence> evidences;

    @SerializedName("idAmbit")
    public int idAmbit;
    @SerializedName("indicatorPriority")
    public int indicatorPriority;

    @SerializedName("indicatorVersion")
    public int indicatorVersion;

    public int numFilledEvidences=0;

    public Indicator(int indicatorId, int idAmbit, String indicatorType, String descriptionEnglish, String descriptionSpanish, String descriptionFrench, String descriptionBasque, String descriptionCatalan, String descriptionDutch, String descriptionGalician, String descriptionGerman, String descriptionItalian, String descriptionPortuguese, int indicatorPriority, int indicatorVersion) {
        setIdIndicator(indicatorId);
        setIdAmbit(idAmbit);
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

    public int getIdAmbit() {
        return idAmbit;
    }

    public void setIdAmbit(int idAmbit) {
        this.idAmbit = idAmbit;
    }
}
