using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Sessions")]
    public class SessionsController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public SessionsController(DatabaseContext context)
        {
            _context = context;
        }

        [HttpGet("all")]
        public IActionResult GetAll()
        {
            var sessions = _context.Sessions.ToList();
            return Ok(sessions);
        }

        [HttpGet("get")]
        public ActionResult<Session> Get([FromQuery] string sessionToken)
        {
            var session = _context.Sessions.FirstOrDefault(s => s.sessionToken == sessionToken);

            if (session == null)
                return NotFound();

            return session;
        }

        [HttpPost]
        public IActionResult Create([FromBody] Session session)
        {
            _context.Sessions.Add(session);
            _context.SaveChanges();
            return CreatedAtAction(nameof(Get), new { sessionToken = session.sessionToken }, session);
        }

        
        [HttpPut]

        public IActionResult Update([FromQuery] string sessionToken, [FromBody] Session session){
            if (sessionToken!=session.sessionToken)
                return BadRequest();
            var existingSession=_context.Sessions.FirstOrDefault(s => s.sessionToken==sessionToken);
            if(existingSession is null)
                return NotFound();
            
            existingSession.sessionToken=sessionToken;
            existingSession.email=session.email;
            _context.SaveChanges();

            return Ok(existingSession);
        }

        [HttpDelete]
        public IActionResult Delete([FromQuery] string sessionToken)
        {
            var existingSession=_context.Sessions.FirstOrDefault(s => s.sessionToken==sessionToken);
            if(existingSession is null)
                return NotFound();
            
            _context.Sessions.Remove(existingSession);
            _context.SaveChanges();

            return NoContent();
        }
    }
}