using System.Text.Json.Serialization;
using System.Reflection.Metadata;
using System.Security.Cryptography.X509Certificates;

namespace OTEAServer.Models
{
    public class Organization
    {
        public Organization(int idOrganization, string orgType, string illness, string nameOrg, int idAddress, string email, long telephone, string informationSpanish, string informationEnglish, string informationFrench, string informationBasque, string informationCatalan, string informationDutch, string informationGalician, string informationGerman, string informationItalian, string informationPortuguese, string? emailOrgPrincipal) {
            this.idOrganization = idOrganization;
            this.orgType = orgType;
            this.illness = illness;
            this.nameOrg = nameOrg;
            this.idAddress = idAddress;
            this.email = email;
            this.telephone = telephone;
            this.informationSpanish = informationSpanish;
            this.informationEnglish = informationEnglish;
            this.informationFrench = informationFrench;
            this.informationBasque = informationBasque;
            this.informationCatalan = informationCatalan;
            this.informationDutch = informationDutch;
            this.informationGalician = informationGalician;
            this.informationGerman = informationGerman;
            this.informationItalian = informationItalian;
            this.informationPortuguese = informationPortuguese;
            this.emailOrgPrincipal = emailOrgPrincipal;

        }

        [JsonPropertyName("idOrganization")]
        public int idOrganization { get; set; }

        [JsonPropertyName("orgType")]
        public string orgType { get; set; }

        [JsonPropertyName("illness")]
        public string illness { get; set; }

        [JsonPropertyName("nameOrg")]
        public string nameOrg { get; set; }

        [JsonPropertyName("idAddress")]
        public int idAddress { get; set; }

        [JsonPropertyName("email")]
        public string email { get; set; }

        [JsonPropertyName("telephone")]
        public long telephone { get; set; }

        [JsonPropertyName("informationSpanish")]
        public string informationSpanish { get; set; }

        [JsonPropertyName("informationEnglish")]
        public string informationEnglish { get; set; }

        [JsonPropertyName("informationFrench")]
        public string informationFrench { get; set; }

        [JsonPropertyName("informationBasque")]
        public string informationBasque { get; set; }

        [JsonPropertyName("informationCatalan")]
        public string informationCatalan { get; set; }

        [JsonPropertyName("informationDutch")]
        public string informationDutch { get; set; }

        [JsonPropertyName("informationGalician")]
        public string informationGalician { get; set; }

        [JsonPropertyName("informationGerman")]
        public string informationGerman { get; set; }

        [JsonPropertyName("informationItalian")]
        public string informationItalian { get; set; }

        [JsonPropertyName("informationPortuguese")]
        public string informationPortuguese { get; set; }

        [JsonPropertyName("emailOrgPrincipal")]
        public string? emailOrgPrincipal { get; set; }


    }
}
