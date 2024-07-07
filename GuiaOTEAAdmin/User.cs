using System.Text.Json.Serialization;

namespace WebApplication1
{
    /// <summary>
    /// Model class for users
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    public class User
    {
        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="emailUser">User email</param>
        /// <param name="userType">User type</param>
        /// <param name="first_name">User first name</param>
        /// <param name="last_name">User last name</param>
        /// <param name="passwordUser">User password</param>
        /// <param name="telephone">User telephone</param>
        /// <param name="idOrganization">User organization identifier</param>
        /// <param name="orgType">User organization type</param>
        /// <param name="illness">User organization illness or syndrome</param>
        /// <param name="profilePhoto">Profile photo</param>
        public User(string emailUser, string userType, string first_name, string last_name, string passwordUser, string telephone, int? idOrganization, string? orgType, string? illness, string? profilePhoto, int isActive)
        {
            this.emailUser = emailUser;
            this.userType = userType;
            this.first_name = first_name;
            this.last_name = last_name;
            this.passwordUser = passwordUser;
            this.telephone = telephone;
            this.idOrganization = idOrganization;
            this.orgType = orgType;
            this.illness = illness;
            this.profilePhoto = profilePhoto;
            this.isActive = isActive;
        }

        /// <summary>
        /// User email
        /// </summary>
        [JsonPropertyName("emailUser")]
        public string emailUser { get; set; }

        /// <summary>
        /// User first name
        /// </summary>
        [JsonPropertyName("first_name")]
        public string first_name { get; set; }

        /// <summary>
        /// User last name
        /// </summary>
        [JsonPropertyName("last_name")]
        public string last_name { get; set; }

        /// <summary>
        /// User password
        /// </summary>
        [JsonPropertyName("passwordUser")]
        public string passwordUser { get; set; }

        /// <summary>
        /// User telephone
        /// </summary>
        [JsonPropertyName("telephone")]
        public string telephone { get; set; }

        /// <summary>
        /// User type
        /// </summary>
        [JsonPropertyName("userType")]
        public string userType { get; set; }

        /// <summary>
        /// User organization identifier
        /// </summary>
        [JsonPropertyName("idOrganization")]
        public int? idOrganization { get; set; }

        /// <summary>
        /// User organization type
        /// </summary>
        [JsonPropertyName("orgType")]
        public string? orgType { get; set; }

        /// <summary>
        /// User organization illness or syndrome
        /// </summary>
        [JsonPropertyName("illness")]
        public string? illness { get; set; }


        /// <summary>
        /// Profile photo
        /// </summary>
        [JsonPropertyName("profilePhoto")]
        public string? profilePhoto { get; set; }

        /// <summary>
        /// User is active
        /// </summary>
        [JsonPropertyName("isActive")]
        public int isActive { get; set; }

    }

}
