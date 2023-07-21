using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class Indicator
    {
        public Indicator(int indicatorId, string indicatorType, string descriptionEnglish, string descriptionSpanish, string descriptionFrench, int indicatorPriority, int indicatorVersion) {
            this.indicatorId = indicatorId;
            this.indicatorType = indicatorType;
            this.descriptionEnglish = descriptionEnglish;
            this.descriptionSpanish = descriptionSpanish;
            this.descriptionFrench = descriptionFrench;
            this.indicatorPriority = indicatorPriority;
            this.indicatorVersion = indicatorVersion;
        }


        [JsonProperty("indicatorId")]
        public int indicatorId { get; set; }

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
    }
}
