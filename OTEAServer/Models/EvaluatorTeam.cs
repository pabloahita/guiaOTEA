using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    public class EvaluatorTeam
    {

        public EvaluatorTeam(int idEvaluatorTeam, long creationDate, string emailProfessional, string emailResponsible, string otherMembers, int idEvaluatorOrganization, string orgTypeEvaluator, int idEvaluatedOrganization, string orgTypeEvaluated, int idCenter, string illness, string externalConsultant, string patientName, string relativeName, long evaluationDate1, long evaluationDate2, long evaluationDate3, long evaluationDate4, string observationsSpanish, string observationsEnglish, string observationsFrench, string observationsBasque, string observationsCatalan, string observationsDutch, string observationsGalician, string observationsGerman, string observationsItalian, string observationsPortuguese) {
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

        [JsonPropertyName("idEvaluatorTeam")]
        public int idEvaluatorTeam { get; set; }

        [JsonPropertyName("creationDate")]
        public long creationDate { get; set; }

        [JsonPropertyName("emailResponsible")]
        public string emailResponsible { get; set; }
        
        [JsonPropertyName("emailProfessional")]
        public string emailProfessional { get; set; }

        [JsonPropertyName("otherMembers")]
        public string otherMembers { get; set; }

        [JsonPropertyName("idEvaluatorOrganization")]
        public int idEvaluatorOrganization { get; set; }

        [JsonPropertyName("orgTypeEvaluator")]
        public string orgTypeEvaluator { get; set; }

        [JsonPropertyName("idEvaluatedOrganization")]
        public int idEvaluatedOrganization { get; set; }

        [JsonPropertyName("orgTypeEvaluated")]
        public string orgTypeEvaluated { get; set; }

        [JsonPropertyName("idCenter")]
        public int idCenter { get; set; }

        [JsonPropertyName("illness")]
        public string illness { get; set; }
        
        [JsonPropertyName("externalConsultant")]
        public string externalConsultant { get; set; }

        [JsonPropertyName("patientName")]
        public string patientName { get; set; } 
        
        [JsonPropertyName("relativeName")]
        public string relativeName { get; set; }


        [JsonPropertyName("evaluationDate1")]
        public long evaluationDate1 { get; set; }

        [JsonPropertyName("evaluationDate2")]
        public long evaluationDate2 { get; set; }

        [JsonPropertyName("evaluationDate3")]
        public long evaluationDate3 { get; set; }

        [JsonPropertyName("evaluationDate4")]
        public long evaluationDate4 { get; set; }

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

    }
}
