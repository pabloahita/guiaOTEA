using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Evidences")]
    public class EvidencesController : ControllerBase
    {
        private readonly ILogger<EvidencesController> _logger;
        private readonly EvidencesService _evidencesService;

        public EvidencesController(ILogger<EvidencesController> logger, EvidencesService evidencesService)
        {
            _logger = logger;
            _evidencesService = evidencesService;
        }


        // GET all action
        [HttpGet]
        public IActionResult GetAll()
        {
            var evidences = _evidencesService.GetAll();
            return Ok(evidences);
        }

        // GET all by INDICATORTYPE action
        [HttpGet("idIndicator={idIndicator}")]
        public IActionResult GetAllByIndicator(int idIndicator, string indicatorType)
        {
            var evidences = _evidencesService.GetAllByIndicator(idIndicator, indicatorType);
            return Ok(evidences);
        }

        // GET by ID AND INDICATOR TYPE action

        [HttpGet("idEvidence={idEvidence}:idIndicator={idIndicator}:indicatorType={indicatorType}")]
        public ActionResult<Evidence> Get(int idEvidence, int idIndicator, string indicatorType)
        {
            var evidence = _evidencesService.Get(idEvidence,idIndicator, indicatorType);

            if (evidence == null)
                return NotFound();

            return evidence;
        }



        // POST action
        [HttpPost]
        public IActionResult Create(int idEvidence, int idIndicator, string indicatorType, string evidenceDescription, int evidenceValue)
        {
            _evidencesService.Add(idEvidence,idIndicator,indicatorType,evidenceDescription, evidenceValue);
            Evidence evidence = new Evidence(idEvidence, idIndicator, indicatorType, evidenceDescription, evidenceValue);
            return CreatedAtAction(nameof(Get), new { id = evidence.IdEvidence, idIndicator=evidence.IdIndicator, type = evidence.IndicatorType }, evidence);
        }

        // PUT action
        [HttpPut("idEvidence={idEvidence}:idIndicator={idIndicator}:indicatorType={indicatorType}")]
        public IActionResult Update(int idEvidence, int idIndicator, string indicatorType, Evidence evidence)
        {
            // This code will update the mesa and return a result
            if (idEvidence != evidence.IdEvidence || idIndicator != evidence.IdIndicator || indicatorType != evidence.IndicatorType)
                return BadRequest();

            var existingEvidence = _evidencesService.Get(idEvidence, idIndicator, indicatorType);
            if (existingEvidence is null)
                return NotFound();

            _evidencesService.Update(evidence);

            return Ok(evidence);
        }

        // DELETE action
        [HttpDelete("idEvidence={idEvidence}:idIndicator={idIndicator}:indicatorType={indicatorType}")]
        public IActionResult Delete(int idEvidence, int idIndicator, string indicatorType)
        {
            // This code will delete the mesa and return a result
            var evidence = _evidencesService.Get(idEvidence, idIndicator, indicatorType);

            if (evidence is null)
                return NotFound();

            _evidencesService.Delete(idEvidence, idIndicator, indicatorType);

            return NoContent();
        }
    }
}
