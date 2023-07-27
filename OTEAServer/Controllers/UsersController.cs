using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Users")]
    public class UsersController : ControllerBase
    {
        /*private readonly ILogger<UsersController> _logger;
        private readonly UsersService _usersService;
        */
        private readonly DatabaseContext _context;
        public UsersController(DatabaseContext context)
        {
            _context = context;
        }

        
        // GET all action
        [HttpGet("all")]
        public IActionResult GetAll()
        {
            var users = _context.Users.ToList();
            return Ok(users);
        }


        //GET all by user type
        [HttpGet("allByType")]
        public IActionResult GetAllByType([FromQuery] string userType)
        {
            var users = _context.Users.Where(u => u.userType == userType).ToList();
            return Ok(users);
        }

        //GET all organization users by organization type
        [HttpGet("allByOrg")]
        public IActionResult GetAllOrgUsersByOrganization([FromQuery] int idOrganization, [FromQuery] string orgType, [FromQuery] string illness)
        {
            var users = _context.Users.Where(u => u.idOrganization==idOrganization && u.organizationType==orgType && u.illness==illness).ToList();
            return Ok(users);
        }

        // GET by EMAIL action

        [HttpGet("get")]
        public ActionResult<User> Get([FromQuery] string email)
        {
            var user = _context.Users.FirstOrDefault(u => u.emailUser == email);

            if (user == null)
                return NotFound();

            return user;
        }

        //GET for LOGIN
        [HttpGet("login")]
        public ActionResult<User> GetForLogin([FromQuery] string email,[FromQuery] string password)
        {
            var user = _context.Users.FirstOrDefault(u => u.emailUser == email && u.passwordUser==password);

            if (user == null)
                return NotFound();

            return user;
        }

        // GET by EMAIL and USER TYPE action

        [HttpGet("type")]
        public ActionResult<User> GetByType([FromQuery] string email, [FromQuery] string userType)
        {
            var user = _context.Users.FirstOrDefault(u => u.emailUser == email && u.userType==userType);

            if (user == null)
                return NotFound();

            return user;
        }

        // GET by EMAIL and ORGANIZATION action

        [HttpGet("org")]
        public ActionResult<User> GetOrgUserByOrganization([FromQuery] string email, [FromQuery] int idOrganization, [FromQuery] string orgType, [FromQuery] string illness)
        {
            var user = _context.Users.FirstOrDefault(u => u.emailUser == email && u.idOrganization == idOrganization && u.organizationType == orgType && u.illness == illness);

            if (user == null)
                return NotFound();

            return user;
        }

        // POST action
        [HttpPost]
        public IActionResult Create([FromBody] User user)
        {
            _context.Users.Add(user);
            _context.SaveChanges();
            return CreatedAtAction(nameof(Get), new { email = user.emailUser}, user);
        }

        // PUT action
        [HttpPut]
        public IActionResult Update([FromQuery] string email, [FromBody] User user)
        {
            if (email != user.emailUser)
                return BadRequest();

            var existingUser = _context.Users.FirstOrDefault(u => u.emailUser == email);
            if (existingUser is null)
                return NotFound();

            existingUser.emailUser = email;
            existingUser.userType = user.userType;
            existingUser.first_name = user.first_name;
            existingUser.last_name = user.last_name;
            existingUser.passwordUser = user.passwordUser;
            existingUser.telephone = user.telephone;
            existingUser.idOrganization = user.idOrganization;
            existingUser.organizationType = user.organizationType;
            existingUser.illness = user.illness;
            _context.SaveChanges();

            return Ok(existingUser);
        }

        // DELETE action
        [HttpDelete]
        public IActionResult Delete([FromQuery] string email)
        {
            // This code will delete the user and return a result
            var user = _context.Users.FirstOrDefault(u => u.emailUser == email);

            if (user is null)
                return NotFound();

            _context.Users.Remove(user);
            _context.SaveChanges();

            return NoContent();
        }
    }
}
