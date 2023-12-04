package cli.indicators;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class for the indicators
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class Evidence implements Serializable {

    /**Evidence identifier*/
    @SerializedName("idEvidence")
    public int idEvidence;

    /**Indicator identifier*/
    @SerializedName("idIndicator")
    public int idIndicator;

    /**Indicator type*/
    @SerializedName("indicatorType")
    public String indicatorType;

    /**Second level division of the ambit. It will be -1 if there is no division*/
    @SerializedName("idSubSubAmbit")
    public int idSubSubAmbit;

    /**First level division of the ambit. It will be -1 if there is no division*/
    @SerializedName("idSubAmbit")
    public int idSubAmbit;

    /**Ambit identifier*/
    @SerializedName("idAmbit")
    public int idAmbit;

    /**Evidence description in English*/
    @SerializedName("descriptionEnglish")
    public String descriptionEnglish;

    /**Evidence description in Spanish*/
    @SerializedName("descriptionSpanish")
    public String descriptionSpanish;

    /**Evidence description in French*/
    @SerializedName("descriptionFrench")
    public String descriptionFrench;

    /**Evidence description in Basque*/
    @SerializedName("descriptionBasque")
    public String descriptionBasque;

    /**Evidence description in Catalan*/
    @SerializedName("descriptionCatalan")
    public String descriptionCatalan;

    /**Evidence description in Dutch*/
    @SerializedName("descriptionDutch")
    public String descriptionDutch;

    /**Evidence description in Galician*/
    @SerializedName("descriptionGalician")
    public String descriptionGalician;

    /**Evidence description in German*/
    @SerializedName("descriptionGerman")
    public String descriptionGerman;

    /**Evidence description in Italian*/
    @SerializedName("descriptionItalian")
    public String descriptionItalian;

    /**Evidence description in Portuguese*/
    @SerializedName("descriptionPortuguese")
    public String descriptionPortuguese;

    /**Evidence value*/
    @SerializedName("evidenceValue")
    public int evidenceValue;

    /**Indicator version*/
    @SerializedName("indicatorVersion")
    public int indicatorVersion;

    /**
     * Class constructor
     *
     * @param idEvidence - Evidence identifier
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param descriptionEnglish - Evidence description in English
     * @param descriptionSpanish - Evidence description in Spanish
     * @param descriptionFrench - Evidence description in French
     * @param descriptionBasque - Evidence description in Basque
     * @param descriptionCatalan - Evidence description in Catalan
     * @param descriptionDutch - Evidence description in Dutch
     * @param descriptionGalician - Evidence description in Galician
     * @param descriptionGerman - Evidence description in German
     * @param descriptionItalian - Evidence description in Italian
     * @param descriptionPortuguese - Evidence description in Portuguese
     * @param evidenceValue - Evidence value
     * @param indicatorVersion - Indicator version
     * */
    public Evidence(int idEvidence, int idIndicator, String indicatorType, int idSubSubAmbit, int idSubAmbit, int idAmbit, String descriptionEnglish, String descriptionSpanish, String descriptionFrench, String descriptionBasque, String descriptionCatalan, String descriptionDutch, String descriptionGalician, String descriptionGerman, String descriptionItalian, String descriptionPortuguese, int evidenceValue, int indicatorVersion){
        setIdEvidence(idEvidence);
        setIdIndicator(idIndicator);
        setIndicatorType(indicatorType);
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
        setEvidenceValue(evidenceValue);
        setIndicatorVersion(indicatorVersion);
    }

    /**
     * Method that obtains the evidence identifier
     *
     * @return Evidence identifier
     * */
    public int getIdEvidence() {
        return idEvidence;
    }

    /**
     * Method that sets the new evidence identifier
     *
     * @param idEvidence - Evidence identifier
     * */
    public void setIdEvidence(int idEvidence) {
        this.idEvidence = idEvidence;
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
     * Method that obtains the evidence description in English
     *
     * @return Evidence description in English
     * */
    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    /**
     * Method that sets the new evidence description in English
     *
     * @param descriptionEnglish - Evidence description in English
     * */
    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    /**
     * Method that obtains the evidence description in Spanish
     *
     * @return Evidence description in Spanish
     * */
    public String getDescriptionSpanish() {
        return descriptionSpanish;
    }

    /**
     * Method that sets the new evidence description in Spanish
     *
     * @param descriptionSpanish - Evidence description in Spanish
     * */
    public void setDescriptionSpanish(String descriptionSpanish) {
        this.descriptionSpanish = descriptionEnglish;
    }

    /**
     * Method that obtains the evidence description in French
     *
     * @return Evidence description in French
     * */
    public String getDescriptionFrench() {
        return descriptionFrench;
    }

    /**
     * Method that sets the new evidence description in French
     *
     * @param descriptionFrench - Evidence description in French
     * */
    public void setDescriptionFrench(String descriptionFrench) {
        this.descriptionFrench = descriptionFrench;
    }

    /**
     * Method that obtains the evidence description in Basque
     *
     * @return Evidence description in Basque
     * */
    public String getDescriptionBasque() {
        return descriptionBasque;
    }

    /**
     * Method that sets the new evidence description in Basque
     *
     * @param descriptionBasque - Evidence description in Basque
     * */
    public void setDescriptionBasque(String descriptionBasque) {
        this.descriptionBasque = descriptionBasque;
    }

    /**
     * Method that obtains the evidence description in Catalan
     *
     * @return Evidence description in Catalan
     * */
    public String getDescriptionCatalan() {
        return descriptionCatalan;
    }

    /**
     * Method that sets the new evidence description in Catalan
     *
     * @param descriptionCatalan - Evidence description in Catalan
     * */
    public void setDescriptionCatalan(String descriptionCatalan) {
        this.descriptionCatalan = descriptionCatalan;
    }

    /**
     * Method that obtains the evidence description in Dutch
     *
     * @return Evidence description in Dutch
     * */
    public String getDescriptionDutch() {
        return descriptionDutch;
    }

    /**
     * Method that sets the new evidence description in Dutch
     *
     * @param descriptionDutch - Evidence description in Dutch
     * */
    public void setDescriptionDutch(String descriptionDutch) {
        this.descriptionDutch = descriptionDutch;
    }

    /**
     * Method that obtains the evidence description in Galician
     *
     * @return Evidence description in Galician
     * */
    public String getDescriptionGalician() {
        return descriptionGalician;
    }

    /**
     * Method that sets the new evidence description in Galician
     *
     * @param descriptionGalician - Evidence description in Galician
     * */
    public void setDescriptionGalician(String descriptionGalician) {
        this.descriptionGalician = descriptionGalician;
    }

    /**
     * Method that obtains the evidence description in German
     *
     * @return Evidence description in German
     * */
    public String getDescriptionGerman() {
        return descriptionGerman;
    }

    /**
     * Method that sets the new evidence description in German
     *
     * @param descriptionGerman - Evidence description in German
     * */
    public void setDescriptionGerman(String descriptionGerman) {
        this.descriptionGerman = descriptionGerman;
    }

    /**
     * Method that obtains the evidence description in Italian
     *
     * @return Evidence description in Italian
     * */
    public String getDescriptionItalian() {
        return descriptionItalian;
    }

    /**
     * Method that sets the new evidence description in Italian
     *
     * @param descriptionItalian - Evidence description in Italian
     * */
    public void setDescriptionItalian(String descriptionItalian) {
        this.descriptionItalian = descriptionItalian;
    }

    /**
     * Method that obtains the evidence description in Portuguese
     *
     * @return Evidence description in Portuguese
     * */
    public String getDescriptionPortuguese() {
        return descriptionPortuguese;
    }

    /**
     * Method that sets the new evidence description in Portuguese
     *
     * @param descriptionPortuguese - Evidence description in Portuguese
     * */
    public void setDescriptionPortuguese(String descriptionPortuguese) {
        this.descriptionPortuguese = descriptionPortuguese;
    }

    /**
     * Method that obtains the evidence value
     *
     * @return Evidence value
     * */
    public int getEvidenceValue() {
        return evidenceValue;
    }

    /**
     * Method that sets the new evidence value
     *
     * @param evidenceValue - Evidence value
     * */
    public void setEvidenceValue(int evidenceValue) {
        this.evidenceValue = evidenceValue;
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
}
