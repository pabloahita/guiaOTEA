using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Requests")]
    public class RequestsController : ControllerBase
    {

        private readonly DatabaseContext _context;

        public RequestsController(DatabaseContext context)
        {
            _context = context;
        }

        [HttpGet("all")]
        public IActionResult GetAll()
        {
            var requests = _context.Requests.ToList();
            return Ok(requests);
        }

        [HttpGet("get")]
        public ActionResult<Request> Get([FromQuery] string email)
        {
            var request = _context.Requests.FirstOrDefault(r => r.email == email);

            if (request == null)
                return NotFound();

            return request;
        }

        [HttpPost]
        public IActionResult Create([FromBody] Request request)
        {
            _context.Requests.Add(request);
            _context.SaveChanges();
            return CreatedAtAction(nameof(Get), new { email = request.email }, request);
        }

        
        [HttpPut]

        public IActionResult Update([FromQuery] string email, [FromBody] Request request){
            if (email!=request.email)
                return BadRequest();
            var existingRequest=_context.Requests.FirstOrDefault(r => r.email==email);
            if(existingRequest is null)
                return NotFound();
            
            existingRequest.email=email;
            existingRequest.statusReq=request.statusReq;
            existingRequest.tempPassword=request.tempPassword;
            _context.SaveChanges();

            return Ok(existingRequest);
        }

        [HttpDelete]
        public IActionResult Delete([FromQuery] string email)
        {
            var existingRequest=_context.Requests.FirstOrDefault(r => r.email==email);
            if(existingRequest is null)
                return NotFound();
            
            _context.Requests.Remove(existingRequest);
            _context.SaveChanges();

            return NoContent();
        }


        
    }
}