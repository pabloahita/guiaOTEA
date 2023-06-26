using Microsoft.AspNetCore.Mvc;

namespace OTEAServer.Controllers
{
    public class CentersController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
    }
}
