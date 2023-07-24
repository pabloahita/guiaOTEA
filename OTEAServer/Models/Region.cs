using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;

namespace OTEAServer.Models
{
    public class Region
    {
        public Region(int idRegion, string idCountry, string nameRegion) {
            this.idRegion = idRegion;
            this.idCountry = idCountry;
            this.nameRegion = nameRegion;
        }

        [JsonProperty("idRegion")]
        public int idRegion { get; set; }

        [JsonProperty("idCountry")]
        public string idCountry { get; set; }

        [JsonProperty("nameRegion")]
        public string nameRegion { get; set; }

    }
}
