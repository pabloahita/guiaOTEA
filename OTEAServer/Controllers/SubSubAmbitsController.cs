using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for subsubambits operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("SubSubAmbits")]
    public class SubSubAmbitsController : Controller
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public SubSubAmbitsController(DatabaseContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Method that obtains from the database all the subSubAmbits
        /// </summary>
        /// <returns>All subAmbits</returns>
        [HttpGet("all")]
        public IActionResult GetAll()
        {
            try
            {
                var subSubAmbits = _context.SubSubAmbits.ToList();
                return Ok(subSubAmbits);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains from the database all the subSubAmbits of an subAmbit
        /// </summary>
        /// <param name="idSubAmbit">Subambit identifier</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <returns>All subAmbits</returns>
        [HttpGet("allBySubAmbit")]
        public IActionResult GetAllBySubAmbit(int idSubAmbit, int idAmbit)
        {
            try
            {
                var subSubAmbits = _context.SubSubAmbits.Where(ss => ss.idSubAmbit == idSubAmbit && ss.idAmbit == idAmbit).ToList();
                return Ok(subSubAmbits);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains from the database a subSubAmbit using its identifier
        /// </summary>
        /// <param name="id">subSubAmbit identifier</param>
        /// <returns>The subSubAmbit if exists, null if not.</returns>

        [HttpGet("get")]
        public ActionResult<SubSubAmbit> Get([FromQuery] int id)
        {
            try
            {
                var subSubAmbit = _context.SubSubAmbits.FirstOrDefault(a => a.idSubSubAmbit == id);

                if (subSubAmbit == null)
                    return NotFound();

                return subSubAmbit;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that appends a new subSubAmbit to the database
        /// </summary>
        /// <param name="subSubAmbit">SubSubAmbit to append</param>
        /// <returns>SubSubAmbit append, null if not</returns>
        [HttpPost]
        public IActionResult Create([FromBody] SubSubAmbit subSubAmbit)
        {
            try
            {
                _context.SubSubAmbits.Add(subSubAmbit);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new { idSubSubAmbit = subSubAmbit.idSubSubAmbit }, subSubAmbit);

            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }


        /// <summary>
        /// Method that updates the subSubAmbit
        /// </summary>
        /// <param name="idSubSubAmbit">SubSubAmbit identifier</param>
        /// <param name="subSubAmbit">New subSubAmbit</param>
        /// <returns>subSubAmbit if was updated, null if not</returns>
        [HttpPut]
        public IActionResult Update([FromQuery] int idSubSubAmbit, [FromBody] SubSubAmbit subSubAmbit)
        {
            try
            {
                if (idSubSubAmbit != subSubAmbit.idSubSubAmbit)
                    return BadRequest();

                var existingSubSubAmbit = _context.SubSubAmbits.FirstOrDefault(a => a.idSubSubAmbit == idSubSubAmbit);
                if (existingSubSubAmbit is null)
                    return NotFound();


                existingSubSubAmbit.idSubSubAmbit = subSubAmbit.idSubSubAmbit;
                existingSubSubAmbit.idSubAmbit = subSubAmbit.idSubAmbit;
                existingSubSubAmbit.idAmbit = subSubAmbit.idAmbit;
                existingSubSubAmbit.descriptionEnglish = subSubAmbit.descriptionEnglish;
                existingSubSubAmbit.descriptionSpanish = subSubAmbit.descriptionSpanish;
                existingSubSubAmbit.descriptionFrench = subSubAmbit.descriptionFrench;
                existingSubSubAmbit.descriptionBasque = subSubAmbit.descriptionBasque;
                existingSubSubAmbit.descriptionCatalan = subSubAmbit.descriptionCatalan;
                existingSubSubAmbit.descriptionDutch = subSubAmbit.descriptionDutch;
                existingSubSubAmbit.descriptionGalician = subSubAmbit.descriptionGalician;
                existingSubSubAmbit.descriptionGerman = subSubAmbit.descriptionGerman;
                existingSubSubAmbit.descriptionItalian = subSubAmbit.descriptionItalian;
                existingSubSubAmbit.descriptionPortuguese = subSubAmbit.descriptionPortuguese;
                _context.SaveChanges();

                return Ok(existingSubSubAmbit);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that deletes the subSubAmbit using its identifier
        /// </summary>
        /// <param name="id">SubSubAmbit identifier</param>
        /// <returns>Deleted subSubAmbit if deletion was successful, null if not</returns>
        [HttpDelete]
        public IActionResult Delete([FromQuery] int id)
        {
            try
            {
                var subSubAmbit = _context.SubSubAmbits.FirstOrDefault(a => a.idSubSubAmbit == id);

                if (subSubAmbit is null)
                    return NotFound();

                _context.SubSubAmbits.Remove(subSubAmbit);
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
