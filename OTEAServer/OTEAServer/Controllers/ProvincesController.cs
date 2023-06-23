using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Provinces")]
    public class ProvincesController : ControllerBase
    {
        private readonly ILogger<ProvincesController> _logger;
        private readonly ProvincesService _provincesService;

        public ProvincesController(ILogger<ProvincesController> logger, ProvincesService provincesService)
        {
            _logger = logger;
            _provincesService = provincesService;
        }

        // GET all by region action
        [HttpGet("region::idRegion={idRegion}:idCountry={idCountry}")]
        public IActionResult GetAllByRegion(int idRegion, string idCountry)
        {
            var provinces = _provincesService.GetAllByRegion(idRegion, idCountry);
            return Ok(provinces);
        }

        // GET by CITY by IDS action

        [HttpGet("get::idProvince={idProvince}:idRegion={idRegion}:idCountry={idCountry}")]
        public ActionResult<Province> Get(int idProvince, int idRegion, string idCountry)
        {
            var province = _provincesService.Get(idProvince, idRegion, idCountry);

            if (province == null)
                return NotFound();

            return province;
        }
    }
}
