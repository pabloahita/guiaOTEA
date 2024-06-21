using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Data;
using System.Net;
using System.Text.Json;

namespace OTEAServer.Controllers
{
    ///<summary>
    /// Controller class for address operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>


    [ApiController]
    [Route("Addresses")]
    public class AddressesController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;


        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public AddressesController(DatabaseContext context)
        {
            _context = context;
        }

        /// <summary>
        ///  Method that obtains from the database all the addresses
        /// </summary>
        /// <returns>Addresses list</returns>
        [HttpGet("all")]
        [Authorize]
        public IActionResult GetAll([FromHeader] string Authorization)
        {
            try {
                var addresses = _context.Addresses.ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (Address address in addresses)
                {
                    String rg = "{\"idAddress\":\"" + address.idAddress + "\"," +
                            "\"addressName\":\"" + address.addressName + "\"," +
                            "\"idCity\":\"" + address.idCity + "\"," +
                            "\"idProvince\":\"" + address.idProvince + "\"," +
                            "\"idRegion\":\"" + address.idRegion + "\"," +
                            "\"idCountry\":\"" + address.idCountry + "\"," +
                            "\"nameCity\":\"" + address.nameCity + "\"," +
                            "\"nameProvince\":\"" + address.nameProvince + "\"," +
                            "\"nameRegion\":\"" + address.nameRegion + "\"}";
                    result.Add(JsonDocument.Parse(rg));
                }
                return Ok(result);
            }
            catch (Exception ex) {
                return BadRequest(ex.Message);
            }
            
        }

        /// <summary>
        /// Method that obtains from the database the address using its identifier
        /// </summary>
        /// <param name="id">Address identifier</param>
        /// <returns>Address if is in the database, null if not</returns>
        [HttpGet("get")]
        [Authorize]
        public ActionResult<Address> Get([FromQuery] int id, [FromHeader] string Authorization)
        {
            try
            {
                var address = _context.Addresses.FirstOrDefault(a => a.idAddress == id);

                if (address == null)
                    return NotFound();

                return address;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
            
        }

        /// <summary>
        /// Method that appends the address to the database
        /// </summary>
        /// <param name="address">New address</param>
        /// <returns>Address if was append, null if not</returns>
        [HttpPost]
        [Authorize]
        public IActionResult Create([FromBody] Address address, [FromHeader] string Authorization)
        {
            try
            {
                _context.Addresses.Add(address);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new { idAddress = address.idAddress }, address);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
            

        }


        /// <summary>
        /// Method that updates an address using its identifier
        /// </summary>
        /// <param name="idAddress">Address identifier</param>
        /// <param name="address">Address</param>
        /// <returns>Address if was updated, null if not</returns>
        [HttpPut]
        [Authorize]
        public IActionResult Update([FromQuery] int idAddress, [FromBody] Address address, [FromHeader] string Authorization)
        {
            try
            {
                if (idAddress != address.idAddress)
                    return BadRequest();

                var existingAddress = _context.Addresses.FirstOrDefault(a => a.idAddress == idAddress);
                if (existingAddress is null)
                    return NotFound();


                existingAddress.addressName = address.addressName;
                existingAddress.idCity = address.idCity;
                existingAddress.idProvince = address.idProvince;
                existingAddress.idRegion = address.idRegion;
                existingAddress.idCountry = address.idCountry;
                existingAddress.nameCity = address.nameCity;
                existingAddress.nameProvince = address.nameProvince;
                existingAddress.nameRegion = address.nameRegion;

                _context.SaveChanges();

                return Ok(existingAddress);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }

           
        }

        /// <summary>
        /// Method that deletes the address using its identifier
        /// </summary>
        /// <param name="id">Address identifier</param>
        /// <returns>Address if was deleted, null if not</returns>
        [HttpDelete]
        [Authorize]
        public IActionResult Delete([FromQuery] int id, [FromHeader] string Authorization)
        {
            try
            {
                var address = _context.Addresses.FirstOrDefault(a => a.idAddress == id);

                if (address is null)
                    return NotFound();

                _context.Addresses.Remove(address);
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
