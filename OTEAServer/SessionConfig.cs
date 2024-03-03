using Azure.Identity;
using System;
using Azure.Security.KeyVault.Secrets;

namespace OTEAServer
{
    public class SessionConfig
    {
        /*public SessionConfig() {

            var kVUrl = $"https://guiaoteakeyvault.vault.azure.net/";
            var client = new SecretClient(new Uri(kVUrl), new DefaultAzureCredential());
            connectionString = client.GetSecret("connectionString").Value.ToString();
            secret= client.GetSecret("secretKeyGuiaOtea").Value.ToString();

        }*/
        public String secret { get; set; }

        //public String connectionString { get; set; }
    }
}
