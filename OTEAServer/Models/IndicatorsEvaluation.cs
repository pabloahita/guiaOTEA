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
        /// <param name="scoreAmbit1">Total score of ambit 1</param>
        /// <param name="scoreAmbit2">Total score of ambit 2</param>
        /// <param name="scoreAmbit3">Total score of ambit 3</param>
        /// <param name="scoreAmbit4">Total score of ambit 4</param>
        /// <param name="scoreAmbit5">Total score of ambit 5</param>
        /// <param name="scoreAmbit6">Total score of ambit 6</param>
        /// <param name="totalScore">Total score</param>
        /// <param name="isFinished">1 if is finished, 0 if not</param>
        /// <param name="evaluationType">Evaluation type</param>
        public IndicatorsEvaluation(long evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, 
            int idEvaluatorTeam, int idEvaluatorOrganization, string orgTypeEvaluator, string illness, int idCenter, 
            int scoreAmbit1, int scoreAmbit2, int scoreAmbit3, int scoreAmbit4, int scoreAmbit5, int scoreAmbit6, int totalScore, string conclusionsEnglish, string conclusionsSpanish, string conclusionsFrench, string conclusionsBasque, string conclusionsCatalan, string conclusionsDutch, string conclusionsGalician, string conclusionsGerman, string conclusionsItalian, string conclusionsPortuguese, int isFinished, string evaluationType)
        {
            this.evaluationDate= evaluationDate;
            this.idEvaluatedOrganization = idEvaluatedOrganization;
            this.orgTypeEvaluated= orgTypeEvaluated;
            this.idEvaluatorTeam = idEvaluatorTeam;
            this.idEvaluatorOrganization = idEvaluatorOrganization;
            this.orgTypeEvaluator = orgTypeEvaluator;
            this.illness = illness;
            this.idCenter=idCenter;
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
            this.scoreAmbit1 = scoreAmbit1;
            this.scoreAmbit2 = scoreAmbit2;
            this.scoreAmbit3 = scoreAmbit3;
            this.scoreAmbit4 = scoreAmbit4;
            this.scoreAmbit5 = scoreAmbit5;
            this.scoreAmbit6 = scoreAmbit6;
            this.totalScore = totalScore;
            this.isFinished = isFinished;
            this.evaluationType = evaluationType;
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
        [JsonPropertyName("orgTypeEvaluator")]
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
        [JsonPropertyName("scoreAmbit1")]
        public int scoreAmbit1 { get; set; }

        /// <summary>
        /// Total score of ambit 2
        /// </summary>
        [JsonPropertyName("scoreAmbit2")]
        public int scoreAmbit2 { get; set; }

        /// <summary>
        /// Total score of ambit 3
        /// </summary>
        [JsonPropertyName("scoreAmbit3")]
        public int scoreAmbit3 { get; set; }

        /// <summary>
        /// Total score of ambit 4
        /// </summary>
        [JsonPropertyName("scoreAmbit4")]
        public int scoreAmbit4 { get; set; }

        /// <summary>
        /// Total score of ambit 5
        /// </summary>
        [JsonPropertyName("scoreAmbit5")]
        public int scoreAmbit5 { get; set; }

        /// <summary>
        /// Total score of ambit 6
        /// </summary>
        [JsonPropertyName("scoreAmbit6")]
        public int scoreAmbit6 { get; set; }
        
        /// <summary>
        /// Total score
        /// </summary>
        [JsonPropertyName("totalScore")]
        public int totalScore { get; set; }

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

        /// <summary>
        /// Evaluation type
        /// </summary>
        [JsonPropertyName("evaluationType")]
        public string evaluationType { get; set; }


    }
}
