package cli.organization.data.geo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class for precharged cities
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class City implements Serializable {

    /**City identifier*/
    @SerializedName("idCity")
    public int idCity;

    /**Province identifier*/
    @SerializedName("idProvince")
    public int idProvince;

    /**Region identifier*/
    @SerializedName("idRegion")
    public int idRegion;

    /**Country identifier*/
    @SerializedName("idCountry")
    public String idCountry;

    /**City name in Spanish*/
    @SerializedName("nameSpanish")
    public String nameSpanish;

    /**City name in English*/
    @SerializedName("nameEnglish")
    public String nameEnglish;

    /**City name in French*/
    @SerializedName("nameFrench")
    public String nameFrench;

    /**City name in Basque*/
    @SerializedName("nameBasque")
    public String nameBasque;

    /**City name in Catalan*/
    @SerializedName("nameCatalan")
    public String nameCatalan;

    /**City name in Dutch*/
    @SerializedName("nameDutch")
    public String nameDutch;

    /**City name in Galician*/
    @SerializedName("nameGalician")
    public String nameGalician;

    /**City name in German*/
    @SerializedName("nameGerman")
    public String nameGerman;

    /**City name in Italian*/
    @SerializedName("nameItalian")
    public String nameItalian;

    /**City name in Portuguese*/
    @SerializedName("namePortuguese")
    public String namePortuguese;

    /**
     * Class constructor
     *
     * @param idCity - City identifier
     * @param idProvince - Province identifier
     * @param idRegion - Region identifier
     * @param idCountry - Country identifier
     * @param nameSpanish - City name in Spanish
     * @param nameEnglish - City name in English
     * @param nameFrench - City name in French
     * @param nameBasque - City name in Basque
     * @param nameCatalan - City name in Catalan
     * @param nameDutch - City name in Dutch
     * @param nameGalician - City name in Galician
     * @param nameGerman - City name in German
     * @param nameItalian - City name in Italian
     * @param namePortuguese - City name in Portuguese
     * */
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

    /**
     * Method that obtains the city identifier
     *
     * @return City identifier
     * */
    public int getIdCity() {
        return idCity;
    }

    /**
     * Method that sets the new city identifier
     *
     * @param idCity - City identifier
     * */
    public void setIdCity(int idCity) {
        this.idCity = idCity;
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
     * Method that obtains the city name in Spanish
     *
     * @return City name in Spanish
     * */
    public String getNameSpanish() {
        return nameSpanish;
    }

    /**
     * Method that sets the new city name in Spanish
     *
     * @param nameSpanish - City name in Spanish
     * */
    public void setNameSpanish(String nameSpanish) {
        this.nameSpanish = nameSpanish;
    }

    /**
     * Method that obtains the city name in English
     *
     * @return City name in English
     * */
    public String getNameEnglish() {
        return nameEnglish;
    }

    /**
     * Method that sets the new city name in English
     *
     * @param nameEnglish - City name in English
     * */
    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    /**
     * Method that obtains the city name in French
     *
     * @return City name in French
     * */
    public String getNameFrench() {
        return nameFrench;
    }

    /**
     * Method that sets the new city name in French
     *
     * @param nameFrench - City name in French
     * */
    public void setNameFrench(String nameFrench) {
        this.nameFrench = nameFrench;
    }

    /**
     * Method that obtains the city name in Basque
     *
     * @return City name in Basque
     * */
    public String getNameBasque() {
        return nameBasque;
    }

    /**
     * Method that sets the new city name in Basque
     *
     * @param nameBasque - City name in Basque
     * */
    public void setNameBasque(String nameBasque) {
        this.nameBasque = nameBasque;
    }

    /**
     * Method that obtains the city name in Catalan
     *
     * @return City name in Catalan
     * */
    public String getNameCatalan() {
        return nameCatalan;
    }

    /**
     * Method that sets the new city name in Catalan
     *
     * @param nameCatalan - City name in Catalan
     * */
    public void setNameCatalan(String nameCatalan) {
        this.nameCatalan = nameCatalan;
    }

    /**
     * Method that obtains the city name in Dutch
     *
     * @return City name in Dutch
     * */
    public String getNameDutch() {
        return nameDutch;
    }

    /**
     * Method that sets the new city name in Dutch
     *
     * @param nameDutch - City name in Dutch
     * */
    public void setNameDutch(String nameDutch) {
        this.nameDutch = nameDutch;
    }

    /**
     * Method that obtains the city name in Galician
     *
     * @return City name in Galician
     * */
    public String getNameGalician() {
        return nameGalician;
    }

    /**
     * Method that sets the new city name in Galician
     *
     * @param nameGalician - City name in Galician
     * */
    public void setNameGalician(String nameGalician) {
        this.nameGalician = nameGalician;
    }

    /**
     * Method that obtains the city name in German
     *
     * @return City name in German
     * */
    public String getNameGerman() {
        return nameGerman;
    }

    /**
     * Method that sets the new city name in German
     *
     * @param nameGerman - City name in German
     * */
    public void setNameGerman(String nameGerman) {
        this.nameGerman = nameGerman;
    }

    /**
     * Method that obtains the city name in Italian
     *
     * @return City name in Italian
     * */
    public String getNameItalian() {
        return nameItalian;
    }

    /**
     * Method that sets the new city name in Italian
     *
     * @param nameItalian - City name in Italian
     * */
    public void setNameItalian(String nameItalian) {
        this.nameItalian = nameItalian;
    }

    /**
     * Method that obtains the city name in Portuguese
     *
     * @return City name in Portuguese
     * */
    public String getNamePortuguese() {
        return namePortuguese;
    }

    /**
     * Method that sets the new city name in Portuguese
     *
     * @param namePortuguese - City name in Portuguese
     * */
    public void setNamePortuguese(String namePortuguese) {
        this.namePortuguese = namePortuguese;
    }
}
