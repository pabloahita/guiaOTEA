﻿using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Net.NetworkInformation;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("IndicatorsEvaluations")]
    public class IndicatorsEvaluationsController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public IndicatorsEvaluationsController(DatabaseContext context)
        {
            _context=context;
        }


        // GET all action
        [HttpGet("all")]
        public IActionResult GetAll()
        {
            var indicatorsEvaluations = _context.IndicatorsEvaluations.ToList();
            return Ok(indicatorsEvaluations);
        }

        // GET all by EVALUATOR ORGANIZATION action
        [HttpGet("evaluatorOrg")]
        public IActionResult GetAllByEvaluatorOrganization([FromQuery] int idEvaluatorOrganization, [FromQuery] string orgType, [FromQuery] string illness)
        {
            var indicatorsEvaluations = _context.IndicatorsEvaluations.Where(e=>e.idEvaluatorOrganization==idEvaluatorOrganization && e.orgTypeEvaluator==orgType && e.illness==illness).ToList();
            return Ok(indicatorsEvaluations);
        }

        //GET all by EVALUATED ORGANIZATION
        [HttpGet("evaluatedOrg")]
        public IActionResult GetAllByEvaluatedOrganization([FromQuery] int idEvaluatedOrganization, [FromQuery] string orgType, [FromQuery] string illness)
        {
            var indicatorsEvaluations = _context.IndicatorsEvaluations.Where(e => e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgType && e.illness == illness).ToList();
            return Ok(indicatorsEvaluations);
        }


        //GET non finished by CENTER
        [HttpGet("nonFinished")]
        public IActionResult GetNonFinishedByCenter([FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter)
        {
            var indicatorsEvaluations = _context.IndicatorsEvaluations.Where(e => e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.illness == illness && e.idCenter == idCenter && e.isFinished==0).ToList();
            return Ok(indicatorsEvaluations);
        }
        // GET by PK action

        [HttpGet("get")]
        public ActionResult<IndicatorsEvaluation> Get([FromQuery] long evaluationDate,[FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter)
        {
            var indicatorsEvaluation = _context.IndicatorsEvaluations.FirstOrDefault(e => e.evaluationDate==evaluationDate && e.idEvaluatorTeam == idEvaluatorTeam && e.idEvaluatorOrganization == idEvaluatorOrganization && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.illness == illness);

            if (indicatorsEvaluation == null)
                return NotFound();

            return indicatorsEvaluation;
        }



        // POST action
        [HttpPost]
        public IActionResult Create([FromBody] IndicatorsEvaluation indicatorsEvaluation)
        {
            _context.IndicatorsEvaluations.Add(indicatorsEvaluation);
            _context.SaveChanges();
            return CreatedAtAction(nameof(Get), new { evaluation_date = indicatorsEvaluation.evaluationDate, idEvaluatorTeam = indicatorsEvaluation.idEvaluatorTeam, idEvaluatorOrganization = indicatorsEvaluation.idEvaluatorOrganization, orgTypeEvaluator = indicatorsEvaluation.orgTypeEvaluator, idEvaluatedOrganization = indicatorsEvaluation.idEvaluatedOrganization, orgTypeEvaluated = indicatorsEvaluation.orgTypeEvaluated, illness = indicatorsEvaluation.illness, idCenter=indicatorsEvaluation.idCenter }, indicatorsEvaluation);
        }

        // PUT action
        [HttpPut]
        public IActionResult Update([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromBody] IndicatorsEvaluation indicatorsEvaluation)
        {
            // This code will update the mesa and return a result
            if (evaluationDate != indicatorsEvaluation.evaluationDate || idEvaluatorOrganization != indicatorsEvaluation.idEvaluatorOrganization || orgTypeEvaluator != indicatorsEvaluation.orgTypeEvaluator || idEvaluatedOrganization != indicatorsEvaluation.idEvaluatedOrganization || orgTypeEvaluated != indicatorsEvaluation.orgTypeEvaluated || illness!=indicatorsEvaluation.illness || idCenter!=indicatorsEvaluation.idCenter)
                return BadRequest();

            var existingIndicatorsEvaluation = _context.IndicatorsEvaluations.FirstOrDefault(e => e.evaluationDate==evaluationDate && e.idEvaluatorTeam == idEvaluatorTeam && e.idEvaluatorOrganization == idEvaluatorOrganization && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.illness == illness);
            if (existingIndicatorsEvaluation is null)
                return NotFound();

            existingIndicatorsEvaluation.evaluationDate = evaluationDate;
            existingIndicatorsEvaluation.idEvaluatedOrganization = idEvaluatedOrganization;
            existingIndicatorsEvaluation.orgTypeEvaluated = orgTypeEvaluated;
            existingIndicatorsEvaluation.idEvaluatorTeam = idEvaluatorTeam;
            existingIndicatorsEvaluation.idEvaluatorOrganization = indicatorsEvaluation.idEvaluatorOrganization;
            existingIndicatorsEvaluation.orgTypeEvaluator = indicatorsEvaluation.orgTypeEvaluator;
            existingIndicatorsEvaluation.illness = illness;
            existingIndicatorsEvaluation.idCenter = idCenter;
            existingIndicatorsEvaluation.observationsSpanish = indicatorsEvaluation.observationsSpanish;
            existingIndicatorsEvaluation.observationsEnglish = indicatorsEvaluation.observationsEnglish;
            existingIndicatorsEvaluation.observationsFrench = indicatorsEvaluation.observationsFrench;
            existingIndicatorsEvaluation.observationsBasque = indicatorsEvaluation.observationsBasque;
            existingIndicatorsEvaluation.observationsCatalan = indicatorsEvaluation.observationsCatalan;
            existingIndicatorsEvaluation.observationsDutch = indicatorsEvaluation.observationsDutch;
            existingIndicatorsEvaluation.observationsGalician = indicatorsEvaluation.observationsGalician;
            existingIndicatorsEvaluation.observationsGerman = indicatorsEvaluation.observationsGerman;
            existingIndicatorsEvaluation.observationsItalian = indicatorsEvaluation.observationsItalian;
            existingIndicatorsEvaluation.observationsPortuguese = indicatorsEvaluation.observationsPortuguese;
            existingIndicatorsEvaluation.conclusionsSpanish = indicatorsEvaluation.conclusionsSpanish;
            existingIndicatorsEvaluation.conclusionsEnglish = indicatorsEvaluation.conclusionsEnglish;
            existingIndicatorsEvaluation.conclusionsFrench = indicatorsEvaluation.conclusionsFrench;
            existingIndicatorsEvaluation.conclusionsBasque = indicatorsEvaluation.conclusionsBasque;
            existingIndicatorsEvaluation.conclusionsCatalan = indicatorsEvaluation.conclusionsCatalan;
            existingIndicatorsEvaluation.conclusionsDutch = indicatorsEvaluation.conclusionsDutch;
            existingIndicatorsEvaluation.conclusionsGalician = indicatorsEvaluation.conclusionsGalician;
            existingIndicatorsEvaluation.conclusionsGerman = indicatorsEvaluation.conclusionsGerman;
            existingIndicatorsEvaluation.conclusionsItalian = indicatorsEvaluation.conclusionsItalian;
            existingIndicatorsEvaluation.conclusionsPortuguese = indicatorsEvaluation.conclusionsPortuguese;
            existingIndicatorsEvaluation.scoreLevel1 = indicatorsEvaluation.scoreLevel1;
            existingIndicatorsEvaluation.scoreLevel2 = indicatorsEvaluation.scoreLevel2;
            existingIndicatorsEvaluation.scoreLevel3 = indicatorsEvaluation.scoreLevel3;
            existingIndicatorsEvaluation.scoreLevel4 = indicatorsEvaluation.scoreLevel4;
            existingIndicatorsEvaluation.scoreLevel5 = indicatorsEvaluation.scoreLevel5;
            existingIndicatorsEvaluation.scoreLevel6 = indicatorsEvaluation.scoreLevel6;
            existingIndicatorsEvaluation.totalScore = indicatorsEvaluation.totalScore;
            existingIndicatorsEvaluation.isFinished = indicatorsEvaluation.isFinished;
            _context.SaveChanges();
            return Ok(existingIndicatorsEvaluation);
        }

        // DELETE action
        [HttpDelete]
        public IActionResult Delete([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness)
        {
            // This code will delete the mesa and return a result
            var indicatorsEvaluation = _context.IndicatorsEvaluations.FirstOrDefault(e => e.evaluationDate==evaluationDate && e.idEvaluatorTeam == idEvaluatorTeam && e.idEvaluatorOrganization == idEvaluatorOrganization && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.illness == illness);

            if (indicatorsEvaluation is null)
                return NotFound();

            _context.IndicatorsEvaluations.Remove(indicatorsEvaluation);
            _context.SaveChanges();

            return NoContent();
        }
    }
}
