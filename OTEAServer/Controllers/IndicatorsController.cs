using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Indicators")]
    public class IndicatorsController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public IndicatorsController(DatabaseContext context)
        {
            _context = context;
        }


        // GET all action
        [HttpGet]
        public IActionResult GetAll()
        {
            var indicators = _context.Indicators.ToList();
            return Ok(indicators);
        }

        // GET all by INDICATORTYPE action
        [HttpGet("all::indicatorType={indicatorType}")]
        public IActionResult GetAllByType(string indicatorType)
        {
            var indicators = _context.Indicators.Where(i=>i.indicatorType == indicatorType).ToList();
            return Ok(indicators);
        }

        // GET by ID AND INDICATOR TYPE action

        [HttpGet("get::idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public ActionResult<Indicator> Get(int idIndicator, string indicatorType, int indicatorVersion)
        {
            var indicator = _context.Indicators.FirstOrDefault(i=>i.indicatorId== idIndicator && i.indicatorType== indicatorType && i.indicatorVersion== indicatorVersion);

            if (indicator == null)
                return NotFound();

            return indicator;
        }



        // POST action
        [HttpPost]
        public IActionResult Create([FromBody] Indicator indicator)
        {
            _context.Indicators.Add(indicator);
            _context.SaveChanges();
            return CreatedAtAction(nameof(Get), new { id = indicator.indicatorId, type=indicator.indicatorType }, indicator);
        }

        // PUT action
        [HttpPut("upd::idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public IActionResult Update(int idIndicator, string indicatorType, int indicatorVersion, [FromBody] Indicator indicator)
        {
            // This code will update the mesa and return a result
            if (idIndicator != indicator.indicatorId || indicatorType != indicator.indicatorType || indicatorVersion != indicator.indicatorVersion)
                return BadRequest();

            var existingIndicator = _context.Indicators.FirstOrDefault(i => i.indicatorId == idIndicator && i.indicatorType == indicatorType && i.indicatorVersion == indicatorVersion-1);
            if (existingIndicator is null)
                return NotFound();

            //_indicatorsService.Update(idIndicator,indicatorType,indicatorVersion,indicator);
            _context.Indicators.Add(indicator);
            existingIndicator.isActive = 0;
            _context.Indicators.Update(existingIndicator);
            _context.SaveChanges();

            return Ok(indicator);
        }

        // DELETE action
        [HttpDelete("del::idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public IActionResult Delete(int idIndicator, string indicatorType, int indicatorVersion)
        {
            // This code will delete the mesa and return a result
            var indicator = _context.Indicators.FirstOrDefault(i => i.indicatorId == idIndicator && i.indicatorType == indicatorType && i.indicatorVersion == indicatorVersion);

            if (indicator is null)
                return NotFound();

            _context.Indicators.Remove(indicator);
            _context.SaveChanges();
            return NoContent();
        }
    }
}
