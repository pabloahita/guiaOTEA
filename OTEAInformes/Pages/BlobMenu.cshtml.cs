using Azure.Storage.Blobs.Models;
using Azure.Storage.Blobs;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using WebApplication1;

namespace OTEAInformes.Pages
{
    public class BlobMenuModel : PageModel
    {
        private readonly BlobService _blobService;

        public BlobMenuModel(BlobService blobService)
        {
            _blobService = blobService;
        }

        public List<string> BlobNames { get; set; }

        public async Task OnGetAsync()
        {
            BlobNames = await _blobService.ListBlobsAsync("reports");
        }

        public string GetBlobUrl(string blobName)
        {
            return _blobService.GetBlobSasUrl("reports", blobName);
        }
    }
}
