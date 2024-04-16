using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class for evidences
    /// Author: Pablo Ahita del Barrio
    /// Version: 1
    /// </summary>
    public class Evidence
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="idEvidence">Evidence identifier</param>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="indicatorType">Indicator type</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit</param>
        /// <param name="idSubAmbit">First level division of the ambit</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="descriptionEnglish">Evidence description in English</param>
        /// <param name="descriptionSpanish">Evidence description in Spanish</param>
        /// <param name="descriptionFrench">Evidence description in French</param>
        /// <param name="descriptionBasque">Evidence description in Basque</param>
        /// <param name="descriptionCatalan">Evidence description in Catalan</param>
        /// <param name="descriptionDutch">Evidence description in Dutch</param>
        /// <param name="descriptionGalician">Evidence description in Galician</param>
        /// <param name="descriptionGerman">Evidence description in German</param>
        /// <param name="descriptionItalian">Evidence description in Italian</param>
        /// <param name="descriptionPortuguese">Evidence description in Portuguese</param>
        /// <param name="evidenceValue">Evidence value</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="evaluationType">Evaluation type</param>
        public Evidence(int idEvidence, int idIndicator, string indicatorType, int idSubSubAmbit, int idSubAmbit, int idAmbit, string descriptionEnglish, string descriptionSpanish, string descriptionFrench, string descriptionBasque, string descriptionCatalan, string descriptionDutch, string descriptionGalician, string descriptionGerman, string descriptionItalian, string descriptionPortuguese, int evidenceValue, int indicatorVersion, string evaluationType)
        {
            this.idEvidence = idEvidence;
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
            this.evidenceValue = evidenceValue;
            this.indicatorVersion = indicatorVersion;
            this.evaluationType = evaluationType;
        }

        /// <summary>
        /// Evidence identifier
        /// </summary>
        [JsonPropertyName("idEvidence")]
        public int idEvidence { get; set; }

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
        /// Evidence description in Spanish
        /// </summary>
        [JsonPropertyName("descriptionSpanish")]
        public string descriptionSpanish { get; set; }

        /// <summary>
        /// Evidence description in English
        /// </summary>
        [JsonPropertyName("descriptionEnglish")]
        public string descriptionEnglish { get; set; }

        /// <summary>
        /// Evidence description in French
        /// </summary>
        [JsonPropertyName("descriptionFrench")]
        public string descriptionFrench { get; set; }

        /// <summary>
        /// Evidence description in Basque
        /// </summary>
        [JsonPropertyName("descriptionBasque")]
        public string descriptionBasque { get; set; }

        /// <summary>
        /// Evidence description in Catalan
        /// </summary>
        [JsonPropertyName("descriptionCatalan")]
        public string descriptionCatalan { get; set; }

        /// <summary>
        /// Evidence description in Dutch
        /// </summary>
        [JsonPropertyName("descriptionDutch")]
        public string descriptionDutch { get; set; }


        /// <summary>
        /// Evidence description in Galician
        /// </summary>
        [JsonPropertyName("descriptionGalician")]
        public string descriptionGalician { get; set; }

        /// <summary>
        /// Evidence description in German
        /// </summary>
        [JsonPropertyName("descriptionGerman")]
        public string descriptionGerman { get; set; }

        /// <summary>
        /// Evidence description in Italian
        /// </summary>
        [JsonPropertyName("descriptionItalian")]
        public string descriptionItalian { get; set; }

        /// <summary>
        /// Evidence description in Portuguese
        /// </summary>
        [JsonPropertyName("descriptionPortuguese")]
        public string descriptionPortuguese { get; set; }

        /// <summary>
        /// Evidence value
        /// </summary>
        [JsonPropertyName("evidenceValue")]
        public int evidenceValue{ get; set; }

        /// <summary>
        /// Indicator version
        /// </summary>
        [JsonPropertyName("indicatorVersion")]
        public int indicatorVersion { get; set; }

        /// <summary>
        /// Evaluation type
        /// </summary>
        [JsonPropertyName("evaluationType")]
        public string evaluationType { get; set; }
    }
}
