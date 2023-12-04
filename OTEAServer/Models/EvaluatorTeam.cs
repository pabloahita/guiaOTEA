using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class for evaluator teams
    /// Author: Pablo Ahita del Barrio
    /// Version: 1
    /// </summary>
    public class EvaluatorTeam
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="idEvaluatorTeam">Evaluator team identifier</param>
        /// <param name="creationDate">Evaluator team creation date in timestamp</param>
        /// <param name="emailProfessional">Direct attendance professional email</param>
        /// <param name="emailResponsible">Responsible email</param>
        /// <param name="otherMembers">Other members names joint by commas</param>
        /// <param name="idEvaluatorOrganization">Evaluator team evaluator organization identifier</param>
        /// <param name="orgTypeEvaluator">Evaluator team evaluator organization type</param>
        /// <param name="idEvaluatedOrganization">Evaluator team evaluated organization identifier</param>
        /// <param name="orgTypeEvaluated">Evaluator team evaluated organization type</param>
        /// <param name="idCenter">Center identifier</param>
        /// <param name="illness">Evaluator organization illness or syndrome</param>
        /// <param name="externalConsultant">External consultant name</param>
        /// <param name="patientName">Patient name or names</param>
        /// <param name="relativeName">Patient relatives names</param>
        /// <param name="evaluationDate1">First evaluation date in timestamp</param>
        /// <param name="evaluationDate2">Second evaluation date in timestamp</param>
        /// <param name="evaluationDate3">Third evaluation date in timestamp</param>
        /// <param name="evaluationDate4">Fourth evaluation date in timestamp</param>
        /// <param name="observationsEnglish">Evaluator team observations in English</param>
        /// <param name="observationsSpanish">Evaluator team observations in Spanish</param>
        /// <param name="observationsFrench">Evaluator team observations in French</param>
        /// <param name="observationsBasque">Evaluator team observations in Basque</param>
        /// <param name="observationsCatalan">Evaluator team observations in Catalan</param>
        /// <param name="observationsDutch">Evaluator team observations in Dutch</param>
        /// <param name="observationsGalician">Evaluator team observations in Galician</param>
        /// <param name="observationsGerman">Evaluator team observations in German</param>
        /// <param name="observationsItalian">Evaluator team observations in Italian</param>
        /// <param name="observationsPortuguese">Evaluator team observations in Portuguese</param>
        public EvaluatorTeam(int idEvaluatorTeam, long creationDate, string emailProfessional, string emailResponsible, string otherMembers, int idEvaluatorOrganization, string orgTypeEvaluator, int idEvaluatedOrganization, string orgTypeEvaluated, int idCenter, string illness, string externalConsultant, string patientName, string relativeName, long evaluationDate1, long evaluationDate2, long evaluationDate3, long evaluationDate4, string observationsEnglish, string observationsSpanish, string observationsFrench, string observationsBasque, string observationsCatalan, string observationsDutch, string observationsGalician, string observationsGerman, string observationsItalian, string observationsPortuguese) {
            this.idEvaluatorTeam = idEvaluatorTeam;
            this.creationDate = creationDate;
            this.emailProfessional = emailProfessional;
            this.emailResponsible = emailResponsible;
            this.otherMembers = otherMembers;
            this.idEvaluatorOrganization = idEvaluatorOrganization;
            this.orgTypeEvaluator = orgTypeEvaluator;
            this.idEvaluatedOrganization = idEvaluatedOrganization;
            this.orgTypeEvaluated = orgTypeEvaluated;
            this.idCenter = idCenter;
            this.illness = illness;
            this.externalConsultant = externalConsultant;
            this.patientName = patientName;
            this.relativeName = relativeName;
            this.evaluationDate1 = evaluationDate1;
            this.evaluationDate2 = evaluationDate2;
            this.evaluationDate3 = evaluationDate3;
            this.evaluationDate4 = evaluationDate4;
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
        }

        /// <summary>
        /// Evaluator team identifier
        /// </summary>
        [JsonPropertyName("idEvaluatorTeam")]
        public int idEvaluatorTeam { get; set; }

        /// <summary>
        /// Evaluator team creation date in timestamp
        /// </summary>
        [JsonPropertyName("creationDate")]
        public long creationDate { get; set; }

        /// <summary>
        /// Direct attendance professional email
        /// </summary>
        [JsonPropertyName("emailResponsible")]
        public string emailResponsible { get; set; }

        /// <summary>
        /// Responsible email
        /// </summary>
        [JsonPropertyName("emailProfessional")]
        public string emailProfessional { get; set; }

        /// <summary>
        /// Other members names joint by commas
        /// </summary>
        [JsonPropertyName("otherMembers")]
        public string otherMembers { get; set; }

        /// <summary>
        /// Evaluator team evaluator organization identifier
        /// </summary>
        [JsonPropertyName("idEvaluatorOrganization")]
        public int idEvaluatorOrganization { get; set; }

        /// <summary>
        /// Evaluator team evaluator organization type
        /// </summary>
        [JsonPropertyName("orgTypeEvaluator")]
        public string orgTypeEvaluator { get; set; }

        /// <summary>
        /// Evaluator team evaluated organization identifier
        /// </summary>
        [JsonPropertyName("idEvaluatedOrganization")]
        public int idEvaluatedOrganization { get; set; }

        /// <summary>
        /// Evaluator team evaluated organization type
        /// </summary>
        [JsonPropertyName("orgTypeEvaluated")]
        public string orgTypeEvaluated { get; set; }

        /// <summary>
        /// Center identifier
        /// </summary>
        [JsonPropertyName("idCenter")]
        public int idCenter { get; set; }

        /// <summary>
        /// Evaluator organization illness or syndrome
        /// </summary>
        [JsonPropertyName("illness")]
        public string illness { get; set; }

        /// <summary>
        /// External consultant name
        /// </summary>
        [JsonPropertyName("externalConsultant")]
        public string externalConsultant { get; set; }

        /// <summary>
        /// Patient name or names
        /// </summary>
        [JsonPropertyName("patientName")]
        public string patientName { get; set; }

        /// <summary>
        /// Patient relatives names
        /// </summary>
        [JsonPropertyName("relativeName")]
        public string relativeName { get; set; }

        /// <summary>
        /// First evaluation date in timestamp
        /// </summary>
        [JsonPropertyName("evaluationDate1")]
        public long evaluationDate1 { get; set; }

        /// <summary>
        /// Second evaluation date in timestamp
        /// </summary>
        [JsonPropertyName("evaluationDate2")]
        public long evaluationDate2 { get; set; }

        /// <summary>
        /// Third evaluation date in timestamp
        /// </summary>
        [JsonPropertyName("evaluationDate3")]
        public long evaluationDate3 { get; set; }

        /// <summary>
        /// Fourth evaluation date in timestamp
        /// </summary>
        [JsonPropertyName("evaluationDate4")]
        public long evaluationDate4 { get; set; }

        /// <summary>
        /// Evaluator team observations in Spanish
        /// </summary>
        [JsonPropertyName("observationsSpanish")]
        public string observationsSpanish { get; set; }

        /// <summary>
        /// Evaluator team observations in English
        /// </summary>
        [JsonPropertyName("observationsEnglish")]
        public string observationsEnglish { get; set; }

        /// <summary>
        /// Evaluator team observations in French
        /// </summary>
        [JsonPropertyName("observationsFrench")]
        public string observationsFrench { get; set; }

        /// <summary>
        /// Evaluator team observations in French
        /// </summary>
        [JsonPropertyName("observationsBasque")]
        public string observationsBasque { get; set; }

        /// <summary>
        /// Evaluator team observations in Catalan
        /// </summary>
        [JsonPropertyName("observationsCatalan")]
        public string observationsCatalan { get; set; }

        /// <summary>
        /// Evaluator team observations in Dutch
        /// </summary>
        [JsonPropertyName("observationsDutch")]
        public string observationsDutch { get; set; }

        /// <summary>
        /// Evaluator team observations in Galician
        /// </summary>
        [JsonPropertyName("observationsGalician")]
        public string observationsGalician { get; set; }

        /// <summary>
        /// Evaluator team observations in German
        /// </summary>
        [JsonPropertyName("observationsGerman")]
        public string observationsGerman { get; set; }

        /// <summary>
        /// Evaluator team observations in Italian
        /// </summary>
        [JsonPropertyName("observationsItalian")]
        public string observationsItalian { get; set; }

        /// <summary>
        /// Evaluator team observations in Portuguese
        /// </summary>
        [JsonPropertyName("observationsPortuguese")]
        public string observationsPortuguese { get; set; }

    }
}
