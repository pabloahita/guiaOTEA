using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Linq.Expressions;
using System.Text.Json;
using static OTEAServer.Controllers.CitiesController;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for provinces operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("Provinces")]
    public class ProvincesController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public ProvincesController(DatabaseContext context)
        {
            _context=context;
        }

        private class ProvinceDto
        {
            public int IdProvince { get; set; }
            public string Name { get; set; }
        }
        /// <summary>
        /// Method that obtains all provinces of a region
        /// </summary>
        /// <param name="idRegion">Region identifier</param>
        /// <param name="idCountry">Country identifier</param>
        /// <returns>Provinces list</returns>
        [HttpGet("allByRegion")]
        public IActionResult GetAllByRegion([FromQuery] int idRegion, [FromQuery] string idCountry, [FromQuery] string language)
        {
            try
            {
                IQueryable<Province> query = _context.Provinces
                    .Where(p => p.idRegion == idRegion && p.idCountry == idCountry)
                    .AsQueryable();

                Expression<Func<Province, ProvinceDto>> selector = p => new ProvinceDto
                {
                    IdProvince = p.idProvince,
                    Name = p.nameEnglish // Default value
                };
                switch (language)
                {
                    case "es":
                        selector = p => new ProvinceDto
                        {
                            IdProvince = p.idProvince,
                            Name = p.nameSpanish
                        };
                        query = query.OrderBy(p => p.nameSpanish); break;
                    case "fr":
                        selector = p => new ProvinceDto
                        {
                            IdProvince = p.idProvince,
                            Name = p.nameFrench
                        };
                        query = query.OrderBy(p => p.nameFrench); break;
                    case "eu":
                        selector = p => new ProvinceDto
                        {
                            IdProvince = p.idProvince,
                            Name = p.nameBasque
                        };
                        query = query.OrderBy(p => p.nameBasque); break;
                    case "ca":
                        selector = p => new ProvinceDto
                        {
                            IdProvince = p.idProvince,
                            Name = p.nameCatalan
                        };
                        query = query.OrderBy(p => p.nameCatalan); break;
                    case "gl":
                        selector = p => new ProvinceDto
                        {
                            IdProvince = p.idProvince,
                            Name = p.nameGalician
                        };
                        query = query.OrderBy(p => p.nameGalician); break;
                    case "pt":
                        selector = p => new ProvinceDto
                        {
                            IdProvince = p.idProvince,
                            Name = p.namePortuguese
                        };
                        query = query.OrderBy(p => p.namePortuguese); break;
                    case "de":
                        selector = p => new ProvinceDto
                        {
                            IdProvince = p.idProvince,
                            Name = p.nameGerman
                        };
                        query = query.OrderBy(p => p.nameGerman); break;
                    case "it":
                        selector = p => new ProvinceDto
                        {
                            IdProvince = p.idProvince,
                            Name = p.nameItalian
                        };
                        query = query.OrderBy(p => p.nameItalian); break;
                    case "nl":
                        selector = p => new ProvinceDto
                        {
                            IdProvince = p.idProvince,
                            Name = p.nameDutch
                        };
                        query = query.OrderBy(p => p.nameDutch); break;
                    default:
                        selector = p => new ProvinceDto
                        {
                            IdProvince = p.idProvince,
                            Name = p.nameEnglish // Default value
                        };
                        query = query.OrderBy(p => p.nameEnglish); break;
                }
                var provinces=query.Select(selector).ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (ProvinceDto province in provinces)
                {
                    String rg = "{\"idProvince\":\"" + province.IdProvince + "\"," +
                            "\"name\":\"" + province.Name + "\"}";
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
        /// Method that obtains a province from the database
        /// </summary>
        /// <param name="idProvince">Province identifier</param>
        /// <param name="idRegion">Region identifier</param>
        /// <param name="idCountry">Country identifier</param>
        /// <returns></returns>
        [HttpGet("get")]
        public ActionResult<Province> Get([FromQuery] int idProvince, [FromQuery] int idRegion, [FromQuery] string idCountry)
        {
            try
            {
                var province = _context.Provinces.FirstOrDefault(p => p.idProvince == idProvince && p.idRegion == idRegion && p.idCountry == idCountry);

                if (province == null)
                    return NotFound();

                return province;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

       
    }
}
