using Newtonsoft.Json;
using System.Reflection.Metadata;
using System.Security.Cryptography.X509Certificates;

namespace OTEAServer.Models
{
    public class Organization
    {
        public Organization(int id, string orgType, string illness, string name, int idAddress, string email, long telephone, string information, string? emailOrgPrincipal, string? emailOrgConsultant) {
            this.Id = id;
            this.OrgType = orgType;
            this.Illness = illness;
            this.Name = name;
            this.IdAddress = idAddress;
            this.Email = email;
            this.Telephone = telephone;
            this.Information = information;
            this.EmailOrgPrincipal = emailOrgPrincipal;
            this.EmailOrgConsultant = emailOrgConsultant;

        }

        [JsonProperty("idOrganization")]
        public int Id { get; set; }

        [JsonProperty("orgType")]
        public string OrgType { get; set; }

        [JsonProperty("illness")]
        public string Illness { get; set; }

        [JsonProperty("nameOrg")]
        public string Name { get; set; }

        [JsonProperty("idAddress")]
        public int IdAddress { get; set; }

        [JsonProperty("email")]
        public string Email { get; set; }

        [JsonProperty("telephone")]
        public long Telephone { get; set; }

        [JsonProperty("information")]
        public string Information { get; set; }

        [JsonProperty("emailOrgPrincipal")]
        public string? EmailOrgPrincipal { get; set; }

        [JsonProperty("emailOrgConsultant")]
        public string? EmailOrgConsultant {  get; set; }


    }
}
