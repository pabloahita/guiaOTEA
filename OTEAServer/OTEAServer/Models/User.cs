using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class User
    {
        public User(string email, string userType, string first_Name, string last_Name, string password, long telephone, int? idOrganization, string? organizationType, string? illness) { 
            this.emailUser = email;
            this.userType = userType;
            this.first_name = first_Name;
            this.last_name = last_Name;
            this.passwordUser = password;
            this.telephone = telephone;
            this.idOrganization = idOrganization;
            this.organizationType = organizationType;
            this.illness = illness;
        }

        [JsonProperty("emailUser")]
        public string emailUser { get; set; }

        [JsonProperty("first_name")]
        public string first_name { get; set; }

        [JsonProperty("last_name")]
        public string last_name { get; set; }

        [JsonProperty("passwordUser")]
        public string passwordUser { get; set; }

        [JsonProperty("telephone")]
        public long telephone { get; set; }

        [JsonProperty("userType")]
        public string userType { get; set; }

        [JsonProperty("idOrganization")]
        public int? idOrganization { get; set; }

        [JsonProperty("organizationType")]
        public string? organizationType { get; set; }

        [JsonProperty("illness")]
        public string? illness { get; set; }

    }
}
