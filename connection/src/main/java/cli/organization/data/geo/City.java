package cli.organization.data.geo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class City implements Serializable {

    @SerializedName("idCity")
    public int idCity;

    @SerializedName("idProvince")
    public int idProvince;

    @SerializedName("idRegion")
    public int idRegion;

    @SerializedName("idCountry")
    public String idCountry;

    @SerializedName("nameSpanish")
    public String nameSpanish;

    @SerializedName("nameEnglish")
    public String nameEnglish;

    @SerializedName("nameFrench")
    public String nameFrench;

    @SerializedName("nameBasque")
    public String nameBasque;

    @SerializedName("nameCatalan")
    public String nameCatalan;

    @SerializedName("nameDutch")
    public String nameDutch;

    @SerializedName("nameGalician")
    public String nameGalician;

    @SerializedName("nameGerman")
    public String nameGerman;

    @SerializedName("nameItalian")
    public String nameItalian;

    @SerializedName("namePortuguese")
    public String namePortuguese;


    public City(int idCity, int idProvince, int idRegion, String idCountry, String nameSpanish, String nameEnglish, String nameFrench, String nameBasque, String nameCatalan, String nameDutch, String nameGalician, String nameGerman, String nameItalian, String namePortuguese){
        setIdCity(idCity);
        setIdProvince(idProvince);
        setIdRegion(idRegion);
        setIdCountry(idCountry);
        setNameSpanish(nameSpanish);
        setNameEnglish(nameEnglish);
        setNameFrench(nameFrench);
        setNameBasque(nameBasque);
        setNameCatalan(nameCatalan);
        setNameDutch(nameDutch);
        setNameGalician(nameGalician);
        setNameGerman(nameGerman);
        setNameItalian(nameItalian);
        setNamePortuguese(namePortuguese);
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public int getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(int idProvince) {
        this.idProvince = idProvince;
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public String getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(String idCountry) {
        this.idCountry = idCountry;
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

    public String getNameBasque() {
        return nameBasque;
    }

    public void setNameBasque(String nameBasque) {
        this.nameBasque = nameBasque;
    }

    public String getNameCatalan() {
        return nameCatalan;
    }

    public void setNameCatalan(String nameCatalan) {
        this.nameCatalan = nameCatalan;
    }

    public String getNameDutch() {
        return nameDutch;
    }

    public void setNameDutch(String nameDutch) {
        this.nameDutch = nameDutch;
    }

    public String getNameGalician() {
        return nameGalician;
    }

    public void setNameGalician(String nameGalician) {
        this.nameGalician = nameGalician;
    }

    public String getNameGerman() {
        return nameGerman;
    }

    public void setNameGerman(String nameGerman) {
        this.nameGerman = nameGerman;
    }

    public String getNameItalian() {
        return nameItalian;
    }

    public void setNameItalian(String nameItalian) {
        this.nameItalian = nameItalian;
    }

    public String getNamePortuguese() {
        return namePortuguese;
    }

    public void setNamePortuguese(String namePortuguese) {
        this.namePortuguese = namePortuguese;
    }
}
