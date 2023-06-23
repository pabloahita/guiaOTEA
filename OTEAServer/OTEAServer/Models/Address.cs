using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class Address
    {
        public Address(int idAddress, string nameStreet, int numberStreet, int floorApartment,
                   char apartmentLetter, int zipCode, int idCity, int idProvince, int idRegion, string idCountry)
        {
            this.idAddress = idAddress;
            this.nameStreet = nameStreet;
            this.numberSt = numberStreet;
            this.floorApartment = floorApartment;
            this.apartmentLetter = apartmentLetter;
            this.zipCode = zipCode;
            this.idCity = idCity;
            this.idProvince = idProvince;
            this.idRegion = idRegion;
            this.idCountry = idCountry;
        }

        [JsonProperty("idAddress")]
        public int idAddress { get; set; }

        [JsonProperty("nameStreet")]
        public string nameStreet { get; set; }

        [JsonProperty("numberSt")]
        public int numberSt { get; set; }

        [JsonProperty("floorApartment")]
        public int floorApartment { get; set; }

        [JsonProperty("apartmentLetter")]
        public char apartmentLetter { get; set; }

        [JsonProperty("zipCode")]
        public int zipCode { get; set; }

        [JsonProperty("idCity")]
        public int idCity { get; set; }

        [JsonProperty("idProvince")]
        public int idProvince { get; set; }

        [JsonProperty("idRegion")]
        public int idRegion { get; set; }

        [JsonProperty("idCountry")]
        public string idCountry { get; set; }
    }
}
