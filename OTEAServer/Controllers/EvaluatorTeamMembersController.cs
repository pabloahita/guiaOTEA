using Microsoft.AspNetCore.Mvc;
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
        [HttpGet]
        public IActionResult GetAll() {
            var evaluatorTeamMembers = _context.EvaluatorTeamMembers.ToList();
            return Ok(evaluatorTeamMembers);
        }

        [HttpGet("evalTeam::idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")]
        public IActionResult GetAllByEvaluatorTeam(int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness) {
            var evaluatorTeamMembers = _context.EvaluatorTeamMembers.Where(m=> m.idEvaluatorTeam==idEvaluatorTeam && m.idEvaluatorOrganization==idEvaluatorOrganization && m.orgType==orgType && m.illness==illness).ToList();
            return Ok(evaluatorTeamMembers);
        }

        [HttpGet("get::emailUser={emailUser}:idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")]
        public ActionResult<EvaluatorTeamMember> Get(string emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness) {
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

        [HttpPut("upd::emailUser={emailUser}:idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")]
        public ActionResult<EvaluatorTeamMember> Update(string emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness, [FromBody] EvaluatorTeamMember evaluatorTeamMember) {
            // This code will update the evaluator team and return a result
            if (emailUser != evaluatorTeamMember.emailUser || idEvaluatorTeam != evaluatorTeamMember.idEvaluatorTeam || idEvaluatorOrganization != evaluatorTeamMember.idEvaluatorOrganization || orgType != evaluatorTeamMember.orgType || illness != evaluatorTeamMember.illness)
                return BadRequest();

            var existingEvaluatorTeamMember = _context.EvaluatorTeamMembers.FirstOrDefault(m => m.emailUser == emailUser && m.idEvaluatorTeam == idEvaluatorTeam && m.idEvaluatorOrganization == idEvaluatorOrganization && m.orgType == orgType && m.illness == illness);
            if (existingEvaluatorTeamMember is null)
                return NotFound();

            _context.EvaluatorTeamMembers.Update(evaluatorTeamMember);
            _context.SaveChanges();

            return Ok(existingEvaluatorTeamMember);
        }

        [HttpDelete("del::emailUser={emailUser}:idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")]
        public ActionResult<EvaluatorTeamMember> Delete(string emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness) {
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
