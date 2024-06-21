using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Security.Policy;
namespace OTEAServer.Controllers
{

    /// <summary>
    /// Controller for indicators evaluation registers operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("IndicatorsEvaluationsEvidencesRegs")]
    public class IndicatorsEvaluationsEvidencesRegsController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public IndicatorsEvaluationsEvidencesRegsController(DatabaseContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Method that obtains all the registers
        /// </summary>
        /// <returns>Registrations list</returns>
        [HttpGet("all")]
        [Authorize]
        public IActionResult GetAll([FromQuery] string evaluationType, [FromHeader] string Authorization) {
            try
            {
                var IndicatorsEvaluationsEvidencesRegs = _context.IndicatorsEvaluationsEvidencesRegs.Where(i => i.evaluationType == evaluationType).ToList();
                return Ok(IndicatorsEvaluationsEvidencesRegs);
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
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Indicators list</returns>
        [HttpGet("indEval")]
        [Authorize]
        public IActionResult GetAllByIndicatorsEvaluation([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] string evaluationType, [FromHeader] string Authorization) {
            try
            {
                var IndicatorsEvaluationsEvidencesRegs = _context.IndicatorsEvaluationsEvidencesRegs.Where(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.evaluationType==evaluationType).ToList();
                return Ok(IndicatorsEvaluationsEvidencesRegs);
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
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Register if success, null if not</returns>

        [HttpGet("get")]
        [Authorize]
        public ActionResult<IndicatorsEvaluationEvidenceReg> Get([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int idIndicator, [FromQuery] int idEvidence, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType, [FromHeader] string Authorization) {
            try
            {
                var indicatorsEvaluation = _context.IndicatorsEvaluationsEvidencesRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.idSubSubAmbit == idSubSubAmbit && r.idSubAmbit == idSubAmbit && r.idAmbit == idAmbit && r.idIndicator == idIndicator && r.idEvidence == idEvidence && r.indicatorVersion == indicatorVersion && r.evaluationType == evaluationType);

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
        /// <param name="indicatorsEvaluationEvidenceReg">Indicators evaluation register</param>
        /// <returns>Register if success, null if not</returns>
        [HttpPost]
        [Authorize(Policy = "Administrator")]
        public ActionResult<IndicatorsEvaluationEvidenceReg> Create([FromBody] IndicatorsEvaluationEvidenceReg indicatorsEvaluationEvidenceReg, [FromHeader] string Authorization) {
            try
            {
                _context.IndicatorsEvaluationsEvidencesRegs.Add(indicatorsEvaluationEvidenceReg);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new
                {
                    evaluationDate = indicatorsEvaluationEvidenceReg.evaluationDate,
                    idEvaluatorTeam = indicatorsEvaluationEvidenceReg.idEvaluatorTeam,
                    idEvaluatorOrganization = indicatorsEvaluationEvidenceReg.idEvaluatorOrganization,
                    orgTypeEvaluator = indicatorsEvaluationEvidenceReg.orgTypeEvaluator,
                    idEvaluatedOrganization = indicatorsEvaluationEvidenceReg.idEvaluatedOrganization,
                    orgTypeEvaluated = indicatorsEvaluationEvidenceReg.orgTypeEvaluated,
                    illness = indicatorsEvaluationEvidenceReg.illness,
                    idCenter = indicatorsEvaluationEvidenceReg.idCenter,
                    idSubSubAmbit = indicatorsEvaluationEvidenceReg.idSubSubAmbit,
                    idSubAmbit = indicatorsEvaluationEvidenceReg.idSubAmbit,
                    idAmbit = indicatorsEvaluationEvidenceReg.idAmbit,
                    idIndicator = indicatorsEvaluationEvidenceReg.idIndicator,
                    idEvidence = indicatorsEvaluationEvidenceReg.idEvidence,
                    indicatorVersion = indicatorsEvaluationEvidenceReg.indicatorVersion,
                    evaluationType = indicatorsEvaluationEvidenceReg.evaluationType
                }, indicatorsEvaluationEvidenceReg);
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
        [Authorize(Policy = "Administrator")]
        public IActionResult CreateRegs([FromBody] List<IndicatorsEvaluationEvidenceReg> regs, [FromHeader] string Authorization)
        {
            if (regs == null) {
                return BadRequest();
            }
            try
            {
                foreach (IndicatorsEvaluationEvidenceReg reg in regs)
                {
                    if (reg == null) { continue; }
                    IndicatorsEvaluationEvidenceReg aux=_context.IndicatorsEvaluationsEvidencesRegs.FirstOrDefault(r => r.evaluationDate == reg.evaluationDate && r.idEvaluatorTeam == reg.idEvaluatorTeam && r.idEvaluatorOrganization == reg.idEvaluatorOrganization && r.orgTypeEvaluator == reg.orgTypeEvaluator && r.idEvaluatedOrganization == reg.idEvaluatedOrganization && r.orgTypeEvaluated == reg.orgTypeEvaluated && r.illness == reg.illness && r.idCenter == reg.idCenter && r.idSubSubAmbit == reg.idSubSubAmbit && r.idSubAmbit == reg.idSubAmbit && r.idAmbit == reg.idAmbit && r.idIndicator == reg.idIndicator && r.idEvidence == reg.idEvidence && r.indicatorVersion == reg.indicatorVersion && r.evaluationType == reg.evaluationType);
                    if (aux == null) {
                        _context.IndicatorsEvaluationsEvidencesRegs.Add(reg);
                    }
                    else
                    {
                        aux.evaluationDate = reg.evaluationDate;
                        aux.idEvaluatorTeam = reg.idEvaluatorTeam;
                        aux.idEvaluatorOrganization = reg.idEvaluatorOrganization;
                        aux.orgTypeEvaluator = reg.orgTypeEvaluator;
                        aux.idEvaluatedOrganization = reg.idEvaluatedOrganization;
                        aux.orgTypeEvaluated = reg.orgTypeEvaluated;
                        aux.illness = reg.illness;
                        aux.idCenter = reg.idCenter;
                        aux.idSubSubAmbit = reg.idSubSubAmbit;
                        aux.idSubAmbit = reg.idSubAmbit;
                        aux.idAmbit = reg.idAmbit;
                        aux.idIndicator = reg.idIndicator;
                        aux.idEvidence = reg.idEvidence;
                        aux.isMarked = reg.isMarked;
                        aux.indicatorVersion = reg.indicatorVersion;
                        aux.evaluationType = reg.evaluationType;

                        aux.observationsSpanish = reg.observationsSpanish;
                        aux.observationsEnglish = reg.observationsEnglish;
                        aux.observationsFrench = reg.observationsFrench;
                        aux.observationsBasque = reg.observationsBasque;
                        aux.observationsCatalan = reg.observationsCatalan;
                        aux.observationsDutch = reg.observationsDutch;
                        aux.observationsGalician = reg.observationsGalician;
                        aux.observationsGerman = reg.observationsGerman;
                        aux.observationsItalian = reg.observationsItalian;
                        aux.observationsPortuguese = reg.observationsPortuguese;
                    }
                        
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
        /// <param name="indicatorsEvaluationEvidenceReg">Indicators evaluation registration</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Register if success, null if not</returns>
        [HttpPut]
        [Authorize(Policy = "Administrator")]
        public ActionResult<IndicatorsEvaluationEvidenceReg> Update([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int idIndicator, [FromQuery] int idEvidence, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType, [FromBody] IndicatorsEvaluationEvidenceReg indicatorsEvaluationEvidenceReg, [FromHeader] string Authorization) {
            try
            {
                var existingIndicatorsEvaluationEvidenceReg = _context.IndicatorsEvaluationsEvidencesRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.idSubSubAmbit == idSubSubAmbit && r.idSubAmbit == idSubAmbit && r.idAmbit == idAmbit && r.idIndicator == idIndicator && r.idEvidence == idEvidence && r.indicatorVersion == indicatorVersion && r.evaluationType==evaluationType);
                if (existingIndicatorsEvaluationEvidenceReg is null)
                    return NotFound();

                existingIndicatorsEvaluationEvidenceReg.evaluationDate = indicatorsEvaluationEvidenceReg.evaluationDate;
                existingIndicatorsEvaluationEvidenceReg.idEvaluatorTeam = indicatorsEvaluationEvidenceReg.idEvaluatorTeam;
                existingIndicatorsEvaluationEvidenceReg.idEvaluatorOrganization = indicatorsEvaluationEvidenceReg.idEvaluatorOrganization;
                existingIndicatorsEvaluationEvidenceReg.orgTypeEvaluator = indicatorsEvaluationEvidenceReg.orgTypeEvaluator;
                existingIndicatorsEvaluationEvidenceReg.idEvaluatedOrganization = indicatorsEvaluationEvidenceReg.idEvaluatedOrganization;
                existingIndicatorsEvaluationEvidenceReg.orgTypeEvaluated = indicatorsEvaluationEvidenceReg.orgTypeEvaluated;
                existingIndicatorsEvaluationEvidenceReg.illness = indicatorsEvaluationEvidenceReg.illness;
                existingIndicatorsEvaluationEvidenceReg.idCenter = indicatorsEvaluationEvidenceReg.idCenter;
                existingIndicatorsEvaluationEvidenceReg.idSubSubAmbit = indicatorsEvaluationEvidenceReg.idSubSubAmbit;
                existingIndicatorsEvaluationEvidenceReg.idSubAmbit = indicatorsEvaluationEvidenceReg.idSubAmbit;
                existingIndicatorsEvaluationEvidenceReg.idAmbit = indicatorsEvaluationEvidenceReg.idAmbit;
                existingIndicatorsEvaluationEvidenceReg.idIndicator = indicatorsEvaluationEvidenceReg.idIndicator;
                existingIndicatorsEvaluationEvidenceReg.idEvidence = indicatorsEvaluationEvidenceReg.idEvidence;
                existingIndicatorsEvaluationEvidenceReg.isMarked = indicatorsEvaluationEvidenceReg.isMarked;
                existingIndicatorsEvaluationEvidenceReg.indicatorVersion = indicatorsEvaluationEvidenceReg.indicatorVersion;
                existingIndicatorsEvaluationEvidenceReg.evaluationType = indicatorsEvaluationEvidenceReg.evaluationType;

                existingIndicatorsEvaluationEvidenceReg.observationsSpanish = indicatorsEvaluationEvidenceReg.observationsSpanish;
                existingIndicatorsEvaluationEvidenceReg.observationsEnglish = indicatorsEvaluationEvidenceReg.observationsEnglish;
                existingIndicatorsEvaluationEvidenceReg.observationsFrench = indicatorsEvaluationEvidenceReg.observationsFrench;
                existingIndicatorsEvaluationEvidenceReg.observationsBasque = indicatorsEvaluationEvidenceReg.observationsBasque;
                existingIndicatorsEvaluationEvidenceReg.observationsCatalan = indicatorsEvaluationEvidenceReg.observationsCatalan;
                existingIndicatorsEvaluationEvidenceReg.observationsDutch = indicatorsEvaluationEvidenceReg.observationsDutch;
                existingIndicatorsEvaluationEvidenceReg.observationsGalician = indicatorsEvaluationEvidenceReg.observationsGalician;
                existingIndicatorsEvaluationEvidenceReg.observationsGerman = indicatorsEvaluationEvidenceReg.observationsGerman;
                existingIndicatorsEvaluationEvidenceReg.observationsItalian = indicatorsEvaluationEvidenceReg.observationsItalian;
                existingIndicatorsEvaluationEvidenceReg.observationsPortuguese = indicatorsEvaluationEvidenceReg.observationsPortuguese;
                _context.SaveChanges();
                return Ok(existingIndicatorsEvaluationEvidenceReg);
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
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Register if success, null if not</returns>
        [HttpDelete]
        [Authorize(Policy = "Administrator")]
        public ActionResult<IndicatorsEvaluationEvidenceReg> Delete([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int idIndicator, [FromQuery] int idEvidence, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType, [FromHeader] string Authorization) {
            try
            {
                var indicatorsEvaluation = _context.IndicatorsEvaluationsEvidencesRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.idSubSubAmbit == idSubSubAmbit && r.idSubAmbit == idSubAmbit && r.idAmbit == idAmbit && r.idIndicator == idIndicator && r.idEvidence == idEvidence && r.indicatorVersion == indicatorVersion && r.evaluationType==evaluationType);

                if (indicatorsEvaluation is null)
                    return NotFound();

                _context.IndicatorsEvaluationsEvidencesRegs.Remove(indicatorsEvaluation);
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
