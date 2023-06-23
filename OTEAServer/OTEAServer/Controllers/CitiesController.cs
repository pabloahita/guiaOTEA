using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Cities")]
    public class CitiesController : ControllerBase
    {
        private readonly ILogger<CitiesController> _logger;
        private readonly CitiesService _citiesService;

        public CitiesController(ILogger<CitiesController> logger, CitiesService citiesService)
        {
            _logger = logger;
            _citiesService = citiesService;
        }

        // GET all by province action
        [HttpGet("province::idProvince={idProvince}:idRegion={idRegion}:idCountry={idCountry}")]
        public IActionResult GetAllByProvince(int idProvince, int idRegion, string idCountry)
        {
            var cities = _citiesService.GetAllByProvince(idProvince,idRegion,idCountry);
            return Ok(cities);
        }

        // GET by CITY by IDS action

        [HttpGet("city::idCity={idCity}:idProvince={idProvince}:idRegion={idRegion}:idCountry={idCountry}")]
        public ActionResult<City> Get(int idCity, int idProvince, int idRegion, string idCountry)
        {
            var city = _citiesService.Get(idCity,idProvince,idRegion,idCountry);

            if (city == null)
                return NotFound();

            return city;
        }
    }
}
