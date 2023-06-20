package cli.organization.data.geo;

import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("nameSpanish")
    public String nameSpanish;

    @SerializedName("nameEnglish")
    public String nameEnglish;

    @SerializedName("nameFrench")
    public String nameFrench;

    @SerializedName("idCountry")
    public String idCountry;

    public Country(String idCountry, String nameSpanish, String nameEnglish, String nameFrench){
        setIdCountry(idCountry);
        setNameSpanish(nameSpanish);
        setNameEnglish(nameSpanish);
        setNameFrench(nameSpanish);
    }

    public String getNameSpanish() {
        return nameSpanish;
    }

    public void setNameSpanish(String nameSpanish) {
        this.nameSpanish = nameSpanish;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getNameFrench() {
        return nameFrench;
    }

    public void setNameFrench(String nameFrench) {
        this.nameFrench = nameFrench;
    }

    public String getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(String idCountry) {
        this.idCountry = idCountry;
    }
}
