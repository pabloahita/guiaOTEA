using Newtonsoft.Json;
using System.Reflection.Metadata;
using System.Security.Cryptography.X509Certificates;

namespace OTEAServer.Models
{
    public class Organization
    {
        public Organization(int idOrganization, string orgType, string illness, string nameOrg, int idAddress, string email, long telephone, string information, string emailOrgPrincipal, string emailOrgConsultant) {
            this.IdOrganization = idOrganization;
            this.orgType = orgType;
            this.illness = illness;
            this.nameOrg = nameOrg;
            this.idAddress = idAddress;
            this.email = email;
            this.telephone = telephone;
            this.information = information;
            this.emailOrgPrincipal = emailOrgPrincipal;
            this.emailOrgConsultant = emailOrgConsultant;

        }

        [JsonProperty("IdOrganization")]
        public int IdOrganization { get; set; }

        [JsonProperty("orgType")]
        public string orgType { get; set; }

        [JsonProperty("illness")]
        public string illness { get; set; }

        [JsonProperty("nameOrg")]
        public string nameOrg { get; set; }

        [JsonProperty("idAddress")]
        public int idAddress { get; set; }

        [JsonProperty("email")]
        public string email { get; set; }

        [JsonProperty("telephone")]
        public long telephone { get; set; }

        [JsonProperty("information")]
        public string information { get; set; }

        [JsonProperty("emailOrgPrincipal")]
        public string emailOrgPrincipal { get; set; }

        [JsonProperty("emailOrgConsultant")]
        public string emailOrgConsultant {  get; set; }


    }
}
