using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("IndicatorsEvaluations")]
    public class IndicatorsEvaluationsController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public IndicatorsEvaluationsController(DatabaseContext context)
        {
            _context=context;
        }


        // GET all action
        [HttpGet]
        public IActionResult GetAll()
        {
            var indicatorsEvaluations = _context.IndicatorsEvaluations.ToList();
            return Ok(indicatorsEvaluations);
        }

        // GET all by EVALUATOR ORGANIZATION action
        [HttpGet("evaluatorOrg::idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")]
        public IActionResult GetAllByEvaluatorOrganization(int idEvaluatorOrganization,string orgType,string illness)
        {
            var indicatorsEvaluations = _context.IndicatorsEvaluations.Where(e=>e.idEvaluatorOrganization==idEvaluatorOrganization && e.orgTypeEvaluator==orgType && e.illness==illness).ToList();
            return Ok(indicatorsEvaluations);
        }

        //GET all by EVALUATED ORGANIZATION
        [HttpGet("evaluatedOrg::idEvaluatedOrganization={idEvaluatedOrganization}:orgType={orgType}:illness={illness}")]
        public IActionResult GetAllByEvaluatedOrganization(int idEvaluatedOrganization, string orgType, string illness)
        {
            var indicatorsEvaluations = _context.IndicatorsEvaluations.Where(e => e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgType && e.illness == illness).ToList();
            return Ok(indicatorsEvaluations);
        }

        // GET by PK action

        [HttpGet("get::evaluation_date={evaluation_date}:idEvaluatedOrganization:orgTypeEvaluated={orgTypeEvaluated}:illness={illness}")]
        public ActionResult<IndicatorsEvaluation> Get(long evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, string illness)
        {
            var indicatorsEvaluation = _context.IndicatorsEvaluations.FirstOrDefault(e => e.evaluationDate==evaluationDate && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.illness == illness);

            if (indicatorsEvaluation == null)
                return NotFound();

            return indicatorsEvaluation;
        }



        // POST action
        [HttpPost]
        public IActionResult Create([FromBody] IndicatorsEvaluation indicatorsEvaluation)
        {
            _context.IndicatorsEvaluations.Add(indicatorsEvaluation);
            _context.SaveChanges();
            return CreatedAtAction(nameof(Get), new { evaluation_date = indicatorsEvaluation.evaluationDate, idEvaluatedOrganization = indicatorsEvaluation.idEvaluatedOrganization, orgTypeEvaluated = indicatorsEvaluation.orgTypeEvaluated, illness = indicatorsEvaluation.illness }, indicatorsEvaluation);
        }

        // PUT action
        [HttpPut("upd::evaluation_date={evaluation_date}:idEvaluatedOrganization:orgTypeEvaluated={orgTypeEvaluated}:illness={illness}")]
        public IActionResult Update(long evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, string illness, IndicatorsEvaluation indicatorsEvaluation)
        {
            // This code will update the mesa and return a result
            if (evaluationDate != indicatorsEvaluation.evaluationDate || idEvaluatedOrganization != indicatorsEvaluation.idEvaluatedOrganization || orgTypeEvaluated != indicatorsEvaluation.orgTypeEvaluated || illness!=indicatorsEvaluation.illness)
                return BadRequest();

            var existingIndicatorsEvaluation = _context.IndicatorsEvaluations.FirstOrDefault(e => e.evaluationDate == evaluationDate && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.illness == illness);
            if (existingIndicatorsEvaluation is null)
                return NotFound();

            _context.IndicatorsEvaluations.Update(indicatorsEvaluation);
            _context.SaveChanges();
            return Ok(indicatorsEvaluation);
        }

        // DELETE action
        [HttpDelete("del::evaluation_date={evaluation_date}:idEvaluatedOrganization:orgTypeEvaluated={orgTypeEvaluated}:illness={illness}")]
        public IActionResult Delete(long evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, string illness)
        {
            // This code will delete the mesa and return a result
            var indicatorsEvaluation = _context.IndicatorsEvaluations.FirstOrDefault(e => e.evaluationDate == evaluationDate && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.illness == illness);

            if (indicatorsEvaluation is null)
                return NotFound();

            _context.IndicatorsEvaluations.Remove(indicatorsEvaluation);
            _context.SaveChanges();

            return NoContent();
        }
    }
}
