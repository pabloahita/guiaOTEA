package cli.indicators;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class for the ambits
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class Ambit implements Serializable {

    /**Ambit identifier*/
    @SerializedName("idAmbit")
    public int idAmbit;

    /**Ambit description in English*/
    @SerializedName("descriptionEnglish")
    public String descriptionEnglish;

    /**Ambit description in Spanish*/
    @SerializedName("descriptionSpanish")
    public String descriptionSpanish;

    /**Ambit description in French*/
    @SerializedName("descriptionFrench")
    public String descriptionFrench;

    /**Ambit description in Basque*/
    @SerializedName("descriptionBasque")
    public String descriptionBasque;

    /**Ambit description in Catalan*/
    @SerializedName("descriptionCatalan")
    public String descriptionCatalan;

    /**Ambit description in Dutch*/
    @SerializedName("descriptionDutch")
    public String descriptionDutch;

    /**Ambit description in Galician*/
    @SerializedName("descriptionGalician")
    public String descriptionGalician;

    /**Ambit description in German*/
    @SerializedName("descriptionGerman")
    public String descriptionGerman;

    /**Ambit description in Italian*/
    @SerializedName("descriptionItalian")
    public String descriptionItalian;

    /**Ambit description in Portuguese*/
    @SerializedName("descriptionPortuguese")
    public String descriptionPortuguese;

    /**
     * Class constructor
     *
     * @param idAmbit - Ambit identifier
     * @param descriptionEnglish - Ambit description in English
     * @param descriptionSpanish - Ambit description in Spanish
     * @param descriptionFrench - Ambit description in French
     * @param descriptionBasque - Ambit description in Basque
     * @param descriptionCatalan - Ambit description in Catalan
     * @param descriptionDutch - Ambit description in Dutch
     * @param descriptionGalician - Ambit description in Galician
     * @param descriptionGerman - Ambit description in German
     * @param descriptionItalian - Ambit description in Italian
     * @param descriptionPortuguese - Ambit description in Portuguese
     * */
    public Ambit(int idAmbit, String descriptionEnglish, String descriptionSpanish, String descriptionFrench,String descriptionBasque, String descriptionCatalan, String descriptionDutch,String descriptionGalician, String descriptionGerman, String descriptionItalian,String descriptionPortuguese){
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
     * Method that obtains the ambit description in English
     *
     * @return Ambit description in English
     * */
    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    /**
     * Method that sets the new ambit description in Portuguese
     *
     * @param descriptionEnglish - Ambit description in English
     * */
    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    /**
     * Method that obtains the ambit description in Spanish
     *
     * @return Ambit description in Spanish
     * */
    public String getDescriptionSpanish() {
        return descriptionSpanish;
    }

    /**
     * Method that sets the new ambit description in Spanish
     *
     * @param descriptionSpanish - Ambit description in Spanish
     * */
    public void setDescriptionSpanish(String descriptionSpanish) {
        this.descriptionSpanish = descriptionSpanish;
    }

    /**
     * Method that obtains the ambit description in French
     *
     * @return Ambit description in French
     * */
    public String getDescriptionFrench() {
        return descriptionFrench;
    }

    /**
     * Method that sets the new ambit description in Portuguese
     *
     * @param descriptionFrench - Ambit description in French
     * */
    public void setDescriptionFrench(String descriptionFrench) {
        this.descriptionFrench = descriptionFrench;
    }

    /**
     * Method that obtains the ambit description in Basque
     *
     * @return Ambit description in Basque
     * */
    public String getDescriptionBasque() {
        return descriptionBasque;
    }

    /**
     * Method that sets the new ambit description in Basque
     *
     * @param descriptionBasque - Ambit description in Basque
     * */
    public void setDescriptionBasque(String descriptionBasque) {
        this.descriptionBasque = descriptionBasque;
    }

    /**
     * Method that obtains the ambit description in Catalan
     *
     * @return Ambit description in Catalan
     * */
    public String getDescriptionCatalan() {
        return descriptionCatalan;
    }

    /**
     * Method that sets the new ambit description in Catalan
     *
     * @param descriptionCatalan - Ambit description in Catalan
     * */
    public void setDescriptionCatalan(String descriptionCatalan) {
        this.descriptionCatalan = descriptionCatalan;
    }

    /**
     * Method that obtains the ambit description in Dutch
     *
     * @return Ambit description in Dutch
     * */
    public String getDescriptionDutch() {
        return descriptionDutch;
    }

    /**
     * Method that sets the new ambit description in Dutch
     *
     * @param descriptionDutch - Ambit description in Dutch
     * */
    public void setDescriptionDutch(String descriptionDutch) {
        this.descriptionDutch = descriptionDutch;
    }

    /**
     * Method that obtains the ambit description in Galician
     *
     * @return Ambit description in Galician
     * */
    public String getDescriptionGalician() {
        return descriptionGalician;
    }

    /**
     * Method that sets the new ambit description in Galician
     *
     * @param descriptionGalician - Ambit description in Galician
     * */
    public void setDescriptionGalician(String descriptionGalician) {
        this.descriptionGalician = descriptionGalician;
    }

    /**
     * Method that obtains the ambit description in German
     *
     * @return Ambit description in German
     * */
    public String getDescriptionGerman() {
        return descriptionGerman;
    }

    /**
     * Method that sets the new ambit description in German
     *
     * @param descriptionGerman - Ambit description in German
     * */
    public void setDescriptionGerman(String descriptionGerman) {
        this.descriptionGerman = descriptionGerman;
    }

    /**
     * Method that obtains the ambit description in Italian
     *
     * @return Ambit description in Italian
     * */
    public String getDescriptionItalian() {
        return descriptionItalian;
    }

    /**
     * Method that sets the new ambit description in Italian
     *
     * @param descriptionItalian - Ambit description in Italian
     * */
    public void setDescriptionItalian(String descriptionItalian) {
        this.descriptionItalian = descriptionItalian;
    }

    /**
     * Method that obtains the ambit description in Portuguese
     *
     * @return Ambit description in Portuguese
     * */
    public String getDescriptionPortuguese() {
        return descriptionPortuguese;
    }

    /**
     * Method that sets the new ambit description in Portuguese
     *
     * @param descriptionPortuguese - Ambit description in Portuguese
     * */
    public void setDescriptionPortuguese(String descriptionPortuguese) {
        this.descriptionPortuguese = descriptionPortuguese;
    }
}
