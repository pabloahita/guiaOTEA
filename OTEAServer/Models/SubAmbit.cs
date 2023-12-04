using System.Text.Json.Serialization;
using System.ComponentModel.DataAnnotations;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class for first level division of ambits
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    public class SubAmbit
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="idSubAmbit">First division of ambit identifier</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="descriptionEnglish">SubAmbit description in English</param>
        /// <param name="descriptionSpanish">SubAmbit description in Spanish</param>
        /// <param name="descriptionFrench">SubAmbit description in French</param>
        /// <param name="descriptionBasque">SubAmbit description in Basque</param>
        /// <param name="descriptionCatalan">SubAmbit description in Catalan</param>
        /// <param name="descriptionDutch">SubAmbit description in Dutch</param>
        /// <param name="descriptionGalician">SubAmbit description in Galician</param>
        /// <param name="descriptionGerman">SubAmbit description in German</param>
        /// <param name="descriptionItalian">SubAmbit description in Italian</param>
        /// <param name="descriptionPortuguese">SubAmbit description in Portuguese</param>
        public SubAmbit(int idSubAmbit, int idAmbit, String descriptionEnglish, String descriptionSpanish, string descriptionFrench, string descriptionBasque, string descriptionCatalan, string descriptionDutch, string descriptionGalician, string descriptionGerman, string descriptionItalian, string descriptionPortuguese)
        {
            this.idSubAmbit = idSubAmbit;
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
        /// First division of ambit identifier
        /// </summary>
        [JsonPropertyName("idSubAmbit")]
        public int idSubAmbit { get; set; }

        /// <summary>
        /// Ambit identifier
        /// </summary>
        [Key]
        [JsonPropertyName("idAmbit")]
        public int idAmbit { get; set; }

        /// <summary>
        /// SubAmbit description in Spanish
        /// </summary>
        [JsonPropertyName("descriptionSpanish")]
        public string descriptionSpanish { get; set; }

        /// <summary>
        /// SubAmbit description in English
        /// </summary>
        [JsonPropertyName("descriptionEnglish")]
        public string descriptionEnglish { get; set; }

        /// <summary>
        /// SubAmbit description in French
        /// </summary>
        [JsonPropertyName("descriptionFrench")]
        public string descriptionFrench { get; set; }

        /// <summary>
        /// SubAmbit description in Basque
        /// </summary>
        [JsonPropertyName("descriptionBasque")]
        public string descriptionBasque { get; set; }

        /// <summary>
        /// SubAmbit description in Catalan
        /// </summary>
        [JsonPropertyName("descriptionCatalan")]
        public string descriptionCatalan { get; set; }

        /// <summary>
        /// SubAmbit description in Dutch
        /// </summary>
        [JsonPropertyName("descriptionDutch")]
        public string descriptionDutch { get; set; }


        /// <summary>
        /// SubAmbit description in Galician
        /// </summary>
        [JsonPropertyName("descriptionGalician")]
        public string descriptionGalician { get; set; }

        /// <summary>
        /// SubAmbit description in German
        /// </summary>
        [JsonPropertyName("descriptionGerman")]
        public string descriptionGerman { get; set; }

        /// <summary>
        /// SubAmbit description in Italian
        /// </summary>
        [JsonPropertyName("descriptionItalian")]
        public string descriptionItalian { get; set; }

        /// <summary>
        /// SubAmbit description in Portuguese
        /// </summary>
        [JsonPropertyName("descriptionPortuguese")]
        public string descriptionPortuguese { get; set; }
    }
}
