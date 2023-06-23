using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Countries")]
    public class CountriesController : ControllerBase
    {
        private readonly ILogger<CountriesController> _logger;
        private readonly CountriesService _countriesService;

        public CountriesController(ILogger<CountriesController> logger, CountriesService countriesService)
        {
            _logger = logger;
            _countriesService = countriesService;
        }

        // GET all by country action
        [HttpGet]
        public IActionResult GetAll()
        {
            var countries = _countriesService.GetAll();
            return Ok(countries);
        }

        // GET COUNTRY by IDS action

        [HttpGet("idCountry={idCountry}")]
        public ActionResult<Country> Get(string idCountry)
        {
            var country = _countriesService.Get(idCountry);

            if (country == null)
                return NotFound();

            return country;
        }
    }
}
