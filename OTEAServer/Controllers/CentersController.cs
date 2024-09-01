using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Data.SqlClient;
using System.Linq.Expressions;
using System.Net;
using System.Security.Policy;
using System.Text.Json;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller for Centers operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>


    [ApiController]
    [Route("Centers")]
    public class CentersController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public CentersController(DatabaseContext context)
        {
            _context = context;
        }

        private class CenterDto
        {
            public int idOrganization {  get; set; }
            public string orgType {  get; set; }
            public string illness { get; set; }
            public int idCenter { get; set; }

            public string description { get; set; }

            public string telephone { get; set; }

            public int idAddress { get; set; }

            public string email { get; set; }

            public string profilePhoto { get; set; }
        }

        /// <summary>
        /// Method that obtains all the centers
        /// </summary>
        /// <returns>Centers list</returns>
        [HttpGet("all")]
        [Authorize]
        public IActionResult GetAll([FromHeader] string Authorization) {
            try
            {

                var centers = _context.Centers.ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var center in centers)
                {
                    String rg = "{\"idOrganization\":\"" + center.idOrganization + "\"," +
                            "\"orgType\":\"" + center.orgType + "\"," +
                            "\"illness\":\"" + center.illness + "\"," +
                            "\"idCenter\":\"" + center.idCenter + "\"," +
                            "\"descriptionSpanish\":\"" + center.descriptionSpanish + "\"," +
                            "\"descriptionEnglish\":\"" + center.descriptionEnglish + "\"," +
                            "\"descriptionFrench\":\"" + center.descriptionFrench + "\"," +
                            "\"descriptionBasque\":\"" + center.descriptionBasque + "\"," +
                            "\"descriptionCatalan\":\"" + center.descriptionCatalan + "\"," +
                            "\"descriptionDutch\":\"" + center.descriptionDutch + "\"," +
                            "\"descriptionGalician\":\"" + center.descriptionGalician + "\"," +
                            "\"descriptionGerman\":\"" + center.descriptionGerman + "\"," +
                            "\"descriptionItalian\":\"" + center.descriptionItalian + "\"," +
                            "\"descriptionPortuguese\":\"" + center.descriptionPortuguese + "\"," +
                            "\"telephone\":\"" + center.telephone + "\"," +
                            "\"idAddress\":\"" + center.idAddress + "\"," +
                            "\"email\":\"" + center.email + "\"," +
                            "\"profilePhoto\":\"" + center.profilePhoto + "\"}";
                    result.Add(JsonDocument.Parse(rg));
                }
                return Ok(result);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains all the centers of an organization
        /// </summary>
        /// <param name="idOrganization">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <returns>All centers of an organization</returns>
        [HttpGet("org")]
        [Authorize]
        public IActionResult GetAllByOrganization([FromQuery] int idOrganization,[FromQuery] string orgType,[FromQuery] string illness, [FromQuery] string language, [FromHeader] string Authorization) {
            try
            {

                var aux = _context.Centers.Where(c => c.idOrganization == idOrganization && c.orgType == orgType && c.illness == illness);//.ToList();
                Expression<Func<Center, CenterDto>> selector = c => new CenterDto {
                    idOrganization=c.idOrganization,
                    orgType=c.orgType,
                    illness=c.illness,
                    idCenter=c.idCenter,
                    description=c.descriptionEnglish,
                    telephone=c.telephone,
                    idAddress=c.idAddress,
                    email=c.email,
                    profilePhoto=c.profilePhoto
                };
                switch (language)
                {
                    case "es":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionSpanish,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "fr":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionFrench,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "eu":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionBasque,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "ca":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionCatalan,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "gl":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionGalician,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "pt":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionPortuguese,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "de":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionGerman,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "it":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionItalian,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "nl":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionDutch,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    default:
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionEnglish,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                }
                var centers = aux.Select(selector).ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var center in centers)
                {
                    String rg = "{\"idOrganization\":\"" + center.idOrganization + "\"," +
                            "\"orgType\":\"" + center.orgType + "\"," +
                            "\"illness\":\"" + center.illness + "\"," +
                            "\"idCenter\":\"" + center.idCenter + "\"," +
                            "\"description\":\"" + center.description + "\"," +
                            "\"telephone\":\"" + center.telephone + "\"," +
                            "\"idAddress\":\"" + center.idAddress + "\"," +
                            "\"email\":\"" + center.email + "\"," +
                            "\"profilePhoto\":\"" + center.profilePhoto + "\"}";
                    result.Add(JsonDocument.Parse(rg));
                }
                return Ok(result);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains all the centers of an organization
        /// </summary>
        /// <param name="idOrganization">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <returns>All centers of an organization</returns>
        [HttpGet("aditional")]
        [Authorize]
        public IActionResult GetAditionalCentersByOrganization([FromQuery] int idOrganization, [FromQuery] string orgType, [FromQuery] string illness, [FromQuery] string language, [FromHeader] string Authorization)
        {
            try
            {

                var aux = _context.Centers.Where(c => c.idOrganization == idOrganization && c.orgType == orgType && c.illness == illness && c.idCenter>1);//.ToList();
                Expression<Func<Center, CenterDto>> selector = c => new CenterDto
                {
                    idOrganization = c.idOrganization,
                    orgType = c.orgType,
                    illness = c.illness,
                    idCenter = c.idCenter,
                    description = c.descriptionEnglish,
                    telephone = c.telephone,
                    idAddress = c.idAddress,
                    email = c.email,
                    profilePhoto = c.profilePhoto
                };
                switch (language)
                {
                    case "es":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionSpanish,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "fr":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionFrench,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "eu":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionBasque,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "ca":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionCatalan,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "gl":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionGalician,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "pt":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionPortuguese,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "de":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionGerman,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "it":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionItalian,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    case "nl":
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionDutch,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                    default:
                        selector = c => new CenterDto
                        {
                            idOrganization = c.idOrganization,
                            orgType = c.orgType,
                            illness = c.illness,
                            idCenter = c.idCenter,
                            description = c.descriptionEnglish,
                            telephone = c.telephone,
                            idAddress = c.idAddress,
                            email = c.email,
                            profilePhoto = c.profilePhoto
                        };
                        break;
                }
                var centers = aux.Select(selector).ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var center in centers)
                {
                    String rg = "{\"idOrganization\":\"" + center.idOrganization + "\"," +
                            "\"orgType\":\"" + center.orgType + "\"," +
                            "\"illness\":\"" + center.illness + "\"," +
                            "\"idCenter\":\"" + center.idCenter + "\"," +
                            "\"description\":\"" + center.description + "\"," +
                            "\"telephone\":\"" + center.telephone + "\"," +
                            "\"idAddress\":\"" + center.idAddress + "\"," +
                            "\"email\":\"" + center.email + "\"," +
                            "\"profilePhoto\":\"" + center.profilePhoto + "\"}";
                    result.Add(JsonDocument.Parse(rg));
                }
                return Ok(result);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }


        /// <summary>
        /// Method that obtains a center using its identifier and the belonging organization
        /// </summary>
        /// <param name="idOrganization">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <param name="idCenter">Center identifier</param>
        /// <returns>Center if found on database, null if not</returns>
        [HttpGet("get")]
        [Authorize]
        public ActionResult<Center> Get([FromQuery] int idOrganization, [FromQuery] string orgType, [FromQuery] string illness,[FromQuery] int idCenter, [FromHeader] string Authorization)
        {
            try
            {
                var center = _context.Centers.FirstOrDefault(c => c.idOrganization == idOrganization && c.orgType == orgType && c.illness == illness && c.idCenter == idCenter);
                if (center == null)
                {
                    return NotFound();
                }
                return center;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
            
        }


        /// <summary>
        /// Method that appends a new center to the database
        /// </summary>
        /// <param name="center">Center to append</param>
        /// <returns>Center appended if sucess, null if not</returns>
        [HttpPost]
        [Authorize]
        public ActionResult<Center> Create([FromBody] Center center, [FromHeader] string Authorization)
        {
            try
            {
                _context.Centers.Add(center);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new { idOrganization = center.idOrganization, orgType = center.orgType, illness = center.illness, idCenter = center.idCenter }, center);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
            
        }

        /// <summary>
        /// Method that updates an existing center
        /// </summary>
        /// <param name="idOrganization">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <param name="idCenter">Center identifier</param>
        /// <param name="center">Center</param>
        /// <returns>Updated center if success, null if not</returns>
        [HttpPut]
        [Authorize(Policy = "Director")]
        public ActionResult<Center> Update([FromQuery] int idOrganization, [FromQuery] string orgType, [FromQuery] string illness, [FromQuery] int idCenter,[FromBody] Center center, [FromHeader] string Authorization)
        {
            try
            {
                if(idOrganization != center.idOrganization || orgType != center.orgType || illness != center.illness || idCenter != center.idCenter)
                return BadRequest();

                var existingCenter = _context.Centers.FirstOrDefault(c => c.idOrganization == idOrganization && c.orgType == orgType && c.illness == illness && c.idCenter == idCenter);
                if (existingCenter is null)
                    return NotFound();

                existingCenter.idOrganization = idOrganization;
                existingCenter.orgType = orgType;
                existingCenter.illness = illness;
                existingCenter.idCenter = idCenter;
                existingCenter.descriptionSpanish = center.descriptionSpanish;
                existingCenter.descriptionEnglish = center.descriptionEnglish;
                existingCenter.descriptionFrench = center.descriptionFrench;
                existingCenter.descriptionBasque = center.descriptionBasque;
                existingCenter.descriptionCatalan = center.descriptionCatalan;
                existingCenter.descriptionDutch = center.descriptionDutch;
                existingCenter.descriptionGalician = center.descriptionGalician;
                existingCenter.descriptionGerman = center.descriptionGerman;
                existingCenter.descriptionItalian = center.descriptionItalian;
                existingCenter.descriptionPortuguese = center.descriptionPortuguese;
                existingCenter.telephone = center.telephone;
                existingCenter.idAddress = center.idAddress;
                existingCenter.email = center.email;
                existingCenter.profilePhoto = center.profilePhoto;
                _context.SaveChanges();

                return Ok(existingCenter);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
            
        }


        /// <summary>
        /// Method that deletes a center
        /// </summary>
        /// <param name="idOrganization">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <param name="idCenter">Center identifier</param>
        /// <returns>Deleted center if success, null if not</returns>
        [HttpDelete]
        [Authorize(Policy = "Director")]
        public ActionResult<Center> Delete([FromQuery] int idOrganization, [FromQuery] string orgType, [FromQuery] string illness, [FromQuery] int idCenter, [FromHeader] string Authorization)
        {
            try
            {
                var center = _context.Centers.FirstOrDefault(c => c.idOrganization == idOrganization && c.orgType == orgType && c.illness == illness && c.idCenter == idCenter);

                if (center is null)
                    return NotFound();

                var indicatorsEvaluationSimpleEvidenceRegs = _context.IndicatorsEvaluationsSimpleEvidencesRegs.Where(c => c.idEvaluatedOrganization == idOrganization && c.orgTypeEvaluated == orgType && c.illness == illness && c.idCenter == idCenter).ToList();
                if (indicatorsEvaluationSimpleEvidenceRegs is not null)
                {
                    foreach (var reg in indicatorsEvaluationSimpleEvidenceRegs)
                    {
                        _context.IndicatorsEvaluationsSimpleEvidencesRegs.Remove(reg);
                    }
                }
                _context.SaveChanges();

                var indicatorsEvaluationEvidenceRegs = _context.IndicatorsEvaluationsEvidencesRegs.Where(c => c.idEvaluatedOrganization == idOrganization && c.orgTypeEvaluated == orgType && c.illness == illness && c.idCenter == idCenter).ToList();
                if(indicatorsEvaluationEvidenceRegs is not null) {
                    foreach (var reg in indicatorsEvaluationEvidenceRegs)
                    {
                        _context.IndicatorsEvaluationsEvidencesRegs.Remove(reg);
                    }
                }
                _context.SaveChanges();

                var indicatorsEvaluationIndicatorRegs = _context.IndicatorsEvaluationsIndicatorsRegs.Where(c => c.idEvaluatedOrganization == idOrganization && c.orgTypeEvaluated == orgType && c.illness == illness && c.idCenter == idCenter).ToList();
                if (indicatorsEvaluationIndicatorRegs is not null)
                {
                    foreach (var reg in indicatorsEvaluationIndicatorRegs)
                    {
                        _context.IndicatorsEvaluationsIndicatorsRegs.Remove(reg);
                    }
                }
                _context.SaveChanges();

                var indicatorsEvaluations = _context.IndicatorsEvaluations.Where(c => c.idEvaluatedOrganization == idOrganization && c.orgTypeEvaluated == orgType && c.illness == illness && c.idCenter == idCenter).ToList();
                if (indicatorsEvaluations is not null) {
                    foreach(var indEval in indicatorsEvaluations) {
                        _context.IndicatorsEvaluations.Remove(indEval);
                    }
                }
                _context.SaveChanges();

                var evaluatorTeams = _context.EvaluatorTeams.Where(c => c.idEvaluatedOrganization == idOrganization && c.orgTypeEvaluated == orgType && c.illness == illness && c.idCenter == idCenter);
                if (evaluatorTeams is not null)
                {
                    foreach (var team in evaluatorTeams)
                    {
                        _context.EvaluatorTeams.Remove(team);
                    }
                }

                _context.Centers.Remove(center);
                _context.SaveChanges();

                var address = _context.Addresses.FirstOrDefault(c => c.idAddress == center.idAddress);
                if (address is not null)
                {
                    _context.Addresses.Remove(address);
                }

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

