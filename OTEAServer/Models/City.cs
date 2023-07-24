using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;

namespace OTEAServer.Models
{
    public class City
    {
        public City(int idCity, int idProvince, int idRegion, string idCountry, string cityName) {
            this.idCity = idCity;
            this.idProvince = idProvince;
            this.idRegion = idRegion;
            this.idCountry = idCountry;
            this.cityName = cityName;
        }

        [JsonProperty("idCity")]
        public int idCity { get; set; }

        [JsonProperty("idProvince")]
        public int idProvince { get; set; }

        [JsonProperty("idRegion")]
        public int idRegion { get; set; }

        [JsonProperty("idCountry")]
        public string idCountry { get; set; }

        [JsonProperty("cityName")]
        public string cityName { get; set; }

    }
}
