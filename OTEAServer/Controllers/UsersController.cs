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
        [HttpGet]
        public IActionResult GetAll()
        {
            var users = _context.Users.ToList();
            return Ok(users);
        }


        //GET all by user type
        [HttpGet("getAllByType::userType={userType}")]
        public IActionResult GetAllByType(string userType)
        {
            var users = _context.Users.Where(u => u.userType == userType).ToList();
            return Ok(users);
        }

        //GET all organization users by organization type
        [HttpGet("getAllByOrg::idOrganization={idOrganization}:orgType={orgType}:illness={illness}")]
        public IActionResult GetAllOrgUsersByOrganization(int idOrganization, string orgType, string illness)
        {
            var users = _context.Users.Where(u => u.idOrganization==idOrganization && u.organizationType==orgType && u.illness==illness).ToList();
            return Ok(users);
        }

        // GET by EMAIL action

        [HttpGet("get::email={email}")]
        public ActionResult<User> Get(string email)
        {
            var user = _context.Users.FirstOrDefault(u => u.emailUser == email);

            if (user == null)
                return NotFound();

            return user;
        }

        //GET for LOGIN
        [HttpGet("login::email={email}:password={password}")]
        public ActionResult<User> GetForLogin(string email,string password)
        {
            var user = _context.Users.FirstOrDefault(u => u.emailUser == email && u.passwordUser==password);

            if (user == null)
                return NotFound();

            return user;
        }

        // GET by EMAIL and USER TYPE action

        [HttpGet("getByType::email={email}:userType={userType}")]
        public ActionResult<User> GetByType(string email, string userType)
        {
            var user = _context.Users.FirstOrDefault(u => u.emailUser == email && u.userType==userType);

            if (user == null)
                return NotFound();

            return user;
        }

        // GET by EMAIL and ORGANIZATION action

        [HttpGet("getByOrg::email={email}:idOrganization={idOrganization}:orgType={orgType}:illness={illness}")]
        public ActionResult<User> GetOrgUserByOrganization(string email, int idOrganization, string orgType, string illness)
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
        [HttpPut("upd::email={email}")]
        public IActionResult Update(string email, [FromBody] User user)
        {
            if (email != user.emailUser)
                return BadRequest();

            var existingUser = _context.Users.FirstOrDefault(u => u.emailUser == email);
            if (existingUser is null)
                return NotFound();

            _context.Users.Update(user);
            _context.SaveChanges();

            return Ok(existingUser);
        }

        // DELETE action
        [HttpDelete("del::email={email}")]
        public IActionResult Delete(string email)
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
