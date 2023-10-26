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
                case "fr": query=query.OrderByDescending(c=>c.idCountry=="FRA")
                                .ThenByDescending(c=>c.idCountry=="MCO")
                                .ThenByDescending(c=>c.idCountry=="BEL")
                                .ThenByDescending(c=>c.idCountry=="CHE")
                                .ThenByDescending(c=>c.idCountry=="LUX")
                                .ThenByDescending(c=>c.idCountry=="CAN")
                                .ThenByDescending(c=>c.idCountry=="MAR")
                                .ThenByDescending(c=>c.idCountry=="DZA")
                                .ThenBy(c=>c.nameFrench);break;
                case "eu": query=query.OrderByDescending(c=>c.idCountry=="ESP")
                                .ThenByDescending(c=>c.idCountry=="FRA")
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
                                .ThenBy(c=>c.nameBasque);break; 
                case "ca": query=query.OrderByDescending(c=>c.idCountry=="ESP")
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
                                .ThenBy(c=>c.nameCatalan);break;
                case "gl": query=query.OrderByDescending(c=>c.idCountry=="ESP")
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
                                .ThenBy(c=>c.nameGalician);break;
                case "pt": query=query.OrderByDescending(c=>c.idCountry=="PRT")
                                .ThenByDescending(c=>c.idCountry=="BRA")
                                .ThenByDescending(c=>c.idCountry=="GNB")
                                .ThenByDescending(c=>c.idCountry=="MOZ")
                                .ThenByDescending(c=>c.idCountry=="STP")
                                .ThenByDescending(c=>c.idCountry=="TLS")
                                .ThenBy(c=>c.namePortuguese);break;
                case "de": query=query.OrderByDescending(c=>c.idCountry=="DEU")
                                .ThenByDescending(c=>c.idCountry=="AUT")
                                .ThenByDescending(c=>c.idCountry=="BEL")
                                .ThenByDescending(c=>c.idCountry=="LUX")
                                .ThenByDescending(c=>c.idCountry=="LIE")
                                .ThenByDescending(c=>c.idCountry=="CHE")
                                .ThenBy(c=>c.nameGerman);break;
                case "it": query=query.OrderByDescending(c=>c.idCountry=="ITA")
                                .ThenByDescending(c=>c.idCountry=="CHE")
                                .ThenByDescending(c=>c.idCountry=="SMR")
                                .ThenByDescending(c=>c.idCountry=="VAT")
                                .ThenBy(c=>c.nameItalian);break;
                case "nl": query=query.OrderByDescending(c=>c.idCountry=="NLD")
                                .ThenByDescending(c=>c.idCountry=="BEL")
                                .ThenByDescending(c=>c.idCountry=="FRA")
                                .ThenByDescending(c=>c.idCountry=="ABW")
                                .ThenByDescending(c=>c.idCountry=="SUR")
                                .ThenBy(c=>c.nameDutch);break;
                default: query=query.OrderByDescending(c=>c.idCountry=="USA")
                                .ThenByDescending(c=>c.idCountry=="GBR")
                                .ThenByDescending(c=>c.idCountry=="AUS")
                                .ThenByDescending(c=>c.idCountry=="CAN")
                                .ThenByDescending(c=>c.idCountry=="GIB")
                                .ThenByDescending(c=>c.idCountry=="IOT")
                                .ThenByDescending(c=>c.idCountry=="IND")
                                .ThenByDescending(c=>c.idCountry=="IRL")
                                .ThenByDescending(c=>c.idCountry=="NZF")
                                .ThenByDescending(c=>c.idCountry=="ZAF")
                                .ThenBy(c=>c.nameEnglish);break;
            }

            
            return Ok(query.ToList());

        }

        [HttpGet("countriesWithPhoneCode")]
        public IActionResult GetCountriesWithPhoneCode([FromQuery] string language)
        {
            /*var countries = _context.Countries.ToList();
            return Ok(countries);*/

            IQueryable<Country> query = _context.Countries.Where(c=>c.phone_code!=null).AsQueryable();

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
                case "fr": query=query.OrderByDescending(c=>c.idCountry=="FRA")
                                .ThenByDescending(c=>c.idCountry=="MCO")
                                .ThenByDescending(c=>c.idCountry=="BEL")
                                .ThenByDescending(c=>c.idCountry=="CHE")
                                .ThenByDescending(c=>c.idCountry=="LUX")
                                .ThenByDescending(c=>c.idCountry=="CAN")
                                .ThenByDescending(c=>c.idCountry=="MAR")
                                .ThenByDescending(c=>c.idCountry=="DZA")
                                .ThenBy(c=>c.nameFrench);break;
                case "eu": query=query.OrderByDescending(c=>c.idCountry=="ESP")
                                .ThenByDescending(c=>c.idCountry=="FRA")
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
                                .ThenBy(c=>c.nameBasque);break; 
                case "ca": query=query.OrderByDescending(c=>c.idCountry=="ESP")
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
                                .ThenBy(c=>c.nameCatalan);break;
                case "gl": query=query.OrderByDescending(c=>c.idCountry=="ESP")
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
                                .ThenBy(c=>c.nameGalician);break;
                case "pt": query=query.OrderByDescending(c=>c.idCountry=="PRT")
                                .ThenByDescending(c=>c.idCountry=="BRA")
                                .ThenByDescending(c=>c.idCountry=="GNB")
                                .ThenByDescending(c=>c.idCountry=="MOZ")
                                .ThenByDescending(c=>c.idCountry=="STP")
                                .ThenByDescending(c=>c.idCountry=="TLS")
                                .ThenBy(c=>c.namePortuguese);break;
                case "de": query=query.OrderByDescending(c=>c.idCountry=="DEU")
                                .ThenByDescending(c=>c.idCountry=="AUT")
                                .ThenByDescending(c=>c.idCountry=="BEL")
                                .ThenByDescending(c=>c.idCountry=="LUX")
                                .ThenByDescending(c=>c.idCountry=="LIE")
                                .ThenByDescending(c=>c.idCountry=="CHE")
                                .ThenBy(c=>c.nameGerman);break;
                case "it": query=query.OrderByDescending(c=>c.idCountry=="ITA")
                                .ThenByDescending(c=>c.idCountry=="CHE")
                                .ThenByDescending(c=>c.idCountry=="SMR")
                                .ThenByDescending(c=>c.idCountry=="VAT")
                                .ThenBy(c=>c.nameItalian);break;
                case "nl": query=query.OrderByDescending(c=>c.idCountry=="NLD")
                                .ThenByDescending(c=>c.idCountry=="BEL")
                                .ThenByDescending(c=>c.idCountry=="FRA")
                                .ThenByDescending(c=>c.idCountry=="ABW")
                                .ThenByDescending(c=>c.idCountry=="SUR")
                                .ThenBy(c=>c.nameDutch);break;
                default: query=query.OrderByDescending(c=>c.idCountry=="USA")
                                .ThenByDescending(c=>c.idCountry=="GBR")
                                .ThenByDescending(c=>c.idCountry=="AUS")
                                .ThenByDescending(c=>c.idCountry=="CAN")
                                .ThenByDescending(c=>c.idCountry=="GIB")
                                .ThenByDescending(c=>c.idCountry=="IOT")
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
