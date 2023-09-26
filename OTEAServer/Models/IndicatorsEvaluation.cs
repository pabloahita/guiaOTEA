using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    public class IndicatorsEvaluation
    {
        public IndicatorsEvaluation(long evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, int idEvaluatorTeam, int idEvaluatorOrganization, string orgTypeEvaluator, string illness, int idCenter, string observationsSpanish, string observationsEnglish, string observationsFrench, string observationsBasque, string observationsCatalan, string observationsDutch, string observationsGalician, string observationsGerman, string observationsItalian, string observationsPortuguese, string conclusionsSpanish, string conclusionsEnglish, string conclusionsFrench, string conclusionsBasque, string conclusionsCatalan, string conclusionsDutch, string conclusionsGalician, string conclusionsGerman, string conclusionsItalian, string conclusionsPortuguese, int scoreLevel1, int scoreLevel2, int scoreLevel3, int scoreLevel4, int scoreLevel5, int scoreLevel6, int totalScore, int isFinished)
        {
            this.evaluationDate= evaluationDate;
            this.idEvaluatedOrganization = idEvaluatedOrganization;
            this.orgTypeEvaluated= orgTypeEvaluated;
            this.idEvaluatorTeam = idEvaluatorTeam;
            this.idEvaluatorOrganization = idEvaluatorOrganization;
            this.orgTypeEvaluator = orgTypeEvaluator;
            this.illness = illness;
            this.idCenter=idCenter;
            this.observationsSpanish = observationsSpanish;
            this.observationsEnglish = observationsEnglish;
            this.observationsFrench = observationsFrench;
            this.observationsBasque = observationsBasque;
            this.observationsCatalan = observationsCatalan;
            this.observationsDutch = observationsDutch;
            this.observationsGalician = observationsGalician;
            this.observationsGerman = observationsGerman;
            this.observationsItalian = observationsItalian;
            this.observationsPortuguese = observationsPortuguese;
            this.conclusionsSpanish = conclusionsSpanish;
            this.conclusionsEnglish = conclusionsEnglish;
            this.conclusionsFrench = conclusionsFrench;
            this.conclusionsBasque = conclusionsBasque;
            this.conclusionsCatalan = conclusionsCatalan;
            this.conclusionsDutch = conclusionsDutch;
            this.conclusionsGalician = conclusionsGalician;
            this.conclusionsGerman = conclusionsGerman;
            this.conclusionsItalian = conclusionsItalian;
            this.conclusionsPortuguese = conclusionsPortuguese;
            this.scoreLevel1 = scoreLevel1;
            this.scoreLevel2 = scoreLevel2;
            this.scoreLevel3 = scoreLevel3;
            this.scoreLevel4 = scoreLevel4;
            this.scoreLevel5 = scoreLevel5;
            this.scoreLevel6 = scoreLevel6;
            this.totalScore = totalScore;
            this.isFinished = isFinished;
        }


        [JsonPropertyName("evaluationDate")]
        public long evaluationDate { get; set; }

        [JsonPropertyName("idEvaluatedOrganization")]
        public int idEvaluatedOrganization { get; set; }

        [JsonPropertyName("orgTypeEvaluated")]
        public string orgTypeEvaluated { get; set; }
       
        [JsonPropertyName("idEvaluatorTeam")]
        public int idEvaluatorTeam { get; set; }

        [JsonPropertyName("idEvaluatorOrganization")]
        public int idEvaluatorOrganization { get; set; }

        [JsonPropertyName("orgTypeEvaluator")]
        public string orgTypeEvaluator { get; set; }
        
        [JsonPropertyName("illness")]
        public string illness { get; set; }

        [JsonPropertyName("idCenter")]
        public int idCenter { get; set; }

        [JsonPropertyName("scoreLevel1")]
        public int scoreLevel1 { get; set; }

        [JsonPropertyName("scoreLevel2")]
        public int scoreLevel2 { get; set; }

        [JsonPropertyName("scoreLevel3")]
        public int scoreLevel3 { get; set; }

        [JsonPropertyName("scoreLevel4")]
        public int scoreLevel4 { get; set; }

        [JsonPropertyName("scoreLevel5")]
        public int scoreLevel5 { get; set; }

        [JsonPropertyName("scoreLevel6")]
        public int scoreLevel6 { get; set; }

        [JsonPropertyName("totalScore")]
        public int totalScore { get; set; }
        
        [JsonPropertyName("observationsSpanish")]
        public string observationsSpanish { get; set; }

        [JsonPropertyName("observationsEnglish")]
        public string observationsEnglish { get; set; }

        [JsonPropertyName("observationsFrench")]
        public string observationsFrench { get; set; }

        [JsonPropertyName("observationsBasque")]
        public string observationsBasque { get; set; }

        [JsonPropertyName("observationsCatalan")]
        public string observationsCatalan { get; set; }

        [JsonPropertyName("observationsDutch")]
        public string observationsDutch { get; set; }

        [JsonPropertyName("observationsGalician")]
        public string observationsGalician { get; set; }

        [JsonPropertyName("observationsGerman")]
        public string observationsGerman { get; set; }

        [JsonPropertyName("observationsItalian")]
        public string observationsItalian { get; set; }

        [JsonPropertyName("observationsPortuguese")]
        public string observationsPortuguese { get; set; }

        [JsonPropertyName("conclusionsSpanish")]
        public string conclusionsSpanish { get; set; }

        [JsonPropertyName("conclusionsEnglish")]
        public string conclusionsEnglish { get; set; }

        [JsonPropertyName("conclusionsFrench")]
        public string conclusionsFrench { get; set; }

        [JsonPropertyName("conclusionsBasque")]
        public string conclusionsBasque { get; set; }

        [JsonPropertyName("conclusionsCatalan")]
        public string conclusionsCatalan { get; set; }

        [JsonPropertyName("conclusionsDutch")]
        public string conclusionsDutch { get; set; }

        [JsonPropertyName("conclusionsGalician")]
        public string conclusionsGalician { get; set; }

        [JsonPropertyName("conclusionsGerman")]
        public string conclusionsGerman { get; set; }

        [JsonPropertyName("conclusionsItalian")]
        public string conclusionsItalian { get; set; }

        [JsonPropertyName("conclusionsPortuguese")]
        public string conclusionsPortuguese { get; set; }

        [JsonPropertyName("isFinished")]
        public int isFinished { get; set; }


    }
}
