package cli.organization.data.geo;

import com.google.gson.annotations.SerializedName;
import com.vdurmont.emoji.EmojiParser;

import java.io.Serializable;

public class Country implements Serializable {

    /**Country identifier*/
    @SerializedName("idCountry")
    public String idCountry;

    /**Region name in Spanish*/
    @SerializedName("nameSpanish")
    public String nameSpanish;

    /**Region name in English*/
    @SerializedName("nameEnglish")
    public String nameEnglish;

    /**Region name in French*/
    @SerializedName("nameFrench")
    public String nameFrench;

    /**Region name in Basque*/
    @SerializedName("nameBasque")
    public String nameBasque;

    /**Region name in Catalan*/
    @SerializedName("nameCatalan")
    public String nameCatalan;

    /**Region name in Dutch*/
    @SerializedName("nameDutch")
    public String nameDutch;

    /**Region name in Galician*/
    @SerializedName("nameGalician")
    public String nameGalician;

    /**Region name in German*/
    @SerializedName("nameGerman")
    public String nameGerman;

    /**Region name in Italian*/
    @SerializedName("nameItalian")
    public String nameItalian;

    /**Region name in Portuguese*/
    @SerializedName("namePortuguese")
    public String namePortuguese;

    /**Country phone code*/
    @SerializedName("phone_code")
    public String phone_code;

    /**Country flag emoji*/
    @SerializedName("flag")
    public String flag;

    /**
     * Class constructor
     *
     * @param idCountry - Country identifier
     * @param nameSpanish - Region name in Spanish
     * @param nameEnglish - Region name in English
     * @param nameFrench - Region name in French
     * @param nameBasque - Region name in Basque
     * @param nameCatalan - Region name in Catalan
     * @param nameDutch - Region name in Dutch
     * @param nameGalician - Region name in Galician
     * @param nameGerman - Region name in German
     * @param nameItalian - Region name in Italian
     * @param namePortuguese - Region name in Portuguese
     * @param phone_code - Country phone code
     * @param flag - Country flag emoji
     * */
    public Country(String idCountry, String nameSpanish, String nameEnglish, String nameFrench, String nameBasque, String nameCatalan, String nameDutch, String nameGalician, String nameGerman, String nameItalian, String namePortuguese, String phone_code, String flag){
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
        setPhone_code(phone_code);
        setFlag(EmojiParser.parseToUnicode(flag)); //It's mandatory to convert it to a visible emoji because its code is reached by server via JSON
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
     * Method that obtains the region name in Spanish
     *
     * @return Region name in Spanish
     * */
    public String getNameSpanish() {
        return nameSpanish;
    }

    /**
     * Method that sets the new region name in Spanish
     *
     * @param nameSpanish - Region name in Spanish
     * */
    public void setNameSpanish(String nameSpanish) {
        this.nameSpanish = nameSpanish;
    }

    /**
     * Method that obtains the region name in English
     *
     * @return Region name in English
     * */
    public String getNameEnglish() {
        return nameEnglish;
    }

    /**
     * Method that sets the new region name in English
     *
     * @param nameEnglish - Region name in English
     * */
    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    /**
     * Method that obtains the region name in French
     *
     * @return Region name in French
     * */
    public String getNameFrench() {
        return nameFrench;
    }

    /**
     * Method that sets the new region name in French
     *
     * @param nameFrench - Region name in French
     * */
    public void setNameFrench(String nameFrench) {
        this.nameFrench = nameFrench;
    }

    /**
     * Method that obtains the region name in Basque
     *
     * @return Region name in Basque
     * */
    public String getNameBasque() {
        return nameBasque;
    }

    /**
     * Method that sets the new region name in Basque
     *
     * @param nameBasque - Region name in Basque
     * */
    public void setNameBasque(String nameBasque) {
        this.nameBasque = nameBasque;
    }

    /**
     * Method that obtains the region name in Catalan
     *
     * @return Region name in Catalan
     * */
    public String getNameCatalan() {
        return nameCatalan;
    }

    /**
     * Method that sets the new region name in Catalan
     *
     * @param nameCatalan - Region name in Catalan
     * */
    public void setNameCatalan(String nameCatalan) {
        this.nameCatalan = nameCatalan;
    }

    /**
     * Method that obtains the region name in Dutch
     *
     * @return Region name in Dutch
     * */
    public String getNameDutch() {
        return nameDutch;
    }

    /**
     * Method that sets the new region name in Dutch
     *
     * @param nameDutch - Region name in Dutch
     * */
    public void setNameDutch(String nameDutch) {
        this.nameDutch = nameDutch;
    }

    /**
     * Method that obtains the region name in Galician
     *
     * @return Region name in Galician
     * */
    public String getNameGalician() {
        return nameGalician;
    }

    /**
     * Method that sets the new region name in Galician
     *
     * @param nameGalician - Region name in Galician
     * */
    public void setNameGalician(String nameGalician) {
        this.nameGalician = nameGalician;
    }

    /**
     * Method that obtains the region name in German
     *
     * @return Region name in German
     * */
    public String getNameGerman() {
        return nameGerman;
    }

    /**
     * Method that sets the new region name in German
     *
     * @param nameGerman - Region name in German
     * */
    public void setNameGerman(String nameGerman) {
        this.nameGerman = nameGerman;
    }

    /**
     * Method that obtains the region name in Italian
     *
     * @return Region name in Italian
     * */
    public String getNameItalian() {
        return nameItalian;
    }

    /**
     * Method that sets the new region name in Italian
     *
     * @param nameItalian - Region name in Italian
     * */
    public void setNameItalian(String nameItalian) {
        this.nameItalian = nameItalian;
    }

    /**
     * Method that obtains the region name in Portuguese
     *
     * @return Region name in Portuguese
     * */
    public String getNamePortuguese() {
        return namePortuguese;
    }

    /**
     * Method that sets the new region name in Portuguese
     *
     * @param namePortuguese - Region name in Portuguese
     * */
    public void setNamePortuguese(String namePortuguese) {
        this.namePortuguese = namePortuguese;
    }

    /**
     * Method that obtains the country phone code
     *
     * @return Country phone code
     * */
    public String getPhone_code() {
        return phone_code;
    }

    /**
     * Method that sets the new country phone code
     *
     * @param phone_code - Country phone code
     * */
    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    /**
     * Method that obtains the emoji flag of the country
     *
     * @return Country flag of the country
     * */
    public String getFlag() {
        return flag;
    }

    /**
     * Method that sets the new emoji flag of the country
     *
     * @param flag - Country flag of the country
     * */
    public void setFlag(String flag) {
        this.flag = flag;
    }
}
