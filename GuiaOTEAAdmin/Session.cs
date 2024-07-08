using Microsoft.Extensions.Diagnostics.HealthChecks;
using System.Text.Json;

namespace WebApplication1
{
    public class Session
    {

        private static Session instance;

        private UserSession user;

        private Organization organization;

        private string token;

        private List<UserSession> userList;

        private List<Organization> orgsList;

        public Session() { }

        private Session(JsonDocument data) {
            if (data.RootElement.TryGetProperty("user", out JsonElement userElement))
            {
                user = JsonSerializer.Deserialize<UserSession>(userElement.GetRawText());
            }

            if (data.RootElement.TryGetProperty("organization", out JsonElement organizationElement))
            {
                organization = JsonSerializer.Deserialize<Organization>(organizationElement.GetRawText());
            }

            if (data.RootElement.TryGetProperty("token", out JsonElement tokenElement))
            {
                token = tokenElement.GetString();
            }
        }

        public static void createInstance(JsonDocument data)
        {
            instance = new Session(data);
        }

        public static Session Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new Session();
                }
                return instance;
            }
        }

        public UserSession getUser() {
            return user;
        }

        public void setInstance(Session instance) {
            Session.instance=instance;
        }

        public Organization getOrganization() { return organization; }

        public string getToken() { return token; }

        public List<UserSession> getUserList() { return userList; }

        public void setUserList(List<UserSession> userList) { this.userList=userList; }

        public List<Organization> getOrganizationList() { return orgsList; }

        public void setOrgList(List<Organization> orgsList) { this.orgsList = orgsList; }

    }
}
