using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;

namespace OTEAServer.Models
{
    public class Address
    {
        public Address(int idAddress, string addressName, int zipCode, int? idCity, int? idProvince, int? idRegion, string idCountry, string nameCity, string nameProvince, string nameRegion)
        {
            this.idAddress = idAddress;
            this.addressName= addressName;
            this.zipCode = zipCode;
            this.idCity = idCity;
            this.idProvince = idProvince;
            this.idRegion = idRegion;
            this.idCountry = idCountry;
            this.nameCity = nameCity;
            this.nameProvince = nameProvince;
            this.nameRegion = nameRegion;
        }

        [Key]
        [JsonProperty("idAddress")]
        public int idAddress { get; set; }

        [JsonProperty("addressName")]
        public string addressName { get; set; }

        [JsonProperty("zipCode")]
        public int zipCode { get; set; }

        [JsonProperty("idCity")]
        public int? idCity { get; set; }

        [JsonProperty("idProvince")]
        public int? idProvince { get; set; }

        [JsonProperty("idRegion")]
        public int? idRegion { get; set; }

        [JsonProperty("idCountry")]
        public string idCountry { get; set; }
        
        [JsonProperty("nameCity")]
        public string nameCity { get; set; }

        [JsonProperty("nameProvince")]
        public string nameProvince { get; set; }

        [JsonProperty("nameRegion")]
        public string nameRegion { get; set; }
    }
}
