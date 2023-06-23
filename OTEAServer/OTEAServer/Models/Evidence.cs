using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class Evidence
    {
        public Evidence(int idEvidence, int idIndicator, string indicatorType, string descriptionEnglish, string descriptionSpanish, string descriptionFrench, int evidenceValue) {
            this.idEvidence = idEvidence;
            this.idIndicator = idIndicator;
            this.indicatorType = indicatorType;
            this.descriptionEnglish = descriptionEnglish;
            this.descriptionSpanish = descriptionSpanish;
            this.descriptionFrench = descriptionFrench;
            this.evidenceValue = evidenceValue;
        }


        [JsonProperty("idEvidence")]
        public int idEvidence { get; set; }

        [JsonProperty("idIndicator")]
        public int idIndicator { get; set; }

        [JsonProperty("indicatorType")]
        public string indicatorType { get; set; }

        [JsonProperty("descriptionEnglish")]
        public string descriptionEnglish { get; set; }

        [JsonProperty("descriptionSpanish")]
        public string descriptionSpanish { get; set; }

        [JsonProperty("descriptionFrench")]
        public string descriptionFrench { get; set; }

        [JsonProperty("evidenceValue")]
        public int evidenceValue{ get; set; }
    }
}
