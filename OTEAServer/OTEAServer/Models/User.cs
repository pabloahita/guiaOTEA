using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class User
    {
        public User(string email, string userType, string first_Name, string last_Name, string password, long telephone, int? idOrganization, string? organizationType, string? illness) { 
            this.Email = email;
            this.UserType = userType;
            this.First_Name = first_Name;
            this.Last_Name = last_Name;
            this.Password = password;
            this.Telephone = telephone;
            this.idOrganization = idOrganization;
            this.organizationType = organizationType;
            this.illness = illness;
        }

        [JsonProperty("emailUser")]
        public string Email { get; set; }

        [JsonProperty("first_name")]
        public string First_Name { get; set; }

        [JsonProperty("last_name")]
        public string Last_Name { get; set; }

        [JsonProperty("passwordUser")]
        public string Password { get; set; }

        [JsonProperty("telephone")]
        public long Telephone { get; set; }

        [JsonProperty("userType")]
        public string UserType { get; set; }

        [JsonProperty("idOrganization")]
        public int? idOrganization { get; set; }

        [JsonProperty("organizationType")]
        public string? organizationType { get; set; }

        [JsonProperty("illness")]
        public string? illness { get; set; }

    }
}
