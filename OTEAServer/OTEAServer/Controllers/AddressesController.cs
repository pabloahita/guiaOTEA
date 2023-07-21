using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Data;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Addresses")]
    public class AddressesController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public AddressesController(DatabaseContext context)
        {
            _context = context;
        }
        // GET all action
        [HttpGet]
        public IActionResult GetAll()
        {
            var addresses = _context.Addresses.ToList();
            return Ok(addresses);
        }

        // GET by ID AND ORGTYPE action

        [HttpGet("get::{id}")]
        public ActionResult<Address> Get(int id)
        {
            var address = _context.Addresses.FirstOrDefault(a => a.idAddress == id);

            if (address == null)
                return NotFound();

            return address;
        }

        // POST action
        [HttpPost]
        public IActionResult Create([FromBody] Address address)
        {
            _context.Addresses.Add(address);
            _context.SaveChanges();
            return CreatedAtAction(nameof(Get), new { idAddress = address.idAddress }, address);
        }


        // PUT action
        [HttpPut("upd::{id}")]
        public IActionResult Update(int idAddress, Address address)
        {
            // This code will update the mesa and return a result
            if (idAddress != address.idAddress)
                return BadRequest();

            var existingAddress = _context.Addresses.FirstOrDefault(a => a.idAddress == idAddress);
            if (existingAddress is null)
                return NotFound();

            existingAddress.idAddress = idAddress;
            existingAddress.addressName = address.addressName;
            existingAddress.zipCode = address.zipCode;
            existingAddress.idCity= address.idCity;
            existingAddress.idProvince = address.idProvince;
            existingAddress.idRegion = address.idRegion;
            existingAddress.idCountry=address.idCountry;
            existingAddress.nameCity= address.nameCity;
            existingAddress.nameProvince=address.nameProvince;
            existingAddress.nameRegion = address.nameRegion;

            _context.SaveChanges();

            return Ok(existingAddress);
        }

        // DELETE action
        [HttpDelete("del::{id}")]
        public IActionResult Delete(int id)
        {
            // This code will delete the address and return a result
            var address = _context.Addresses.FirstOrDefault(a => a.idAddress == id);

            if (address is null)
                return NotFound();

            _context.Addresses.Remove(address);
            _context.SaveChanges();

            return NoContent();
        }
    }
}
