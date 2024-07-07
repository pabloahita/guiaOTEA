using Azure.Identity;
using System;
using Azure.Security.KeyVault.Secrets;

namespace OTEAServer
{
    public class SessionConfig
    {

        private static readonly Lazy<SessionConfig> instance = new Lazy<SessionConfig>(() => new SessionConfig());

        public SessionConfig() {

            try
            {
                var kVUrl = $"https://guiaoteakeyvault.vault.azure.net/";
                var client = new SecretClient(new Uri(kVUrl), new ManagedIdentityCredential());
                connectionString = client.GetSecret("connectionString").Value.Value;
                secret = client.GetSecret("secretKeyGuiaOtea").Value.Value;
            }
            catch (Exception ex)
            {
                // Handle exceptions or log errors appropriately
                throw new ApplicationException("Failed to initialize SessionConfig", ex);
            }

        }

        private static SessionConfig Instance => instance.Value;
        public String secret { get; set; }

        public String connectionString { get; set; }

    }
}
