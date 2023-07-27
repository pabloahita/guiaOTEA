using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Countries")]
    public class CountriesController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public CountriesController(DatabaseContext context)
        {
            _context = context;
        }

        // GET all by country action
        [HttpGet("all")]
        public IActionResult GetAll()
        {
            var countries = _context.Countries.ToList();
            return Ok(countries);
        }

        // GET COUNTRY by IDS action

        [HttpGet("get")]
        public ActionResult<Country> Get([FromQuery] string idCountry)
        {
            var country = _context.Countries.FirstOrDefault(c=>c.idCountry == idCountry);

            if (country == null)
                return NotFound();

            return country;
        }
    }
}
