using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
namespace OTEAServer.Controllers
{

    /// <summary>
    /// Controller for indicators evaluation registers operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("IndicatorsEvaluationsRegs")]
    public class IndicatorsEvaluationsRegsController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public IndicatorsEvaluationsRegsController(DatabaseContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Method that obtains all the registers
        /// </summary>
        /// <returns>Registrations list</returns>
        [HttpGet("all")]
        public IActionResult GetAll() {
            try
            {
                var IndicatorsEvaluationsRegs = _context.IndicatorsEvaluationsRegs.ToList();
                return Ok(IndicatorsEvaluationsRegs);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains all registers of an indicators evaluation
        /// </summary>
        /// <param name="evaluationDate">Evaluation date in timestamp</param>
        /// <param name="idEvaluatorTeam">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrganization">Identifier of the evaluator organization that will do the indicators evaluation</param>
        /// <param name="orgTypeEvaluator">Organization type of the evaluator organization that will do the indicators evaluation.It will be always "EVALUATOR"</param>
        /// <param name="idEvaluatedOrganization">Identifier of the external organization that will recieve the indicators evaluation</param>
        /// <param name="orgTypeEvaluated">Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"</param>
        /// <param name="illness">Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"</param>
        /// <param name="idCenter">Center identifier of the external organization</param>
        /// <returns>Indicators list</returns>
        [HttpGet("indEval")]
        public IActionResult GetAllByIndicatorsEvaluation([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter) {
            try
            {
                var IndicatorsEvaluationsRegs = _context.IndicatorsEvaluationsRegs.Where(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter).ToList();
                return Ok(IndicatorsEvaluationsRegs);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }


        /// <summary>
        /// Gets an indicators evaluation register
        /// </summary>
        /// <param name="evaluationDate">Evaluation date in timestamp</param>
        /// <param name="idEvaluatorTeam">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrganization">Identifier of the evaluator organization that will do the indicators evaluation</param>
        /// <param name="orgTypeEvaluator">Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"</param>
        /// <param name="idEvaluatedOrganization"></param>
        /// <param name="orgTypeEvaluated">Identifier of the external organization that will recieve the indicators evaluation</param>
        /// <param name="illness">Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"</param>
        /// <param name="idCenter">Center identifier of the external organization</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit. It will be -1 if there is no division</param>
        /// <param name="idSubAmbit">First level division of the ambit. It will be -1 if there is no division</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="idEvidence">Evidence identifier</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <returns>Register if success, null if not</returns>

        [HttpGet("get")]
        public ActionResult<IndicatorsEvaluationReg> Get([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int idIndicator, [FromQuery] int idEvidence, [FromQuery] int indicatorVersion) {
            try
            {
                var indicatorsEvaluation = _context.IndicatorsEvaluationsRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.idSubSubAmbit == idSubSubAmbit && r.idSubAmbit == idSubAmbit && r.idAmbit == idAmbit && r.idIndicator == idIndicator && r.idEvidence == idEvidence && r.indicatorVersion == indicatorVersion);

                if (indicatorsEvaluation == null)
                    return NotFound();

                return indicatorsEvaluation;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that appends a new indicators evaluation register
        /// </summary>
        /// <param name="indicatorsEvaluationReg">Indicators evaluation register</param>
        /// <returns>Register if success, null if not</returns>
        [HttpPost]
        public ActionResult<IndicatorsEvaluationReg> Create([FromBody] IndicatorsEvaluationReg indicatorsEvaluationReg) {
            try
            {
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
                    idSubSubAmbit = indicatorsEvaluationReg.idSubSubAmbit,
                    idSubAmbit = indicatorsEvaluationReg.idSubAmbit,
                    idAmbit = indicatorsEvaluationReg.idAmbit,
                    idIndicator = indicatorsEvaluationReg.idIndicator,
                    idEvidence = indicatorsEvaluationReg.idEvidence,
                    indicatorVersion = indicatorsEvaluationReg.indicatorVersion
                }, indicatorsEvaluationReg);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that appends new indicators evaluation register
        /// </summary>
        /// <param name="regs">Indicators evaluation registers</param>
        /// <returns>Register if success, null if not</returns>
        [HttpPost("fromList")]
        public IActionResult CreateRegs([FromBody] List<IndicatorsEvaluationReg> regs)
        {
            try
            {
                foreach (IndicatorsEvaluationReg reg in regs)
                {
                    _context.IndicatorsEvaluationsRegs.Add(reg);
                }
                _context.SaveChanges();
                return Ok(regs);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }



        /// <summary>
        /// 
        /// </summary>
        /// <param name="evaluationDate">Evaluation date in timestamp</param>
        /// <param name="idEvaluatorTeam">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrganization">Identifier of the evaluator organization that will do the indicators evaluation</param>
        /// <param name="orgTypeEvaluator">Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"</param>
        /// <param name="idEvaluatedOrganization"></param>
        /// <param name="orgTypeEvaluated">Identifier of the external organization that will recieve the indicators evaluation</param>
        /// <param name="illness">Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"</param>
        /// <param name="idCenter">Center identifier of the external organization</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit. It will be -1 if there is no division</param>
        /// <param name="idSubAmbit">First level division of the ambit. It will be -1 if there is no division</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="idEvidence">Evidence identifier</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="indicatorsEvaluationReg">Indicators evaluation registration</param>
        /// <returns>Register if success, null if not</returns>
        [HttpPut]
        public ActionResult<IndicatorsEvaluationReg> Update([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int idIndicator, [FromQuery] int idEvidence, [FromQuery] int indicatorVersion, [FromBody] IndicatorsEvaluationReg indicatorsEvaluationReg) {
            try
            {
                var existingIndicatorsEvaluationReg = _context.IndicatorsEvaluationsRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.idSubSubAmbit == idSubSubAmbit && r.idSubAmbit == idSubAmbit && r.idAmbit == idAmbit && r.idIndicator == idIndicator && r.idEvidence == idEvidence && r.indicatorVersion == indicatorVersion);
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
                existingIndicatorsEvaluationReg.idSubSubAmbit = indicatorsEvaluationReg.idSubSubAmbit;
                existingIndicatorsEvaluationReg.idSubAmbit = indicatorsEvaluationReg.idSubAmbit;
                existingIndicatorsEvaluationReg.idAmbit = indicatorsEvaluationReg.idAmbit;
                existingIndicatorsEvaluationReg.idIndicator = indicatorsEvaluationReg.idIndicator;
                existingIndicatorsEvaluationReg.idEvidence = indicatorsEvaluationReg.idEvidence;
                existingIndicatorsEvaluationReg.isMarked = indicatorsEvaluationReg.isMarked;
                existingIndicatorsEvaluationReg.indicatorVersion = indicatorsEvaluationReg.indicatorVersion;
                _context.SaveChanges();
                return Ok(existingIndicatorsEvaluationReg);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }


        /// <summary>
        /// Method that deletes an indicators evaluation register
        /// </summary>
        /// <param name="evaluationDate">Evaluation date in timestamp</param>
        /// <param name="idEvaluatorTeam">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrganization">Identifier of the evaluator organization that will do the indicators evaluation</param>
        /// <param name="orgTypeEvaluator">Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"</param>
        /// <param name="idEvaluatedOrganization"></param>
        /// <param name="orgTypeEvaluated">Identifier of the external organization that will recieve the indicators evaluation</param>
        /// <param name="illness">Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"</param>
        /// <param name="idCenter">Center identifier of the external organization</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit. It will be -1 if there is no division</param>
        /// <param name="idSubAmbit">First level division of the ambit. It will be -1 if there is no division</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="idEvidence">Evidence identifier</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <returns>Register if success, null if not</returns>
        [HttpDelete]
        public ActionResult<IndicatorsEvaluationReg> Delete([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int idIndicator, [FromQuery] int idEvidence, [FromQuery] int indicatorVersion) {
            try
            {
                var indicatorsEvaluation = _context.IndicatorsEvaluationsRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.idSubSubAmbit == idSubSubAmbit && r.idSubAmbit == idSubAmbit && r.idAmbit == idAmbit && r.idIndicator == idIndicator && r.idEvidence == idEvidence && r.indicatorVersion == indicatorVersion);

                if (indicatorsEvaluation is null)
                    return NotFound();

                _context.IndicatorsEvaluationsRegs.Remove(indicatorsEvaluation);
                _context.SaveChanges();
                return NoContent();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

    }
}
