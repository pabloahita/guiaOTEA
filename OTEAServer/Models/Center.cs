using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class for centers
    /// Author: Pablo Ahita del Barrio
    /// Version: 1
    /// </summary>
    public class Center
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="idOrganization">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <param name="idCenter">Center identifier</param>
        /// <param name="descriptionEnglish">Center description in English</param>
        /// <param name="descriptionSpanish">Center description in Spanish</param>
        /// <param name="descriptionFrench">Center description in French</param>
        /// <param name="descriptionBasque">Center description in Basque</param>
        /// <param name="descriptionCatalan">Center description in Catalan</param>
        /// <param name="descriptionDutch">Center description in Dutch</param>
        /// <param name="descriptionGalician">Center description in Galician</param>
        /// <param name="descriptionGerman">Center description in German</param>
        /// <param name="descriptionItalian">Center description in Italian</param>
        /// <param name="descriptionPortuguese">Center description in Portuguese</param>
        /// <param name="idAddress">Address identifier</param>
        /// <param name="telephone">Center telephone</param>
        /// <param name="email">Center email</param>
        public Center(int idOrganization, string orgType, string illness, int idCenter, string descriptionEnglish, string descriptionSpanish, string descriptionFrench,string descriptionBasque, string descriptionCatalan, string descriptionDutch, string descriptionGalician, string descriptionGerman, string descriptionItalian, string descriptionPortuguese, int idAddress, string telephone, string email) { 
            this.idOrganization = idOrganization;
            this.orgType = orgType;
            this.illness = illness;
            this.idCenter = idCenter;
            this.descriptionSpanish = descriptionSpanish;
            this.descriptionEnglish = descriptionEnglish;
            this.descriptionFrench = descriptionFrench;
            this.descriptionBasque = descriptionBasque;
            this.descriptionCatalan = descriptionCatalan;
            this.descriptionDutch = descriptionDutch;
            this.descriptionGalician = descriptionGalician;
            this.descriptionGerman = descriptionGerman;
            this.descriptionItalian = descriptionItalian;
            this.descriptionPortuguese = descriptionPortuguese;
            this.telephone = telephone;
            this.idAddress = idAddress;
            this.email=email;
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
        /// Center identifier
        /// </summary>
        [JsonPropertyName("idCenter")]
        public int idCenter { get; set; }

        /// <summary>
        /// Center description in Spanish
        /// </summary>
        [JsonPropertyName("descriptionSpanish")]
        public string descriptionSpanish { get; set; }

        /// <summary>
        /// Center description in English
        /// </summary>
        [JsonPropertyName("descriptionEnglish")]
        public string descriptionEnglish { get; set; }

        /// <summary>
        /// Center description in French
        /// </summary>
        [JsonPropertyName("descriptionFrench")]
        public string descriptionFrench { get; set; }

        /// <summary>
        /// Center description in Basque
        /// </summary>
        [JsonPropertyName("descriptionBasque")]
        public string descriptionBasque { get; set; }

        /// <summary>
        /// Center description in Catalan
        /// </summary>
        [JsonPropertyName("descriptionCatalan")]
        public string descriptionCatalan { get; set; }

        /// <summary>
        /// Center description in Dutch
        /// </summary>
        [JsonPropertyName("descriptionDutch")]
        public string descriptionDutch { get; set; }


        /// <summary>
        /// Center description in Galician
        /// </summary>
        [JsonPropertyName("descriptionGalician")]
        public string descriptionGalician { get; set; }

        /// <summary>
        /// Center description in German
        /// </summary>
        [JsonPropertyName("descriptionGerman")]
        public string descriptionGerman { get; set; }

        /// <summary>
        /// Center description in Italian
        /// </summary>
        [JsonPropertyName("descriptionItalian")]
        public string descriptionItalian { get; set; }

        /// <summary>
        /// Center description in Portuguese
        /// </summary>
        [JsonPropertyName("descriptionPortuguese")]
        public string descriptionPortuguese { get; set; }

        /// <summary>
        /// Address identifier
        /// </summary>
        [JsonPropertyName("idAddress")]
        public int idAddress { get; set; }

        /// <summary>
        /// Center telephone
        /// </summary>
        [JsonPropertyName("telephone")]
        public string telephone { get; set; }

        /// <summary>
        /// Center email
        /// </summary>
        [JsonPropertyName("email")]
        public string email{get;set;}
    }

    
}
