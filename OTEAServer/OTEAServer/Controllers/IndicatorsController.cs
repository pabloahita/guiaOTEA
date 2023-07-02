using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Indicators")]
    public class IndicatorsController : ControllerBase
    {
        private readonly ILogger<IndicatorsController> _logger;
        private readonly IndicatorsService _indicatorsService;

        public IndicatorsController(ILogger<IndicatorsController> logger, IndicatorsService indicatorsService)
        {
            _logger = logger;
            _indicatorsService = indicatorsService;
        }


        // GET all action
        [HttpGet]
        public IActionResult GetAll()
        {
            var indicators = _indicatorsService.GetAll();
            return Ok(indicators);
        }

        // GET all by INDICATORTYPE action
        [HttpGet("all::indicatorType={indicatorType}")]
        public IActionResult GetAllByType(string indicatorType)
        {
            var indicators = _indicatorsService.GetAllByType(indicatorType);
            return Ok(indicators);
        }

        // GET by ID AND INDICATOR TYPE action

        [HttpGet("get::idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public ActionResult<Indicator> Get(int idIndicator, string indicatorType, int indicatorVersion)
        {
            var indicator = _indicatorsService.Get(idIndicator,indicatorType,indicatorVersion);

            if (indicator == null)
                return NotFound();

            return indicator;
        }



        // POST action
        [HttpPost]
        public IActionResult Create([FromBody] Indicator indicator)
        {
            _indicatorsService.Add(indicator.indicatorId, indicator.indicatorType, indicator.descriptionEnglish, indicator.descriptionSpanish, indicator.descriptionFrench, indicator.indicatorPriority, indicator.indicatorVersion);
            return CreatedAtAction(nameof(Get), new { id = indicator.indicatorId, type=indicator.indicatorType }, indicator);
        }

        // PUT action
        [HttpPut("upd::idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public IActionResult Update(int idIndicator, string indicatorType, int indicatorVersion, [FromBody] Indicator indicator)
        {
            // This code will update the mesa and return a result
            if (idIndicator != indicator.indicatorId || indicatorType != indicator.indicatorType || indicatorVersion != indicator.indicatorVersion)
                return BadRequest();

            var existingIndicator = _indicatorsService.Get(idIndicator, indicatorType, indicatorVersion-1);
            if (existingIndicator is null)
                return NotFound();

            _indicatorsService.Update(idIndicator,indicatorType,indicatorVersion,indicator);

            return Ok(indicator);
        }

        // DELETE action
        [HttpDelete("del::idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public IActionResult Delete(int idIndicator, string indicatorType, int indicatorVersion)
        {
            // This code will delete the mesa and return a result
            var indicator = _indicatorsService.Get(idIndicator, indicatorType, indicatorVersion);

            if (indicator is null)
                return NotFound();

            _indicatorsService.Delete(idIndicator, indicatorType, indicatorVersion);

            return NoContent();
        }
    }
}
