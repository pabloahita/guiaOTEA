using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Data.SqlClient;
using System.Net;
using System.Security.Policy;

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

        /// <summary>
        /// Method that obtains all the centers
        /// </summary>
        /// <returns>Centers list</returns>
        [HttpGet("all")]
        public IActionResult GetAll() {
            try
            {

                var centers = _context.Centers.ToList();
                return Ok(centers);
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
        public IActionResult GetAllByOrganization([FromQuery] int idOrganization,[FromQuery] string orgType,[FromQuery] string illness) {
            try
            {

                var centers = _context.Centers.Where(c => c.idOrganization == idOrganization && c.orgType == orgType && c.illness == illness).ToList();
                return Ok(centers);
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
        public ActionResult<Center> Get([FromQuery] int idOrganization, [FromQuery] string orgType, [FromQuery] string illness,[FromQuery] int idCenter)
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
        public ActionResult<Center> Create([FromBody] Center center)
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
        public ActionResult<Center> Update([FromQuery] int idOrganization, [FromQuery] string orgType, [FromQuery] string illness, [FromQuery] int idCenter,[FromBody] Center center)
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
        public ActionResult<Center> Delete([FromQuery] int idOrganization, [FromQuery] string orgType, [FromQuery] string illness, [FromQuery] int idCenter)
        {
            try
            {
                var center = _context.Centers.FirstOrDefault(c => c.idOrganization == idOrganization && c.orgType == orgType && c.illness == illness && c.idCenter == idCenter);

                if (center is null)
                    return NotFound();

                _context.Centers.Remove(center);
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

