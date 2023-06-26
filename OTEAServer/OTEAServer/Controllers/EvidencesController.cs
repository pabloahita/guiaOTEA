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
        [HttpGet("ind::idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public IActionResult GetAllByIndicator(int idIndicator, string indicatorType, int indicatorVersion)
        {
            var evidences = _evidencesService.GetAllByIndicator(idIndicator, indicatorType, indicatorVersion);
            return Ok(evidences);
        }

        // GET by ID AND INDICATOR TYPE action

        [HttpGet("get::idEvidence={idEvidence}:idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public ActionResult<Evidence> Get(int idEvidence, int idIndicator, string indicatorType, int indicatorVersion)
        {
            var evidence = _evidencesService.Get(idEvidence,idIndicator, indicatorType, indicatorVersion);

            if (evidence == null)
                return NotFound();

            return evidence;
        }



        // POST action
        [HttpPost]
        public IActionResult Create(int idEvidence, int idIndicator, string indicatorType, string descriptionEnglish, string descriptionSpanish, string descriptionFrench, int evidenceValue, int indicatorVersion)
        {
            _evidencesService.Add(idEvidence,idIndicator,indicatorType, descriptionEnglish, descriptionSpanish, descriptionFrench, evidenceValue, indicatorVersion);
            Evidence evidence = new Evidence(idEvidence, idIndicator, indicatorType, descriptionEnglish, descriptionSpanish, descriptionFrench, evidenceValue,indicatorVersion);
            return CreatedAtAction(nameof(Get), new { id = evidence.idEvidence, idIndicator=evidence.idIndicator, type = evidence.indicatorType, version=evidence.indicatorVersion }, evidence);
        }

        // PUT action
        [HttpPut("put::idEvidence={idEvidence}:idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public IActionResult Update(int idEvidence, int idIndicator, string indicatorType, int indicatorVersion, Evidence evidence)
        {
            // This code will update the mesa and return a result
            if (idEvidence != evidence.idEvidence || idIndicator != evidence.idIndicator || indicatorType != evidence.indicatorType)
                return BadRequest();

            var existingEvidence = _evidencesService.Get(idEvidence, idIndicator, indicatorType, indicatorVersion);
            if (existingEvidence is null)
                return NotFound();

            _evidencesService.Update(evidence);

            return Ok(evidence);
        }

        // DELETE action
        [HttpDelete("del::idEvidence={idEvidence}:idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")]
        public IActionResult Delete(int idEvidence, int idIndicator, string indicatorType, int indicatorVersion)
        {
            // This code will delete the mesa and return a result
            var evidence = _evidencesService.Get(idEvidence, idIndicator, indicatorType, indicatorVersion);

            if (evidence is null)
                return NotFound();

            _evidencesService.Delete(idEvidence, idIndicator, indicatorType, indicatorVersion);

            return NoContent();
        }
    }
}
