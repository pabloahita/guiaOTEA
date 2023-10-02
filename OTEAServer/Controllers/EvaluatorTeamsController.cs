using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("EvaluatorTeams")]
    public class EvaluatorTeamsController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public EvaluatorTeamsController(DatabaseContext context)
        {
            _context = context;
        }
        // GET all action
        [HttpGet("all")]
        public IActionResult GetAll()
        {
            var evaluatorTeams = _context.EvaluatorTeams.ToList();
            return Ok(evaluatorTeams);
        }

        [HttpGet("allByCenter")]
        public IActionResult GetAllByCenter([FromQuery] int id, [FromQuery] string orgType, [FromQuery] int idCenter, [FromQuery] string illness)
        {
            var evaluatorTeams = _context.EvaluatorTeams.Where(e=>e.idEvaluatedOrganization==id && e.orgTypeEvaluated==orgType && e.idCenter==idCenter && e.illness==illness).ToList();
            return Ok(evaluatorTeams);
        }

        [HttpGet("allByOrganization")]
        public IActionResult GetAllByOrganization([FromQuery] int id, [FromQuery] string orgType, [FromQuery] string illness)
        {
            var evaluatorTeams = _context.EvaluatorTeams.Where(e=>e.idEvaluatedOrganization==id && e.orgTypeEvaluated==orgType && e.illness==illness).ToList();
            return Ok(evaluatorTeams);
        }

        // GET by ID AND ORGTYPE action

        [HttpGet("get")]
        public ActionResult<EvaluatorTeam> Get([FromQuery] int id, [FromQuery] int idEvaluatorOrg, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrg, [FromQuery] string orgTypeEvaluated, [FromQuery] int idCenter, [FromQuery] string illness)
        {
            var evaluatorTeam = _context.EvaluatorTeams.FirstOrDefault(e => e.idEvaluatorTeam == id && e.idEvaluatorOrganization == idEvaluatorOrg && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization==idEvaluatedOrg && e.orgTypeEvaluated==orgTypeEvaluated && e.idCenter==idCenter && e.illness == illness);

            if (evaluatorTeam == null)
                return NotFound();

            return evaluatorTeam;
        }

        // POST action
        [HttpPost]
        public IActionResult Create([FromBody] EvaluatorTeam evaluatorTeam)
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

        // PUT action
        [HttpPut]
        public IActionResult Update([FromQuery] int id, [FromQuery] int idEvaluatorOrg, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrg, [FromQuery] string orgTypeEvaluated, [FromQuery] int idCenter, [FromQuery] string illness, [FromBody] EvaluatorTeam evaluatorTeam)
        {
            // This code will update the evaluator team and return a result
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

        // DELETE action
        [HttpDelete]
        public IActionResult Delete([FromQuery] int id, [FromQuery] int idEvaluatorOrg, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrg, [FromQuery] string orgTypeEvaluated, [FromQuery] int idCenter, [FromQuery] string illness)
        {
            // This code will delete the evaluator team and return a result
            var evaluatorTeam = _context.EvaluatorTeams.FirstOrDefault(e => e.idEvaluatorTeam == id && e.idEvaluatorOrganization == idEvaluatorOrg && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrg && e.orgTypeEvaluated == orgTypeEvaluated && e.idCenter == idCenter && e.illness == illness);

            if (evaluatorTeam is null)
                return NotFound();

            _context.EvaluatorTeams.Remove(evaluatorTeam);
            _context.SaveChanges();

            return NoContent();
        }
    }
}
