using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Security.Policy;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for indicators
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    
    
    [ApiController]
    [Route("Indicators")]
    public class IndicatorsController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        public IndicatorsController(DatabaseContext context)
        {
            _context = context;
        }


        /// <summary>
        /// Method that obtains all the indicators
        /// </summary>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Indicators list</returns>
        [HttpGet("all")]
        public IActionResult GetAll([FromQuery] string evaluationType)
        {
            try
            {
                var indicators = _context.Indicators
                    .Where(i => i.evaluationType==evaluationType && i.isActive == 1)
                    .OrderBy(i => i.idIndicator)
                    .ToList();
                return Ok(indicators);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains all the indicators of an ambit
        /// </summary>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <returns>Indicators list</returns>
        [HttpGet("ambit")]
        public IActionResult GetAllByType([FromQuery] int idAmbit)
        {
            try
            {
                var indicators = _context.Indicators.Where(i => i.idAmbit == idAmbit && i.isActive == 1).ToList();
                return Ok(indicators);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains an indicator from the database
        /// </summary>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="indicatorType">Indicator type</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit</param>
        /// <param name="idSubAmbit">First level division of the ambit</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Indicator if success, null if not</returns>

        [HttpGet("get")]
        public ActionResult<Indicator> Get([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType)
        {
            try
            {
                var indicator = _context.Indicators.FirstOrDefault(i => i.idIndicator == idIndicator && i.indicatorType == indicatorType && i.idSubSubAmbit == idSubSubAmbit && i.idSubAmbit == idSubAmbit && i.idAmbit == idAmbit && i.indicatorVersion == indicatorVersion && i.evaluationType==evaluationType);

                if (indicator == null)
                    return NotFound();

                return indicator;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }



        /// <summary>
        /// Method that creates an indicator
        /// </summary>
        /// <param name="indicator">Indicator</param>
        /// <returns>Indicator if success, null if not</returns>
        [HttpPost]
        public IActionResult Create([FromBody] Indicator indicator)
        {
            try
            {
                _context.Indicators.Add(indicator);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new { id = indicator.idIndicator, type = indicator.indicatorType, idSubSubAmbit = indicator.idSubSubAmbit, idSubAmbit = indicator.idSubAmbit, idAmbit = indicator.idAmbit, version = indicator.indicatorVersion, evaluationType =indicator.evaluationType }, indicator);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that updates an indicator
        /// </summary>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="indicatorType">Indicator type</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit</param>
        /// <param name="idSubAmbit">First level division of the ambit</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <param name="indicator">Indicator</param>
        /// <returns>Indicator if success, null if not</returns>
        [HttpPut]
        public IActionResult Update([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType, [FromBody] Indicator indicator)
        {
            try
            {
                if (idIndicator != indicator.idIndicator || indicatorType != indicator.indicatorType || indicatorVersion != indicator.indicatorVersion - 1 || idSubSubAmbit != indicator.idSubSubAmbit || idSubAmbit != indicator.idSubAmbit || idAmbit != indicator.idAmbit || evaluationType!=indicator.evaluationType)
                    return BadRequest();
                var existingIndicator = _context.Indicators.FirstOrDefault(i => i.idIndicator == idIndicator && i.indicatorType == indicatorType && i.idSubSubAmbit == idSubSubAmbit && i.idSubAmbit == idSubAmbit && i.idAmbit == idAmbit && i.indicatorVersion == indicatorVersion && i.evaluationType == evaluationType);
                if (existingIndicator is null)
                    return NotFound();

                _context.Indicators.Add(indicator);
                existingIndicator.isActive = 0;
                _context.SaveChanges();

                return Ok(indicator);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that deletes an indicator
        /// </summary>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="indicatorType">Indicator type</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit</param>
        /// <param name="idSubAmbit">First level division of the ambit</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Indicator if success, null if not</returns>
        [HttpDelete]
        public IActionResult Delete([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType)
        {
            try
            {
                var indicator = _context.Indicators.FirstOrDefault(i => i.idIndicator == idIndicator && i.indicatorType == indicatorType && i.idSubSubAmbit == idSubSubAmbit && i.idSubAmbit == idSubAmbit && i.idAmbit == idAmbit && i.indicatorVersion == indicatorVersion && i.evaluationType == evaluationType);

                if (indicator is null)
                    return NotFound();

                _context.Indicators.Remove(indicator);
                _context.SaveChanges();
                return NoContent();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}
