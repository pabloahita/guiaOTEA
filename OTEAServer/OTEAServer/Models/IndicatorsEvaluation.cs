using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class IndicatorsEvaluation
    {
        public IndicatorsEvaluation(DateTime evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, int idEvaluatorTeam, int idEvaluatorOrganization, string orgTypeEvaluator, string illness, int totalScore)
        {
            this.evaluationDate= evaluationDate;
            this.idEvaluatedOrganization = idEvaluatedOrganization;
            this.orgTypeEvaluated= orgTypeEvaluated;
            this.idEvaluatorTeam = idEvaluatorTeam;
            this.idEvaluatorOrganization = idEvaluatorOrganization;
            this.orgTypeEvaluator = orgTypeEvaluator;
            this.illness = illness;
            this.totalScore = totalScore;
        }


        [JsonProperty("evaluationDate")]
        public DateTime evaluationDate { get; set; }

        [JsonProperty("idEvaluatedOrganization")]
        public int idEvaluatedOrganization { get; set; }

        [JsonProperty("orgTypeEvaluated")]
        public string orgTypeEvaluated { get; set; }
       
        [JsonProperty("idEvaluatorTeam")]
        public int idEvaluatorTeam { get; set; }

        [JsonProperty("idEvaluatorOrganization")]
        public int idEvaluatorOrganization { get; set; }

        [JsonProperty("orgTypeEvaluator")]
        public string orgTypeEvaluator { get; set; }
        
        [JsonProperty("illness")]
        public string illness { get; set; }

        [JsonProperty("totalScore")]
        public int totalScore { get; set; }

    }
}
