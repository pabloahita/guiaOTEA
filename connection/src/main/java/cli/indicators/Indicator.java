package cli.indicators;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Model class for the indicators
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */

public class Indicator implements Serializable {

    /**Indicator's identifier*/
    @SerializedName("idIndicator")
    public int idIndicator;

    /**Indicator's type*/
    @SerializedName("indicatorType")
    public String indicatorType;

    /**Indicator's description in English*/
    @SerializedName("descriptionEnglish")
    public String descriptionEnglish;

    /**Indicator's description in Spanish*/
    @SerializedName("descriptionSpanish")
    public String descriptionSpanish;

    /**Indicator's description in French*/
    @SerializedName("descriptionFrench")
    public String descriptionFrench;

    /**Indicator's description in Basque*/
    @SerializedName("descriptionBasque")
    public String descriptionBasque;

    /**Indicator's description in Catalan*/
    @SerializedName("descriptionCatalan")
    public String descriptionCatalan;

    /**Indicator's description in Dutch*/
    @SerializedName("descriptionDutch")
    public String descriptionDutch;

    /**Indicator's description in Galician*/
    @SerializedName("descriptionGalician")
    public String descriptionGalician;

    /**Indicator's description in German*/
    @SerializedName("descriptionGerman")
    public String descriptionGerman;

    /**Indicator's description in Italian*/
    @SerializedName("descriptionItalian")
    public String descriptionItalian;

    /**Indicator's description in Portuguese*/
    @SerializedName("descriptionPortuguese")
    public String descriptionPortuguese;

    /**Evidence list of the indicator*/
    public List<Evidence> evidences;

    /**Second level division of the ambit. It will be -1 if there is no division*/
    @SerializedName("idSubSubAmbit")
    public int idSubSubAmbit;

    /**First level division of the ambit. It will be -1 if there is no division*/
    @SerializedName("idSubAmbit")
    public int idSubAmbit;

    /**Ambit identifier*/
    @SerializedName("idAmbit")
    public int idAmbit;

    /**Indicator priority*/
    @SerializedName("indicatorPriority")
    public int indicatorPriority;

    /**Indicator version*/
    @SerializedName("indicatorVersion")
    public int indicatorVersion;

    /**Boolean that determines if the indicator is or not is activated. 1 if is, 0 if not*/
    @SerializedName("isActive")
    public int isActive;

    /**Evaluation type*/
    @SerializedName("evaluationType")
    public String evaluationType;



    /**
     * Class constructor
     *
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit. It will be -1 if there is no division
     * @param idSubAmbit - First level division of the ambit. It will be -1 if there is no division
     * @param idAmbit - Ambit identifier
     * @param descriptionEnglish - Indicator's description in English
     * @param descriptionSpanish - Indicator's description in Spanish
     * @param descriptionFrench - Indicator's description in French
     * @param descriptionBasque - Indicator's description in Basque
     * @param descriptionCatalan - Indicator's description in Catalan
     * @param descriptionDutch - Indicator's description in Dutch
     * @param descriptionGalician - Indicator's description in Galician
     * @param descriptionGerman - Indicator's description in German
     * @param descriptionItalian - Indicator's description in Italian
     * @param descriptionPortuguese - Indicator's description in Portuguese
     * @param indicatorPriority - Indicator priority
     * @param indicatorVersion - Indicator version
     * @param isActive - Boolean that determines if the indicator is or not is activated. 1 if is, 0 if not
     * @param evaluationType - Evaluation type
     * */
    public Indicator(int idIndicator, String indicatorType, int idSubSubAmbit, int idSubAmbit, int idAmbit, String descriptionEnglish, String descriptionSpanish, String descriptionFrench, String descriptionBasque, String descriptionCatalan, String descriptionDutch, String descriptionGalician, String descriptionGerman, String descriptionItalian, String descriptionPortuguese, int indicatorPriority, int indicatorVersion, int isActive, String evaluationType) {
        setIdIndicator(idIndicator);
        setIndicatorType(indicatorType);
        setIdSubSubAmbit(idSubSubAmbit);
        setIdSubAmbit(idSubAmbit);
        setIdAmbit(idAmbit);
        setIndicatorType(indicatorType);
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
        setPriority(indicatorPriority);
        setIndicatorVersion(indicatorVersion);
        setIsActive(isActive);
        setEvaluationType(evaluationType);
    }

    /**
     * Method that obtains the indicator identifier
     *
     * @return Indicator identifier
     * */
    public int getIdIndicator() {
        return idIndicator;
    }

    /**
     * Method that sets the new indicator identifier
     *
     * @param idIndicator - Indicator identifier
     * */
    public void setIdIndicator(int idIndicator) {
        this.idIndicator = idIndicator;
    }

    /**
     * Method that obtains the indicator type
     *
     * @return Indicator type
     * */

    public String getIndicatorType() {
        return indicatorType;
    }

    /**
     * Method that sets the new indicator type
     *
     * @param indicatorType - Indicator type
     * */
    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    /**
     * Method that sets the evidences of an indicator
     *
     * @param evidences - Evidences list
     * */
    public void setEvidences(List<Evidence> evidences) {
        this.evidences=evidences;
    }

    /**
     * Method that obtains the evidences of an indicator
     *
     * @return Evidences of an indicator
     * */
    public List<Evidence> getEvidences() {
        return evidences;
    }

    /**
     * Method that obtains the indicator description in English
     *
     * @return Indicator's description in English
     * */
    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    /**
     * Method that sets the new description in English
     *
     * @param descriptionEnglish - Indicator's description in English
     * */
    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    /**
     * Method that obtains the indicator description in Spanish
     *
     * @return Indicator's description in Spanish
     * */
    public String getDescriptionSpanish() {
        return descriptionSpanish;
    }

    /**
     * Method that sets the new description in Spanish
     *
     * @param descriptionSpanish - Indicator's description in Spanish
     * */
    public void setDescriptionSpanish(String descriptionSpanish) {
        this.descriptionSpanish = descriptionSpanish;
    }

    /**
     * Method that obtains the indicator description in French
     *
     * @return Indicator's description in French
     * */
    public String getDescriptionFrench() {
        return descriptionFrench;
    }

    /**
     * Method that sets the new description in French
     *
     * @param descriptionFrench - Indicator's description in French
     * */
    public void setDescriptionFrench(String descriptionFrench) {
        this.descriptionFrench = descriptionFrench;
    }

    /**
     * Method that obtains the indicator description in Basque
     *
     * @return Indicator's description in Basque
     * */
    public String getDescriptionBasque() {
        return descriptionBasque;
    }

    /**
     * Method that sets the new description in Basque
     *
     * @param descriptionBasque - Indicator's description in Basque
     * */
    public void setDescriptionBasque(String descriptionBasque) {
        this.descriptionBasque = descriptionBasque;
    }

    /**
     * Method that obtains the indicator description in Catalan
     *
     * @return Indicator's description in Catalan
     * */
    public String getDescriptionCatalan() {
        return descriptionCatalan;
    }

    /**
     * Method that sets the new description in Catalan
     *
     * @param descriptionCatalan - Indicator's description in Catalan
     * */
    public void setDescriptionCatalan(String descriptionCatalan) {
        this.descriptionCatalan = descriptionCatalan;
    }

    /**
     * Method that obtains the indicator description in Dutch
     *
     * @return Indicator's description in Dutch
     * */
    public String getDescriptionDutch() {
        return descriptionDutch;
    }

    /**
     * Method that sets the new description in Dutch
     *
     * @param descriptionDutch - Indicator's description in Dutch
     * */
    public void setDescriptionDutch(String descriptionDutch) {
        this.descriptionDutch = descriptionDutch;
    }

    /**
     * Method that obtains the indicator description in Galician
     *
     * @return Indicator's description in Galician
     * */
    public String getDescriptionGalician() {
        return descriptionGalician;
    }

    /**
     * Method that sets the new description in Galician
     *
     * @param descriptionGalician - Indicator's description in Galician
     * */
    public void setDescriptionGalician(String descriptionGalician) {
        this.descriptionGalician = descriptionGalician;
    }

    /**
     * Method that obtains the indicator description in German
     *
     * @return Indicator's description in German
     * */
    public String getDescriptionGerman() {
        return descriptionGerman;
    }

    /**
     * Method that sets the new description in German
     *
     * @param descriptionGerman - Indicator's description in German
     * */
    public void setDescriptionGerman(String descriptionGerman) {
        this.descriptionGerman = descriptionGerman;
    }

    /**
     * Method that obtains the indicator description in Italian
     *
     * @return Indicator's description in Italian
     * */
    public String getDescriptionItalian() {
        return descriptionItalian;
    }

    /**
     * Method that sets the new description in Italian
     *
     * @param descriptionItalian - Indicator's description in Italian
     * */
    public void setDescriptionItalian(String descriptionItalian) {
        this.descriptionItalian = descriptionItalian;
    }

    /**
     * Method that obtains the indicator description in Portuguese
     *
     * @return Indicator's description in Portuguese
     * */
    public String getDescriptionPortuguese() {
        return descriptionPortuguese;
    }

    /**
     * Method that sets the new description in Portuguese
     *
     * @param descriptionPortuguese - Indicator's description in Portuguese
     * */
    public void setDescriptionPortuguese(String descriptionPortuguese) {
        this.descriptionPortuguese = descriptionPortuguese;
    }

    /**
     * Method that obtains the indicator priority
     *
     * @return Indicator priority
     * */
    public int getPriority() {
        return indicatorPriority;
    }

    /**
     * Method that sets the new indicator priority
     *
     * @param indicatorPriority - Indicator priority
     * */
    public void setPriority(int indicatorPriority) {
        this.indicatorPriority = indicatorPriority;
    }

    /**
     * Method that obtains the indicator version
     *
     * @return Indicator version
     * */
    public int getIndicatorVersion() {
        return indicatorVersion;
    }

    /**
     * Method that sets the new indicator version
     *
     * @param indicatorVersion - Indicator version
     * */
    public void setIndicatorVersion(int indicatorVersion) {
        this.indicatorVersion = indicatorVersion;
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
     * Method that obtains the second level division of the ambit
     *
     * @return Second level division of the ambit
     * */
    public int getIdSubSubAmbit() {
        return idSubSubAmbit;
    }

    /**
     * Method that sets the new second level division of the ambit
     *
     * @param idSubSubAmbit - Second level division of the ambit
     * */
    public void setIdSubSubAmbit(int idSubSubAmbit) {
        this.idSubSubAmbit = idSubSubAmbit;
    }

    /**
     * Method that obtains the first level division of the ambit
     *
     * @return First level division of the ambit
     * */
    public int getIdSubAmbit() {
        return idSubAmbit;
    }

    /**
     * Method that sets the new first level division of the ambit
     *
     * @param idSubAmbit - First level division of the ambit
     * */
    public void setIdSubAmbit(int idSubAmbit) {
        this.idSubAmbit = idSubAmbit;
    }

    /**
     * Method that obtains the new indicator priority
     *
     * @return Indicator priority
     * */
    public int getIndicatorPriority() {
        return indicatorPriority;
    }

    /**
     * Method that sets the new indicator identifier
     *
     * @param indicatorPriority - Indicator priority
     * */
    public void setIndicatorPriority(int indicatorPriority) {
        this.indicatorPriority = indicatorPriority;
    }

    /**
     * Method that obtains if the indicator is active
     *
     * @return 1 if is activated, 0 if not
     * */
    public int getIsActive() {
        return isActive;
    }

    /**
     * Method that sets if the indicator is active
     *
     * @param isActive - 1 if is activated, 0 if not
     * */
    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }


    /**
     * Method that obtains the evaluation type
     *
     * @return Evaluation type
     * */

    public String getEvaluationType() {
        return evaluationType;
    }

    /**
     * Method that sets the new evaluation type
     *
     * @param evaluationType - Evaluation type
     * */
    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }
}
