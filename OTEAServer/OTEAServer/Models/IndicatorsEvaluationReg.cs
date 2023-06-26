using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class IndicatorsEvaluationReg
    {
        public IndicatorsEvaluationReg(DateTime evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, string illness, int indicatorId, int idEvidence, int isMarked, int indicatorVersion)
        {
            this.evaluationDate = evaluationDate;
            this.idEvaluatedOrganization = idEvaluatedOrganization;
            this.orgTypeEvaluated = orgTypeEvaluated;
            this.illness = illness;
            this.indicatorId = indicatorId;
            this.idEvidence = idEvidence;
            this.isMarked = isMarked;
            this.indicatorVersion = indicatorVersion;
        }

        [JsonProperty("evaluationDate")]
        public DateTime evaluationDate { get; set; }
        [JsonProperty("idEvaluatedOrganization")]
        public int idEvaluatedOrganization { get; set; }
        [JsonProperty("orgTypeEvaluated")]
        public string orgTypeEvaluated { get; set; }
        [JsonProperty("illness")]
        public string illness { get; set; }
        [JsonProperty("indicatorId")]
        public int indicatorId { get; set; }
        [JsonProperty("idEvidence")]
        public int idEvidence { get; set; }
        [JsonProperty("isMarked")]
        public int isMarked { get; set; }

        [JsonProperty("indicatorVersion")]
        public int indicatorVersion { get; set; }
    }
}
