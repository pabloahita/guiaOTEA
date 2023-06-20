using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class Address
    {
        public Address(int idAddress, string nameStreet, int numberStreet, int floorApartment,
                   char apartmentLetter, int zipCode, int idCity, int idProvince, int idRegion, string idCountry)
        {
            this.IdAddress = idAddress;
            this.NameStreet = nameStreet;
            this.NumberStreet = numberStreet;
            this.FloorApartment = floorApartment;
            this.ApartmentLetter = apartmentLetter;
            this.ZipCode = zipCode;
            this.IdCity = idCity;
            this.IdProvince = idProvince;
            this.IdRegion = idRegion;
            this.IdCountry = idCountry;
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
