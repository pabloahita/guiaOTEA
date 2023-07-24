using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
namespace OTEAServer.Controllers
{

    [ApiController]
    [Route("IndicatorsEvaluationRegs")]
    public class IndicatorsEvaluationRegsController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public IndicatorsEvaluationRegsController(DatabaseContext context)
        {
            _context = context;
        }

        [HttpGet]
        public IActionResult GetAll() {
            var indicatorsEvaluationRegs = _context.IndicatorsEvaluationRegs.ToList();
            return Ok(indicatorsEvaluationRegs);
        }

        [HttpGet("indEval::evaluationDate={evaluationDate}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")]
        public IActionResult GetAllByIndicatorsEvaluation(long evaluationDate, int idEvaluatedOrganization, string orgType, string illness) {
            var indicatorsEvaluationRegs = _context.IndicatorsEvaluationRegs.Where(r=>r.evaluationDate==evaluationDate && r.idEvaluatedOrganization==idEvaluatedOrganization && r.orgTypeEvaluated==orgType && r.illness==illness).ToList();
            return Ok(indicatorsEvaluationRegs);
        }
       

        [HttpGet("get::evaluationDate={evaluationDate}:idEvaluatedOrganization={idEvaluatedOrganization}:orgType={orgType}:illness={illness}:indicatorId={indicatorId}:idEvidence={idEvidence}:indicatorVersion={indicatorVersion}")]
        public ActionResult<IndicatorsEvaluationReg> Get(long evaluationDate, int idEvaluatedOrganization, string orgType, string illness, int indicatorId, int idEvidence, int indicatorVersion) {
            var indicatorsEvaluation = _context.IndicatorsEvaluationRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgType && r.illness == illness && r.indicatorId == indicatorId && r.idEvidence == idEvidence && r.indicatorVersion==indicatorVersion);

            if (indicatorsEvaluation == null)
                return NotFound();

            return indicatorsEvaluation;
        }

        [HttpPost]
        public ActionResult<IndicatorsEvaluationReg> Create([FromBody] IndicatorsEvaluationReg indicatorsEvaluationReg) {
            _context.IndicatorsEvaluationRegs.Add(indicatorsEvaluationReg);
            _context.SaveChanges();
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
            if (evaluationDate != indicatorsEvaluationReg.evaluationDate || idEvaluatedOrganization != indicatorsEvaluationReg.idEvaluatedOrganization || orgType != indicatorsEvaluationReg.orgTypeEvaluated || illness != indicatorsEvaluationReg.illness || indicatorId!=indicatorsEvaluationReg.indicatorId || idEvidence!=indicatorsEvaluationReg.idEvidence || indicatorVersion!=indicatorsEvaluationReg.indicatorVersion-1)
                return BadRequest();

            var existingIndicatorsEvaluationReg = _context.IndicatorsEvaluationRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgType && r.illness == illness && r.indicatorId == indicatorId && r.idEvidence == idEvidence && r.indicatorVersion == indicatorVersion);
            if (existingIndicatorsEvaluationReg is null)
                return NotFound();

            _context.IndicatorsEvaluationRegs.Update(indicatorsEvaluationReg);
            _context.SaveChanges();
            return Ok(indicatorsEvaluationReg);
        }


        [HttpDelete("del::evaluationDate={evaluationDate}:idEvaluatedOrganization={idEvaluatedOrganization}:orgType={orgType}:illness={illness}:indicatorId={indicatorId}:idEvidence={idEvidence}:indicatorVersion={indicatorVersion}")]
        public ActionResult<IndicatorsEvaluationReg> Delete(long evaluationDate, int idEvaluatedOrganization, string orgType, string illness, int indicatorId, int idEvidence, int indicatorVersion) {
            var indicatorsEvaluation = _context.IndicatorsEvaluationRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgType && r.illness == illness && r.indicatorId == indicatorId && r.idEvidence == idEvidence && r.indicatorVersion == indicatorVersion);

            if (indicatorsEvaluation is null)
                return NotFound();

            _context.IndicatorsEvaluationRegs.Remove(indicatorsEvaluation);
            _context.SaveChanges();
            return NoContent();
        }

    }
}
