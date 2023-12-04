package cli.organization.data.geo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class for provinces
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class Province implements Serializable {
    /**Province identifier*/
    @SerializedName("idProvince")
    public int idProvince;

    /**Region identifier*/
    @SerializedName("idRegion")
    public int idRegion;

    /**Country identifier*/
    @SerializedName("idCountry")
    public String idCountry;

    /**Province name in Spanish*/
    @SerializedName("nameSpanish")
    public String nameSpanish;

    /**Province name in English*/
    @SerializedName("nameEnglish")
    public String nameEnglish;

    /**Province name in French*/
    @SerializedName("nameFrench")
    public String nameFrench;

    /**Province name in Basque*/
    @SerializedName("nameBasque")
    public String nameBasque;

    /**Province name in Catalan*/
    @SerializedName("nameCatalan")
    public String nameCatalan;

    /**Province name in Dutch*/
    @SerializedName("nameDutch")
    public String nameDutch;

    /**Province name in Galician*/
    @SerializedName("nameGalician")
    public String nameGalician;

    /**Province name in German*/
    @SerializedName("nameGerman")
    public String nameGerman;

    /**Province name in Italian*/
    @SerializedName("nameItalian")
    public String nameItalian;

    /**Province name in Portuguese*/
    @SerializedName("namePortuguese")
    public String namePortuguese;

    /**
     * Class constructor
     *
     * @param idProvince - Province identifier
     * @param idRegion - Region identifier
     * @param idCountry - Country identifier
     * @param nameSpanish - Province name in Spanish
     * @param nameEnglish - Province name in English
     * @param nameFrench - Province name in French
     * @param nameBasque - Province name in Basque
     * @param nameCatalan - Province name in Catalan
     * @param nameDutch - Province name in Dutch
     * @param nameGalician - Province name in Galician
     * @param nameGerman - Province name in German
     * @param nameItalian - Province name in Italian
     * @param namePortuguese - Province name in Portuguese
     * */
    public Province(int idProvince, int idRegion, String idCountry, String nameSpanish, String nameEnglish, String nameFrench, String nameBasque, String nameCatalan, String nameDutch, String nameGalician, String nameGerman, String nameItalian, String namePortuguese){
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

    /**
     * Method that obtains the region identifier
     *
     * @return Region identifier
     * */
    public int getIdRegion() {
        return idRegion;
    }

    /**
     * Method that sets the new region identifier
     *
     * @param idRegion - Region identifier
     * */
    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    /**
     * Method that obtains the province identifier
     *
     * @return Province identifier
     * */
    public int getIdProvince() {
        return idProvince;
    }

    /**
     * Method that sets the new province identifier
     *
     * @param idProvince - Province identifier
     * */
    public void setIdProvince(int idProvince) {
        this.idProvince = idProvince;
    }

    /**
     * Method that obtains the country identifier
     *
     * @return Country identifier
     * */
    public String getIdCountry() {
        return idCountry;
    }

    /**
     * Method that sets the new country identifier
     *
     * @param idCountry - Country identifier
     * */
    public void setIdCountry(String idCountry) {
        this.idCountry = idCountry;
    }

    /**
     * Method that obtains the province name in Spanish
     *
     * @return Province name in Spanish
     * */
    public String getNameSpanish() {
        return nameSpanish;
    }

    /**
     * Method that sets the new province name in Spanish
     *
     * @param nameSpanish - Province name in Spanish
     * */
    public void setNameSpanish(String nameSpanish) {
        this.nameSpanish = nameSpanish;
    }

    /**
     * Method that obtains the province name in English
     *
     * @return Province name in English
     * */
    public String getNameEnglish() {
        return nameEnglish;
    }

    /**
     * Method that sets the new province name in English
     *
     * @param nameEnglish - Province name in English
     * */
    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    /**
     * Method that obtains the province name in French
     *
     * @return Province name in French
     * */
    public String getNameFrench() {
        return nameFrench;
    }

    /**
     * Method that sets the new province name in French
     *
     * @param nameFrench - Province name in French
     * */
    public void setNameFrench(String nameFrench) {
        this.nameFrench = nameFrench;
    }

    /**
     * Method that obtains the province name in Basque
     *
     * @return Province name in Basque
     * */
    public String getNameBasque() {
        return nameBasque;
    }

    /**
     * Method that sets the new province name in Basque
     *
     * @param nameBasque - Province name in Basque
     * */
    public void setNameBasque(String nameBasque) {
        this.nameBasque = nameBasque;
    }

    /**
     * Method that obtains the province name in Catalan
     *
     * @return Province name in Catalan
     * */
    public String getNameCatalan() {
        return nameCatalan;
    }

    /**
     * Method that sets the new province name in Catalan
     *
     * @param nameCatalan - Province name in Catalan
     * */
    public void setNameCatalan(String nameCatalan) {
        this.nameCatalan = nameCatalan;
    }

    /**
     * Method that obtains the province name in Dutch
     *
     * @return Province name in Dutch
     * */
    public String getNameDutch() {
        return nameDutch;
    }

    /**
     * Method that sets the new province name in Dutch
     *
     * @param nameDutch - Province name in Dutch
     * */
    public void setNameDutch(String nameDutch) {
        this.nameDutch = nameDutch;
    }

    /**
     * Method that obtains the province name in Galician
     *
     * @return Province name in Galician
     * */
    public String getNameGalician() {
        return nameGalician;
    }

    /**
     * Method that sets the new province name in Galician
     *
     * @param nameGalician - Province name in Galician
     * */
    public void setNameGalician(String nameGalician) {
        this.nameGalician = nameGalician;
    }

    /**
     * Method that obtains the province name in German
     *
     * @return Province name in German
     * */
    public String getNameGerman() {
        return nameGerman;
    }

    /**
     * Method that sets the new province name in German
     *
     * @param nameGerman - Province name in German
     * */
    public void setNameGerman(String nameGerman) {
        this.nameGerman = nameGerman;
    }

    /**
     * Method that obtains the province name in Italian
     *
     * @return Province name in Italian
     * */
    public String getNameItalian() {
        return nameItalian;
    }

    /**
     * Method that sets the new province name in Italian
     *
     * @param nameItalian - Province name in Italian
     * */
    public void setNameItalian(String nameItalian) {
        this.nameItalian = nameItalian;
    }

    /**
     * Method that obtains the province name in Portuguese
     *
     * @return Province name in Portuguese
     * */
    public String getNamePortuguese() {
        return namePortuguese;
    }

    /**
     * Method that sets the new province name in Portuguese
     *
     * @param namePortuguese - Province name in Portuguese
     * */
    public void setNamePortuguese(String namePortuguese) {
        this.namePortuguese = namePortuguese;
    }

}
