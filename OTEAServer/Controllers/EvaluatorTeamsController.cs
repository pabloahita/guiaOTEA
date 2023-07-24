﻿using Microsoft.AspNetCore.Http;
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
        [HttpGet]
        public IActionResult GetAll()
        {
            var evaluatorTeams = _context.EvaluatorTeams.ToList();
            return Ok(evaluatorTeams);
        }

        [HttpGet("org::id={id}:orgType={orgType}:illness={illness}")]
        public IActionResult GetAllByOrganization(int id, string orgType, string illness)
        {
            var evaluatorTeams = _context.EvaluatorTeams.Where(e=>e.idOrganization==id && e.orgType==orgType && e.illness==illness).ToList();
            return Ok(evaluatorTeams);
        }

        // GET by ID AND ORGTYPE action

        [HttpGet("get::id={id}:idEvaluatorOrg={idEvaluatorOrg}:orgType={orgType}:illness={illness}")]
        public ActionResult<EvaluatorTeam> Get(int id, int idEvaluatorOrg, string orgType, string illness)
        {
            var evaluatorTeam = _context.EvaluatorTeams.FirstOrDefault(e => e.idEvaluatorTeam == id && e.idOrganization == idEvaluatorOrg && e.orgType == orgType && e.illness == illness);

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
                idEvaluatorOrg = evaluatorTeam.idOrganization,
                orgType = evaluatorTeam.orgType,
                illness = evaluatorTeam.illness
            }, evaluatorTeam);
        }

        // PUT action
        [HttpPut("upd::id={id}:idEvaluatorOrg={idEvaluatorOrg}:orgType={orgType}:illness={illness}")]
        public IActionResult Update(int id, int idEvaluatorOrg, string orgType, string illness, [FromBody] EvaluatorTeam evaluatorTeam)
        {
            // This code will update the evaluator team and return a result
            if (id != evaluatorTeam.idEvaluatorTeam || idEvaluatorOrg!=evaluatorTeam.idOrganization || orgType!=evaluatorTeam.orgType || illness!=evaluatorTeam.illness)
                return BadRequest();

            var existingEvaluatorTeam = _context.EvaluatorTeams.FirstOrDefault(e => e.idEvaluatorTeam == id && e.idOrganization == idEvaluatorOrg && e.orgType == orgType && e.illness == illness);

            if (existingEvaluatorTeam is null)
                return NotFound();

            _context.EvaluatorTeams.Update(evaluatorTeam);

            _context.SaveChanges();

            return Ok(existingEvaluatorTeam);
        }

        // DELETE action
        [HttpDelete("del::id={id}:idEvaluatorOrg={idEvaluatorOrg}:orgType={orgType}:illness={illness}")]
        public IActionResult Delete(int id, int idEvaluatorOrg, string orgType, string illness)
        {
            // This code will delete the evaluator team and return a result
            var evaluatorTeam = _context.EvaluatorTeams.FirstOrDefault(e => e.idEvaluatorTeam == id && e.idOrganization == idEvaluatorOrg && e.orgType == orgType && e.illness == illness);

            if (evaluatorTeam is null)
                return NotFound();

            _context.EvaluatorTeams.Remove(evaluatorTeam);
            _context.SaveChanges();

            return NoContent();
        }
    }
}