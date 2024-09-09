using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using NRules;
using NRules.Fluent;
using OTEAServer.ExpertSystem;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Runtime.Intrinsics.X86;
namespace OTEAServer.Controllers
{

    /// <summary>
    /// Controller for indicators evaluation registers operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("IndicatorsEvaluationsSimpleEvidencesRegs")]
    public class IndicatorsEvaluationsSimpleEvidencesRegsController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public IndicatorsEvaluationsSimpleEvidencesRegsController(DatabaseContext context)
        {
            _context = context;
        }



        /// <summary>
        /// Method that appends new indicators evaluation register
        /// </summary>
        /// <param name="regs">Indicators evaluation registers</param>
        /// <returns>Register if success, null if not</returns>
        [HttpPost("fromList")]
        [Authorize(Roles = "ADMIN,DIRECTOR")]
        public IActionResult CreateRegs([FromBody] List<IndicatorsEvaluationSimpleEvidenceReg> regs, [FromHeader] string Authorization)
        {
            if (regs == null)
            {
                return BadRequest();
            }
            try
            {


                foreach (IndicatorsEvaluationSimpleEvidenceReg reg in regs)
                {
                    if (reg == null) { continue; }
                    IndicatorsEvaluationSimpleEvidenceReg aux = _context.IndicatorsEvaluationsSimpleEvidencesRegs.FirstOrDefault(r => r.evaluationDate == reg.evaluationDate && r.idEvaluatorTeam == reg.idEvaluatorTeam && r.idEvaluatorOrganization == reg.idEvaluatorOrganization && r.orgTypeEvaluator == reg.orgTypeEvaluator && r.idEvaluatedOrganization == reg.idEvaluatedOrganization && r.orgTypeEvaluated == reg.orgTypeEvaluated && r.illness == reg.illness && r.idCenter == reg.idCenter && r.idSubSubAmbit == reg.idSubSubAmbit && r.idSubAmbit == reg.idSubAmbit && r.idAmbit == reg.idAmbit && r.idIndicator == reg.idIndicator && r.idEvidence == reg.idEvidence && r.indicatorVersion == reg.indicatorVersion && r.evaluationType == reg.evaluationType);

                    if (aux == null)
                    {
                        _context.IndicatorsEvaluationsSimpleEvidencesRegs.Add(reg);
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
                        aux.descriptionSpanish = reg.descriptionSpanish;
                        aux.descriptionEnglish = reg.descriptionEnglish;
                        aux.descriptionFrench = reg.descriptionFrench;
                        aux.descriptionBasque = reg.descriptionBasque;
                        aux.descriptionCatalan = reg.descriptionCatalan;
                        aux.descriptionDutch = reg.descriptionDutch;
                        aux.descriptionGalician = reg.descriptionGalician;
                        aux.descriptionGerman = reg.descriptionGerman;
                        aux.descriptionItalian = reg.descriptionItalian;
                        aux.descriptionPortuguese = reg.descriptionPortuguese;
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
    }

}
