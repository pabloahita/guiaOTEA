using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class that serves to store all registers of the Indicators' Evaluation
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    public class IndicatorsEvaluationReg
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
        /// <param name="idSubSubAmbit">Second level division of the ambit</param>
        /// <param name="idSubAmbit">First level division of the ambit</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="indicatorType">Indicator type</param>
        /// <param name="idEvidence">Evidence identifier</param>
        /// <param name="isMarked">Boolean that is 1 if the indicator is marked and 0 if not</param>
        /// <param name="indicatorVersion">Indicator version</param>
        public IndicatorsEvaluationReg(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, string orgTypeEvaluator, int idEvaluatedOrganization, string orgTypeEvaluated, string illness, int idCenter, int idSubSubAmbit, int idSubAmbit, int idAmbit, int idIndicator, string indicatorType, int idEvidence, int isMarked, int indicatorVersion)
        {
            this.evaluationDate = evaluationDate;
            this.idEvaluatorTeam=idEvaluatorTeam;
            this.idEvaluatorOrganization = idEvaluatorOrganization;
            this.orgTypeEvaluator = orgTypeEvaluator;
            this.idEvaluatedOrganization = idEvaluatedOrganization;
            this.orgTypeEvaluated = orgTypeEvaluated;
            this.illness = illness;
            this.idCenter=idCenter; 
            this.idSubSubAmbit = idSubSubAmbit;
            this.idSubAmbit = idSubAmbit;
            this.idAmbit = idAmbit;
            this.idIndicator = idIndicator;
            this.indicatorType = indicatorType;
            this.idEvidence = idEvidence;
            this.isMarked = isMarked;
            this.indicatorVersion = indicatorVersion;
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
        /// Evidence identifier
        /// </summary>
        [JsonPropertyName("idEvidence")]
        public int idEvidence { get; set; }

        /// <summary>
        /// Indicator identifier
        /// </summary>
        [JsonPropertyName("idIndicator")]
        public int idIndicator { get; set; }

        /// <summary>
        /// Indicator type
        /// </summary>
        [JsonPropertyName("indicatorType")]
        public string indicatorType { get; set; }

        /// <summary>
        /// Second level division of the ambit
        /// </summary>
        [JsonPropertyName("idSubSubAmbit")]
        public int idSubSubAmbit { get; set; }

        /// <summary>
        /// First level division of the ambit
        /// </summary>
        [JsonPropertyName("idSubAmbit")]
        public int idSubAmbit { get; set; }

        /// <summary>
        /// Ambit identifier
        /// </summary>
        [JsonPropertyName("idAmbit")]
        public int idAmbit { get; set; }

        /// <summary>
        /// Boolean that is 1 if the indicator is marked and 0 if not
        /// </summary>
        [JsonPropertyName("isMarked")]
        public int isMarked { get; set; }

        /// <summary>
        /// Indicator version
        /// </summary>
        [JsonPropertyName("indicatorVersion")]
        public int indicatorVersion { get; set; }
    }
}
