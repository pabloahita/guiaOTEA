using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class Address
    {
        public Address(int idAddress, string nameStreet, int numberStreet, int floorApartment,
                   char apartmentLetter, int zipCode, string city, string province, string region, string country)
        {
            this.IdAddress = idAddress;
            this.NameStreet = nameStreet;
            this.NumberStreet = numberStreet;
            this.FloorApartment = floorApartment;
            this.ApartmentLetter = apartmentLetter;
            this.ZipCode = zipCode;
            this.City = city;
            this.Province = province;
            this.Region = region;
            this.Country = country;
        }

        [JsonProperty("idAddress")]
        public int IdAddress { get; set; }

        [JsonProperty("nameStreet")]
        public string NameStreet { get; set; }

        [JsonProperty("numberStreet")]
        public int NumberStreet { get; set; }

        [JsonProperty("floorApartment")]
        public int FloorApartment { get; set; }

        [JsonProperty("apartmentLetter")]
        public char ApartmentLetter { get; set; }

        [JsonProperty("zipCode")]
        public int ZipCode { get; set; }

        [JsonProperty("city")]
        public string City { get; set; }

        [JsonProperty("province")]
        public string Province { get; set; }

        [JsonProperty("region")]
        public string Region { get; set; }

        [JsonProperty("country")]
        public string Country { get; set; }
    }
}
