using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;

namespace OTEAServer.Models
{
    public class User
    {
        public User(string emailUser, string userType, string first_name, string last_name, string passwordUser, long telephone, int idOrganization, string organizationType, string illness)
        {
            this.emailUser = emailUser;
            this.userType = userType;
            this.first_name = first_name;
            this.last_name = last_name;
            this.passwordUser = passwordUser;
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
        public int idOrganization { get; set; }

        [JsonProperty("organizationType")]
        public string organizationType { get; set; }

        [JsonProperty("illness")]
        public string illness { get; set; }

    }
}
