using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class EvaluatorTeamMember
    {

        public EvaluatorTeamMember(string emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness) {
            this.emailUser = emailUser;
            this.idEvaluatorTeam = idEvaluatorTeam;
            this.idEvaluatorOrganization = idEvaluatorOrganization;
            this.orgType = orgType;
            this.illness = illness;
        }

        [JsonProperty("emailUser")]
        public string emailUser { get; set; }

        [JsonProperty("idEvaluatorTeam")]
        public int idEvaluatorTeam { get; set; }

        [JsonProperty("idEvaluatorOrganization")]
        public int idEvaluatorOrganization { get; set; }

        [JsonProperty("orgType")]
        public string orgType { get; set; }

        [JsonProperty("illness")]
        public string illness { get; set; }
    
    }
}
