using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class for countries
    /// Author: Pablo Ahita del Barrio
    /// Version: 1
    /// </summary>
    public class Country
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="idCountry">Country identifier</param>
        /// <param name="nameSpanish">Country name in Spanish</param>
        /// <param name="nameEnglish">Country name in English</param>
        /// <param name="nameFrench">Country name in French</param>
        /// <param name="nameBasque">Country name in Basque</param>
        /// <param name="nameCatalan">Country name in Catalan</param>
        /// <param name="nameDutch">Country name in Dutch</param>
        /// <param name="nameGalician">Country name in Galician</param>
        /// <param name="nameGerman">Country name in German</param>
        /// <param name="nameItalian">Country name in Italian</param>
        /// <param name="namePortuguese">Country name in Portuguese</param>
        /// <param name="phone_code">Country phone code</param>
        /// <param name="flag">Country flag emoji</param>
        public Country(string idCountry, string nameSpanish, string nameEnglish, string nameFrench,string nameBasque, string nameCatalan, string nameDutch, string nameGalician, string nameGerman, string nameItalian, string namePortuguese, string? phone_code, string? flag) {
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
            this.idCountry = idCountry;
            this.phone_code=phone_code;
            this.flag=flag;
        }

        /// <summary>
        /// Country identifier
        /// </summary>
        [JsonPropertyName("idCountry")]
        public string idCountry { get; set; }

        /// <summary>
        /// Country name in Spanish
        /// </summary>
        [JsonPropertyName("nameSpanish")]
        public string nameSpanish { get; set; }

        /// <summary>
        /// Country name in English
        /// </summary>
        [JsonPropertyName("nameEnglish")]
        public string nameEnglish { get; set; }

        /// <summary>
        /// Country name in French
        /// </summary>
        [JsonPropertyName("nameFrench")]
        public string nameFrench { get; set; }

        /// <summary>
        /// Country name in Basque
        /// </summary>
        [JsonPropertyName("nameBasque")]
        public string nameBasque { get; set; }

        /// <summary>
        /// Country name in Catalan
        /// </summary>
        [JsonPropertyName("nameCatalan")]
        public string nameCatalan { get; set; }

        /// <summary>
        /// Country name in Dutch
        /// </summary>
        [JsonPropertyName("nameDutch")]
        public string nameDutch { get; set; }

        /// <summary>
        /// Country name in Galician
        /// </summary>
        [JsonPropertyName("nameGalician")]
        public string nameGalician { get; set; }

        /// <summary>
        /// Country name in German
        /// </summary>
        [JsonPropertyName("nameGerman")]
        public string nameGerman { get; set; }

        /// <summary>
        /// Country name in Italian
        /// </summary>
        [JsonPropertyName("nameItalian")]
        public string nameItalian { get; set; }

        /// <summary>
        /// Country name in Portuguese
        /// </summary>
        [JsonPropertyName("namePortuguese")]
        public string namePortuguese { get; set; }

        /// <summary>
        /// Country phone code
        /// </summary>
        [JsonPropertyName("phone_code")]
        public string? phone_code { get; set; }

        /// <summary>
        /// Country flag emoji
        /// </summary>
        [JsonPropertyName("flag")]
        public string? flag { get; set; }

    }
}
