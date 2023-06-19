using System.Reflection.Metadata;
using System.Security.Cryptography.X509Certificates;

namespace OTEAServer.Models
{
    public class Organization
    {
        public Organization(int id, string orgType, string illness, string name, int idAddress, string email, int telephone, string information, string? emailOrgPrincipal, string? emailOrgConsultant) {
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
        public int Id { get; set; }
        public string OrgType { get; set; }
        public string Illness { get; set; }
        public string Name { get; set; }
        public int IdAddress { get; set; }
        public string Email { get; set; }
        public int Telephone { get; set; }
        public string Information { get; set; }
        public string? EmailOrgPrincipal { get; set; }
        public string? EmailOrgConsultant {  get; set; }


    }
}
