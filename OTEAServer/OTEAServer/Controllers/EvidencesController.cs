﻿using Microsoft.AspNetCore.Http;
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
        [HttpGet]
        public IActionResult GetAll()
        {
            var evidences = _context.Evidences.ToList();
            return Ok(evidences);
        }

        // GET all by INDICATORTYPE action
        [HttpGet("ind::idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public IActionResult GetAllByIndicator(int idIndicator, string indicatorType, int indicatorVersion)
        {
            var evidences = _context.Evidences.Where(e=>e.idIndicator==idIndicator && e.indicatorType==indicatorType && e.indicatorVersion==indicatorVersion).ToList();
            return Ok(evidences);
        }

        // GET by ID AND INDICATOR TYPE action

        [HttpGet("get::idEvidence={idEvidence}:idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public ActionResult<Evidence> Get(int idEvidence, int idIndicator, string indicatorType, int indicatorVersion)
        {
            var evidence = _context.Evidences.FirstOrDefault(e => e.idEvidence == idEvidence && e.idIndicator == idIndicator && e.indicatorType == indicatorType && e.indicatorVersion == indicatorVersion);

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
            return CreatedAtAction(nameof(Get), new { id = evidence.idEvidence, idIndicator=evidence.idIndicator, type = evidence.indicatorType, version=evidence.indicatorVersion }, evidence);
        }

        // PUT action
        [HttpPut("put::idEvidence={idEvidence}:idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public IActionResult Update(int idEvidence, int idIndicator, string indicatorType, int indicatorVersion, [FromBody] Evidence evidence)
        {
            // This code will update the mesa and return a result
            if (idEvidence != evidence.idEvidence || idIndicator != evidence.idIndicator || indicatorType != evidence.indicatorType)
                return BadRequest();

            var existingEvidence = _context.Evidences.FirstOrDefault(e => e.idEvidence == idEvidence && e.idIndicator == idIndicator && e.indicatorType == indicatorType && e.indicatorVersion == indicatorVersion);
            if (existingEvidence is null)
                return NotFound();

            existingEvidence.idEvidence = idEvidence;
            existingEvidence.idIndicator = idIndicator;
            existingEvidence.indicatorType = indicatorType;
            existingEvidence.indicatorVersion = indicatorVersion;
            existingEvidence.descriptionEnglish = evidence.descriptionEnglish;
            existingEvidence.descriptionSpanish = evidence.descriptionSpanish;
            existingEvidence.descriptionFrench = evidence.descriptionFrench;
            existingEvidence.evidenceValue=evidence.evidenceValue;

            _context.SaveChanges();

            return Ok(existingEvidence);
        }

        // DELETE action
        [HttpDelete("del::idEvidence={idEvidence}:idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public IActionResult Delete(int idEvidence, int idIndicator, string indicatorType, int indicatorVersion)
        {
            // This code will delete the mesa and return a result
            var evidence = _context.Evidences.FirstOrDefault(e => e.idEvidence == idEvidence && e.idIndicator == idIndicator && e.indicatorType == indicatorType && e.indicatorVersion == indicatorVersion);

            if (evidence is null)
                return NotFound();

            _context.Evidences.Remove(evidence);
            _context.SaveChanges();

            return NoContent();
        }
    }
}
