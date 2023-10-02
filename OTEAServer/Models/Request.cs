using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    public class Request
    {
        public Request(string email, int statusReq, string tempPassword){
            this.email=email;
            this.statusReq=statusReq;
            this.tempPassword=tempPassword;
        }

        [JsonPropertyName("email")]
        public string email {get;set;}

        [JsonPropertyName("statusReq")]
        public int statusReq {get;set;}

        [JsonPropertyName("tempPassword")]
        public string tempPassword {get;set;}
        
    }
}