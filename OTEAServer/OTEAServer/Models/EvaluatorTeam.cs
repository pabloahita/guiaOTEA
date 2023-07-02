using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class EvaluatorTeam
    {

        public EvaluatorTeam(int idEvaluatorTeam, DateTime creationDate, int idOrganization, string orgType, string illness, string emailConsultant, string emailProfessional, string emailResponsible, string patientName, string relativeName) {
            this.idEvaluatorTeam = idEvaluatorTeam;
            this.creationDate = creationDate;
            this.idOrganization = idOrganization;
            this.orgType = orgType;
            this.illness = illness;
            this.emailConsultant = emailConsultant;
            this.emailProfessional = emailProfessional;
            this.emailResponsible = emailResponsible;
            this.patientName = patientName;
            this.relativeName = relativeName;
        }

        [JsonProperty("idEvaluatorTeam")]
        public int idEvaluatorTeam { get; set; }
        [JsonProperty("creationDate")]
        public DateTime creationDate { get; set; }

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
        
        [JsonProperty("emailConsultant")]
        public string emailConsultant { get; set; }
        [JsonProperty("emailResponsible")]
        public string emailResponsible { get; set; }
        
        [JsonProperty("emailProfessional")]
        public string emailProfessional { get; set; }
    }
}
