using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class Province
    {
        public Province(int idProvince, int idRegion, string idCountry, string nameProvince)
        {
            this.idProvince = idProvince;
            this.idRegion = idRegion;
            this.idCountry = idCountry;
            this.nameProvince = nameProvince;
            
        }

        [JsonProperty("idProvince")]
        public int idProvince { get; set; }

        [JsonProperty("idRegion")]
        public int idRegion { get; set; }

        [JsonProperty("idCountry")]
        public string idCountry { get; set; }

        [JsonProperty("nameProvince")]
        public string nameProvince { get; set; }
    }
}
