using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Evidences")]
    public class EvidencesController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public EvidencesController(DatabaseContext context)
        {
            _context = context;
        }


        // GET all action
        [HttpGet("all")]
        public IActionResult GetAll()
        {
            var evidences = _context.Evidences.ToList();
            return Ok(evidences);
        }

        // GET all by INDICATORTYPE action
        [HttpGet("ind")]
        public IActionResult GetAllByIndicator([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion)
        {
            var evidences = _context.Evidences.Where(e=>e.idIndicator==idIndicator && e.indicatorType==indicatorType && e.idAmbit==idAmbit && e.indicatorVersion==indicatorVersion).ToList();
            return Ok(evidences);
        }

        // GET by ID AND INDICATOR TYPE action

        [HttpGet("get")]
        public ActionResult<Evidence> Get([FromQuery] int idEvidence, [FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion)
        {
            var evidence = _context.Evidences.FirstOrDefault(e => e.idEvidence == idEvidence && e.idIndicator==idIndicator && e.indicatorType==indicatorType && e.idAmbit==idAmbit && e.indicatorVersion==indicatorVersion);

            if (evidence == null)
                return NotFound();

            return evidence;
        }



        // POST action
        [HttpPost]
        public IActionResult Create([FromBody] Evidence evidence)
        {
            _context.Evidences.Add(evidence);
            _context.SaveChanges();
            return CreatedAtAction(nameof(Get), new { id = evidence.idEvidence, idIndicator=evidence.idIndicator, type = evidence.indicatorType, idAmbit=evidence.idAmbit, version=evidence.indicatorVersion }, evidence);
        }

        // PUT action
        [HttpPut]
        public IActionResult Update([FromQuery] int idEvidence, [FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromBody] Evidence evidence)
        {
            // This code will update the mesa and return a result
            if (idEvidence != evidence.idEvidence || idIndicator != evidence.idIndicator || idAmbit!= evidence.idAmbit || indicatorType != evidence.indicatorType)
                return BadRequest();

            var existingEvidence = _context.Evidences.FirstOrDefault(e => e.idEvidence == idEvidence && e.idIndicator == idIndicator && e.indicatorType == indicatorType && e.idAmbit==idAmbit && e.indicatorVersion == indicatorVersion);
            if (existingEvidence is null)
                return NotFound();

            existingEvidence.idEvidence = idEvidence;
            existingEvidence.idIndicator = idIndicator;
            existingEvidence.indicatorType = indicatorType;
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
            existingEvidence.indicatorVersion = indicatorVersion;

            _context.SaveChanges();

            return Ok(existingEvidence);
        }

        // DELETE action
        [HttpDelete]
        public IActionResult Delete([FromQuery] int idEvidence, [FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion)
        {
            // This code will delete the mesa and return a result
            var evidence = _context.Evidences.FirstOrDefault(e => e.idEvidence == idEvidence && e.idIndicator == idIndicator && e.indicatorType == indicatorType && e.idAmbit==idAmbit && e.indicatorVersion == indicatorVersion);

            if (evidence is null)
                return NotFound();

            _context.Evidences.Remove(evidence);
            _context.SaveChanges();

            return NoContent();
        }
    }
}
