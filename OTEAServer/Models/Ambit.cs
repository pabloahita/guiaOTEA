using System.Text.Json.Serialization;
using System.ComponentModel.DataAnnotations;

namespace OTEAServer.Models
{
    public class Ambit
    {
        public Ambit(int idAmbit, string descriptionSpanish, string descriptionEnglish, string descriptionFrench,string descriptionBasque, string descriptionCatalan, string descriptionDutch, string descriptionGalician, string descriptionGerman, string descriptionItalian, string descriptionPortuguese) { 
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

        [Key]
        [JsonPropertyName("idAmbit")]
        public int idAmbit { get; set; }

        [JsonPropertyName("descriptionSpanish")]
        public string descriptionSpanish { get; set; }

        [JsonPropertyName("descriptionEnglish")]
        public string descriptionEnglish { get; set; }

        [JsonPropertyName("descriptionFrench")]
        public string descriptionFrench { get; set; }

        [JsonPropertyName("descriptionBasque")]
        public string descriptionBasque { get; set; }

        [JsonPropertyName("descriptionCatalan")]
        public string descriptionCatalan { get; set; }

        [JsonPropertyName("descriptionDutch")]
        public string descriptionDutch { get; set; }

        [JsonPropertyName("descriptionGalician")]
        public string descriptionGalician { get; set; }

        [JsonPropertyName("descriptionGerman")]
        public string descriptionGerman { get; set; }

        [JsonPropertyName("descriptionItalian")]
        public string descriptionItalian { get; set; }

        [JsonPropertyName("descriptionPortuguese")]
        public string descriptionPortuguese { get; set; }
    }
}
