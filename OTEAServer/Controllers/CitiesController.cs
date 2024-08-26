using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Dynamic;
using System.Linq.Expressions;
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

        private class CityDto
        {
            public int IdCity { get; set; }
            public string Name { get; set; }
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
                var query = _context.Cities
                    .Where(c => c.idProvince == idProvince && c.idRegion == idRegion && c.idCountry == idCountry)
                    .AsQueryable();


                Expression<Func<City, CityDto>> selector = c => new CityDto
                {
                    IdCity = c.idCity,
                    Name = c.nameEnglish // Default value
                };
                switch (language)
                {
                    case "es":
                    selector = c => new CityDto
                    {
                        IdCity = c.idCity,
                        Name = c.nameSpanish
                    };
                    break;
                    case "fr":
                        selector = c => new CityDto
                        {
                            IdCity = c.idCity,
                            Name = c.nameFrench
                        };
                        break;
                    case "eu":
                        selector = c => new CityDto
                        {
                            IdCity = c.idCity,
                            Name = c.nameBasque
                        };
                        break;
                    case "ca":
                        selector = c => new CityDto
                        {
                            IdCity = c.idCity,
                            Name = c.nameCatalan
                        };
                        break;
                    case "gl":
                        selector = c => new CityDto
                        {
                            IdCity = c.idCity,
                            Name = c.nameGalician
                        };
                        break;
                    case "pt":
                        selector = c => new CityDto
                        {
                            IdCity = c.idCity,
                            Name = c.namePortuguese
                        };
                        break;
                    case "de":
                        selector = c => new CityDto
                        {
                            IdCity = c.idCity,
                            Name = c.nameGerman
                        };
                        break;
                    case "it":
                        selector = c => new CityDto
                        {
                            IdCity = c.idCity,
                            Name = c.nameItalian
                        };
                        break;
                    case "nl":
                        selector = c => new CityDto
                        {
                            IdCity = c.idCity,
                            Name = c.nameDutch
                        };
                        break;
                    default:
                        selector = c => new CityDto
                        {
                            IdCity = c.idCity,
                            Name = c.nameEnglish
                        };
                        break;
                }

                // Aplica la proyección y la ordenación
                var cities=query
                    .Select(selector)
                    .OrderBy(c => c.Name).ToList();


                List<JsonDocument> result = new List<JsonDocument>();
                foreach(CityDto city in cities){
                    String rg = "{\"idCity\":\"" + city.IdCity + "\", \"name\":\""+city.Name+"\"}";
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
