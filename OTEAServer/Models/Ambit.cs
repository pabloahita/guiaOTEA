using System.Text.Json.Serialization;
using System.ComponentModel.DataAnnotations;

namespace OTEAServer.Models
{

    /// <summary>
    /// Model class for ambits
    /// Author: Pablo Ahita del Barrio
    /// Version: 1
    /// </summary>
    public class Ambit
    {

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="descriptionEnglish">Ambit description in English</param>
        /// <param name="descriptionSpanish">Ambit description in Spanish</param>
        /// <param name="descriptionFrench">Ambit description in French</param>
        /// <param name="descriptionBasque">Ambit description in Basque</param>
        /// <param name="descriptionCatalan">Ambit description in Catalan</param>
        /// <param name="descriptionDutch">Ambit description in Dutch</param>
        /// <param name="descriptionGalician">Ambit description in Galician</param>
        /// <param name="descriptionGerman">Ambit description in German</param>
        /// <param name="descriptionItalian">Ambit description in Italian</param>
        /// <param name="descriptionPortuguese">Ambit description in Portuguese</param>
        public Ambit(int idAmbit, string descriptionEnglish, string descriptionSpanish, string descriptionFrench,string descriptionBasque, string descriptionCatalan, string descriptionDutch, string descriptionGalician, string descriptionGerman, string descriptionItalian, string descriptionPortuguese) { 
            this.idAmbit = idAmbit;
            this.descriptionSpanish = descriptionSpanish;
            this.descriptionEnglish = descriptionEnglish;
            this.descriptionFrench = descriptionFrench;
            this.descriptionBasque = descriptionBasque;
            this.descriptionCatalan = descriptionCatalan;
            this.descriptionDutch = descriptionDutch;
            this.descriptionGalician = descriptionGalician;
            this.descriptionGerman = descriptionGerman;
            this.descriptionItalian = descriptionItalian;
            this.descriptionPortuguese = descriptionPortuguese;
        }

        /// <summary>
        /// Ambit identifier
        /// </summary>
        [Key]
        [JsonPropertyName("idAmbit")]
        public int idAmbit { get; set; }

        /// <summary>
        /// Ambit description in Spanish
        /// </summary>
        [JsonPropertyName("descriptionSpanish")]
        public string descriptionSpanish { get; set; }

        /// <summary>
        /// Ambit description in English
        /// </summary>
        [JsonPropertyName("descriptionEnglish")]
        public string descriptionEnglish { get; set; }

        /// <summary>
        /// Ambit description in French
        /// </summary>
        [JsonPropertyName("descriptionFrench")]
        public string descriptionFrench { get; set; }

        /// <summary>
        /// Ambit description in Basque
        /// </summary>
        [JsonPropertyName("descriptionBasque")]
        public string descriptionBasque { get; set; }

        /// <summary>
        /// Ambit description in Catalan
        /// </summary>
        [JsonPropertyName("descriptionCatalan")]
        public string descriptionCatalan { get; set; }

        /// <summary>
        /// Ambit description in Dutch
        /// </summary>
        [JsonPropertyName("descriptionDutch")]
        public string descriptionDutch { get; set; }


        /// <summary>
        /// Ambit description in Galician
        /// </summary>
        [JsonPropertyName("descriptionGalician")]
        public string descriptionGalician { get; set; }

        /// <summary>
        /// Ambit description in German
        /// </summary>
        [JsonPropertyName("descriptionGerman")]
        public string descriptionGerman { get; set; }

        /// <summary>
        /// Ambit description in Italian
        /// </summary>
        [JsonPropertyName("descriptionItalian")]
        public string descriptionItalian { get; set; }

        /// <summary>
        /// Ambit description in Portuguese
        /// </summary>
        [JsonPropertyName("descriptionPortuguese")]
        public string descriptionPortuguese { get; set; }
    }
}
