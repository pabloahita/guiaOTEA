using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
namespace OTEAServer.Controllers
{

    [ApiController]
    [Route("IndicatorsEvaluationsRegs")]
    public class IndicatorsEvaluationsRegsController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public IndicatorsEvaluationsRegsController(DatabaseContext context)
        {
            _context = context;
        }

        [HttpGet("all")]
        public IActionResult GetAll() {
            var IndicatorsEvaluationsRegs = _context.IndicatorsEvaluationsRegs.ToList();
            return Ok(IndicatorsEvaluationsRegs);
        }

        [HttpGet("indEval")]
        public IActionResult GetAllByIndicatorsEvaluation([FromQuery] long evaluationDate, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgType, [FromQuery] string illness) {
            var IndicatorsEvaluationsRegs = _context.IndicatorsEvaluationsRegs.Where(r=>r.evaluationDate==evaluationDate && r.idEvaluatedOrganization==idEvaluatedOrganization && r.orgTypeEvaluated==orgType && r.illness==illness).ToList();
            return Ok(IndicatorsEvaluationsRegs);
        }
       

        [HttpGet("get")]
        public ActionResult<IndicatorsEvaluationReg> Get([FromQuery] long evaluationDate, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgType, [FromQuery] string illness, [FromQuery] int indicatorId, [FromQuery] int idEvidence, [FromQuery] int indicatorVersion) {
            var indicatorsEvaluation = _context.IndicatorsEvaluationsRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgType && r.illness == illness && r.indicatorId == indicatorId && r.idEvidence == idEvidence && r.indicatorVersion==indicatorVersion);

            if (indicatorsEvaluation == null)
                return NotFound();

            return indicatorsEvaluation;
        }

        [HttpPost]
        public ActionResult<IndicatorsEvaluationReg> Create([FromBody] IndicatorsEvaluationReg indicatorsEvaluationReg) {
            _context.IndicatorsEvaluationsRegs.Add(indicatorsEvaluationReg);
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


        [HttpPut]
        public ActionResult<IndicatorsEvaluationReg> Update([FromQuery] long evaluationDate, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgType, [FromQuery] string illness, [FromQuery] int indicatorId, [FromQuery] int idEvidence, [FromQuery] int indicatorVersion, [FromBody] IndicatorsEvaluationReg indicatorsEvaluationReg) {
            // This code will update the mesa and return a result
            var existingIndicatorsEvaluationReg = _context.IndicatorsEvaluationsRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgType && r.illness == illness && r.indicatorId == indicatorId && r.idEvidence == idEvidence && r.indicatorVersion == indicatorVersion);
            if (existingIndicatorsEvaluationReg is null)
                return NotFound();

            existingIndicatorsEvaluationReg.evaluationDate = indicatorsEvaluationReg.evaluationDate;
            existingIndicatorsEvaluationReg.idEvaluatedOrganization = indicatorsEvaluationReg.idEvaluatedOrganization;
            existingIndicatorsEvaluationReg.orgTypeEvaluated = indicatorsEvaluationReg.orgTypeEvaluated;
            existingIndicatorsEvaluationReg.illness = indicatorsEvaluationReg.illness;
            existingIndicatorsEvaluationReg.indicatorId = indicatorsEvaluationReg.indicatorId;
            existingIndicatorsEvaluationReg.idEvidence = indicatorsEvaluationReg.idEvidence;
            existingIndicatorsEvaluationReg.isMarked = indicatorsEvaluationReg.isMarked;
            existingIndicatorsEvaluationReg.indicatorVersion = indicatorsEvaluationReg.indicatorVersion;
            _context.SaveChanges();
            return Ok(existingIndicatorsEvaluationReg);
        }


        [HttpDelete]
        public ActionResult<IndicatorsEvaluationReg> Delete([FromQuery] long evaluationDate, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgType, [FromQuery] string illness, [FromQuery] int indicatorId, [FromQuery] int idEvidence, [FromQuery] int indicatorVersion) {
            var indicatorsEvaluation = _context.IndicatorsEvaluationsRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgType && r.illness == illness && r.indicatorId == indicatorId && r.idEvidence == idEvidence && r.indicatorVersion == indicatorVersion);

            if (indicatorsEvaluation is null)
                return NotFound();

            _context.IndicatorsEvaluationsRegs.Remove(indicatorsEvaluation);
            _context.SaveChanges();
            return NoContent();
        }

    }
}
