package cli.indicators;

import com.google.gson.annotations.SerializedName;

public class Ambit {

    @SerializedName("idAmbit")
    public int idAmbit;
    
    @SerializedName("descriptionEnglish")
    public String descriptionEnglish;

    @SerializedName("descriptionSpanish")
    public String descriptionSpanish;

    @SerializedName("descriptionFrench")
    public String descriptionFrench;

    public Ambit(int idAmbit, String descriptionEnglish, String descriptionSpanish, String descriptionFrench){
        setIdAmbit(idAmbit);
        setDescriptionEnglish(descriptionEnglish);
        setDescriptionSpanish(descriptionSpanish);
        setDescriptionFrench(descriptionFrench);
    }

    public int getIdAmbit() {
        return idAmbit;
    }

    public void setIdAmbit(int idAmbit) {
        this.idAmbit = idAmbit;
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
}
