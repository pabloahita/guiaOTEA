using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Data;

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
        public IActionResult GetAll()
        {
            try {
                var addresses = _context.Addresses.ToList();
                return Ok(addresses);
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
        public ActionResult<Address> Get([FromQuery] int id)
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
        public IActionResult Create([FromBody] Address address)
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
        public IActionResult Update([FromQuery] int idAddress, [FromBody] Address address)
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
        public IActionResult Delete([FromQuery] int id)
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
