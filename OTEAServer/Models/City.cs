using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class for cities
    /// Author: Pablo Ahita del Barrio
    /// Version: 1
    /// </summary>
    public class City
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="idCity">City identifier</param>
        /// <param name="idProvince">Province identifier</param>
        /// <param name="idRegion">Region identifier</param>
        /// <param name="idCountry">Country identifier</param>
        /// <param name="nameSpanish">City name in Spanish</param>
        /// <param name="nameEnglish">City name in English</param>
        /// <param name="nameFrench">City name in French</param>
        /// <param name="nameBasque">City name in Basque</param>
        /// <param name="nameCatalan">City name in Catalan</param>
        /// <param name="nameDutch">City name in Dutch</param>
        /// <param name="nameGalician">City name in Galician</param>
        /// <param name="nameGerman">City name in German</param>
        /// <param name="nameItalian">City name in Italian</param>
        /// <param name="namePortuguese">City name in Portuguese</param>
        public City(int idCity, int idProvince, int idRegion, string idCountry, string nameSpanish, string nameEnglish, string nameFrench,string nameBasque, string nameCatalan, string nameDutch, string nameGalician, string nameGerman, string nameItalian, string namePortuguese) {
            this.idCity = idCity;
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
        /// City identifier
        /// </summary>
        [JsonPropertyName("idCity")]
        public int idCity { get; set; }

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
        /// City name in Spanish
        /// </summary>
        [JsonPropertyName("nameSpanish")]
        public string nameSpanish { get; set; }

        /// <summary>
        /// City name in English
        /// </summary>
        [JsonPropertyName("nameEnglish")]
        public string nameEnglish { get; set; }

        /// <summary>
        /// City name in French
        /// </summary>
        [JsonPropertyName("nameFrench")]
        public string nameFrench { get; set; }

        /// <summary>
        /// City name in Basque
        /// </summary>
        [JsonPropertyName("nameBasque")]
        public string nameBasque { get; set; }

        /// <summary>
        /// City name in Catalan
        /// </summary>
        [JsonPropertyName("nameCatalan")]
        public string nameCatalan { get; set; }

        /// <summary>
        /// City name in Dutch
        /// </summary>
        [JsonPropertyName("nameDutch")]
        public string nameDutch { get; set; }

        /// <summary>
        /// City name in Galician
        /// </summary>
        [JsonPropertyName("nameGalician")]
        public string nameGalician { get; set; }

        /// <summary>
        /// City name in German
        /// </summary>
        [JsonPropertyName("nameGerman")]
        public string nameGerman { get; set; }

        /// <summary>
        /// City name in Italian
        /// </summary>
        [JsonPropertyName("nameItalian")]
        public string nameItalian { get; set; }

        /// <summary>
        /// City name in Portuguese
        /// </summary>
        [JsonPropertyName("namePortuguese")]
        public string namePortuguese { get; set; }
    }
}
