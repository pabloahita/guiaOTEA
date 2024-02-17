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
        /// Method that uploads a file to blob
        /// </summary>
        /// <param name="text">Text to translate</param>
        /// <param name="origin">Origin language</param>
        /// <param name="target">Target language</param>
        /// <returns>Translated text</returns>
        [HttpPost("upload")]
        public async Task<string> uploadFile([FromForm] IFormFile file, [FromForm] string containerName)
        {
            try
            {
                // Convertir el archivo a un Stream
                Stream inputStream = file.OpenReadStream();

                string connectionString = "DefaultEndpointsProtocol=https;AccountName=guiaotea;AccountKey=26jn+aC0S1u3EZCiFRPogNPz73Ot3/qKVHWn+s2OU3gu9Dkb1AHLz//qeI6l8WZ7VyxFV8Kmx+4c+ASt0Y0YKg==;EndpointSuffix=core.windows.net";

                // Obtener una referencia a un blob
                BlobContainerClient containerClient = new BlobContainerClient(connectionString, containerName);
                BlobClient blobClient = containerClient.GetBlobClient(file.FileName);

                // Abrir el archivo y subir sus datos
                await blobClient.UploadAsync(inputStream, true);

                string blobUrl = blobClient.Uri.AbsoluteUri;

                // Aquí puedes procesar la descripción como necesites

                return blobUrl;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }
    }
}
