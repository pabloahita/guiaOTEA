using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    public class Center
    {
        public Center(int idOrganization, string orgType, string illness, int idCenter, string descriptionSpanish, string descriptionEnglish, string descriptionFrench,string descriptionBasque, string descriptionCatalan, string descriptionDutch, string descriptionGalician, string descriptionGerman, string descriptionItalian, string descriptionPortuguese, int idAddress, long telephone, string email) { 
            this.idOrganization = idOrganization;
            this.orgType = orgType;
            this.illness = illness;
            this.idCenter = idCenter;
            this.descriptionSpanish = descriptionSpanish;
            this.descriptionEnglish = descriptionEnglish;
            this.descriptionFrench = descriptionFrench;
            this.descriptionBasque = descriptionBasque;
            this.descriptionCatalan = descriptionCatalan;
            this.descriptionDutch = descriptionDutch;
            this.descriptionGalician = descriptionGalician;
            this.descriptionGerman = descriptionGerman;
            this.descriptionItalian = descriptionItalian;
            this.descriptionPortuguese = descriptionPortuguese;
            this.telephone = telephone;
            this.idAddress = idAddress;
            this.email=email;
        }


        [JsonPropertyName("idOrganization")]
        public int idOrganization { get; set; }

        [JsonPropertyName("orgType")]
        public string orgType { get; set; }

        [JsonPropertyName("illness")]
        public string illness { get; set; }

        [JsonPropertyName("idCenter")]
        public int idCenter { get; set; }

        [JsonPropertyName("descriptionSpanish")]
        public string descriptionSpanish { get; set; }

        [JsonPropertyName("descriptionEnglish")]
        public string descriptionEnglish { get; set; }

        [JsonPropertyName("descriptionFrench")]
        public string descriptionFrench { get; set; }

        [JsonPropertyName("descriptionBasque")]
        public string descriptionBasque { get; set; }

        [JsonPropertyName("descriptionCatalan")]
        public string descriptionCatalan { get; set; }

        [JsonPropertyName("descriptionDutch")]
        public string descriptionDutch { get; set; }

        [JsonPropertyName("descriptionGalician")]
        public string descriptionGalician { get; set; }

        [JsonPropertyName("descriptionGerman")]
        public string descriptionGerman { get; set; }

        [JsonPropertyName("descriptionItalian")]
        public string descriptionItalian { get; set; }

        [JsonPropertyName("descriptionPortuguese")]
        public string descriptionPortuguese { get; set; }

        [JsonPropertyName("idAddress")]
        public int idAddress { get; set; }

        [JsonPropertyName("telephone")]
        public long telephone { get; set; }

        [JsonPropertyName("email")]
        public string email{get;set;}
    }

    
}
