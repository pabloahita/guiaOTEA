package cli.indicators;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class for second level division of ambits
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class SubSubAmbit implements Serializable {
    
    /**Second level division of ambit identifier*/
    @SerializedName("idSubSubAmbit")
    public int idSubSubAmbit;

    /**First level division of ambit identifier*/
    @SerializedName("idSubAmbit")
    public int idSubAmbit;
    
    /**Ambit identifier*/
    @SerializedName("idAmbit")
    public int idAmbit;

    /**SubSubAmbit description in English*/
    @SerializedName("descriptionEnglish")
    public String descriptionEnglish;

    /**SubSubAmbit description in Spanish*/
    @SerializedName("descriptionSpanish")
    public String descriptionSpanish;

    /**SubSubAmbit description in French*/
    @SerializedName("descriptionFrench")
    public String descriptionFrench;

    /**SubSubAmbit description in Basque*/
    @SerializedName("descriptionBasque")
    public String descriptionBasque;

    /**SubSubAmbit description in Catalan*/
    @SerializedName("descriptionCatalan")
    public String descriptionCatalan;

    /**SubSubAmbit description in Dutch*/
    @SerializedName("descriptionDutch")
    public String descriptionDutch;

    /**SubSubAmbit description in Galician*/
    @SerializedName("descriptionGalician")
    public String descriptionGalician;

    /**SubSubAmbit description in German*/
    @SerializedName("descriptionGerman")
    public String descriptionGerman;

    /**SubSubAmbit description in Italian*/
    @SerializedName("descriptionItalian")
    public String descriptionItalian;

    /**SubSubAmbit description in Portuguese*/
    @SerializedName("descriptionPortuguese")
    public String descriptionPortuguese;

    /**
     * Class constructor
     *
     * @param idSubSubAmbit - SubSubAmbit identifier
     * @param idSubAmbit - SubAmbit identifier
     * @param idAmbit - Ambit identifier
     * @param descriptionEnglish - SubSubAmbit description in English
     * @param descriptionSpanish - SubSubAmbit description in Spanish
     * @param descriptionFrench - SubSubAmbit description in French
     * @param descriptionBasque - SubSubAmbit description in Basque
     * @param descriptionCatalan - SubSubAmbit description in Catalan
     * @param descriptionDutch - SubSubAmbit description in Dutch
     * @param descriptionGalician - SubSubAmbit description in Galician
     * @param descriptionGerman - SubSubAmbit description in German
     * @param descriptionItalian - SubSubAmbit description in Italian
     * @param descriptionPortuguese - SubSubAmbit description in Portuguese
     * */
    public SubSubAmbit(int idSubSubAmbit, int idSubAmbit, int idAmbit, String descriptionEnglish, String descriptionSpanish, String descriptionFrench,String descriptionBasque, String descriptionCatalan, String descriptionDutch,String descriptionGalician, String descriptionGerman, String descriptionItalian,String descriptionPortuguese){
        setIdSubSubAmbit(idSubSubAmbit);
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
     * Method that obtains the subSubAmbit identifier
     *
     * @return SubSubAmbit identifier
     * */
    public int getIdSubSubAmbit() {
        return idSubSubAmbit;
    }

    /**
     * Method that sets the new subSubAmbit identifier
     *
     * @param idSubSubAmbit - SubSubAmbit identifier
     * */
    public void setIdSubSubAmbit(int idSubSubAmbit) {
        this.idSubSubAmbit = idSubSubAmbit;
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
     * Method that sets the new subSubAmbit description in English
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
