using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    public class IndicatorsEvaluationReg
    {
        public IndicatorsEvaluationReg(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, string orgTypeEvaluator, int idEvaluatedOrganization, string orgTypeEvaluated, string illness, int idCenter, int idAmbit, int idIndicator, int idEvidence, int isMarked, int indicatorVersion)
        {
            this.evaluationDate = evaluationDate;
            this.idEvaluatorTeam=idEvaluatorTeam;
            this.idEvaluatorOrganization = idEvaluatorOrganization;
            this.orgTypeEvaluator = orgTypeEvaluator;
            this.idEvaluatedOrganization = idEvaluatedOrganization;
            this.orgTypeEvaluated = orgTypeEvaluated;
            this.illness = illness;
            this.idCenter=idCenter;
            this.idAmbit=idAmbit;
            this.idIndicator = idIndicator;
            this.idEvidence = idEvidence;
            this.isMarked = isMarked;
            this.indicatorVersion = indicatorVersion;
        }

        [JsonPropertyName("evaluationDate")]
        public long evaluationDate { get; set; }
        [JsonPropertyName("idEvaluatorTeam")]
        public int idEvaluatorTeam { get; set; }
        [JsonPropertyName("idEvaluatorOrganization")]
        public int idEvaluatorOrganization { get; set; }
        [JsonPropertyName("orgTypeEvaluator")]
        public string orgTypeEvaluator { get; set; }
        [JsonPropertyName("idEvaluatedOrganization")]
        public int idEvaluatedOrganization { get; set; }
        [JsonPropertyName("orgTypeEvaluated")]
        public string orgTypeEvaluated { get; set; }
        [JsonPropertyName("illness")]
        public string illness { get; set; }

        [JsonPropertyName("idCenter")]
        public int idCenter { get; set; }

        [JsonPropertyName("idAmbit")]
        public int idAmbit { get; set; }

        [JsonPropertyName("idIndicator")]
        public int idIndicator { get; set; }
        [JsonPropertyName("idEvidence")]
        public int idEvidence { get; set; }
        [JsonPropertyName("isMarked")]
        public int isMarked { get; set; }

        [JsonPropertyName("indicatorVersion")]
        public int indicatorVersion { get; set; }
    }
}
