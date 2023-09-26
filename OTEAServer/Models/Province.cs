using System.Text.Json.Serialization;

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

        [JsonPropertyName("idProvince")]
        public int idProvince { get; set; }

        [JsonPropertyName("idRegion")]
        public int idRegion { get; set; }

        [JsonPropertyName("idCountry")]
        public string idCountry { get; set; }

        [JsonPropertyName("nameProvince")]
        public string nameProvince { get; set; }
    }
}
