using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class Indicator
    {
        public Indicator(int indicatorId, int idAmbit, string indicatorType, string descriptionEnglish, string descriptionSpanish, string descriptionFrench, int indicatorPriority, int indicatorVersion, int isActive)
        {
            this.indicatorId = indicatorId;
            this.indicatorType = indicatorType;
            this.idAmbit = idAmbit;
            this.descriptionEnglish = descriptionEnglish;
            this.descriptionSpanish = descriptionSpanish;
            this.descriptionFrench = descriptionFrench;
            this.indicatorPriority = indicatorPriority;
            this.indicatorVersion = indicatorVersion;
            this.isActive = isActive;
        }


        [JsonProperty("indicatorId")]
        public int indicatorId { get; set; }

        [JsonProperty("idAmbit")]
        public int idAmbit { get; set; }

        [JsonProperty("indicatorType")]
        public string indicatorType { get; set; }

        [JsonProperty("descriptionEnglish")]
        public string descriptionEnglish { get; set; }
        
        [JsonProperty("descriptionSpanish")]
        public string descriptionSpanish { get; set; }
        
        [JsonProperty("descriptionFrench")]
        public string descriptionFrench { get; set; }

        [JsonProperty("indicatorPriority")]
        public int indicatorPriority { get; set; }

        [JsonProperty("indicatorVersion")]
        public int indicatorVersion { get; set; }

        [JsonProperty("isActive")]
        public int isActive { get; set; }
    }
}
