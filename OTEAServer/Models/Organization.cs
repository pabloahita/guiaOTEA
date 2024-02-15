using System.Text.Json.Serialization;
using System.Reflection.Metadata;
using System.Security.Cryptography.X509Certificates;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class for organizations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    public class Organization
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="idOrganization">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <param name="nameOrg">Organization name</param>
        /// <param name="idAddress">Organization address identifier</param>
        /// <param name="email">Organization email</param>
        /// <param name="telephone">Organization telephone</param>
        /// <param name="informationSpanish">Organization information in Spanish</param>
        /// <param name="informationEnglish">Organization information in English</param>
        /// <param name="informationFrench">Organization information in French</param>
        /// <param name="informationBasque">Organization information in Basque</param>
        /// <param name="informationCatalan">Organization information in Catalan</param>
        /// <param name="informationDutch">Organization information in Dutch</param>
        /// <param name="informationGalician">Organization information in Galician</param>
        /// <param name="informationGerman">Organization information in German</param>
        /// <param name="informationItalian">Organization information in Italian</param>
        /// <param name="informationPortuguese">Organization information in Portuguese</param>
        /// <param name="profilePhoto">Profile photo</param>
        public Organization(int idOrganization, string orgType, string illness, string nameOrg, int idAddress, string email, string telephone, string informationSpanish, string informationEnglish, string informationFrench, string informationBasque, string informationCatalan, string informationDutch, string informationGalician, string informationGerman, string informationItalian, string informationPortuguese, string? profilePhoto) {
            this.idOrganization = idOrganization;
            this.orgType = orgType;
            this.illness = illness;
            this.nameOrg = nameOrg;
            this.idAddress = idAddress;
            this.email = email;
            this.telephone = telephone;
            this.informationSpanish = informationSpanish;
            this.informationEnglish = informationEnglish;
            this.informationFrench = informationFrench;
            this.informationBasque = informationBasque;
            this.informationCatalan = informationCatalan;
            this.informationDutch = informationDutch;
            this.informationGalician = informationGalician;
            this.informationGerman = informationGerman;
            this.informationItalian = informationItalian;
            this.informationPortuguese = informationPortuguese;
            this.profilePhoto = profilePhoto;

        }

        /// <summary>
        /// Organization identifier
        /// </summary>
        [JsonPropertyName("idOrganization")]
        public int idOrganization { get; set; }

        /// <summary>
        /// Organization type
        /// </summary>
        [JsonPropertyName("orgType")]
        public string orgType { get; set; }

        /// <summary>
        /// Organization illness or syndrome
        /// </summary>
        [JsonPropertyName("illness")]
        public string illness { get; set; }

        /// <summary>
        /// Organization name
        /// </summary>
        [JsonPropertyName("nameOrg")]
        public string nameOrg { get; set; }

        /// <summary>
        /// Organization address identifier
        /// </summary>
        [JsonPropertyName("idAddress")]
        public int idAddress { get; set; }

        /// <summary>
        /// Organization email
        /// </summary>
        [JsonPropertyName("email")]
        public string email { get; set; }

        /// <summary>
        /// Organization telephone
        /// </summary>
        [JsonPropertyName("telephone")]
        public string telephone { get; set; }

        /// <summary>
        /// Organization information in Spanish
        /// </summary>
        [JsonPropertyName("informationSpanish")]
        public string informationSpanish { get; set; }

        /// <summary>
        /// Organization information in English
        /// </summary>
        [JsonPropertyName("informationEnglish")]
        public string informationEnglish { get; set; }

        /// <summary>
        /// Organization information in French
        /// </summary>
        [JsonPropertyName("informationFrench")]
        public string informationFrench { get; set; }

        /// <summary>
        /// Organization information in Basque
        /// </summary>
        [JsonPropertyName("informationBasque")]
        public string informationBasque { get; set; }

        /// <summary>
        /// Organization information in Catalan
        /// </summary>
        [JsonPropertyName("informationCatalan")]
        public string informationCatalan { get; set; }

        /// <summary>
        /// Organization information in Dutch
        /// </summary>
        [JsonPropertyName("informationDutch")]
        public string informationDutch { get; set; }

        /// <summary>
        /// Organization information in Galician
        /// </summary>
        [JsonPropertyName("informationGalician")]
        public string informationGalician { get; set; }

        /// <summary>
        /// Organization information in German
        /// </summary>
        [JsonPropertyName("informationGerman")]
        public string informationGerman { get; set; }

        /// <summary>
        /// Organization information in Italian
        /// </summary>
        [JsonPropertyName("informationItalian")]
        public string informationItalian { get; set; }

        /// <summary>
        /// Organization information in Portuguese
        /// </summary>
        [JsonPropertyName("informationPortuguese")]
        public string informationPortuguese { get; set; }


        /// <summary>
        /// Profile photo
        /// </summary>
        [JsonPropertyName("profilePhoto")]
        public string? profilePhoto { get; set; }


    }
}
