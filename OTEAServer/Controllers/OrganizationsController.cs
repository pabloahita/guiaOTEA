using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Net;
using System.Text.Json;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for organization operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("Organizations")]
    public class OrganizationsController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public OrganizationsController(DatabaseContext context)
        {
            _context = context;
        }


        /// <summary>
        /// Gets all organizations
        /// </summary>
        /// <returns>Organization list</returns>
        [HttpGet("all")]
        [AllowAnonymous]
        public IActionResult GetAll()
        {
            try
            {
                var organizations = _context.Organizations.ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var organization in organizations)
                {
                    String rg = "{\"idOrganization\":\"" + organization.idOrganization + "\"," +
                                "\"orgType\":\"" + organization.orgType + "\"," +
                                "\"illness\":\"" + organization.illness + "\"," +
                                "\"nameOrg\":\"" + organization.nameOrg + "\"," +
                                "\"idAddress\":\"" + organization.idAddress + "\"," +
                                "\"email\":\"" + organization.email + "\"," +
                                "\"telephone\":\"" + organization.telephone + "\"," +
                                "\"informationSpanish\":\"" + organization.informationSpanish + "\"," +
                                "\"informationEnglish\":\"" + organization.informationEnglish + "\"," +
                                "\"informationFrench\":\"" + organization.informationFrench + "\"," +
                                "\"informationBasque\":\"" + organization.informationBasque + "\"," +
                                "\"informationCatalan\":\"" + organization.informationCatalan + "\"," +
                                "\"informationDutch\":\"" + organization.informationDutch + "\"," +
                                "\"informationGalician\":\"" + organization.informationGalician + "\"," +
                                "\"informationGerman\":\"" + organization.informationGerman + "\"," +
                                "\"informationItalian\":\"" + organization.informationItalian + "\"," +
                                "\"informationPortuguese\":\"" + organization.informationPortuguese + "\"," +
                                "\"profilePhoto\":\"" + organization.profilePhoto + "\"}";
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
        /// Gets all evaluated organizations
        /// </summary>
        /// <returns>Evaluated organization list</returns>
        [HttpGet("allEvaluated")]
        [AllowAnonymous]
        public IActionResult GetAllEvaluatedOrganizations()
        {
            try
            {
                var organizations = _context.Organizations.Where(o => o.orgType == "EVALUATED").ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var organization in organizations)
                {
                    String rg = "{\"idOrganization\":\"" + organization.idOrganization + "\"," +
                                "\"orgType\":\"" + organization.orgType + "\"," +
                                "\"illness\":\"" + organization.illness + "\"," +
                                "\"nameOrg\":\"" + organization.nameOrg + "\"," +
                                "\"idAddress\":\"" + organization.idAddress + "\"," +
                                "\"email\":\"" + organization.email + "\"," +
                                "\"telephone\":\"" + organization.telephone + "\"," +
                                "\"informationSpanish\":\"" + organization.informationSpanish + "\"," +
                                "\"informationEnglish\":\"" + organization.informationEnglish + "\"," +
                                "\"informationFrench\":\"" + organization.informationFrench + "\"," +
                                "\"informationBasque\":\"" + organization.informationBasque + "\"," +
                                "\"informationCatalan\":\"" + organization.informationCatalan + "\"," +
                                "\"informationDutch\":\"" + organization.informationDutch + "\"," +
                                "\"informationGalician\":\"" + organization.informationGalician + "\"," +
                                "\"informationGerman\":\"" + organization.informationGerman + "\"," +
                                "\"informationItalian\":\"" + organization.informationItalian + "\"," +
                                "\"informationPortuguese\":\"" + organization.informationPortuguese + "\"," +
                                "\"profilePhoto\":\"" + organization.profilePhoto + "\"}";
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
        /// Gets all evaluator organizations
        /// </summary>
        /// <returns>Evaluator organization list</returns>
        [HttpGet("allEvaluator")]
        [AllowAnonymous]
        public IActionResult GetAllEvaluatorOrganizations()
        {
            try
            {
                var organizations = _context.Organizations.Where(o => o.orgType == "EVALUATOR").ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var organization in organizations)
                {
                    String rg = "{\"idOrganization\":\"" + organization.idOrganization + "\"," +
                                "\"orgType\":\"" + organization.orgType + "\"," +
                                "\"illness\":\"" + organization.illness + "\"," +
                                "\"nameOrg\":\"" + organization.nameOrg + "\"," +
                                "\"idAddress\":\"" + organization.idAddress + "\"," +
                                "\"email\":\"" + organization.email + "\"," +
                                "\"telephone\":\"" + organization.telephone + "\"," +
                                "\"informationSpanish\":\"" + organization.informationSpanish + "\"," +
                                "\"informationEnglish\":\"" + organization.informationEnglish + "\"," +
                                "\"informationFrench\":\"" + organization.informationFrench + "\"," +
                                "\"informationBasque\":\"" + organization.informationBasque + "\"," +
                                "\"informationCatalan\":\"" + organization.informationCatalan + "\"," +
                                "\"informationDutch\":\"" + organization.informationDutch + "\"," +
                                "\"informationGalician\":\"" + organization.informationGalician + "\"," +
                                "\"informationGerman\":\"" + organization.informationGerman + "\"," +
                                "\"informationItalian\":\"" + organization.informationItalian + "\"," +
                                "\"informationPortuguese\":\"" + organization.informationPortuguese + "\"," +
                                "\"profilePhoto\":\"" + organization.profilePhoto + "\"}";
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
        /// Method that obtains an organization from the database
        /// </summary>
        /// <param name="id">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <returns>Organization if success, null if not</returns>
        [HttpGet("get")]
        [Authorize]
        public ActionResult<Organization> Get([FromQuery] int id,[FromQuery] string orgType,[FromQuery] string illness, [FromHeader] string Authorization)
        {
            try
            {
                var organization = _context.Organizations.FirstOrDefault(o => o.idOrganization == id && o.orgType == orgType && o.illness == illness);

                if (organization == null)
                    return NotFound();

                return organization;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }


        /// <summary>
        /// Method that appends an organization to the database
        /// </summary>
        /// <param name="organization">Organization</param>
        /// <returns>Organization if success, null if not</returns>
        [HttpPost]
        [Authorize(Policy = "Administrator")]
        public IActionResult Create([FromBody] Organization organization, [FromHeader] string Authorization)
        {
            try
            {
                _context.Organizations.Add(organization);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new { id = organization.idOrganization, orgType = organization.orgType, illness = organization.illness }, organization);

            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that updates an organization
        /// </summary>
        /// <param name="id">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <param name="organization">Organization</param>
        /// <returns>Organization if success, null if not</returns>
        [HttpPut]
        [Authorize(Policy = "Administrator")]
        public IActionResult Update([FromQuery] int id, [FromQuery] string orgType, [FromQuery] string illness, [FromBody] Organization organization, [FromHeader] string Authorization)
        {
            try
            {
                if (id != organization.idOrganization || orgType != organization.orgType || illness != organization.illness)
                    return BadRequest();

                var existingOrganization = _context.Organizations.FirstOrDefault(o => o.idOrganization == id && o.orgType == orgType && o.illness == illness);
                if (existingOrganization is null)
                    return NotFound();

                existingOrganization.idOrganization = id;
                existingOrganization.orgType = orgType;
                existingOrganization.illness = illness;
                existingOrganization.nameOrg = organization.nameOrg;
                existingOrganization.idAddress = organization.idAddress;
                existingOrganization.email = organization.email;
                existingOrganization.telephone = organization.telephone;
                existingOrganization.informationSpanish = organization.informationSpanish;
                existingOrganization.informationEnglish = organization.informationEnglish;
                existingOrganization.informationFrench = organization.informationFrench;
                existingOrganization.informationBasque = organization.informationBasque;
                existingOrganization.informationCatalan = organization.informationCatalan;
                existingOrganization.informationDutch = organization.informationDutch;
                existingOrganization.informationGalician = organization.informationGalician;
                existingOrganization.informationGerman = organization.informationGerman;
                existingOrganization.informationItalian = organization.informationItalian;
                existingOrganization.informationPortuguese = organization.informationPortuguese;
                existingOrganization.profilePhoto = organization.profilePhoto;
                _context.SaveChanges();

                return Ok(existingOrganization);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that deletes an organization
        /// </summary>
        /// <param name="id">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <returns>Organization if success, null if not</returns>
        [HttpDelete]
        [Authorize(Policy = "Administrator")]
        public IActionResult Delete([FromQuery] int id, [FromQuery] string orgType, [FromQuery] string illness, [FromHeader] string Authorization)
        {
            try
            {
                var organization = _context.Organizations.FirstOrDefault(o => o.idOrganization == id && o.orgType == orgType && o.illness == illness);

                if (organization is null)
                    return NotFound();

                _context.Organizations.Remove(organization);
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
