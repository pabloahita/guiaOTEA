using OTEAServer.Models;
using System.Data.SqlClient;

namespace OTEAServer.Services
{
    public class CountriesService
    {
        private static IConfiguration _configuration;
        public CountriesService(IConfiguration configuration)
        {
            _configuration = configuration;
        }
        public List<Country> GetAll()
        {
            List<Country> CountriesList = new List<Country>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM COUNTRIES ORDER BY nameEnglish";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {

                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            CountriesList.Add(new Country(reader.GetString(0), reader.GetString(1), reader.GetString(2), reader.GetString(3)));
                        }
                    }
                }
            }
            return CountriesList;
        }

        public Country? Get(string idCountry)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM COUNTRIES WHERE idCountry=@IDCOUNTRY";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDCOUNTRY", idCountry);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new Country(reader.GetString(0),reader.GetString(1),reader.GetString(2),reader.GetString(3));
                        }
                    }
                }
            }
            return null;
        }
    }
}
