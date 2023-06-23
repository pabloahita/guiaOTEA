using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Regions")]
    public class RegionsController : ControllerBase
    {
        private readonly ILogger<RegionsController> _logger;
        private readonly RegionsService _regionsService;

        public RegionsController(ILogger<RegionsController> logger, RegionsService regionsService)
        {
            _logger = logger;
            _regionsService = regionsService;
        }

        // GET all by region action
        [HttpGet("country::idCountry={idCountry}")]
        public IActionResult GetAllByRegion(string idCountry)
        {
            var regions = _regionsService.GetAllByCountry(idCountry);
            return Ok(regions);
        }

        // GET by CITY by IDS action

        [HttpGet("get::idRegion={idRegion}:idCountry={idCountry}")]
        public ActionResult<Region> Get(int idRegion, string idCountry)
        {
            var region = _regionsService.Get(idRegion, idCountry);

            if (region == null)
                return NotFound();

            return region;
        }
    }
}
