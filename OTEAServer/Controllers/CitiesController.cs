using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller for cities operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>


    [ApiController]
    [Route("Cities")]
    public class CitiesController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public CitiesController(DatabaseContext context)
        {
            _context=context;
        }

        /// <summary>
        /// Method that obtains from the database all the cities of a province
        /// </summary>
        /// <param name="idProvince"></param>
        /// <param name="idRegion"></param>
        /// <param name="idCountry"></param>
        /// <returns>Cities of the province</returns>
        [HttpGet("allByProvince")]
        public IActionResult GetAllByProvince([FromQuery] int idProvince, [FromQuery] int idRegion, [FromQuery] string idCountry)
        {
            try
            {
                var cities = _context.Cities.Where(c => c.idProvince == idProvince && c.idRegion == idRegion && c.idCountry == idCountry).ToList();
                return Ok(cities);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains a city from the database
        /// </summary>
        /// <param name="idCity">City identifier</param>
        /// <param name="idProvince">Province identifier</param>
        /// <param name="idRegion">Region identifier</param>
        /// <param name="idCountry">Country identifier</param>
        /// <returns>City if success, null if not</returns>
        [HttpGet("get")]
        public ActionResult<City> Get([FromQuery] int idCity, [FromQuery] int idProvince, [FromQuery] int idRegion, [FromQuery] string idCountry)
        {
            try
            {
                var city = _context.Cities.FirstOrDefault(c => c.idCity == idCity && c.idProvince == idProvince && c.idRegion == idRegion && c.idCountry == idCountry);

                if (city == null)
                    return NotFound();

                return city;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }

        }

        /// <summary>
        /// Method that obtains all cities, by pagination due of huge quantity of registries
        /// </summary>
        /// <param name="numPage">Page number</param>
        /// <param name="pageSize">Page size</param>
        /// <returns>City list</returns>
        
        [HttpGet("all")]
        public IActionResult GetAll([FromQuery] int numPage, [FromQuery] int pageSize)
        {
            try
            {
                var skip = (numPage - 1) * pageSize;
                var cities = _context.Cities.Skip(skip).Take(pageSize).ToList();
                return Ok(cities);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}
