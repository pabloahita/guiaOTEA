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
        [HttpGet("indicatorType={indicatorType}")]
        public IActionResult GetAllByType(string indicatorType)
        {
            var indicators = _indicatorsService.GetAllByType(indicatorType);
            return Ok(indicators);
        }

        // GET by ID AND INDICATOR TYPE action

        [HttpGet("idIndicator={idIndicator}:indicatorType={indicatorType}")]
        public ActionResult<Indicator> Get(int idIndicator, string indicatorType)
        {
            var indicator = _indicatorsService.Get(idIndicator,indicatorType);

            if (indicator == null)
                return NotFound();

            return indicator;
        }



        // POST action
        [HttpPost]
        public IActionResult Create(int idIndicator, string indicatorType, string indicatorDescription, int indicatorPriority)
        {
            _indicatorsService.Add(idIndicator,indicatorType,indicatorDescription,indicatorPriority);
            Indicator indicator = new Indicator(idIndicator, indicatorType, indicatorDescription, indicatorPriority);
            return CreatedAtAction(nameof(Get), new { id = indicator.IdIndicator, type=indicator.IndicatorType }, indicator);
        }

        // PUT action
        [HttpPut("idIndicator={idIndicator}:indicatorType={indicatorType}")]
        public IActionResult Update(int idIndicator, string indicatorType, Indicator indicator)
        {
            // This code will update the mesa and return a result
            if (idIndicator != indicator.IdIndicator || indicatorType != indicator.IndicatorType)
                return BadRequest();

            var existingIndicator = _indicatorsService.Get(idIndicator, indicatorType);
            if (existingIndicator is null)
                return NotFound();

            _indicatorsService.Update(indicator);

            return NoContent();
        }

        // DELETE action
        [HttpDelete("idIndicator={idIndicator}:indicatorType={indicatorType}")]
        public IActionResult Delete(int idIndicator, string indicatorType)
        {
            // This code will delete the mesa and return a result
            var indicator = _indicatorsService.Get(idIndicator, indicatorType);

            if (indicator is null)
                return NotFound();

            _indicatorsService.Delete(idIndicator, indicatorType);

            return NoContent();
        }
    }
}
