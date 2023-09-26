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
        public IActionResult GetAllByIndicatorsEvaluation([FromQuery] long evaluationDate,[FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter) {
            var IndicatorsEvaluationsRegs = _context.IndicatorsEvaluationsRegs.Where(r => r.evaluationDate==evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness).ToList();
            return Ok(IndicatorsEvaluationsRegs);
        }
       

        [HttpGet("get")]
        public ActionResult<IndicatorsEvaluationReg> Get([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] int idAmbit, [FromQuery] int idIndicator, [FromQuery] int idEvidence, [FromQuery] int indicatorVersion) {
            var indicatorsEvaluation = _context.IndicatorsEvaluationsRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam==idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter==idCenter && r.idAmbit==idAmbit && r.idIndicator == idIndicator && r.idEvidence == idEvidence && r.indicatorVersion == indicatorVersion);

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
                idEvaluatorTeam = indicatorsEvaluationReg.idEvaluatorTeam,
                idEvaluatorOrganization = indicatorsEvaluationReg.idEvaluatorOrganization,
                orgTypeEvaluator = indicatorsEvaluationReg.orgTypeEvaluator,
                idEvaluatedOrganization = indicatorsEvaluationReg.idEvaluatedOrganization,
                orgTypeEvaluated = indicatorsEvaluationReg.orgTypeEvaluated,
                illness = indicatorsEvaluationReg.illness,
                idCenter = indicatorsEvaluationReg.idCenter,
                idAmbit = indicatorsEvaluationReg.idAmbit,
                idIndicator = indicatorsEvaluationReg.idIndicator,
                idEvidence = indicatorsEvaluationReg.idEvidence,
                indicatorVersion = indicatorsEvaluationReg.indicatorVersion
            }, indicatorsEvaluationReg);
        }


        [HttpPut]
        public ActionResult<IndicatorsEvaluationReg> Update([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] int idAmbit, [FromQuery] int idIndicator, [FromQuery] int idEvidence, [FromQuery] int indicatorVersion, [FromBody] IndicatorsEvaluationReg indicatorsEvaluationReg) {
            // This code will update the mesa and return a result
            var existingIndicatorsEvaluationReg = _context.IndicatorsEvaluationsRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam==idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter==idCenter && r.idAmbit==idAmbit && r.idIndicator == idIndicator && r.idEvidence == idEvidence && r.indicatorVersion == indicatorVersion);
            if (existingIndicatorsEvaluationReg is null)
                return NotFound();

            existingIndicatorsEvaluationReg.evaluationDate = indicatorsEvaluationReg.evaluationDate;
            existingIndicatorsEvaluationReg.idEvaluatorTeam = indicatorsEvaluationReg.idEvaluatorTeam;
            existingIndicatorsEvaluationReg.idEvaluatorOrganization = indicatorsEvaluationReg.idEvaluatorOrganization;
            existingIndicatorsEvaluationReg.orgTypeEvaluator = indicatorsEvaluationReg.orgTypeEvaluator;
            existingIndicatorsEvaluationReg.idEvaluatedOrganization = indicatorsEvaluationReg.idEvaluatedOrganization;
            existingIndicatorsEvaluationReg.orgTypeEvaluated = indicatorsEvaluationReg.orgTypeEvaluated;
            existingIndicatorsEvaluationReg.illness = indicatorsEvaluationReg.illness;
            existingIndicatorsEvaluationReg.idCenter = indicatorsEvaluationReg.idCenter;
            existingIndicatorsEvaluationReg.idAmbit = indicatorsEvaluationReg.idAmbit;
            existingIndicatorsEvaluationReg.idIndicator = indicatorsEvaluationReg.idIndicator;
            existingIndicatorsEvaluationReg.idEvidence = indicatorsEvaluationReg.idEvidence;
            existingIndicatorsEvaluationReg.isMarked = indicatorsEvaluationReg.isMarked;
            existingIndicatorsEvaluationReg.indicatorVersion = indicatorsEvaluationReg.indicatorVersion;
            _context.SaveChanges();
            return Ok(existingIndicatorsEvaluationReg);
        }


        [HttpDelete]
        public ActionResult<IndicatorsEvaluationReg> Delete([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] int idAmbit, [FromQuery] int idIndicator, [FromQuery] int idEvidence, [FromQuery] int indicatorVersion) {
            var indicatorsEvaluation = _context.IndicatorsEvaluationsRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam==idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter==idCenter && r.idAmbit==idAmbit && r.idIndicator == idIndicator && r.idEvidence == idEvidence && r.indicatorVersion == indicatorVersion);

            if (indicatorsEvaluation is null)
                return NotFound();

            _context.IndicatorsEvaluationsRegs.Remove(indicatorsEvaluation);
            _context.SaveChanges();
            return NoContent();
        }

    }
}
