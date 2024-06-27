using Azure.Identity;
using System;
using Azure.Security.KeyVault.Secrets;

namespace OTEAServer
{
    public class SessionConfig
    {
        private static SessionConfig instance;
        public SessionConfig() {

            try
            {
                var kVUrl = $"https://guiaoteakeyvault.vault.azure.net/";
                var client = new SecretClient(new Uri(kVUrl), new DefaultAzureCredential());
                connectionString = client.GetSecret("connectionString").Value.Value;
                secret = client.GetSecret("secretKeyGuiaOtea").Value.Value;
            }
            catch (Exception ex)
            {
                // Handle exceptions or log errors appropriately
                throw new ApplicationException("Failed to initialize SessionConfig", ex);
            }

        }

        public static SessionConfig GetInstance()
        {
            if (instance == null)
            {
                instance = new SessionConfig();
            }
            return instance;
        }
        public String secret { get; set; }

        public String connectionString { get; set; }
    }
}
