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
        /// <param name="scorePriorityZeroColourRed">Total score of the priority zero and colour red</param>
        /// <param name="scorePriorityZeroColourYellow">Total score of the priority zero and colour yellow</param>
        /// <param name="scorePriorityZeroColourGreen">Total score of the priority zero and colour green</param>
        /// <param name="scorePriorityOneColourRed">Total score of the priority one and colour red</param>
        /// <param name="scorePriorityOneColourYellow">Total score of the priority one and colour yellow</param>
        /// <param name="scorePriorityOneColourGreen">Total score of the priority one and colour green</param>
        /// <param name="scorePriorityTwoColourRed">Total score of the priority two and colour red</param>
        /// <param name="scorePriorityTwoColourYellow">Total score of the priority two and colour yellow</param>
        /// <param name="scorePriorityTwoColourGreen">Total score of the priority two and colour green</param>
        /// <param name="scorePriorityThreeColourRed">Total score of the priority three and colour red</param>
        /// <param name="scorePriorityThreeColourYellow">Total score of the priority three and colour yellow</param>
        /// <param name="scorePriorityThreeColourGreen">Total score of the priority three and colour green</param>
        /// <param name="totalScore">Total score</param>
        /// <param name="conclusionsSpanish">Evaluation's conclusions in Spanish</param>
        /// <param name="conclusionsEnglish">Evaluation's conclusions in English</param>
        /// <param name="conclusionsFrench">Evaluation's conclusions in French</param>
        /// <param name="conclusionsBasque">Evaluation's conclusions in Basque</param>
        /// <param name="conclusionsCatalan">Evaluation's conclusions in Catalan</param>
        /// <param name="conclusionsDutch">Evaluation's conclusions in Dutch</param>
        /// <param name="conclusionsGalician">Evaluation's conclusions in Galician</param>
        /// <param name="conclusionsGerman">Evaluation's conclusions in German</param>
        /// <param name="conclusionsItalian">Evaluation's conclusions in Italian</param>
        /// <param name="conclusionsPortuguese">Evaluation's conclusions in Portuguese</param>
        /// <param name="isFinished">1 if is finished, 0 if not</param>
        /// <param name="evaluationType">Evaluation type</param>
        public IndicatorsEvaluation(long evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, 
            int idEvaluatorTeam, int idEvaluatorOrganization, string orgTypeEvaluator, string illness, int idCenter, 
            int scorePriorityZeroColourRed, int scorePriorityZeroColourYellow, int scorePriorityZeroColourGreen,
            int scorePriorityOneColourRed, int scorePriorityOneColourYellow, int scorePriorityOneColourGreen,
            int scorePriorityTwoColourRed, int scorePriorityTwoColourYellow, int scorePriorityTwoColourGreen,
            int scorePriorityThreeColourRed, int scorePriorityThreeColourYellow, int scorePriorityThreeColourGreen, int totalScore, 
            string conclusionsSpanish, string conclusionsEnglish, string conclusionsFrench, string conclusionsBasque, 
            string conclusionsCatalan, string conclusionsDutch, string conclusionsGalician, string conclusionsGerman, 
            string conclusionsItalian, string conclusionsPortuguese, int isFinished, string evaluationType)
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
            this.scorePriorityZeroColourRed = scorePriorityZeroColourRed;
            this.scorePriorityZeroColourYellow = scorePriorityZeroColourYellow; 
            this.scorePriorityZeroColourGreen = scorePriorityZeroColourGreen;
            this.scorePriorityOneColourRed = scorePriorityOneColourRed;
            this.scorePriorityOneColourYellow = scorePriorityOneColourYellow;
            this.scorePriorityOneColourGreen = scorePriorityOneColourGreen;
            this.scorePriorityTwoColourRed = scorePriorityTwoColourRed;
            this.scorePriorityTwoColourYellow = scorePriorityTwoColourYellow;
            this.scorePriorityTwoColourGreen = scorePriorityTwoColourGreen;
            this.scorePriorityThreeColourRed = scorePriorityThreeColourRed;
            this.scorePriorityThreeColourYellow = scorePriorityThreeColourYellow;
            this.scorePriorityThreeColourGreen = scorePriorityThreeColourGreen;
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
        /// Total score of the priority zero and colour red
        /// </summary>
        [JsonPropertyName("scorePriorityZeroColourRed")]
        public int scorePriorityZeroColourRed { get; set; }

        /// <summary>
        /// Total score of the priority zero and colour yellow
        /// </summary>
        [JsonPropertyName("scorePriorityZeroColourYellow")]
        public int scorePriorityZeroColourYellow { get; set; }

        /// <summary>
        /// Total score of the priority zero and colour green
        /// </summary>
        [JsonPropertyName("scorePriorityZeroColourGreen")]
        public int scorePriorityZeroColourGreen { get; set; }

        /// <summary>
        /// Total score of the priority one and colour red
        /// </summary>
        [JsonPropertyName("scorePriorityOneColourRed")]
        public int scorePriorityOneColourRed { get; set; }

        /// <summary>
        /// Total score of the priority one and colour yellow
        /// </summary>
        [JsonPropertyName("scorePriorityOneColourYellow")]
        public int scorePriorityOneColourYellow { get; set; }

        /// <summary>
        /// Total score of the priority one and colour green
        /// </summary>
        [JsonPropertyName("scorePriorityOneColourGreen")]
        public int scorePriorityOneColourGreen { get; set; }

        /// <summary>
        /// Total score of the priority two and colour red
        /// </summary>
        [JsonPropertyName("scorePriorityTwoColourRed")]
        public int scorePriorityTwoColourRed { get; set; }

        /// <summary>
        /// Total score of the priority two and colour yellow
        /// </summary>
        [JsonPropertyName("scorePriorityTwoColourYellow")]
        public int scorePriorityTwoColourYellow { get; set; }

        /// <summary>
        /// Total score of the priority two and colour green
        /// </summary>
        [JsonPropertyName("scorePriorityTwoColourGreen")]
        public int scorePriorityTwoColourGreen { get; set; }

        /// <summary>
        /// Total score of the priority three and colour red
        /// </summary>
        [JsonPropertyName("scorePriorityThreeColourRed")]
        public int scorePriorityThreeColourRed { get; set; }

        /// <summary>
        /// Total score of the priority three and colour yellow
        /// </summary>
        [JsonPropertyName("scorePriorityThreeColourYellow")]
        public int scorePriorityThreeColourYellow { get; set; }

        /// <summary>
        /// Total score of the priority three and colour green
        /// </summary>
        [JsonPropertyName("scorePriorityThreeColourGreen")]
        public int scorePriorityThreeColourGreen { get; set; }


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
