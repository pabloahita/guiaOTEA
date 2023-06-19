namespace OTEAServer.Models
{
    public class User
    {
        public User(string email, string userType, string first_Name, string last_Name, string password, int telephone, int? idOrganization, string? organizationType, string? illness) { 
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
        public string Email { get; set; }
        public string First_Name { get; set; }
        public string Last_Name { get; set; }
        public string Password { get; set; }
        public int Telephone { get; set; }
        public string UserType { get; set; }
        public int? idOrganization { get; set; }
        public string? organizationType { get; set; }
        public string? illness { get; set; }

    }
}
