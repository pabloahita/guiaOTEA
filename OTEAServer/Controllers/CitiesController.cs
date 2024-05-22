using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Text.Json;

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
        /// <param name="idCountry"></param>
        /// <returns>Cities of the province</returns>
        [HttpGet("allByProvince")]
        public IActionResult GetAllByProvince([FromQuery] int idProvince, [FromQuery] int idRegion, [FromQuery] string idCountry, [FromQuery] string language)
        {
            try
            {
                IQueryable<City> query = _context.Cities
                    .Where(c => c.idProvince == idProvince && c.idRegion == idRegion && c.idCountry == idCountry)
                    .AsQueryable();
                switch (language)
                {
                    case "es":
                        query = query.OrderBy(c => c.nameSpanish); break;
                    case "fr":
                        query = query.OrderBy(c => c.nameFrench); break;
                    case "eu":
                        query = query.OrderBy(c => c.nameBasque); break;
                    case "ca":
                        query = query.OrderBy(c => c.nameCatalan); break;
                    case "gl":
                        query = query.OrderBy(c => c.nameGalician); break;
                    case "pt":
                        query = query.OrderBy(c => c.namePortuguese); break;
                    case "de":
                        query = query.OrderBy(c => c.nameGerman); break;
                    case "it":
                        query = query.OrderBy(c => c.nameItalian); break;
                    case "nl":
                        query = query.OrderBy(c => c.nameDutch); break;
                    default:
                        query = query.OrderBy(c => c.nameEnglish); break;
                }
                var cities = query.ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach(City city in cities){
                    String rg = "{\"idCity\":\"" + city.idCity + "\"," +
                            "\"nameSpanish\":\"" + city.nameSpanish + "\"," +
                            "\"nameEnglish\":\"" + city.nameEnglish + "\"," +
                            "\"nameFrench\":\"" + city.nameFrench + "\"," +
                            "\"nameBasque\":\"" + city.nameBasque + "\"," +
                            "\"nameCatalan\":\"" + city.nameCatalan + "\"," +
                            "\"nameDutch\":\"" + city.nameDutch + "\"," +
                            "\"nameGalician\":\"" + city.nameGalician + "\"," +
                            "\"nameGerman\":\"" + city.nameGerman + "\"," +
                            "\"nameItalian\":\"" + city.nameItalian + "\"," +
                            "\"namePortuguese\":\"" + city.namePortuguese + "\"}";
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
        /// Method that obtains all cities
        /// </summary>
        /// <returns>City list</returns>
        
    }
}
