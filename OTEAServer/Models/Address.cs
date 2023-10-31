using System.Text.Json.Serialization;
using System.ComponentModel.DataAnnotations;

namespace OTEAServer.Models
{
    public class Address
    {
        public Address(int idAddress, string addressName, int? idCity, int? idProvince, int? idRegion, string idCountry, string nameCity, string nameProvince, string nameRegion)
        {
            this.idAddress = idAddress;
            this.addressName= addressName;
            this.idCity = idCity;
            this.idProvince = idProvince;
            this.idRegion = idRegion;
            this.idCountry = idCountry;
            this.nameCity = nameCity;
            this.nameProvince = nameProvince;
            this.nameRegion = nameRegion;
        }

        [Key]
        [JsonPropertyName("idAddress")]
        public int idAddress { get; set; }

        [JsonPropertyName("addressName")]
        public string addressName { get; set; }

        [JsonPropertyName("idCity")]
        public int? idCity { get; set; }

        [JsonPropertyName("idProvince")]
        public int? idProvince { get; set; }

        [JsonPropertyName("idRegion")]
        public int? idRegion { get; set; }

        [JsonPropertyName("idCountry")]
        public string idCountry { get; set; }
        
        [JsonPropertyName("nameCity")]
        public string nameCity { get; set; }

        [JsonPropertyName("nameProvince")]
        public string nameProvince { get; set; }

        [JsonPropertyName("nameRegion")]
        public string nameRegion { get; set; }
    }
}
