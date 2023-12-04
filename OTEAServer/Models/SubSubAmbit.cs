using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class for second level division of ambits
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    public class SubSubAmbit
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="idSubSubAmbit">Second division of ambit identifier</param>
        /// <param name="idSubAmbit">First division of ambit identifier</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="descriptionEnglish">SubSubAmbit description in English</param>
        /// <param name="descriptionSpanish">SubSubAmbit description in Spanish</param>
        /// <param name="descriptionFrench">SubSubAmbit description in French</param>
        /// <param name="descriptionBasque">SubSubAmbit description in Basque</param>
        /// <param name="descriptionCatalan">SubSubAmbit description in Catalan</param>
        /// <param name="descriptionDutch">SubSubAmbit description in Dutch</param>
        /// <param name="descriptionGalician">SubSubAmbit description in Galician</param>
        /// <param name="descriptionGerman">SubSubAmbit description in German</param>
        /// <param name="descriptionItalian">SubSubAmbit description in Italian</param>
        /// <param name="descriptionPortuguese">SubSubAmbit description in Portuguese</param>
        public SubSubAmbit(int idSubSubAmbit, int idSubAmbit, int idAmbit, String descriptionEnglish, String descriptionSpanish, string descriptionFrench, string descriptionBasque, string descriptionCatalan, string descriptionDutch, string descriptionGalician, string descriptionGerman, string descriptionItalian, string descriptionPortuguese)
        {
            this.idSubSubAmbit = idSubSubAmbit;
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
        /// Second division of ambit identifier
        /// </summary>
        [JsonPropertyName("idSubSubAmbit")]
        public int idSubSubAmbit { get; set; }

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
        /// SubSubAmbit description in Spanish
        /// </summary>
        [JsonPropertyName("descriptionSpanish")]
        public string descriptionSpanish { get; set; }

        /// <summary>
        /// SubSubAmbit description in English
        /// </summary>
        [JsonPropertyName("descriptionEnglish")]
        public string descriptionEnglish { get; set; }

        /// <summary>
        /// SubSubAmbit description in French
        /// </summary>
        [JsonPropertyName("descriptionFrench")]
        public string descriptionFrench { get; set; }

        /// <summary>
        /// SubSubAmbit description in Basque
        /// </summary>
        [JsonPropertyName("descriptionBasque")]
        public string descriptionBasque { get; set; }

        /// <summary>
        /// SubSubAmbit description in Catalan
        /// </summary>
        [JsonPropertyName("descriptionCatalan")]
        public string descriptionCatalan { get; set; }

        /// <summary>
        /// SubSubAmbit description in Dutch
        /// </summary>
        [JsonPropertyName("descriptionDutch")]
        public string descriptionDutch { get; set; }


        /// <summary>
        /// SubSubAmbit description in Galician
        /// </summary>
        [JsonPropertyName("descriptionGalician")]
        public string descriptionGalician { get; set; }

        /// <summary>
        /// SubSubAmbit description in German
        /// </summary>
        [JsonPropertyName("descriptionGerman")]
        public string descriptionGerman { get; set; }

        /// <summary>
        /// SubSubAmbit description in Italian
        /// </summary>
        [JsonPropertyName("descriptionItalian")]
        public string descriptionItalian { get; set; }

        /// <summary>
        /// SubSubAmbit description in Portuguese
        /// </summary>
        [JsonPropertyName("descriptionPortuguese")]
        public string descriptionPortuguese { get; set; }
    }
}
