using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Cities")]
    public class CitiesController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public CitiesController(DatabaseContext context)
        {
            _context=context;
        }

        // GET all by province action
        [HttpGet("allByProvince")]
        public IActionResult GetAllByProvince([FromQuery] int idProvince, [FromQuery] int idRegion, [FromQuery] string idCountry)
        {
            var cities = _context.Cities.Where(c=>c.idProvince==idProvince && c.idRegion==idRegion && c.idCountry==idCountry).ToList();
            return Ok(cities);
        }

        // GET by CITY by IDS action
        [HttpGet("get")]
        public ActionResult<City> Get([FromQuery] int idCity, [FromQuery] int idProvince, [FromQuery] int idRegion, [FromQuery] string idCountry)
        {
            var city = _context.Cities.FirstOrDefault(c => c.idCity == idCity && c.idProvince == idProvince && c.idRegion == idRegion && c.idCountry == idCountry);

            if (city == null)
                return NotFound();

            return city;
        }
    }
}
