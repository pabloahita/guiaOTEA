using System.Text.Json.Serialization;
using System.ComponentModel.DataAnnotations;
using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class for addresses
    /// Author: Pablo Ahita del Barrio
    /// Version: 1
    /// </summary>
    public class Address
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="idAddress">Address identifier</param>
        /// <param name="addressName">Address name</param>
        /// <param name="idCity">City identifier (if is precharged)</param>
        /// <param name="idProvince">Province identifier (if is precharged)</param>
        /// <param name="idRegion">Region identifier (if is precharged)</param>
        /// <param name="idCountry">Country identifier</param>
        /// <param name="nameCity">City name</param>
        /// <param name="nameProvince">Province name</param>
        /// <param name="nameRegion">Region name</param>
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

        /// <summary>
        /// Address identifier
        /// </summary>
        [Key]
        [JsonPropertyName("idAddress")]
        public int idAddress { get; set; }

        /// <summary>
        /// Address name
        /// </summary>
        [JsonPropertyName("addressName")]
        public string addressName { get; set; }

        /// <summary>
        /// City identifier (if is precharged)
        /// </summary>
        [JsonPropertyName("idCity")]
        public int? idCity { get; set; }

        /// <summary>
        /// Province identifier (if is precharged)
        /// </summary>
        [JsonPropertyName("idProvince")]
        public int? idProvince { get; set; }

        /// <summary>
        /// Region identifier (if is precharged)
        /// </summary>
        [JsonPropertyName("idRegion")]
        public int? idRegion { get; set; }

        /// <summary>
        /// Country identifier
        /// </summary>
        [JsonPropertyName("idCountry")]
        public string idCountry { get; set; }

        /// <summary>
        /// City name
        /// </summary>
        [JsonPropertyName("nameCity")]
        public string nameCity { get; set; }

        /// <summary>
        /// Province name
        /// </summary>
        [JsonPropertyName("nameProvince")]
        public string nameProvince { get; set; }

        /// <summary>
        /// Region name
        /// </summary>
        [JsonPropertyName("nameRegion")]
        public string nameRegion { get; set; }
    }
}
