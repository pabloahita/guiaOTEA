using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for evaluator teams operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>


    [ApiController]
    [Route("EvaluatorTeams")]
    public class EvaluatorTeamsController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public EvaluatorTeamsController(DatabaseContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Method that obtains all the evaluator teams
        /// </summary>
        /// <returns>Evaluator team list</returns>
        [HttpGet("all")]
        public IActionResult GetAll()
        {
            try
            {
                var evaluatorTeams = _context.EvaluatorTeams.ToList();
                return Ok(evaluatorTeams);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains all the evaluator teams by center
        /// </summary>
        /// <param name="id">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="idCenter">Organization center</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <returns>Evaluator teams list</returns>
        [HttpGet("allByCenter")]
        public IActionResult GetAllByCenter([FromQuery] int id, [FromQuery] string orgType, [FromQuery] int idCenter, [FromQuery] string illness)
        {
            try
            {
                var evaluatorTeams = _context.EvaluatorTeams.Where(e => e.idEvaluatedOrganization == id && e.orgTypeEvaluated == orgType && e.idCenter == idCenter && e.illness == illness).ToList();
                return Ok(evaluatorTeams);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains all the evaluator teams by organization
        /// </summary>
        /// <param name="id">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <returns>Evaluator teams list</returns>
        [HttpGet("allByOrganization")]
        public IActionResult GetAllByOrganization([FromQuery] int id, [FromQuery] string orgType, [FromQuery] string illness)
        {
            try
            {
                var evaluatorTeams = _context.EvaluatorTeams.Where(e => e.idEvaluatedOrganization == id && e.orgTypeEvaluated == orgType && e.illness == illness).ToList();
                return Ok(evaluatorTeams);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains an evaluator team from the database
        /// </summary>
        /// <param name="id">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrg">Evaluator organization identifier (who does the indicators evaluations)</param>
        /// <param name="orgTypeEvaluator">Evaluator organization type (who does the indicators evaluations)</param>
        /// <param name="idEvaluatedOrg">Evaluated organization identifier (who receives the indicators evaluations)</param>
        /// <param name="orgTypeEvaluated">Evaluated organization type (who receives the indicators evaluations)</param>
        /// <param name="idCenter">Evaluated organization center (who receives the indicators evaluations)</param>
        /// <param name="illness">Both organizations illness or syndrome</param>
        /// <returns>Evaluator team if sucess, null if not</returns>

        [HttpGet("get")]
        public ActionResult<EvaluatorTeam> Get([FromQuery] int id, [FromQuery] int idEvaluatorOrg, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrg, [FromQuery] string orgTypeEvaluated, [FromQuery] int idCenter, [FromQuery] string illness)
        {
            try
            {
                var evaluatorTeam = _context.EvaluatorTeams.FirstOrDefault(e => e.idEvaluatorTeam == id && e.idEvaluatorOrganization == idEvaluatorOrg && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrg && e.orgTypeEvaluated == orgTypeEvaluated && e.idCenter == idCenter && e.illness == illness);

                if (evaluatorTeam == null)
                    return NotFound();

                return evaluatorTeam;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that appends a new evaluator team to the database
        /// </summary>
        /// <param name="evaluatorTeam">Evaluator team</param>
        /// <returns>Evaluator team if sucess, null if not</returns>
        [HttpPost]
        public IActionResult Create([FromBody] EvaluatorTeam evaluatorTeam)
        {
            try
            {
                _context.EvaluatorTeams.Add(evaluatorTeam);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new
                {
                    id = evaluatorTeam.idEvaluatorTeam,
                    idEvaluatorOrg = evaluatorTeam.idEvaluatorOrganization,
                    orgTypeEvaluator = evaluatorTeam.orgTypeEvaluator,
                    idEvaluatedOrg = evaluatorTeam.idEvaluatedOrganization,
                    orgTypeEvaluated = evaluatorTeam.orgTypeEvaluated,
                    idCenter = evaluatorTeam.idCenter,
                    illness = evaluatorTeam.illness
                }, evaluatorTeam);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that updates an existant evaluator team
        /// </summary>
        /// <param name="id">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrg">Evaluator organization identifier (who does the indicators evaluations)</param>
        /// <param name="orgTypeEvaluator">Evaluator organization type (who does the indicators evaluations)</param>
        /// <param name="idEvaluatedOrg">Evaluated organization identifier (who receives the indicators evaluations)</param>
        /// <param name="orgTypeEvaluated">Evaluated organization type (who receives the indicators evaluations)</param>
        /// <param name="idCenter">Evaluated organization center (who receives the indicators evaluations)</param>
        /// <param name="illness">Both organizations illness or syndrome</param>
        /// <param name="evaluatorTeam">Evaluator team</param>
        /// <returns>Updated evaluator team if sucess, null if not</returns>
        [HttpPut]
        public IActionResult Update([FromQuery] int id, [FromQuery] int idEvaluatorOrg, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrg, [FromQuery] string orgTypeEvaluated, [FromQuery] int idCenter, [FromQuery] string illness, [FromBody] EvaluatorTeam evaluatorTeam)
        {
            try
            {
                if (id != evaluatorTeam.idEvaluatorTeam || idEvaluatorOrg != evaluatorTeam.idEvaluatorOrganization || orgTypeEvaluator != evaluatorTeam.orgTypeEvaluator || idEvaluatedOrg != evaluatorTeam.idEvaluatedOrganization || orgTypeEvaluated != evaluatorTeam.orgTypeEvaluated || idCenter != evaluatorTeam.idCenter || illness != evaluatorTeam.illness)
                    return BadRequest();

                var existingEvaluatorTeam = _context.EvaluatorTeams.FirstOrDefault(e => e.idEvaluatorTeam == id && e.idEvaluatorOrganization == idEvaluatorOrg && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrg && e.orgTypeEvaluated == orgTypeEvaluated && e.idCenter == idCenter && e.illness == illness);

                if (existingEvaluatorTeam is null)
                    return NotFound();


                existingEvaluatorTeam.idEvaluatorTeam = id;
                existingEvaluatorTeam.creationDate = evaluatorTeam.creationDate;
                existingEvaluatorTeam.idEvaluatorOrganization = idEvaluatorOrg;
                existingEvaluatorTeam.orgTypeEvaluator = orgTypeEvaluator;
                existingEvaluatorTeam.idEvaluatedOrganization = idEvaluatedOrg;
                existingEvaluatorTeam.orgTypeEvaluated = orgTypeEvaluator;
                existingEvaluatorTeam.idCenter = idCenter;
                existingEvaluatorTeam.illness = illness;
                existingEvaluatorTeam.externalConsultant = evaluatorTeam.externalConsultant;
                existingEvaluatorTeam.emailProfessional = evaluatorTeam.emailProfessional;
                existingEvaluatorTeam.emailResponsible = evaluatorTeam.emailResponsible;
                existingEvaluatorTeam.otherMembers = evaluatorTeam.otherMembers;
                existingEvaluatorTeam.patientName = evaluatorTeam.patientName;
                existingEvaluatorTeam.relativeName = evaluatorTeam.relativeName;
                existingEvaluatorTeam.evaluationDate1 = evaluatorTeam.evaluationDate1;
                existingEvaluatorTeam.evaluationDate2 = evaluatorTeam.evaluationDate2;
                existingEvaluatorTeam.evaluationDate3 = evaluatorTeam.evaluationDate3;
                existingEvaluatorTeam.evaluationDate4 = evaluatorTeam.evaluationDate4;
                existingEvaluatorTeam.observationsSpanish = evaluatorTeam.observationsSpanish;
                existingEvaluatorTeam.observationsEnglish = evaluatorTeam.observationsEnglish;
                existingEvaluatorTeam.observationsFrench = evaluatorTeam.observationsFrench;
                existingEvaluatorTeam.observationsBasque = evaluatorTeam.observationsBasque;
                existingEvaluatorTeam.observationsCatalan = evaluatorTeam.observationsCatalan;
                existingEvaluatorTeam.observationsDutch = evaluatorTeam.observationsDutch;
                existingEvaluatorTeam.observationsGalician = evaluatorTeam.observationsGalician;
                existingEvaluatorTeam.observationsGerman = evaluatorTeam.observationsGerman;
                existingEvaluatorTeam.observationsItalian = evaluatorTeam.observationsItalian;
                existingEvaluatorTeam.observationsPortuguese = evaluatorTeam.observationsPortuguese;

                _context.SaveChanges();

                return Ok(existingEvaluatorTeam);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that deletes an evaluator team
        /// </summary>
        /// <param name="id">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrg">Evaluator organization identifier (who does the indicators evaluations)</param>
        /// <param name="orgTypeEvaluator">Evaluator organization type (who does the indicators evaluations)</param>
        /// <param name="idEvaluatedOrg">Evaluated organization identifier (who receives the indicators evaluations)</param>
        /// <param name="orgTypeEvaluated">Evaluated organization type (who receives the indicators evaluations)</param>
        /// <param name="idCenter">Evaluated organization center (who receives the indicators evaluations)</param>
        /// <param name="illness">Both organizations illness or syndrome</param>
        /// <returns>Evaluator team if sucess, null if not</returns>
        [HttpDelete]
        public IActionResult Delete([FromQuery] int id, [FromQuery] int idEvaluatorOrg, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrg, [FromQuery] string orgTypeEvaluated, [FromQuery] int idCenter, [FromQuery] string illness)
        {
            try
            {
                var evaluatorTeam = _context.EvaluatorTeams.FirstOrDefault(e => e.idEvaluatorTeam == id && e.idEvaluatorOrganization == idEvaluatorOrg && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrg && e.orgTypeEvaluated == orgTypeEvaluated && e.idCenter == idCenter && e.illness == illness);

                if (evaluatorTeam is null)
                    return NotFound();

                _context.EvaluatorTeams.Remove(evaluatorTeam);
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
