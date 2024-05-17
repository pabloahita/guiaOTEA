using Microsoft.AspNetCore.Mvc;
using NRules;
using NRules.Fluent;
using OTEAServer.ExpertSystem;
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
    [Route("IndicatorsEvaluationsIndicatorsRegs")]
    public class IndicatorsEvaluationsIndicatorsRegsController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public IndicatorsEvaluationsIndicatorsRegsController(DatabaseContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Method that obtains all the registers
        /// </summary>
        /// <returns>Registrations list</returns>
        [HttpGet("all")]
        public IActionResult GetAll([FromQuery] string evaluationType)
        {
            try
            {
                var IndicatorsEvaluationsIndicatorsRegs = _context.IndicatorsEvaluationsIndicatorsRegs.Where(i => i.evaluationType == evaluationType).ToList();
                return Ok(IndicatorsEvaluationsIndicatorsRegs);
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
        public IActionResult GetAllByIndicatorsEvaluation([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] string evaluationType)
        {
            try
            {
                var IndicatorsEvaluationsIndicatorsRegs = _context.IndicatorsEvaluationsIndicatorsRegs.Where(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.evaluationType==evaluationType).ToList();
                return Ok(IndicatorsEvaluationsIndicatorsRegs);
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
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Register if success, null if not</returns>

        [HttpGet("get")]
        public ActionResult<IndicatorsEvaluationIndicatorReg> Get([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int idIndicator, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType)
        {
            try
            {
                var indicatorsEvaluation = _context.IndicatorsEvaluationsIndicatorsRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.idSubSubAmbit == idSubSubAmbit && r.idSubAmbit == idSubAmbit && r.idAmbit == idAmbit && r.idIndicator == idIndicator && r.indicatorVersion == indicatorVersion && r.evaluationType == evaluationType);

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
        /// <param name="indicatorsEvaluationIndicatorReg">Indicators evaluation register</param>
        /// <returns>Register if success, null if not</returns>
        [HttpPost]
        public ActionResult<IndicatorsEvaluationIndicatorReg> Create([FromBody] IndicatorsEvaluationIndicatorReg indicatorsEvaluationIndicatorReg)
        {
            try
            {
                _context.IndicatorsEvaluationsIndicatorsRegs.Add(indicatorsEvaluationIndicatorReg);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new
                {
                    evaluationDate = indicatorsEvaluationIndicatorReg.evaluationDate,
                    idEvaluatorTeam = indicatorsEvaluationIndicatorReg.idEvaluatorTeam,
                    idEvaluatorOrganization = indicatorsEvaluationIndicatorReg.idEvaluatorOrganization,
                    orgTypeEvaluator = indicatorsEvaluationIndicatorReg.orgTypeEvaluator,
                    idEvaluatedOrganization = indicatorsEvaluationIndicatorReg.idEvaluatedOrganization,
                    orgTypeEvaluated = indicatorsEvaluationIndicatorReg.orgTypeEvaluated,
                    illness = indicatorsEvaluationIndicatorReg.illness,
                    idCenter = indicatorsEvaluationIndicatorReg.idCenter,
                    idSubSubAmbit = indicatorsEvaluationIndicatorReg.idSubSubAmbit,
                    idSubAmbit = indicatorsEvaluationIndicatorReg.idSubAmbit,
                    idAmbit = indicatorsEvaluationIndicatorReg.idAmbit,
                    idIndicator = indicatorsEvaluationIndicatorReg.idIndicator,
                    indicatorVersion = indicatorsEvaluationIndicatorReg.indicatorVersion,
                    evaluationType = indicatorsEvaluationIndicatorReg.evaluationType
                }, indicatorsEvaluationIndicatorReg);
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
        public IActionResult CreateRegs([FromBody] List<IndicatorsEvaluationIndicatorReg> regs)
        {
            if (regs == null)
            {
                return BadRequest();
            }
            try
            {

                var repository = new RuleRepository();
                repository.Load(x => x.From(typeof(RuleIndicatorInProcess).Assembly));
                repository.Load(x => x.From(typeof(RuleIndicatorInStart).Assembly));
                repository.Load(x => x.From(typeof(RuleIndicatorReached).Assembly));
                repository.Load(x => x.From(typeof(RuleRequiresImprovementPlan).Assembly));
                repository.Load(x => x.From(typeof(RuleDoesntRequireImprovementPlan).Assembly));

                var factory = repository.Compile();
                var session = factory.CreateSession();
                foreach (IndicatorsEvaluationIndicatorReg reg in regs)
                {
                    if (reg == null) { continue; }

                    session.Insert(reg);
                    session.Fire();
                    session.Retract(reg);

                    IndicatorsEvaluationIndicatorReg aux = _context.IndicatorsEvaluationsIndicatorsRegs.FirstOrDefault(r => r.evaluationDate == reg.evaluationDate && r.idEvaluatorTeam == reg.idEvaluatorTeam && r.idEvaluatorOrganization == reg.idEvaluatorOrganization && r.orgTypeEvaluator == reg.orgTypeEvaluator && r.idEvaluatedOrganization == reg.idEvaluatedOrganization && r.orgTypeEvaluated == reg.orgTypeEvaluated && r.illness == reg.illness && r.idCenter == reg.idCenter && r.idSubSubAmbit == reg.idSubSubAmbit && r.idSubAmbit == reg.idSubAmbit && r.idAmbit == reg.idAmbit && r.idIndicator == reg.idIndicator && r.indicatorVersion == reg.indicatorVersion && r.evaluationType == reg.evaluationType);

                    if (aux == null)
                    {
                        _context.IndicatorsEvaluationsIndicatorsRegs.Add(reg);
                    }
                    else {
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
                        aux.numEvidencesMarked = reg.numEvidencesMarked;
                        aux.status = reg.status;
                        aux.requiresImprovementPlan = reg.requiresImprovementPlan;
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
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="indicatorsEvaluationIndicatorReg">Indicators evaluation registration</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Register if success, null if not</returns>
        [HttpPut]
        public ActionResult<IndicatorsEvaluationIndicatorReg> Update([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int idIndicator, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType, [FromBody] IndicatorsEvaluationIndicatorReg indicatorsEvaluationIndicatorReg)
        {
            try
            {
                var existingIndicatorsEvaluationIndicatorReg = _context.IndicatorsEvaluationsIndicatorsRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.idSubSubAmbit == idSubSubAmbit && r.idSubAmbit == idSubAmbit && r.idAmbit == idAmbit && r.idIndicator == idIndicator && r.indicatorVersion == indicatorVersion && r.evaluationType == evaluationType);
                if (existingIndicatorsEvaluationIndicatorReg is null)
                    return NotFound();

                existingIndicatorsEvaluationIndicatorReg.evaluationDate = indicatorsEvaluationIndicatorReg.evaluationDate;
                existingIndicatorsEvaluationIndicatorReg.idEvaluatorTeam = indicatorsEvaluationIndicatorReg.idEvaluatorTeam;
                existingIndicatorsEvaluationIndicatorReg.idEvaluatorOrganization = indicatorsEvaluationIndicatorReg.idEvaluatorOrganization;
                existingIndicatorsEvaluationIndicatorReg.orgTypeEvaluator = indicatorsEvaluationIndicatorReg.orgTypeEvaluator;
                existingIndicatorsEvaluationIndicatorReg.idEvaluatedOrganization = indicatorsEvaluationIndicatorReg.idEvaluatedOrganization;
                existingIndicatorsEvaluationIndicatorReg.orgTypeEvaluated = indicatorsEvaluationIndicatorReg.orgTypeEvaluated;
                existingIndicatorsEvaluationIndicatorReg.illness = indicatorsEvaluationIndicatorReg.illness;
                existingIndicatorsEvaluationIndicatorReg.idCenter = indicatorsEvaluationIndicatorReg.idCenter;
                existingIndicatorsEvaluationIndicatorReg.idSubSubAmbit = indicatorsEvaluationIndicatorReg.idSubSubAmbit;
                existingIndicatorsEvaluationIndicatorReg.idSubAmbit = indicatorsEvaluationIndicatorReg.idSubAmbit;
                existingIndicatorsEvaluationIndicatorReg.idAmbit = indicatorsEvaluationIndicatorReg.idAmbit;
                existingIndicatorsEvaluationIndicatorReg.idIndicator = indicatorsEvaluationIndicatorReg.idIndicator;
                existingIndicatorsEvaluationIndicatorReg.indicatorVersion = indicatorsEvaluationIndicatorReg.indicatorVersion;
                existingIndicatorsEvaluationIndicatorReg.evaluationType = indicatorsEvaluationIndicatorReg.evaluationType;

                existingIndicatorsEvaluationIndicatorReg.observationsSpanish = indicatorsEvaluationIndicatorReg.observationsSpanish;
                existingIndicatorsEvaluationIndicatorReg.observationsEnglish = indicatorsEvaluationIndicatorReg.observationsEnglish;
                existingIndicatorsEvaluationIndicatorReg.observationsFrench = indicatorsEvaluationIndicatorReg.observationsFrench;
                existingIndicatorsEvaluationIndicatorReg.observationsBasque = indicatorsEvaluationIndicatorReg.observationsBasque;
                existingIndicatorsEvaluationIndicatorReg.observationsCatalan = indicatorsEvaluationIndicatorReg.observationsCatalan;
                existingIndicatorsEvaluationIndicatorReg.observationsDutch = indicatorsEvaluationIndicatorReg.observationsDutch;
                existingIndicatorsEvaluationIndicatorReg.observationsGalician = indicatorsEvaluationIndicatorReg.observationsGalician;
                existingIndicatorsEvaluationIndicatorReg.observationsGerman = indicatorsEvaluationIndicatorReg.observationsGerman;
                existingIndicatorsEvaluationIndicatorReg.observationsItalian = indicatorsEvaluationIndicatorReg.observationsItalian;
                existingIndicatorsEvaluationIndicatorReg.observationsPortuguese = indicatorsEvaluationIndicatorReg.observationsPortuguese;
                existingIndicatorsEvaluationIndicatorReg.numEvidencesMarked = indicatorsEvaluationIndicatorReg.numEvidencesMarked;
                _context.SaveChanges();
                return Ok(existingIndicatorsEvaluationIndicatorReg);
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
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Register if success, null if not</returns>
        [HttpDelete]
        public ActionResult<IndicatorsEvaluationIndicatorReg> Delete([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int idIndicator, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType)
        {
            try
            {
                var indicatorsEvaluation = _context.IndicatorsEvaluationsIndicatorsRegs.FirstOrDefault(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.idSubSubAmbit == idSubSubAmbit && r.idSubAmbit == idSubAmbit && r.idAmbit == idAmbit && r.idIndicator == idIndicator && r.indicatorVersion == indicatorVersion && r.evaluationType==evaluationType);

                if (indicatorsEvaluation is null)
                    return NotFound();

                _context.IndicatorsEvaluationsIndicatorsRegs.Remove(indicatorsEvaluation);
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
