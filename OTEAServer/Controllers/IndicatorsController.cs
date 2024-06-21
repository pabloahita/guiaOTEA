using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Security.Policy;
using System.Text.Json;

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
        [Authorize]
        public IActionResult GetAll([FromQuery] string evaluationType, [FromHeader] string Authorization)
        {
            try
            {
                List<JsonDocument> allData=new List<JsonDocument>();
                var indicators = _context.Indicators
                    .Where(i => i.evaluationType==evaluationType && i.isActive == 1)
                    .OrderBy(i => i.idIndicator)
                    .ToList();
                foreach (var indicator in indicators)
                {                    
                    String ind= "{\"idIndicator\":\"" + indicator.idIndicator + "\"," +
                        "\"indicatorType\":\"" + indicator.indicatorType + "\"," +
                        "\"idSubSubAmbit\":\"" + indicator.idSubSubAmbit + "\"," +
                        "\"idSubAmbit\":\"" + indicator.idSubAmbit + "\"," +
                        "\"idAmbit\":\"" + indicator.idAmbit + "\"," +
                        "\"descriptionSpanish\":\"" + indicator.descriptionSpanish + "\"," +
                        "\"descriptionEnglish\":\"" + indicator.descriptionEnglish + "\"," +
                        "\"descriptionFrench\":\"" + indicator.descriptionFrench + "\"," +
                        "\"descriptionBasque\":\"" + indicator.descriptionBasque + "\"," +
                        "\"descriptionCatalan\":\"" + indicator.descriptionCatalan + "\"," +
                        "\"descriptionDutch\":\"" + indicator.descriptionDutch + "\"," +
                        "\"descriptionGalician\":\"" + indicator.descriptionGalician + "\"," +
                        "\"descriptionGerman\":\"" + indicator.descriptionGerman + "\"," +
                        "\"descriptionItalian\":\"" + indicator.descriptionItalian + "\"," +
                        "\"descriptionPortuguese\":\"" + indicator.descriptionPortuguese + "\"," +
                        "\"indicatorPriority\":\"" + indicator.indicatorPriority + "\"," +
                        "\"indicatorVersion\":\"" + indicator.indicatorVersion + "\"," +
                        "\"isActive\":\"" + indicator.indicatorVersion + "\"," +
                        "\"evaluationType\":\"" + indicator.evaluationType + "\"}";
                    allData.Add(JsonDocument.Parse(ind));
                }

                int tamEvidences = 0;
           
                if (evaluationType == "COMPLETE")
                {
                    var evidences = _context.Evidences
                        .Where(e => e.evaluationType == evaluationType)
                        .OrderBy(e => e.idIndicator)
                        .ThenBy(e => e.idEvidence)
                        .ToList();
                    foreach (var evidence in evidences)
                    {
                        String ev = "{\"idEvidence\":\"" + evidence.idEvidence + "\"," +
                                "\"idIndicator\":\"" + evidence.idIndicator + "\"," +
                                "\"indicatorType\":\"" + evidence.indicatorType + "\"," +
                                "\"idSubSubAmbit\":\"" + evidence.idSubSubAmbit + "\"," +
                                "\"idSubAmbit\":\"" + evidence.idSubAmbit + "\"," +
                                "\"idAmbit\":\"" + evidence.idAmbit + "\"," +
                                "\"descriptionSpanish\":\"" + evidence.descriptionSpanish + "\"," +
                                "\"descriptionEnglish\":\"" + evidence.descriptionEnglish + "\"," +
                                "\"descriptionFrench\":\"" + evidence.descriptionFrench + "\"," +
                                "\"descriptionBasque\":\"" + evidence.descriptionBasque + "\"," +
                                "\"descriptionCatalan\":\"" + evidence.descriptionCatalan + "\"," +
                                "\"descriptionDutch\":\"" + evidence.descriptionDutch + "\"," +
                                "\"descriptionGalician\":\"" + evidence.descriptionGalician + "\"," +
                                "\"descriptionGerman\":\"" + evidence.descriptionGerman + "\"," +
                                "\"descriptionItalian\":\"" + evidence.descriptionItalian + "\"," +
                                "\"descriptionPortuguese\":\"" + evidence.descriptionPortuguese + "\"," +
                                "\"evidenceValue\":\"" + evidence.evidenceValue + "\"," +
                                "\"indicatorVersion\":\"" + evidence.indicatorVersion + "\"," +
                                "\"evaluationType\":\"" + evidence.evaluationType + "\"}";
                        allData.Add(JsonDocument.Parse(ev));
                    }
                    tamEvidences = evidences.Count;
                }

                var ambits = _context.Ambits.ToList();
                foreach (var ambit in ambits) {
                    String amb= "{\"idAmbit\":\"" + ambit.idAmbit + "\"," +
                            "\"descriptionSpanish\":\"" + ambit.descriptionSpanish + "\"," +
                            "\"descriptionEnglish\":\"" + ambit.descriptionEnglish + "\"," +
                            "\"descriptionFrench\":\"" + ambit.descriptionFrench + "\"," +
                            "\"descriptionBasque\":\"" + ambit.descriptionBasque + "\"," +
                            "\"descriptionCatalan\":\"" + ambit.descriptionCatalan + "\"," +
                            "\"descriptionDutch\":\"" + ambit.descriptionDutch + "\"," +
                            "\"descriptionGalician\":\"" + ambit.descriptionGalician + "\"," +
                            "\"descriptionGerman\":\"" + ambit.descriptionGerman + "\"," +
                            "\"descriptionItalian\":\"" + ambit.descriptionItalian + "\"," +
                            "\"descriptionPortuguese\":\"" + ambit.descriptionPortuguese + "\"}";
                    allData.Add(JsonDocument.Parse(amb));
                }

                var subAmbits = _context.SubAmbits.ToList();
                foreach (var subAmbit in subAmbits) {
                    String subAmb = "{\"idSubAmbit\":\"" + subAmbit.idSubAmbit + "\"," +
                                "\"idAmbit\":\"" + subAmbit.idAmbit + "\"," +
                                "\"descriptionSpanish\":\"" + subAmbit.descriptionSpanish + "\"," +
                                "\"descriptionEnglish\":\"" + subAmbit.descriptionEnglish + "\"," +
                                "\"descriptionFrench\":\"" + subAmbit.descriptionFrench + "\"," +
                                "\"descriptionBasque\":\"" + subAmbit.descriptionBasque + "\"," +
                                "\"descriptionCatalan\":\"" + subAmbit.descriptionCatalan + "\"," +
                                "\"descriptionDutch\":\"" + subAmbit.descriptionDutch + "\"," +
                                "\"descriptionGalician\":\"" + subAmbit.descriptionGalician + "\"," +
                                "\"descriptionGerman\":\"" + subAmbit.descriptionGerman + "\"," +
                                "\"descriptionItalian\":\"" + subAmbit.descriptionItalian + "\"," +
                                "\"descriptionPortuguese\":\"" + subAmbit.descriptionPortuguese + "\"}";
                    allData.Add(JsonDocument.Parse(subAmb));
                }

                var subSubAmbits = _context.SubSubAmbits.ToList();
                foreach (var subSubAmbit in subSubAmbits) {
                    String subSubAmb = "{\"idSubSubAmbit\":\"" + subSubAmbit.idSubSubAmbit + "\"," +
                                "\"idSubAmbit\":\"" + subSubAmbit.idSubAmbit + "\"," +
                                "\"idAmbit\":\"" + subSubAmbit.idAmbit + "\"," +
                                "\"descriptionSpanish\":\"" + subSubAmbit.descriptionSpanish + "\"," +
                                "\"descriptionEnglish\":\"" + subSubAmbit.descriptionEnglish + "\"," +
                                "\"descriptionFrench\":\"" + subSubAmbit.descriptionFrench + "\"," +
                                "\"descriptionBasque\":\"" + subSubAmbit.descriptionBasque + "\"," +
                                "\"descriptionCatalan\":\"" + subSubAmbit.descriptionCatalan + "\"," +
                                "\"descriptionDutch\":\"" + subSubAmbit.descriptionDutch + "\"," +
                                "\"descriptionGalician\":\"" + subSubAmbit.descriptionGalician + "\"," +
                                "\"descriptionGerman\":\"" + subSubAmbit.descriptionGerman + "\"," +
                                "\"descriptionItalian\":\"" + subSubAmbit.descriptionItalian + "\"," +
                                "\"descriptionPortuguese\":\"" + subSubAmbit.descriptionPortuguese + "\"}";
                    allData.Add(JsonDocument.Parse(subSubAmb));
                }
                String tams = "{\"numIndicators\":\"" + indicators.Count + "\",";
                if (evaluationType == "COMPLETE") {
                    tams += "\"numEvidences\":\"" + tamEvidences + "\",";
                }
                tams += "\"numAmbits\":\"" + ambits.Count + "\"," +
                        "\"numSubAmbits\":\"" + subAmbits.Count + "\"," +
                        "\"numSubSubAmbits\":\"" + subSubAmbits.Count + "\"}";
                allData.Add(JsonDocument.Parse(tams));
                return Ok(allData);
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
        [Authorize]
        public IActionResult GetAllByType([FromQuery] int idAmbit, [FromHeader] string Authorization)
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
        [Authorize]
        public ActionResult<Indicator> Get([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType, [FromHeader] string Authorization)
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
        [Authorize(Policy = "Administrator")]
        public IActionResult Create([FromBody] Indicator indicator, [FromHeader] string Authorization)
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
        [Authorize(Policy = "Administrator")]
        public IActionResult Update([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType, [FromBody] Indicator indicator, [FromHeader] string Authorization)
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
        [Authorize(Policy = "Administrator")]
        public IActionResult Delete([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType, [FromHeader] string Authorization)
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
