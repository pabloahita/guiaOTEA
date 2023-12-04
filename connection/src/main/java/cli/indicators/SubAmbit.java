package cli.indicators;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class for first level division of ambits
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class SubAmbit implements Serializable {

    /**First level division of ambit identifier*/
    @SerializedName("idSubAmbit")
    public int idSubAmbit;

    /**Ambit identifier*/
    @SerializedName("idAmbit")
    public int idAmbit;

    /**SubAmbit  description in English*/
    @SerializedName("descriptionEnglish")
    public String descriptionEnglish;

    /**SubAmbit  description in Spanish*/
    @SerializedName("descriptionSpanish")
    public String descriptionSpanish;

    /**SubAmbit  description in French*/
    @SerializedName("descriptionFrench")
    public String descriptionFrench;

    /**SubAmbit  description in Basque*/
    @SerializedName("descriptionBasque")
    public String descriptionBasque;

    /**SubAmbit  description in Catalan*/
    @SerializedName("descriptionCatalan")
    public String descriptionCatalan;

    /**SubAmbit  description in Dutch*/
    @SerializedName("descriptionDutch")
    public String descriptionDutch;

    /**SubAmbit  description in Galician*/
    @SerializedName("descriptionGalician")
    public String descriptionGalician;

    /**SubAmbit  description in German*/
    @SerializedName("descriptionGerman")
    public String descriptionGerman;

    /**SubAmbit  description in Italian*/
    @SerializedName("descriptionItalian")
    public String descriptionItalian;

    /**SubAmbit  description in Portuguese*/
    @SerializedName("descriptionPortuguese")
    public String descriptionPortuguese;

    /**
     * Class constructor
     *
     * @param idSubAmbit - SubAmbit identifier
     * @param idAmbit - Ambit identifier
     * @param descriptionEnglish - SubAmbit  description in English
     * @param descriptionSpanish - SubAmbit  description in Spanish
     * @param descriptionFrench - SubAmbit  description in French
     * @param descriptionBasque - SubAmbit  description in Basque
     * @param descriptionCatalan - SubAmbit  description in Catalan
     * @param descriptionDutch - SubAmbit  description in Dutch
     * @param descriptionGalician - SubAmbit  description in Galician
     * @param descriptionGerman - SubAmbit  description in German
     * @param descriptionItalian - SubAmbit  description in Italian
     * @param descriptionPortuguese - SubAmbit  description in Portuguese
     * */
    public SubAmbit(int idSubAmbit, int idAmbit, String descriptionEnglish, String descriptionSpanish, String descriptionFrench,String descriptionBasque, String descriptionCatalan, String descriptionDutch,String descriptionGalician, String descriptionGerman, String descriptionItalian,String descriptionPortuguese){
        setIdSubAmbit(idSubAmbit);
        setIdAmbit(idAmbit);
        setDescriptionEnglish(descriptionEnglish);
        setDescriptionSpanish(descriptionSpanish);
        setDescriptionFrench(descriptionFrench);
        setDescriptionBasque(descriptionBasque);
        setDescriptionCatalan(descriptionCatalan);
        setDescriptionDutch(descriptionDutch);
        setDescriptionGalician(descriptionGalician);
        setDescriptionGerman(descriptionGerman);
        setDescriptionItalian(descriptionItalian);
        setDescriptionPortuguese(descriptionPortuguese);
    }

    /**
     * Method that obtains the subAmbit identifier
     *
     * @return SubAmbit identifier
     * */
    public int getIdSubAmbit() {
        return idSubAmbit;
    }

    /**
     * Method that sets the new subAmbit identifier
     *
     * @param idSubAmbit - SubAmbit identifier
     * */
    public void setIdSubAmbit(int idSubAmbit) {
        this.idSubAmbit = idSubAmbit;
    }

    /**
     * Method that obtains the ambit identifier
     *
     * @return Ambit identifier
     * */
    public int getIdAmbit() {
        return idAmbit;
    }

    /**
     * Method that sets the new ambit identifier
     *
     * @param idAmbit - Ambit identifier
     * */
    public void setIdAmbit(int idAmbit) {
        this.idAmbit = idAmbit;
    }

    /**
     * Method that obtains the subSubAmbit description in English
     *
     * @return Ambit description in English
     * */
    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    /**
     * Method that sets the new subSubAmbit description in Portuguese
     *
     * @param descriptionEnglish - Ambit description in English
     * */
    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    /**
     * Method that obtains the subSubAmbit description in Spanish
     *
     * @return Ambit description in Spanish
     * */
    public String getDescriptionSpanish() {
        return descriptionSpanish;
    }

    /**
     * Method that sets the new subSubAmbit description in Spanish
     *
     * @param descriptionSpanish - Ambit description in Spanish
     * */
    public void setDescriptionSpanish(String descriptionSpanish) {
        this.descriptionSpanish = descriptionSpanish;
    }

    /**
     * Method that obtains the subSubAmbit description in French
     *
     * @return Ambit description in French
     * */
    public String getDescriptionFrench() {
        return descriptionFrench;
    }

    /**
     * Method that sets the new subSubAmbit description in Portuguese
     *
     * @param descriptionFrench - Ambit description in French
     * */
    public void setDescriptionFrench(String descriptionFrench) {
        this.descriptionFrench = descriptionFrench;
    }

    /**
     * Method that obtains the subSubAmbit description in Basque
     *
     * @return Ambit description in Basque
     * */
    public String getDescriptionBasque() {
        return descriptionBasque;
    }

    /**
     * Method that sets the new subSubAmbit description in Basque
     *
     * @param descriptionBasque - Ambit description in Basque
     * */
    public void setDescriptionBasque(String descriptionBasque) {
        this.descriptionBasque = descriptionBasque;
    }

    /**
     * Method that obtains the subSubAmbit description in Catalan
     *
     * @return Ambit description in Catalan
     * */
    public String getDescriptionCatalan() {
        return descriptionCatalan;
    }

    /**
     * Method that sets the new subSubAmbit description in Catalan
     *
     * @param descriptionCatalan - Ambit description in Catalan
     * */
    public void setDescriptionCatalan(String descriptionCatalan) {
        this.descriptionCatalan = descriptionCatalan;
    }

    /**
     * Method that obtains the subSubAmbit description in Dutch
     *
     * @return Ambit description in Dutch
     * */
    public String getDescriptionDutch() {
        return descriptionDutch;
    }

    /**
     * Method that sets the new subSubAmbit description in Dutch
     *
     * @param descriptionDutch - Ambit description in Dutch
     * */
    public void setDescriptionDutch(String descriptionDutch) {
        this.descriptionDutch = descriptionDutch;
    }

    /**
     * Method that obtains the subSubAmbit description in Galician
     *
     * @return Ambit description in Galician
     * */
    public String getDescriptionGalician() {
        return descriptionGalician;
    }

    /**
     * Method that sets the new subSubAmbit description in Galician
     *
     * @param descriptionGalician - Ambit description in Galician
     * */
    public void setDescriptionGalician(String descriptionGalician) {
        this.descriptionGalician = descriptionGalician;
    }

    /**
     * Method that obtains the subSubAmbit description in German
     *
     * @return Ambit description in German
     * */
    public String getDescriptionGerman() {
        return descriptionGerman;
    }

    /**
     * Method that sets the new subSubAmbit description in German
     *
     * @param descriptionGerman - Ambit description in German
     * */
    public void setDescriptionGerman(String descriptionGerman) {
        this.descriptionGerman = descriptionGerman;
    }

    /**
     * Method that obtains the subSubAmbit description in Italian
     *
     * @return Ambit description in Italian
     * */
    public String getDescriptionItalian() {
        return descriptionItalian;
    }

    /**
     * Method that sets the new subSubAmbit description in Italian
     *
     * @param descriptionItalian - Ambit description in Italian
     * */
    public void setDescriptionItalian(String descriptionItalian) {
        this.descriptionItalian = descriptionItalian;
    }

    /**
     * Method that obtains the subSubAmbit description in Portuguese
     *
     * @return Ambit description in Portuguese
     * */
    public String getDescriptionPortuguese() {
        return descriptionPortuguese;
    }

    /**
     * Method that sets the new subSubAmbit description in Portuguese
     *
     * @param descriptionPortuguese - Ambit description in Portuguese
     * */
    public void setDescriptionPortuguese(String descriptionPortuguese) {
        this.descriptionPortuguese = descriptionPortuguese;
    }
}
