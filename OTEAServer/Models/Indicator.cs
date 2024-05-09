using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class for indicators
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    public class Indicator
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="indicatorType">Indicator type</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit. It will be -1 if there is no division</param>
        /// <param name="idSubAmbit">First level division of the ambit. It will be -1 if there is no division</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="descriptionEnglish">Indicator's description in English</param>
        /// <param name="descriptionSpanish">Indicator's description in Spanish</param>
        /// <param name="descriptionFrench">Indicator's description in French</param>
        /// <param name="descriptionBasque">Indicator's description in Basque</param>
        /// <param name="descriptionCatalan">Indicator's description in Catalan</param>
        /// <param name="descriptionDutch">Indicator's description in Dutch</param>
        /// <param name="descriptionGalician">Indicator's description in Galician</param>
        /// <param name="descriptionGerman">Indicator's description in German</param>
        /// <param name="descriptionItalian">Indicator's description in Italian</param>
        /// <param name="descriptionPortuguese">Indicator's description in Portuguese</param>
        /// <param name="indicatorPriority">Indicator priority</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="isActive">Boolean that determines if the indicator is or not is activated. 1 if is, 0 if not</param>
        /// <param name="evaluationType">Evaluation type</param>
        public Indicator(int idIndicator, string indicatorType, int idSubSubAmbit, int idSubAmbit, int idAmbit, string descriptionEnglish, string descriptionSpanish, string descriptionFrench, string descriptionBasque, string descriptionCatalan, string descriptionDutch, string descriptionGalician, string descriptionGerman, string descriptionItalian, string descriptionPortuguese, string indicatorPriority, int indicatorVersion, int isActive, string evaluationType)
        {
            this.idIndicator = idIndicator;
            this.indicatorType = indicatorType;
            this.idSubSubAmbit = idSubSubAmbit;
            this.idSubAmbit = idSubAmbit;
            this.idAmbit = idAmbit;
            this.descriptionEnglish = descriptionEnglish;
            this.descriptionSpanish = descriptionSpanish;
            this.descriptionFrench = descriptionFrench;
            this.descriptionBasque = descriptionBasque;
            this.descriptionCatalan = descriptionCatalan;
            this.descriptionDutch = descriptionDutch;
            this.descriptionGalician = descriptionGalician;
            this.descriptionGerman = descriptionGerman;
            this.descriptionItalian = descriptionItalian;
            this.descriptionPortuguese = descriptionPortuguese;
            this.indicatorPriority = indicatorPriority;
            this.indicatorVersion = indicatorVersion;
            this.isActive = isActive;
            this.evaluationType = evaluationType;
        }

        /// <summary>
        /// Indicator identifier
        /// </summary>
        [JsonPropertyName("idIndicator")]
        public int idIndicator { get; set; }

        /// <summary>
        /// Indicator type
        /// </summary>
        [JsonPropertyName("indicatorType")]
        public string indicatorType { get; set; }

        /// <summary>
        /// Second level division of the ambit
        /// </summary>
        [JsonPropertyName("idSubSubAmbit")]
        public int idSubSubAmbit { get; set; }

        /// <summary>
        /// First level division of the ambit
        /// </summary>
        [JsonPropertyName("idSubAmbit")]
        public int idSubAmbit { get; set; }

        /// <summary>
        /// Ambit identifier
        /// </summary>
        [JsonPropertyName("idAmbit")]
        public int idAmbit { get; set; }

        /// <summary>
        /// Indicator description in Spanish
        /// </summary>
        [JsonPropertyName("descriptionSpanish")]
        public string descriptionSpanish { get; set; }

        /// <summary>
        /// Indicator description in English
        /// </summary>
        [JsonPropertyName("descriptionEnglish")]
        public string descriptionEnglish { get; set; }

        /// <summary>
        /// Indicator description in French
        /// </summary>
        [JsonPropertyName("descriptionFrench")]
        public string descriptionFrench { get; set; }

        /// <summary>
        /// Indicator description in Basque
        /// </summary>
        [JsonPropertyName("descriptionBasque")]
        public string descriptionBasque { get; set; }

        /// <summary>
        /// Indicator description in Catalan
        /// </summary>
        [JsonPropertyName("descriptionCatalan")]
        public string descriptionCatalan { get; set; }

        /// <summary>
        /// Indicator description in Dutch
        /// </summary>
        [JsonPropertyName("descriptionDutch")]
        public string descriptionDutch { get; set; }


        /// <summary>
        /// Indicator description in Galician
        /// </summary>
        [JsonPropertyName("descriptionGalician")]
        public string descriptionGalician { get; set; }

        /// <summary>
        /// Indicator description in German
        /// </summary>
        [JsonPropertyName("descriptionGerman")]
        public string descriptionGerman { get; set; }

        /// <summary>
        /// Indicator description in Italian
        /// </summary>
        [JsonPropertyName("descriptionItalian")]
        public string descriptionItalian { get; set; }

        /// <summary>
        /// Indicator description in Portuguese
        /// </summary>
        [JsonPropertyName("descriptionPortuguese")]
        public string descriptionPortuguese { get; set; }

        /// <summary>
        /// Indicator priority
        /// </summary>
        [JsonPropertyName("indicatorPriority")]
        public string indicatorPriority { get; set; }

        /// <summary>
        /// Indicator version
        /// </summary>
        [JsonPropertyName("indicatorVersion")]
        public int indicatorVersion { get; set; }

        /// <summary>
        /// Boolean that determines if the indicator is or not is activated. 1 if is, 0 if not
        /// </summary>
        [JsonPropertyName("isActive")]
        public int isActive { get; set; }

        /// <summary>
        /// Evaluation type
        /// </summary>
        [JsonPropertyName("evaluationType")]
        public string evaluationType { get; set; }
    }
}
