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
        public Request(string email, int statusReq, string tempPassword){
            this.email=email;
            this.statusReq=statusReq;
            this.tempPassword=tempPassword;
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
        public string tempPassword {get;set;}
        
    }
}