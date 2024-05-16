using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    /// <summary>
    /// Model class for requests
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    public class Request
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="email">Email of the possible new user</param>
        /// <param name="statusReq">Request status</param>
        /// <param name="tempPassword">Temporary password to access to the registration</param>
        /// <param name="idOrganization">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <param name="userType">User type</param>
        public Request(string email, int statusReq, string? tempPassword, int idOrganization, string orgType, string illness, string userType){
            this.email=email;
            this.statusReq=statusReq;
            this.tempPassword=tempPassword;
            this.idOrganization=idOrganization;
            this.orgType=orgType;
            this.illness=illness;
            this.userType = userType;
        }

        /// <summary>
        /// Email of the possible new user
        /// </summary>
        [JsonPropertyName("email")]
        public string email {get;set;}

        /// <summary>
        /// Request status
        /// </summary>
        [JsonPropertyName("statusReq")]
        public int statusReq {get;set;}

        /// <summary>
        /// Temporary password to access to the registration
        /// </summary>
        [JsonPropertyName("tempPassword")]
        public string? tempPassword {get;set;}

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
        /// User type
        /// </summary>
        [JsonPropertyName("userType")]
        public string userType { get; set; }

    }
}