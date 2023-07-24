using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Ambits")]
    public class AmbitsController:ControllerBase
    {
        private readonly DatabaseContext _context;

        public AmbitsController(DatabaseContext context)
        {
            _context = context;
        }

        // GET all action
        [HttpGet]
        public IActionResult GetAll()
        {
            var ambits = _context.Ambits.ToList();
            return Ok(ambits);
        }

        // GET by ID AND ORGTYPE action

        [HttpGet("get::{id}")]
        public ActionResult<Ambit> Get(int id)
        {
            var ambit = _context.Ambits.FirstOrDefault(a => a.idAmbit==id);

            if (ambit == null)
                return NotFound();

            return ambit;
        }

        // POST action
        [HttpPost]
        public IActionResult Create([FromBody] Ambit ambit)
        {
            _context.Ambits.Add(ambit);
            _context.SaveChanges();
            return CreatedAtAction(nameof(Get), new { idAmbit = ambit.idAmbit }, ambit);
        }


        // PUT action
        [HttpPut("upd::{id}")]
        public IActionResult Update(int idAmbit, Ambit ambit)
        {
            // This code will update the mesa and return a result
            if (idAmbit != ambit.idAmbit)
                return BadRequest();

            var existingambit = _context.Ambits.FirstOrDefault(a => a.idAmbit == idAmbit);
            if (existingambit is null)
                return NotFound();


            _context.Ambits.Update(ambit);
            _context.SaveChanges();

            return Ok(existingambit);
        }

        // DELETE action
        [HttpDelete("del::{id}")]
        public IActionResult Delete(int id)
        {
            // This code will delete the ambit and return a result
            var ambit = _context.Ambits.FirstOrDefault(a => a.idAmbit == id);

            if (ambit is null)
                return NotFound();

            _context.Ambits.Remove(ambit);
            _context.SaveChanges();

            return NoContent();
        }
    }
}
