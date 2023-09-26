using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    public class Region
    {
        public Region(int idRegion, string idCountry, string nameRegion) {
            this.idRegion = idRegion;
            this.idCountry = idCountry;
            this.nameRegion = nameRegion;
        }

        [JsonPropertyName("idRegion")]
        public int idRegion { get; set; }

        [JsonPropertyName("idCountry")]
        public string idCountry { get; set; }

        [JsonPropertyName("nameRegion")]
        public string nameRegion { get; set; }

    }
}
