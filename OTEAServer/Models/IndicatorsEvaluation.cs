using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    public class IndicatorsEvaluation
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="evaluationDate">Evaluation date in timestamp</param>
        /// <param name="idEvaluatorTeam">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrganization">Identifier of the evaluator organization that will do the indicators evaluation</param>
        /// <param name="orgTypeEvaluator">Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"</param>
        /// <param name="idEvaluatedOrganization">Identifier of the external organization that will receive the indicators evaluation</param>
        /// <param name="orgTypeEvaluated">Organization type of the external organization that will receive the indicators evaluation. It will be always "EVALUATED"</param>
        /// <param name="illness">Illness of the external organization that will receive the indicators evaluation. In the case of Fundación Miradas, it will be "AUTISM"</param>
        /// <param name="idCenter">Center identifier of the external organization</param>
        /// <param name="observationsEnglish">Evaluation's observations in English</param>
        /// <param name="observationsSpanish">Evaluation's observations in Spanish</param>
        /// <param name="observationsFrench">Evaluation's observations in French</param>
        /// <param name="observationsBasque">Evaluation's observations in Basque</param>
        /// <param name="observationsCatalan">Evaluation's observations in Catalan</param>
        /// <param name="observationsDutch">Evaluation's observations in Dutch</param>
        /// <param name="observationsGalician">Evaluation's observations in Galician</param>
        /// <param name="observationsGerman">Evaluation's observations in German</param>
        /// <param name="observationsItalian">Evaluation's observations in Italian</param>
        /// <param name="observationsPortuguese">Evaluation's observations in Portuguese</param>
        /// <param name="conclusionsEnglish">Evaluation's conclusions in English</param>
        /// <param name="conclusionsSpanish">Evaluation's conclusions in Spanish</param>
        /// <param name="conclusionsFrench">Evaluation's conclusions in French</param>
        /// <param name="conclusionsBasque">Evaluation's conclusions in Basque</param>
        /// <param name="conclusionsCatalan">Evaluation's conclusions in Catalan</param>
        /// <param name="conclusionsDutch">Evaluation's conclusions in Dutch</param>
        /// <param name="conclusionsGalician">Evaluation's conclusions in Galician</param>
        /// <param name="conclusionsGerman">Evaluation's conclusions in German</param>
        /// <param name="conclusionsItalian">Evaluation's conclusions in Italian</param>
        /// <param name="conclusionsPortuguese">Evaluation's conclusions in Portuguese</param>
        /// <param name="scoreLevel1">Total score of ambit 1</param>
        /// <param name="scoreLevel2">Total score of ambit 2</param>
        /// <param name="scoreLevel3">Total score of ambit 3</param>
        /// <param name="scoreLevel4">Total score of ambit 4</param>
        /// <param name="scoreLevel5">Total score of ambit 5</param>
        /// <param name="scoreLevel6">Total score of ambit 6</param>
        /// <param name="totalScore">Total score</param>
        /// <param name="isFinished">1 if is finished, 0 if not</param>
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

        /// <summary>
        /// Evaluation date in timestamp
        /// </summary>
        [JsonPropertyName("evaluationDate")]
        public long evaluationDate { get; set; }

        /// <summary>
        /// Identifier of the external organization that will receive the indicators evaluation
        /// </summary>
        [JsonPropertyName("idEvaluatedOrganization")]
        public int idEvaluatedOrganization { get; set; }

        /// <summary>
        /// Organization type of the external organization that will receive the indicators evaluation. It will be always "EVALUATED"
        /// </summary>
        [JsonPropertyName("orgTypeEvaluated")]
        public string orgTypeEvaluated { get; set; }

        /// <summary>
        /// Evaluator team identifier
        /// </summary>
        [JsonPropertyName("idEvaluatorTeam")]
        public int idEvaluatorTeam { get; set; }

        /// <summary>
        /// Identifier of the evaluator organization that will do the indicators evaluation
        /// </summary>
        [JsonPropertyName("idEvaluatorOrganization")]
        public int idEvaluatorOrganization { get; set; }

        /// <summary>
        /// Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
        /// </summary>
        [JsonPropertyName("orgTypeEvaluated")]
        public string orgTypeEvaluator { get; set; }

        /// <summary>
        /// Illness of the external organization that will receive the indicators evaluation. In the case of Fundación Miradas, it will be "AUTISM"
        /// </summary>
        [JsonPropertyName("illness")]
        public string illness { get; set; }

        /// <summary>
        /// Center identifier of the external organization
        /// </summary>
        [JsonPropertyName("idCenter")]
        public int idCenter { get; set; }

        /// <summary>
        /// Total score of ambit 1
        /// </summary>
        [JsonPropertyName("scoreLevel1")]
        public int scoreLevel1 { get; set; }

        /// <summary>
        /// Total score of ambit 2
        /// </summary>
        [JsonPropertyName("scoreLevel2")]
        public int scoreLevel2 { get; set; }

        /// <summary>
        /// Total score of ambit 3
        /// </summary>
        [JsonPropertyName("scoreLevel3")]
        public int scoreLevel3 { get; set; }

        /// <summary>
        /// Total score of ambit 4
        /// </summary>
        [JsonPropertyName("scoreLevel4")]
        public int scoreLevel4 { get; set; }

        /// <summary>
        /// Total score of ambit 5
        /// </summary>
        [JsonPropertyName("scoreLevel5")]
        public int scoreLevel5 { get; set; }

        /// <summary>
        /// Total score of ambit 6
        /// </summary>
        [JsonPropertyName("scoreLevel6")]
        public int scoreLevel6 { get; set; }
        
        /// <summary>
        /// Total score
        /// </summary>
        [JsonPropertyName("totalScore")]
        public int totalScore { get; set; }

        /// <summary>
        /// Evaluation's observations in Spanish
        /// </summary>
        [JsonPropertyName("observationsSpanish")]
        public string observationsSpanish { get; set; }

        /// <summary>
        /// Evaluation's observations in English
        /// </summary>
        [JsonPropertyName("observationsEnglish")]
        public string observationsEnglish { get; set; }

        /// <summary>
        /// Evaluation's observations in French
        /// </summary>
        [JsonPropertyName("observationsFrench")]
        public string observationsFrench { get; set; }

        /// <summary>
        /// Evaluation's observations in Basque
        /// </summary>
        [JsonPropertyName("observationsBasque")]
        public string observationsBasque { get; set; }

        /// <summary>
        /// Evaluation's observations in Catalan
        /// </summary>
        [JsonPropertyName("observationsCatalan")]
        public string observationsCatalan { get; set; }


        /// <summary>
        /// Evaluation's observations in Dutch
        /// </summary>
        [JsonPropertyName("observationsDutch")]
        public string observationsDutch { get; set; }

        /// <summary>
        /// Evaluation's observations in Galician
        /// </summary>
        [JsonPropertyName("observationsGalician")]
        public string observationsGalician { get; set; }

        /// <summary>
        /// Evaluation's observations in German
        /// </summary>
        [JsonPropertyName("observationsGerman")]
        public string observationsGerman { get; set; }

        /// <summary>
        /// Evaluation's observations in Italian
        /// </summary>
        [JsonPropertyName("observationsItalian")]
        public string observationsItalian { get; set; }

        /// <summary>
        /// Evaluation's observations in Portuguese
        /// </summary>
        [JsonPropertyName("observationsPortuguese")]
        public string observationsPortuguese { get; set; }

        /// <summary>
        /// Evaluation's conclusions in Spanish
        /// </summary>
        [JsonPropertyName("conclusionsSpanish")]
        public string conclusionsSpanish { get; set; }

        /// <summary>
        /// Evaluation's conclusions in English
        /// </summary>
        [JsonPropertyName("conclusionsEnglish")]
        public string conclusionsEnglish { get; set; }

        /// <summary>
        /// Evaluation's conclusions in French
        /// </summary>
        [JsonPropertyName("conclusionsFrench")]
        public string conclusionsFrench { get; set; }

        /// <summary>
        /// Evaluation's conclusions in Basque
        /// </summary>
        [JsonPropertyName("conclusionsBasque")]
        public string conclusionsBasque { get; set; }

        /// <summary>
        /// Evaluation's conclusions in Catalan
        /// </summary>
        [JsonPropertyName("conclusionsCatalan")]
        public string conclusionsCatalan { get; set; }

        /// <summary>
        /// Evaluation's conclusions in Dutch
        /// </summary>
        [JsonPropertyName("conclusionsDutch")]
        public string conclusionsDutch { get; set; }

        /// <summary>
        /// Evaluation's conclusions in Galician
        /// </summary>
        [JsonPropertyName("conclusionsGalician")]
        public string conclusionsGalician { get; set; }

        /// <summary>
        /// Evaluation's conclusions in German
        /// </summary>
        [JsonPropertyName("conclusionsGerman")]
        public string conclusionsGerman { get; set; }

        /// <summary>
        /// Evaluation's conclusions in Italian
        /// </summary>
        [JsonPropertyName("conclusionsItalian")]
        public string conclusionsItalian { get; set; }

        /// <summary>
        /// Evaluation's conclusions in Portuguese
        /// </summary>
        [JsonPropertyName("conclusionsPortuguese")]
        public string conclusionsPortuguese { get; set; }

        /// <summary>
        /// 1 if is finished, 0 if not
        /// </summary>
        [JsonPropertyName("isFinished")]
        public int isFinished { get; set; }


    }
}
