using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Provinces")]
    public class ProvincesController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public ProvincesController(DatabaseContext context)
        {
            _context=context;
        }

        // GET all by region action
        [HttpGet("region")]
        public IActionResult GetAllByRegion([FromQuery] int idRegion, [FromQuery] string idCountry)
        {
            var provinces = _context.Provinces.Where(p=>p.idRegion==idRegion && p.idCountry==idCountry).ToList();
            return Ok(provinces);
        }

        // GET by CITY by IDS action

        [HttpGet("get")]
        public ActionResult<Province> Get([FromQuery] int idProvince, [FromQuery] int idRegion, [FromQuery] string idCountry)
        {
            var province = _context.Provinces.FirstOrDefault(p=>p.idProvince==idProvince && p.idRegion==idRegion && p.idCountry==idCountry);

            if (province == null)
                return NotFound();

            return province;
        }
    }
}
