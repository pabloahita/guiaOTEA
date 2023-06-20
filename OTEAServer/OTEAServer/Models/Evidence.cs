using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class Evidence
    {
        public Evidence(int idEvidence, int idIndicator, string indicatorType, string evidenceDescription, int evidenceValue) {
            this.IdEvidence = idEvidence;
            this.IdIndicator = idIndicator;
            this.IndicatorType = indicatorType;
            this.EvidenceDescription= evidenceDescription;
            this.EvidenceValue = evidenceValue;
        }


        [JsonProperty("idEvidence")]
        public int IdEvidence { get; set; }

        [JsonProperty("idIndicator")]
        public int IdIndicator { get; set; }

        [JsonProperty("indicatorType")]
        public string IndicatorType { get; set; }

        [JsonProperty("evidenceDescription")]
        public string EvidenceDescription { get; set; }

        [JsonProperty("evidenceValue")]
        public int EvidenceValue{ get; set; }
    }
}
