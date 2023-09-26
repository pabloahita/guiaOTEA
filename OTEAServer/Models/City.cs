using System.Text.Json.Serialization;

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

        [JsonPropertyName("idCity")]
        public int idCity { get; set; }

        [JsonPropertyName("idProvince")]
        public int idProvince { get; set; }

        [JsonPropertyName("idRegion")]
        public int idRegion { get; set; }

        [JsonPropertyName("idCountry")]
        public string idCountry { get; set; }

        [JsonPropertyName("cityName")]
        public string cityName { get; set; }

    }
}
