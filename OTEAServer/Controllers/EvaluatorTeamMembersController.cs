using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("EvaluatorTeamMembers")]
    public class EvaluatorTeamMembersController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public EvaluatorTeamMembersController(DatabaseContext context)
        {
            _context = context;
        }
        [HttpGet("all")]
        public IActionResult GetAll() {
            var evaluatorTeamMembers = _context.EvaluatorTeamMembers.ToList();
            return Ok(evaluatorTeamMembers);
        }

        [HttpGet("evalTeam")]
        public IActionResult GetAllByEvaluatorTeam([FromQuery] int idEvaluatorTeam,[FromQuery] int idEvaluatorOrganization, [FromQuery] string orgType, [FromQuery] string illness) {
            var evaluatorTeamMembers = _context.EvaluatorTeamMembers.Where(m=> m.idEvaluatorTeam==idEvaluatorTeam && m.idEvaluatorOrganization==idEvaluatorOrganization && m.orgType==orgType && m.illness==illness).ToList();
            return Ok(evaluatorTeamMembers);
        }

        [HttpGet("get")]
        public ActionResult<EvaluatorTeamMember> Get([FromQuery] string emailUser, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgType, [FromQuery] string illness) {
            var evaluatorTeamMember = _context.EvaluatorTeamMembers.FirstOrDefault(m => m.emailUser==emailUser && m.idEvaluatorTeam == idEvaluatorTeam && m.idEvaluatorOrganization == idEvaluatorOrganization && m.orgType == orgType && m.illness == illness);

            if (evaluatorTeamMember == null)
                return NotFound();

            return evaluatorTeamMember;
        }

        [HttpPost]
        public ActionResult<EvaluatorTeamMember> Create([FromBody] EvaluatorTeamMember evaluatorTeamMember) {
            _context.EvaluatorTeamMembers.Add(evaluatorTeamMember);
            _context.SaveChanges();
            return CreatedAtAction(nameof(Get), new { emailUser = evaluatorTeamMember.emailUser, idEvaluatorTeam = evaluatorTeamMember.idEvaluatorTeam, idEvaluatorOrganization = evaluatorTeamMember.idEvaluatorOrganization, orgType = evaluatorTeamMember.orgType, illness = evaluatorTeamMember.illness }, evaluatorTeamMember);
        }

        [HttpPut]
        public ActionResult<EvaluatorTeamMember> Update([FromQuery] string emailUser, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgType, [FromQuery] string illness, [FromBody] EvaluatorTeamMember evaluatorTeamMember) {
            
            var existingEvaluatorTeamMember = _context.EvaluatorTeamMembers.FirstOrDefault(m => m.emailUser == emailUser && m.idEvaluatorTeam == idEvaluatorTeam && m.idEvaluatorOrganization == idEvaluatorOrganization && m.orgType == orgType && m.illness == illness);
            if (existingEvaluatorTeamMember is null)
                return NotFound();

            _context.EvaluatorTeamMembers.Remove(existingEvaluatorTeamMember);
            _context.EvaluatorTeamMembers.Add(evaluatorTeamMember);
            _context.SaveChanges();

            return Ok(evaluatorTeamMember);
        }

        [HttpDelete]
        public ActionResult<EvaluatorTeamMember> Delete([FromQuery] string emailUser, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgType, [FromQuery] string illness) {
            // This code will delete the evaluator team member and return a result
            var evaluatorTeamMember = _context.EvaluatorTeamMembers.FirstOrDefault(m => m.emailUser == emailUser && m.idEvaluatorTeam == idEvaluatorTeam && m.idEvaluatorOrganization == idEvaluatorOrganization && m.orgType == orgType && m.illness == illness);

            if (evaluatorTeamMember is null)
                return NotFound();

            _context.EvaluatorTeamMembers.Remove(evaluatorTeamMember);
            _context.SaveChanges();

            return NoContent();
        }
    }
}
