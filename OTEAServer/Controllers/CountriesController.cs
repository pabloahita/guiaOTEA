using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Countries")]
    public class CountriesController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public CountriesController(DatabaseContext context)
        {
            _context = context;
        }

        // GET all by country action
        [HttpGet("all")]
        public IActionResult GetAll([FromQuery] string language)
        {
            /*var countries = _context.Countries.ToList();
            return Ok(countries);*/

            IQueryable<Country> query = _context.Countries.AsQueryable();

            switch(language){
                case "es": query=query.OrderByDescending(c=>c.idCountry=="ESP")
                                .ThenByDescending(c=>c.idCountry=="AND")
                                .ThenByDescending(c=>c.idCountry=="USA")
                                .ThenByDescending(c=>c.idCountry=="ARG")
                                .ThenByDescending(c=>c.idCountry=="BOL")
                                .ThenByDescending(c=>c.idCountry=="CHL")
                                .ThenByDescending(c=>c.idCountry=="COL")
                                .ThenByDescending(c=>c.idCountry=="CRI")
                                .ThenByDescending(c=>c.idCountry=="CUB")
                                .ThenByDescending(c=>c.idCountry=="ECU")
                                .ThenByDescending(c=>c.idCountry=="SLV")
                                .ThenByDescending(c=>c.idCountry=="GUA")
                                .ThenByDescending(c=>c.idCountry=="GNQ")
                                .ThenByDescending(c=>c.idCountry=="HND")
                                .ThenByDescending(c=>c.idCountry=="MEX")
                                .ThenByDescending(c=>c.idCountry=="NIC")
                                .ThenByDescending(c=>c.idCountry=="PAN")
                                .ThenByDescending(c=>c.idCountry=="PRY")
                                .ThenByDescending(c=>c.idCountry=="PER")
                                .ThenByDescending(c=>c.idCountry=="PRI")
                                .ThenByDescending(c=>c.idCountry=="DOM")
                                .ThenByDescending(c=>c.idCountry=="URY")
                                .ThenByDescending(c=>c.idCountry=="VEN")
                                .ThenBy(c=>c.nameSpanish);break;
                case "fr": query=query..OrderByDescending(c=>c.idCountry=="FRA")
                                .ThenByDescending(c=>c.idCountry=="MCO")
                                .ThenByDescending(c=>c.idCountry=="BEL")
                                .ThenByDescending(c=>c.idCountry=="CHE")
                                .ThenByDescending(c=>c.idCountry=="LUX")
                                .ThenByDescending(c=>c.idCountry=="CAN")
                                .ThenByDescending(c=>c.idCountry=="MAR")
                                .ThenByDescending(c=>c.idCountry=="DZA")
                                .ThenBy(c=>c.nameFrench);break;
                /*case "eu": query=query.OrderBy(c=>c.nameBasque);break; Poner cuando se implementen más idiomas en la base de datos
                case "ca": query=query.OrderBy(c=>c.nameCatalan);break;
                case "gl": query=query.OrderBy(c=>c.nameGalician);break;
                case "pt": query=query.OrderBy(c=>c.namePortuguese);break;
                case "de": query=query.OrderBy(c=>c.nameDeustch);break;
                case "it": query=query.OrderBy(c=>c.nameItalian);break;
                case "nl": query=query.OrderBy(c=>c.nameDutch);break;*/
                default: query=query..OrderByDescending(c=>c.idCountry=="USA")
                                .ThenByDescending(c=>c.idCountry=="GBR")
                                .ThenByDescending(c=>c.idCountry=="AUS")
                                .ThenByDescending(c=>c.idCountry=="CAN")
                                .ThenByDescending(c=>c.idCountry=="GIB")
                                .ThenByDescending(c=>c.idCountry=="IND")
                                .ThenByDescending(c=>c.idCountry=="IRL")
                                .ThenByDescending(c=>c.idCountry=="NZF")
                                .ThenByDescending(c=>c.idCountry=="ZAF")
                                .ThenBy(c=>c.nameEnglish);break;
            }

            
            return Ok(query.ToList());

        }

        // GET COUNTRY by IDS action

        [HttpGet("get")]
        public ActionResult<Country> Get([FromQuery] string idCountry)
        {
            var country = _context.Countries.FirstOrDefault(c=>c.idCountry == idCountry);

            if (country == null)
                return NotFound();

            return country;
        }
    }
}
