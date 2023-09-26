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
        [HttpGet("all")]
        public IActionResult GetAll()
        {
            var indicators = _context.Indicators.Where(i=>i.isActive==1).ToList();
            return Ok(indicators);
        }

        // GET all by INDICATORTYPE action
        [HttpGet("ambit")]
        public IActionResult GetAllByType([FromQuery] int idAmbit)
        {
            var indicators = _context.Indicators.Where(i=>i.idAmbit == idAmbit && i.isActive == 1).ToList();
            return Ok(indicators);
        }

        // GET by ID AND INDICATOR TYPE action

        [HttpGet("get")]
        public ActionResult<Indicator> Get([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion)
        {
            var indicator = _context.Indicators.FirstOrDefault(i=>i.idIndicator== idIndicator && i.indicatorType== indicatorType && i.idAmbit==idAmbit && i.indicatorVersion== indicatorVersion);

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
            return CreatedAtAction(nameof(Get), new { id = indicator.idIndicator, type=indicator.indicatorType }, indicator);
        }

        // PUT action
        [HttpPut]
        public IActionResult Update([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int indicatorVersion, [FromQuery] int idAmbit, [FromBody] Indicator indicator)
        {
            // This code will update the mesa and return a result
            if (idIndicator != indicator.idIndicator || indicatorType != indicator.indicatorType || indicatorVersion != indicator.indicatorVersion-1)
                return BadRequest();

            var existingIndicator = _context.Indicators.FirstOrDefault(i => i.idIndicator== idIndicator && i.indicatorType== indicatorType && i.idAmbit==idAmbit && i.indicatorVersion== indicatorVersion);
            if (existingIndicator is null)
                return NotFound();

            _context.Indicators.Add(indicator);
            existingIndicator.isActive = 0;
            _context.SaveChanges();

            return Ok(indicator);
        }

        // DELETE action
        [HttpDelete]
        public IActionResult Delete([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion)
        {
            // This code will delete the mesa and return a result
            var indicator = _context.Indicators.FirstOrDefault(i => i.idIndicator== idIndicator && i.indicatorType== indicatorType && i.idAmbit==idAmbit && i.indicatorVersion== indicatorVersion);

            if (indicator is null)
                return NotFound();

            _context.Indicators.Remove(indicator);
            _context.SaveChanges();
            return NoContent();
        }
    }
}
