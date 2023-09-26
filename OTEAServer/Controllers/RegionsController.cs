using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Regions")]
    public class RegionsController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public RegionsController(DatabaseContext context)
        {
            _context = context;
        }

        // GET all by region action
        [HttpGet("country")]
        public IActionResult GetAllByRegion(string idCountry)
        {
            var regions = _context.Regions.Where(r=>r.idCountry==idCountry).ToList();
            return Ok(regions);
        }

        // GET by CITY by IDS action

        [HttpGet("get")]
        public ActionResult<Region> Get([FromQuery] int idRegion, [FromQuery] string idCountry)
        {
            var region = _context.Regions.FirstOrDefault(r=>r.idRegion==idRegion && r.idCountry==idCountry);

            if (region == null)
                return NotFound();

            return region;
        }
    }
}
