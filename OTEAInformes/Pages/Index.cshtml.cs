using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Logging;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Security.Cryptography;


namespace WebApplication1.Pages
{
    public class IndexModel : PageModel
    {
        private readonly ILogger<IndexModel> _logger;

        private readonly Session _session;

        public string ErrorMessage { get; set; }
        public string SuccessMessage { get; set; }

        public IndexModel(ILogger<IndexModel> logger, Session session)
        {
            _logger = logger;
            _session = session;
        }

        [BindProperty]
        public string Username { get; set; }

        [BindProperty]
        public string Password { get; set; }

        public void OnGet()
        {

        }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            var loginInfo = new
            {
                email = Username,
                password = GetSHA256Hash(Password)
            };

            var json = JsonSerializer.Serialize(loginInfo);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            var client = new HttpClient();
            var response = await client.PostAsync("https://guiaotea.azurewebsites.net/Users/login", content);

            if (response.IsSuccessStatusCode)
            {
                string responseBody = await response.Content.ReadAsStringAsync();

                var responseObject = JsonSerializer.Deserialize<JsonDocument>(responseBody);

                Session.createInstance(responseObject);
                
                
                // Handle successful login
                SuccessMessage = "Se ha completado el inicio de sesión.";
                return RedirectToPage("/BlobMenu");
            }
            else
            {
                ErrorMessage = "Credenciales o cuenta incorrectos.";
                return Page();
            }
        }

        public static string GetSHA256Hash(string input)
        {
            using (SHA256 sha256Hash = SHA256.Create())
            {
                // Convertir la cadena en bytes y calcular el hash
                byte[] bytes = sha256Hash.ComputeHash(Encoding.UTF8.GetBytes(input));

                // Convertir el arreglo de bytes a una cadena hexadecimal
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < bytes.Length; i++)
                {
                    builder.Append(bytes[i].ToString("x2"));
                }
                return builder.ToString();
            }
        }
    }
}
