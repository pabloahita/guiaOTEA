using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    public class Session
    {
        public Session(string email, string sessionToken){
            this.email=email;
            this.sessionToken=sessionToken;
        }

        [JsonPropertyName("email")]
        public string email {get;set;}

        [JsonPropertyName("sessionToken")]
        public string sessionToken {get;set;}
    }
}