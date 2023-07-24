using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class IndicatorsEvaluation
    {
        public IndicatorsEvaluation(long evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, int idEvaluatorTeam, int idEvaluatorOrganization, string orgTypeEvaluator, string illness, string observations, string conclusions, int scoreLevel1, int scoreLevel2, int scoreLevel3, int scoreLevel4, int scoreLevel5, int scoreLevel6, int totalScore, int isFinished)
        {
            this.evaluationDate= evaluationDate;
            this.idEvaluatedOrganization = idEvaluatedOrganization;
            this.orgTypeEvaluated= orgTypeEvaluated;
            this.idEvaluatorTeam = idEvaluatorTeam;
            this.idEvaluatorOrganization = idEvaluatorOrganization;
            this.orgTypeEvaluator = orgTypeEvaluator;
            this.illness = illness;
            this.observations = observations;
            this.conclusions = conclusions;
            this.scoreLevel1 = scoreLevel1;
            this.scoreLevel2 = scoreLevel2;
            this.scoreLevel3 = scoreLevel3;
            this.scoreLevel4 = scoreLevel4;
            this.scoreLevel5 = scoreLevel5;
            this.scoreLevel6 = scoreLevel6;
            this.totalScore = totalScore;
            this.isFinished = isFinished;
        }


        [JsonProperty("evaluationDate")]
        public long evaluationDate { get; set; }

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

        [JsonProperty("scoreLevel1")]
        public int scoreLevel1 { get; set; }

        [JsonProperty("scoreLevel2")]
        public int scoreLevel2 { get; set; }

        [JsonProperty("scoreLevel3")]
        public int scoreLevel3 { get; set; }

        [JsonProperty("scoreLevel4")]
        public int scoreLevel4 { get; set; }

        [JsonProperty("scoreLevel5")]
        public int scoreLevel5 { get; set; }

        [JsonProperty("scoreLevel6")]
        public int scoreLevel6 { get; set; }

        [JsonProperty("totalScore")]
        public int totalScore { get; set; }

        [JsonProperty("observations")]
        public string observations { get; set; }

        [JsonProperty("conclusions")]
        public string conclusions { get; set; }

        [JsonProperty("isFinished")]
        public int isFinished { get; set; }


    }
}
