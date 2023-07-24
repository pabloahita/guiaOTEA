using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class EvaluatorTeam
    {

        public EvaluatorTeam(int idEvaluatorTeam, long creationDate, int idOrganization, string orgType, string illness, string externalConsultant, string emailProfessional, string emailResponsible, string patientName, string relativeName, long evaluationDate1, long evaluationDate2, long evaluationDate3, long evaluationDate4, string observations) {
            this.idEvaluatorTeam = idEvaluatorTeam;
            this.creationDate = creationDate;
            this.idOrganization = idOrganization;
            this.orgType = orgType;
            this.illness = illness;
            this.externalConsultant = externalConsultant;
            this.emailProfessional = emailProfessional;
            this.emailResponsible = emailResponsible;
            this.patientName = patientName;
            this.relativeName = relativeName;
            this.evaluationDate1 = evaluationDate1;
            this.evaluationDate2 = evaluationDate2;
            this.evaluationDate3 = evaluationDate3;
            this.evaluationDate4 = evaluationDate4;
            this.observations = observations;
        }

        [JsonProperty("idEvaluatorTeam")]
        public int idEvaluatorTeam { get; set; }
        [JsonProperty("creationDate")]
        public long creationDate { get; set; }

        [JsonProperty("idOrganization")]
        public int idOrganization { get; set; }

        [JsonProperty("orgType")]
        public string orgType { get; set; }

        [JsonProperty("illness")]
        public string illness { get; set; }

        [JsonProperty("patientName")]
        public string patientName { get; set; } 
        
        [JsonProperty("relativeName")]
        public string relativeName { get; set; }
        
        [JsonProperty("externalConsultant")]
        public string externalConsultant { get; set; }
        [JsonProperty("emailResponsible")]
        public string emailResponsible { get; set; }
        
        [JsonProperty("emailProfessional")]
        public string emailProfessional { get; set; }


        [JsonProperty("evaluationDate1")]
        public long evaluationDate1 { get; set; }

        [JsonProperty("evaluationDate2")]
        public long evaluationDate2 { get; set; }

        [JsonProperty("evaluationDate3")]
        public long evaluationDate3 { get; set; }

        [JsonProperty("evaluationDate4")]
        public long evaluationDate4 { get; set; }

        [JsonProperty("observations")]
        public string observations { get; set; }

    }
}
