using Azure.Storage.Blobs;
using GTranslate.Translators;
using Microsoft.AspNetCore.Mvc;

namespace OTEAServer.Misc
{
    /// <summary>
    /// Class controller for file uploading operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("Uploader")]
    public class FileUploader
    {
        /// <summary>
        /// Method that translates a text from an origin language to a target language
        /// </summary>
        /// <param name="text">Text to translate</param>
        /// <param name="origin">Origin language</param>
        /// <param name="target">Target language</param>
        /// <returns>Translated text</returns>
        [HttpPut("upload")]
        public async Task<string> uploadFile([FromBody] byte[] data, [FromQuery] string containerName, [FromQuery] string fileName)
        {
            try
            {
                Stream inputStream = new MemoryStream(data);
                string connectionString = "DefaultEndpointsProtocol=https;AccountName=guiaotea;AccountKey=26jn+aC0S1u3EZCiFRPogNPz73Ot3/qKVHWn+s2OU3gu9Dkb1AHLz//qeI6l8WZ7VyxFV8Kmx+4c+ASt0Y0YKg==;EndpointSuffix=core.windows.net";
                // Get a reference to a blob
                BlobContainerClient containerClient = new BlobContainerClient(connectionString, containerName);
                BlobClient blobClient = containerClient.GetBlobClient(fileName);

                // Open the file and upload its data
                await blobClient.UploadAsync(inputStream, true);
                
                string blobUrl= blobClient.Uri.AbsoluteUri;

                return blobUrl;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }
    }
}
