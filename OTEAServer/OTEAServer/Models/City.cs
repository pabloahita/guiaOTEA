using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;

namespace OTEAServer.Models
{
    public class City
    {
        public City(int idCity, int idProvince, int idRegion, string idCountry) {
            this.IdCity = idCity;
            this.IdProvince = idProvince;
            this.IdRegion = idRegion;
            this.IdCountry = idCountry;
        }

        [JsonProperty("idCity")]
        public int IdCity { get; set; }

        [JsonProperty("idProvince")]
        public int IdProvince { get; set; }

        [JsonProperty("idRegion")]
        public int IdRegion { get; set; }

        [JsonProperty("idCountry")]
        public string IdCountry { get; set; }
    }
}
