using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("IndicatorsEvaluations")]
    public class IndicatorsEvaluationsController : ControllerBase
    {
        private readonly ILogger<IndicatorsEvaluationsController> _logger;
        private readonly IndicatorsEvaluationsService _indicatorsEvaluationsService;

        public IndicatorsEvaluationsController(ILogger<IndicatorsEvaluationsController> logger, IndicatorsEvaluationsService indicatorsEvaluationsService)
        {
            _logger = logger;
            _indicatorsEvaluationsService = indicatorsEvaluationsService;
        }


        // GET all action
        [HttpGet]
        public IActionResult GetAll()
        {
            var indicatorsEvaluations = _indicatorsEvaluationsService.GetAll();
            return Ok(indicatorsEvaluations);
        }

        // GET all by EVALUATOR TEAM action
        [HttpGet("evalTeam::idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")]
        public IActionResult GetAllByEvaluatorTeam(int idEvaluatorTeam,int idEvaluatorOrganization,string orgType,string illness)
        {
            var indicatorsEvaluations = _indicatorsEvaluationsService.GetAllByEvaluatorTeam(idEvaluatorTeam, idEvaluatorOrganization, orgType, illness);
            return Ok(indicatorsEvaluations);
        }

        // GET by PK action

        [HttpGet("get::evaluation_date={evaluation_date}:idEvaluatedOrganization:orgTypeEvaluated={orgTypeEvaluated}:illness={illness}")]
        public ActionResult<IndicatorsEvaluation> Get(DateTime evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, string illness)
        {
            var indicatorsEvaluation = _indicatorsEvaluationsService.Get(evaluationDate,idEvaluatedOrganization,orgTypeEvaluated,illness);

            if (indicatorsEvaluation == null)
                return NotFound();

            return indicatorsEvaluation;
        }



        // POST action
        [HttpPost]
        public IActionResult Create(DateTime evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, int idEvaluatorTeam, int idEvaluatorOrganization, string orgTypeEvaluator, string illness, int totalScore)
        {
            _indicatorsEvaluationsService.Add(evaluationDate, idEvaluatedOrganization, orgTypeEvaluated, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, illness, totalScore);
            IndicatorsEvaluation indicatorsEvaluation = new IndicatorsEvaluation(evaluationDate, idEvaluatedOrganization, orgTypeEvaluated, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, illness, totalScore);
            return CreatedAtAction(nameof(Get), new { evaluationDate = indicatorsEvaluation.evaluationDate, idEvaluatedOrganization=indicatorsEvaluation.idEvaluatedOrganization, orgTypeEvaluated=indicatorsEvaluation.orgTypeEvaluated, illness=indicatorsEvaluation.illness }, indicatorsEvaluation);
        }

        // PUT action
        [HttpPut("upd::evaluation_date={evaluation_date}:idEvaluatedOrganization:orgTypeEvaluated={orgTypeEvaluated}:illness={illness}")]
        public IActionResult Update(DateTime evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, string illness, IndicatorsEvaluation indicatorsEvaluation)
        {
            // This code will update the mesa and return a result
            if (evaluationDate != indicatorsEvaluation.evaluationDate || idEvaluatedOrganization != indicatorsEvaluation.idEvaluatedOrganization || orgTypeEvaluated != indicatorsEvaluation.orgTypeEvaluated || illness!=indicatorsEvaluation.illness)
                return BadRequest();

            var existingIndicatorsEvaluation = _indicatorsEvaluationsService.Get(evaluationDate,idEvaluatedOrganization,orgTypeEvaluated,illness);
            if (existingIndicatorsEvaluation is null)
                return NotFound();

            _indicatorsEvaluationsService.Update(indicatorsEvaluation);

            return Ok(indicatorsEvaluation);
        }

        // DELETE action
        [HttpDelete("del::evaluation_date={evaluation_date}:idEvaluatedOrganization:orgTypeEvaluated={orgTypeEvaluated}:illness={illness}")]
        public IActionResult Delete(DateTime evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, string illness)
        {
            // This code will delete the mesa and return a result
            var indicatorsEvaluation = _indicatorsEvaluationsService.Get(evaluationDate, idEvaluatedOrganization, orgTypeEvaluated, illness);

            if (indicatorsEvaluation is null)
                return NotFound();

            _indicatorsEvaluationsService.Delete(evaluationDate, idEvaluatedOrganization, orgTypeEvaluated, illness);

            return NoContent();
        }
    }
}
