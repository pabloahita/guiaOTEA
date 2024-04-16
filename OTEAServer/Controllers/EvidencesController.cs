using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for evidences operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    
    
    [ApiController]
    [Route("Evidences")]
    public class EvidencesController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public EvidencesController(DatabaseContext context)
        {
            _context = context;
        }


        /// <summary>
        /// Method that obtains all the evidences
        /// </summary>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Evidences list</returns>
        [HttpGet("all")]
        public IActionResult GetAll([FromQuery] string evaluationType)
        {
            try
            {
                var evidences = _context.Evidences
                    .Where(e=>e.evaluationType==evaluationType)
                    .OrderBy(e=>e.idIndicator)
                    .ThenBy(e=>e.idEvidence)
                    .ToList();
                return Ok(evidences);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains from the database all the evidences of an indicator
        /// </summary>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="indicatorType">Indicator type</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit</param>
        /// <param name="idSubAmbit">First level division of the ambit</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Evidences list</returns>
        [HttpGet("ind")]
        public IActionResult GetAllByIndicator([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType)
        {
            try
            {
                var evidences = _context.Evidences.Where(e => e.idIndicator == idIndicator && e.indicatorType == indicatorType && e.idSubSubAmbit == idSubSubAmbit && e.idSubAmbit == idSubAmbit && e.idAmbit == idAmbit && e.indicatorVersion == indicatorVersion && e.evaluationType==evaluationType).ToList();
                return Ok(evidences);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains from the database an evidence
        /// </summary>
        /// <param name="idEvidence">Evidence identifier</param>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="indicatorType">Indicator type</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit</param>
        /// <param name="idSubAmbit">First level division of the ambit</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Evidence if success, null if not</returns>
        [HttpGet("get")]
        public ActionResult<Evidence> Get([FromQuery] int idEvidence, [FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType)
        {
            try
            {
                var evidence = _context.Evidences.FirstOrDefault(e => e.idEvidence == idEvidence && e.idIndicator == idIndicator && e.indicatorType == indicatorType && e.idSubSubAmbit == idSubSubAmbit && e.idSubAmbit == idSubAmbit && e.idAmbit == idAmbit && e.indicatorVersion == indicatorVersion && e.evaluationType==evaluationType);

                if (evidence == null)
                    return NotFound();

                return evidence;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }



        /// <summary>
        /// Method that appends a new evidence to the database
        /// </summary>
        /// <param name="evidence">Evidence</param>
        /// <returns>Evidence if sucess, null if not</returns>
        [HttpPost]
        public IActionResult Create([FromBody] Evidence evidence)
        {
            try
            {
                _context.Evidences.Add(evidence);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new { id = evidence.idEvidence, idIndicator = evidence.idIndicator, type = evidence.indicatorType, idSubSubAmbit = evidence.idSubSubAmbit, idSubAmbit = evidence.idSubAmbit, idAmbit = evidence.idAmbit, version = evidence.indicatorVersion , evaluationType = evidence.evaluationType}, evidence);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that updates an existant evidence
        /// </summary>
        /// <param name="idEvidence">Evidence identifier</param>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="indicatorType">Indicator type</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit</param>
        /// <param name="idSubAmbit">First level division of the ambit</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <param name="evidence">Evidence</param>
        /// <returns>Updated evidence if success, null if not</returns>
        [HttpPut]
        public IActionResult Update([FromQuery] int idEvidence, [FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType, [FromBody] Evidence evidence)
        {
            try
            {
                if (idEvidence != evidence.idEvidence || idIndicator != evidence.idIndicator || idSubSubAmbit != evidence.idSubSubAmbit || idSubAmbit != evidence.idSubAmbit || idAmbit != evidence.idAmbit || indicatorType != evidence.indicatorType || evaluationType!=evidence.evaluationType)
                    return BadRequest();

                var existingEvidence = _context.Evidences.FirstOrDefault(e => e.idEvidence == idEvidence && e.idIndicator == idIndicator && e.indicatorType == indicatorType && e.idSubSubAmbit == idSubSubAmbit && e.idSubAmbit == idSubAmbit && e.idAmbit == idAmbit && e.indicatorVersion == indicatorVersion && e.evaluationType==evaluationType);
                if (existingEvidence is null)
                    return NotFound();

                existingEvidence.descriptionEnglish = evidence.descriptionEnglish;
                existingEvidence.descriptionSpanish = evidence.descriptionSpanish;
                existingEvidence.descriptionFrench = evidence.descriptionFrench;
                existingEvidence.descriptionBasque = evidence.descriptionBasque;
                existingEvidence.descriptionCatalan = evidence.descriptionCatalan;
                existingEvidence.descriptionDutch = evidence.descriptionDutch;
                existingEvidence.descriptionGalician = evidence.descriptionGalician;
                existingEvidence.descriptionGerman = evidence.descriptionGerman;
                existingEvidence.descriptionItalian = evidence.descriptionItalian;
                existingEvidence.descriptionPortuguese = evidence.descriptionPortuguese;
                existingEvidence.evidenceValue = evidence.evidenceValue;
                existingEvidence.evaluationType = evaluationType;

                _context.SaveChanges();

                return Ok(existingEvidence);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }

        }

        /// <summary>
        /// Method that deletes an evidence
        /// </summary>
        /// <param name="idEvidence">Evidence identifier</param>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="indicatorType">Indicator type</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit</param>
        /// <param name="idSubAmbit">First level division of the ambit</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <returns>Deleted evidence if success, null if not</returns>
        [HttpDelete]
        public IActionResult Delete([FromQuery] int idEvidence, [FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType)
        {
            try
            {
                var evidence = _context.Evidences.FirstOrDefault(e => e.idEvidence == idEvidence && e.idIndicator == idIndicator && e.indicatorType == indicatorType && e.idSubSubAmbit == idSubSubAmbit && e.idSubAmbit == idSubAmbit && e.idAmbit == idAmbit && e.indicatorVersion == indicatorVersion && e.evaluationType == evaluationType);

                if (evidence is null)
                    return NotFound();

                _context.Evidences.Remove(evidence);
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
