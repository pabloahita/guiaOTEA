using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;

namespace OTEAServer.Models
{
    public class Country
    {
        public Country(string nameSpanish, string nameEnglish, string nameFrench, string idCountry) {
            this.nameSpanish = nameSpanish;
            this.nameEnglish = nameEnglish;
            this.nameFrench = nameFrench;
            this.idCountry = idCountry;
        }
        
        [JsonProperty("idCountry")]
        public string idCountry { get; set; }

        [JsonProperty("nameRegion")]
        public string nameSpanish { get; set; }

        [JsonProperty("nameRegion")]
        public string nameEnglish { get; set; }

        [JsonProperty("nameRegion")]
        public string nameFrench { get; set; }

    }
}
