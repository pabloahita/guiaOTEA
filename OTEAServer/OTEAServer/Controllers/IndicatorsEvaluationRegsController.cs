using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;

namespace OTEAServer.Controllers
{

    [ApiController]
    [Route("IndicatorsEvaluationRegs")]
    public class IndicatorsEvaluationRegsController : ControllerBase
    {
        private readonly ILogger<IndicatorsEvaluationRegsController> _logger;
        private readonly IndicatorsEvaluationRegsService _IndicatorsEvaluationRegsService;

        public IndicatorsEvaluationRegsController(ILogger<IndicatorsEvaluationRegsController> logger, IndicatorsEvaluationRegsService IndicatorsEvaluationRegsService)
        {
            _logger = logger;
            _IndicatorsEvaluationRegsService = IndicatorsEvaluationRegsService;
        }
        [HttpGet]
        public IActionResult GetAll() {
            var indicatorsEvaluationRegs = _IndicatorsEvaluationRegsService.GetAll();
            return Ok(indicatorsEvaluationRegs);
        }

        [HttpGet("indEval::evaluationDate={evaluationDate}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")]
        public IActionResult GetAllByIndicatorsEvaluation(long evaluationDate, int idEvaluatedOrganization, string orgType, string illness) {
            var indicatorsEvaluationRegs = _IndicatorsEvaluationRegsService.GetAllByIndicatorsEvaluation(evaluationDate,idEvaluatedOrganization,orgType,illness);
            return Ok(indicatorsEvaluationRegs);
        }
       

        [HttpGet("get::evaluationDate={evaluationDate}:idEvaluatedOrganization={idEvaluatedOrganization}:orgType={orgType}:illness={illness}:indicatorId={indicatorId}:idEvidence={idEvidence}:indicatorVersion={indicatorVersion}")]
        public ActionResult<IndicatorsEvaluationReg> Get(long evaluationDate, int idEvaluatedOrganization, string orgType, string illness, int indicatorId, int idEvidence, int indicatorVersion) {
            var indicatorsEvaluation = _IndicatorsEvaluationRegsService.Get(evaluationDate, idEvaluatedOrganization, orgType, illness,indicatorId,idEvidence,indicatorVersion);

            if (indicatorsEvaluation == null)
                return NotFound();

            return indicatorsEvaluation;
        }

        [HttpPost]
        public ActionResult<IndicatorsEvaluationReg> Create([FromBody] IndicatorsEvaluationReg indicatorsEvaluationReg) {
            _IndicatorsEvaluationRegsService.Add(indicatorsEvaluationReg.evaluationDate, indicatorsEvaluationReg.idEvaluatedOrganization, indicatorsEvaluationReg.orgTypeEvaluated, indicatorsEvaluationReg.illness, indicatorsEvaluationReg.indicatorId, indicatorsEvaluationReg.idEvidence, indicatorsEvaluationReg.isMarked, indicatorsEvaluationReg.indicatorVersion);
            //IndicatorsEvaluationReg indicatorsEvaluationReg = new IndicatorsEvaluationReg(evaluationDate, idEvaluatedOrganization,orgType, illness, indicatorId, idEvidence, isMarked, indicatorVersion);
            return CreatedAtAction(nameof(Get), new
            {
                evaluationDate = indicatorsEvaluationReg.evaluationDate,
                idEvaluatedOrganization = indicatorsEvaluationReg.idEvaluatedOrganization,
                orgType = indicatorsEvaluationReg.orgTypeEvaluated,
                illness = indicatorsEvaluationReg.illness,
                indicatorId = indicatorsEvaluationReg.indicatorId,
                idEvidence = indicatorsEvaluationReg.idEvidence,
                indicatorVersion = indicatorsEvaluationReg.indicatorVersion
            }, indicatorsEvaluationReg);
        }


        [HttpPut("upd::evaluationDate={evaluationDate}:idEvaluatedOrganization={idEvaluatedOrganization}:orgType={orgType}:illness={illness}:indicatorId={indicatorId}:idEvidence={idEvidence}:indicatorVersion={indicatorVersion}")]
        public ActionResult<IndicatorsEvaluationReg> Update(long evaluationDate, int idEvaluatedOrganization, string orgType, string illness, int indicatorId, int idEvidence, int indicatorVersion, [FromBody] IndicatorsEvaluationReg indicatorsEvaluationReg) {
            // This code will update the mesa and return a result
            if (evaluationDate != indicatorsEvaluationReg.evaluationDate || idEvaluatedOrganization != indicatorsEvaluationReg.idEvaluatedOrganization || orgType != indicatorsEvaluationReg.orgTypeEvaluated || illness != indicatorsEvaluationReg.illness || indicatorId!=indicatorsEvaluationReg.indicatorId || idEvidence!=indicatorsEvaluationReg.idEvidence || indicatorVersion!=indicatorVersion)
                return BadRequest();

            var existingIndicatorsEvaluationReg = _IndicatorsEvaluationRegsService.Get(evaluationDate, idEvaluatedOrganization, orgType, illness,indicatorId,idEvidence,indicatorVersion);
            if (existingIndicatorsEvaluationReg is null)
                return NotFound();

            _IndicatorsEvaluationRegsService.Update(indicatorsEvaluationReg);

            return Ok(indicatorsEvaluationReg);
        }


        [HttpDelete("del::evaluationDate={evaluationDate}:idEvaluatedOrganization={idEvaluatedOrganization}:orgType={orgType}:illness={illness}:indicatorId={indicatorId}:idEvidence={idEvidence}:indicatorVersion={indicatorVersion}")]
        public ActionResult<IndicatorsEvaluationReg> Delete(long evaluationDate, int idEvaluatedOrganization, string orgType, string illness, int indicatorId, int idEvidence, int indicatorVersion) {
            var indicatorsEvaluation = _IndicatorsEvaluationRegsService.Get(evaluationDate, idEvaluatedOrganization, orgType, illness, indicatorId, idEvidence, indicatorVersion);

            if (indicatorsEvaluation is null)
                return NotFound();

            _IndicatorsEvaluationRegsService.Delete(evaluationDate, idEvaluatedOrganization, orgType, illness, indicatorId, idEvidence, indicatorVersion);

            return NoContent();
        }

    }
}
