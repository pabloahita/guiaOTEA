using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class for regions
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    public class Region
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="idRegion">Region identifier</param>
        /// <param name="idCountry">Country identifier</param>
        /// <param name="nameSpanish">Region name in Spanish</param>
        /// <param name="nameEnglish">Region name in English</param>
        /// <param name="nameFrench">Region name in French</param>
        /// <param name="nameBasque">Region name in Basque</param>
        /// <param name="nameCatalan">Region name in Catalan</param>
        /// <param name="nameDutch">Region name in Dutch</param>
        /// <param name="nameGalician">Region name in Galician</param>
        /// <param name="nameGerman">Region name in German</param>
        /// <param name="nameItalian">Region name in Italian</param>
        /// <param name="namePortuguese">Region name in Portuguese</param>
        public Region(int idRegion, string idCountry, string? nameSpanish, string? nameEnglish, string? nameFrench,string? nameBasque, string? nameCatalan, string? nameDutch, string? nameGalician, string? nameGerman, string? nameItalian, string? namePortuguese) {
            this.idRegion = idRegion;
            this.idCountry = idCountry;
            this.nameSpanish = nameSpanish;
            this.nameEnglish = nameEnglish;
            this.nameFrench = nameFrench;
            this.nameBasque = nameBasque;
            this.nameCatalan = nameCatalan;
            this.nameDutch = nameDutch;
            this.nameGalician = nameGalician;
            this.nameGerman = nameGerman;
            this.nameItalian = nameItalian;
            this.namePortuguese = namePortuguese;
        }

        /// <summary>
        /// Region identifier
        /// </summary>
        [JsonPropertyName("idRegion")]
        public int idRegion { get; set; }

        /// <summary>
        /// Country identifier
        /// </summary>
        [JsonPropertyName("idCountry")]
        public string idCountry { get; set; }

        /// <summary>
        /// Region name in Spanish
        /// </summary>
        [JsonPropertyName("nameSpanish")]
        public string? nameSpanish { get; set; }

        /// <summary>
        /// Region name in English
        /// </summary>
        [JsonPropertyName("nameEnglish")]
        public string? nameEnglish { get; set; }

        /// <summary>
        /// Region name in French
        /// </summary>
        [JsonPropertyName("nameFrench")]
        public string? nameFrench { get; set; }

        /// <summary>
        /// Region name in Basque
        /// </summary>
        [JsonPropertyName("nameBasque")]
        public string? nameBasque { get; set; }

        /// <summary>
        /// Region name in Catalan
        /// </summary>
        [JsonPropertyName("nameCatalan")]
        public string? nameCatalan { get; set; }

        /// <summary>
        /// Region name in Dutch
        /// </summary>
        [JsonPropertyName("nameDutch")]
        public string? nameDutch { get; set; }

        /// <summary>
        /// Region name in Galician
        /// </summary>
        [JsonPropertyName("nameGalician")]
        public string? nameGalician { get; set; }

        /// <summary>
        /// Region name in German
        /// </summary>
        [JsonPropertyName("nameGerman")]
        public string? nameGerman { get; set; }

        /// <summary>
        /// Region name in Italian
        /// </summary>
        [JsonPropertyName("nameItalian")]
        public string? nameItalian { get; set; }

        /// <summary>
        /// Region name in Portuguese
        /// </summary>
        [JsonPropertyName("namePortuguese")]
        public string? namePortuguese { get; set; }
    }
}
