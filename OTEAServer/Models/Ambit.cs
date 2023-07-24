using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;
using System.ComponentModel.DataAnnotations;

namespace OTEAServer.Models
{
    public class Ambit
    {
        public Ambit(int idAmbit, string descriptionEnglish, string descriptionSpanish, string descriptionFrench) { 
            this.idAmbit = idAmbit;
            this.descriptionEnglish = descriptionEnglish;
            this.descriptionSpanish = descriptionSpanish;
            this.descriptionFrench = descriptionFrench;
        }

        [Key]
        [JsonProperty("idAmbit")]
        public int idAmbit { get; set; }

        [JsonProperty("descriptionEnglish")]
        public string descriptionEnglish { get; set; }

        [JsonProperty("descriptionSpanish")]
        public string descriptionSpanish { get; set; }

        [JsonProperty("descriptionFrench")]
        public string descriptionFrench { get; set; }
    }
}
