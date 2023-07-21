using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class Center
    {
        public Center(int idOrganization, string orgType, string illness, int idCenter, string centerDescription, int idAddress, long telephone) { 
            this.idOrganization = idOrganization;
            this.orgType = orgType;
            this.illness = illness;
            this.idCenter = idCenter;
            this.centerDescription =centerDescription;
            this.telephone = telephone;
            this.idAddress = idAddress;
        }


        [JsonProperty("idOrganization")]
        public int idOrganization { get; set; }

        [JsonProperty("orgType")]
        public string orgType { get; set; }

        [JsonProperty("illness")]
        public string illness { get; set; }

        [JsonProperty("idCenter")]
        public int idCenter { get; set; }

        [JsonProperty("centerDescription")]
        public string centerDescription { get; set; }

        [JsonProperty("idAddress")]
        public int idAddress { get; set; }

        [JsonProperty("telephone")]
        public long telephone { get; set; }

        [JsonProperty("email")]
        public string email { get; set; }
    }

    
}
