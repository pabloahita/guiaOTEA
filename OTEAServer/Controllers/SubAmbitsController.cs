using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{

    /// <summary>
    /// Controller class for subambits operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("SubAmbits")]
    public class SubAmbitsController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public SubAmbitsController(DatabaseContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Method that obtains from the database all the subAmbits
        /// </summary>
        /// <returns>All subAmbits</returns>
        [HttpGet("all")]
        [Authorize]
        public IActionResult GetAll([FromHeader] string Authorization)
        {
            try
            {
                var subAmbits = _context.SubAmbits.ToList();
                return Ok(subAmbits);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains from the database all the subAmbits
        /// </summary>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <returns>All subAmbits</returns>
        [HttpGet("allByAmbit")]
        [Authorize]
        public IActionResult GetAllByAmbit(int idAmbit, [FromHeader] string Authorization)
        {
            try
            {
                var subAmbits = _context.SubAmbits.Where(s => s.idAmbit == idAmbit).ToList();
                return Ok(subAmbits);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains from the database a subAmbit using its identifier
        /// </summary>
        /// <param name="id">subAmbit identifier</param>
        /// <returns>The subAmbit if exists, null if not.</returns>
        [HttpGet("get")]
        [Authorize]
        public ActionResult<SubAmbit> Get([FromQuery] int id, [FromHeader] string Authorization)
        {
            try
            {
                var subAmbit = _context.SubAmbits.FirstOrDefault(a => a.idSubAmbit == id);

                if (subAmbit == null)
                    return NotFound();

                return subAmbit;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that appends a new subAmbit to the database
        /// </summary>
        /// <param name="subAmbit">SubAmbit to append</param>
        /// <returns>SubAmbit append, null if not</returns>
        [HttpPost]
        [Authorize(Policy = "Administrator")]
        public IActionResult Create([FromBody] SubAmbit subAmbit, [FromHeader] string Authorization)
        {
            try
            {
                _context.SubAmbits.Add(subAmbit);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new { idSubAmbit = subAmbit.idSubAmbit }, subAmbit);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }


        /// <summary>
        /// Method that updates the subAmbit
        /// </summary>
        /// <param name="idSubAmbit">SubAmbit identifier</param>
        /// <param name="subAmbit">new subAmbit</param>
        /// <returns>subAmbit if was updated, null if not</returns>
        [HttpPut]
        [Authorize(Policy = "Administrator")]
        public IActionResult Update([FromQuery] int idSubAmbit, [FromBody] SubAmbit subAmbit, [FromHeader] string Authorization)
        {
            try
            {
                if (idSubAmbit != subAmbit.idSubAmbit)
                    return BadRequest();

                var existingSubAmbit = _context.SubAmbits.FirstOrDefault(a => a.idSubAmbit == idSubAmbit);
                if (existingSubAmbit is null)
                    return NotFound();


                existingSubAmbit.idSubAmbit = subAmbit.idSubAmbit;
                existingSubAmbit.idAmbit = subAmbit.idAmbit;
                existingSubAmbit.descriptionEnglish = subAmbit.descriptionEnglish;
                existingSubAmbit.descriptionSpanish = subAmbit.descriptionSpanish;
                existingSubAmbit.descriptionFrench = subAmbit.descriptionFrench;
                existingSubAmbit.descriptionBasque = subAmbit.descriptionBasque;
                existingSubAmbit.descriptionCatalan = subAmbit.descriptionCatalan;
                existingSubAmbit.descriptionDutch = subAmbit.descriptionDutch;
                existingSubAmbit.descriptionGalician = subAmbit.descriptionGalician;
                existingSubAmbit.descriptionGerman = subAmbit.descriptionGerman;
                existingSubAmbit.descriptionItalian = subAmbit.descriptionItalian;
                existingSubAmbit.descriptionPortuguese = subAmbit.descriptionPortuguese;
                _context.SaveChanges();

                return Ok(existingSubAmbit);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that deletes the subAmbit using its identifier
        /// </summary>
        /// <param name="id">SubAmbit identifier</param>
        /// <returns>Deleted subAmbit if deletion was successful, null if not</returns>
        [HttpDelete]
        [Authorize(Policy = "Administrator")]
        public IActionResult Delete([FromQuery] int id, [FromHeader] string Authorization)
        {
            try
            {
                var subAmbit = _context.SubAmbits.FirstOrDefault(a => a.idSubAmbit == id);

                if (subAmbit is null)
                    return NotFound();

                _context.SubAmbits.Remove(subAmbit);
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
