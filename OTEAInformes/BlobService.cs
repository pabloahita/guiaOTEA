using Azure.Storage.Blobs.Models;
using Azure.Storage.Blobs;
using WebApplication1;
using Azure.Storage.Sas;
using Azure.Storage;
using Microsoft.Extensions.Configuration;

namespace OTEAInformes
{
    public class BlobService
    {
        private readonly BlobServiceClient _blobServiceClient;
        private readonly StorageSharedKeyCredential _storageSharedKeyCredential;

        public BlobService(IConfiguration configuration)
        {
            var connectionString = configuration.GetConnectionString("AzureBlobStorage");
            _blobServiceClient = new BlobServiceClient(connectionString);

            // Analizar manualmente la cadena de conexión
            var connSettings = connectionString.Split(new[] { ';' }, StringSplitOptions.RemoveEmptyEntries);
            string accountName = null;
            string accountKey = null;

            foreach (var setting in connSettings)
            {
                if (setting.StartsWith("AccountName=", StringComparison.OrdinalIgnoreCase))
                {
                    accountName = setting.Substring("AccountName=".Length);
                }
                else if (setting.StartsWith("AccountKey=", StringComparison.OrdinalIgnoreCase))
                {
                    accountKey = setting.Substring("AccountKey=".Length);
                }
            }

            if (accountName == null || accountKey == null)
            {
                throw new InvalidOperationException("AccountName or AccountKey is missing in the connection string.");
            }

            _storageSharedKeyCredential = new StorageSharedKeyCredential(accountName, accountKey);
           
        }


        public async Task<List<string>> ListBlobsAsync(string containerName)
        {
            var blobs = new List<string>();
            var containerClient = _blobServiceClient.GetBlobContainerClient(containerName);

            if (Session.Instance.getUser().userType == "ADMIN")
            {
                await foreach (BlobItem blobItem in containerClient.GetBlobsAsync())
                {
                    blobs.Add(blobItem.Name);
                }
            }
            else
            {
                var organization = Session.Instance.getOrganization();
                await foreach (BlobItem blobItem in containerClient.GetBlobsAsync())
                {
                    if (blobItem.Name.StartsWith("ORG_" + organization.idOrganization + "_" + organization.orgType + "_" + organization.illness))
                    {
                        blobs.Add(blobItem.Name);
                    }
                }
            }


            return blobs;
        }

        public string GetBlobSasUrl(string containerName, string blobName)
        {
            var containerClient = _blobServiceClient.GetBlobContainerClient(containerName);
            var blobClient = containerClient.GetBlobClient(blobName);

            var sasBuilder = new BlobSasBuilder
            {
                BlobContainerName = containerName,
                BlobName = blobName,
                Resource = "b",
                StartsOn = DateTimeOffset.UtcNow.AddMinutes(-5),
                ExpiresOn = DateTimeOffset.UtcNow.AddHours(1) // Tiempo de expiración de 1 hora
            };

            sasBuilder.SetPermissions(BlobSasPermissions.Read);

            var sasToken = sasBuilder.ToSasQueryParameters(_storageSharedKeyCredential).ToString();

            return $"{blobClient.Uri}?{sasToken}";
        }
    }
}
