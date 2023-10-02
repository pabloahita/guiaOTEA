using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    public class User
    {
        public User(string emailUser, string userType, string first_name, string last_name, string passwordUser, long telephone, int? idOrganization, string? organizationType, string? illness)
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

        [JsonPropertyName("emailUser")]
        public string emailUser { get; set; }

        [JsonPropertyName("first_name")]
        public string first_name { get; set; }

        [JsonPropertyName("last_name")]
        public string last_name { get; set; }

        [JsonPropertyName("passwordUser")]
        public string passwordUser { get; set; }

        [JsonPropertyName("telephone")]
        public long telephone { get; set; }

        [JsonPropertyName("userType")]
        public string userType { get; set; }

        [JsonPropertyName("idOrganization")]
        public int? idOrganization { get; set; }

        [JsonPropertyName("organizationType")]
        public string? organizationType { get; set; }

        [JsonPropertyName("illness")]
        public string? illness { get; set; }

    }
}
