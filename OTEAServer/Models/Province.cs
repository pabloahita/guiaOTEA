using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class for provinces
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    public class Province
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="idProvince">Province identifier</param>
        /// <param name="idRegion">Region identifier</param>
        /// <param name="idCountry">Country identifier</param>
        /// <param name="nameSpanish">Province name in Spanish</param>
        /// <param name="nameEnglish">Province name in English</param>
        /// <param name="nameFrench">Province name in French</param>
        /// <param name="nameBasque">Province name in Basque</param>
        /// <param name="nameCatalan">Province name in Catalan</param>
        /// <param name="nameDutch">Province name in Dutch</param>
        /// <param name="nameGalician">Province name in Galician</param>
        /// <param name="nameGerman">Province name in German</param>
        /// <param name="nameItalian">Province name in Italian</param>
        /// <param name="namePortuguese">Province name in Portuguese</param>
        public Province(int idProvince, int idRegion, string idCountry, string nameSpanish, string nameEnglish, string nameFrench,string nameBasque, string nameCatalan, string nameDutch, string nameGalician, string nameGerman, string nameItalian, string namePortuguese)
        {
            this.idProvince = idProvince;
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
        /// Province identifier
        /// </summary>
        [JsonPropertyName("idProvince")]
        public int idProvince { get; set; }

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
        /// Province name in Spanish
        /// </summary>
        [JsonPropertyName("nameSpanish")]
        public string nameSpanish { get; set; }

        /// <summary>
        /// Province name in English
        /// </summary>
        [JsonPropertyName("nameEnglish")]
        public string nameEnglish { get; set; }

        /// <summary>
        /// Province name in French
        /// </summary>
        [JsonPropertyName("nameFrench")]
        public string nameFrench { get; set; }

        /// <summary>
        /// Province name in Basque
        /// </summary>
        [JsonPropertyName("nameBasque")]
        public string nameBasque { get; set; }

        /// <summary>
        /// Province name in Catalan
        /// </summary>
        [JsonPropertyName("nameCatalan")]
        public string nameCatalan { get; set; }

        /// <summary>
        /// Province name in Dutch
        /// </summary>
        [JsonPropertyName("nameDutch")]
        public string nameDutch { get; set; }

        /// <summary>
        /// Province name in Galician
        /// </summary>
        [JsonPropertyName("nameGalician")]
        public string nameGalician { get; set; }

        /// <summary>
        /// Province name in German
        /// </summary>
        [JsonPropertyName("nameGerman")]
        public string nameGerman { get; set; }

        /// <summary>
        /// Province name in Italian
        /// </summary>
        [JsonPropertyName("nameItalian")]
        public string nameItalian { get; set; }

        /// <summary>
        /// Province name in Portuguese
        /// </summary>
        [JsonPropertyName("namePortuguese")]
        public string namePortuguese { get; set; }
    }
}
