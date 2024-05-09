using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    public class IndicatorsEvaluationIndicatorReg
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
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <param name="observationsSpanish">Observations in English</param>
        /// <param name="observationsEnglish">Observations in Spanish</param>
        /// <param name="observationsFrench">Observations in French</param>
        /// <param name="observationsBasque">Observations in Basque</param>
        /// <param name="observationsCatalan">Observations in Catalan</param>
        /// <param name="observationsDutch">Observations in Dutch</param>
        /// <param name="observationsGalician">Observations in Galician</param>
        /// <param name="observationsGerman">Observations in German</param>
        /// <param name="observationsItalian">Observations in Italian</param>
        /// <param name="observationsPortuguese">Observations in Portuguese</param>
        /// <param name="numEvidencesMarked">Number of marked evidences</param>
        /// <param name="status">Indicator status</param>
        public IndicatorsEvaluationIndicatorReg(long evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, int idEvaluatorTeam, int idEvaluatorOrganization, string orgTypeEvaluator, string illness, int idCenter, int idIndicator, int idSubSubAmbit, int idSubAmbit, int idAmbit, int indicatorVersion, string evaluationType,
            string observationsSpanish, string observationsEnglish, string observationsFrench, string observationsBasque, string observationsCatalan,
            string observationsDutch, string observationsGalician, string observationsGerman, string observationsItalian, string observationsPortuguese, int numEvidencesMarked, string status)
        {
            this.evaluationDate = evaluationDate;
            this.idEvaluatorTeam = idEvaluatorTeam;
            this.idEvaluatorOrganization = idEvaluatorOrganization;
            this.orgTypeEvaluator = orgTypeEvaluator;
            this.idEvaluatedOrganization = idEvaluatedOrganization;
            this.orgTypeEvaluated = orgTypeEvaluated;
            this.illness = illness;
            this.idCenter = idCenter;
            this.idSubSubAmbit = idSubSubAmbit;
            this.idSubAmbit = idSubAmbit;
            this.idAmbit = idAmbit;
            this.idIndicator = idIndicator;
            this.indicatorVersion = indicatorVersion;
            this.evaluationType = evaluationType;
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
            this.numEvidencesMarked = numEvidencesMarked;
            this.status = status;
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
        /// Indicator identifier
        /// </summary>
        [JsonPropertyName("idIndicator")]
        public int idIndicator { get; set; }

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
        /// Indicator version
        /// </summary>
        [JsonPropertyName("indicatorVersion")]
        public int indicatorVersion { get; set; }

        /// <summary>
        /// Evaluation type
        /// </summary>
        [JsonPropertyName("evaluationType")]
        public string evaluationType { get; set; }

        /// <summary>
        /// Observations in Spanish
        /// </summary>
        [JsonPropertyName("observationsSpanish")]
        public string observationsSpanish { get; set; }

        /// <summary>
        /// Observations in English
        /// </summary>
        [JsonPropertyName("observationsEnglish")]
        public string observationsEnglish { get; set; }

        /// <summary>
        /// Observations in French
        /// </summary>
        [JsonPropertyName("observationsFrench")]
        public string observationsFrench { get; set; }

        /// <summary>
        /// Observations in Basque
        /// </summary>
        [JsonPropertyName("observationsBasque")]
        public string observationsBasque { get; set; }

        /// <summary>
        /// Observations in Catalan
        /// </summary>
        [JsonPropertyName("observationsCatalan")]
        public string observationsCatalan { get; set; }


        /// <summary>
        /// Observations in Dutch
        /// </summary>
        [JsonPropertyName("observationsDutch")]
        public string observationsDutch { get; set; }

        /// <summary>
        /// Observations in Galician
        /// </summary>
        [JsonPropertyName("observationsGalician")]
        public string observationsGalician { get; set; }

        /// <summary>
        /// Observations in German
        /// </summary>
        [JsonPropertyName("observationsGerman")]
        public string observationsGerman { get; set; }

        /// <summary>
        /// Observations in Italian
        /// </summary>
        [JsonPropertyName("observationsItalian")]
        public string observationsItalian { get; set; }

        /// <summary>
        /// Observations in Portuguese
        /// </summary>
        [JsonPropertyName("observationsPortuguese")]
        public string observationsPortuguese { get; set; }
        
        /// <summary>
        /// Number of evidences marked
        /// </summary>
        [JsonPropertyName("numEvidencesMarked")]
        public int numEvidencesMarked { get; set; }

        /// <summary>
        /// Indicator status
        /// </summary>
        [JsonPropertyName("status")]
        public string status { get; set; }

    }
}

