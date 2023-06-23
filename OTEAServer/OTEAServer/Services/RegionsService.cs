using OTEAServer.Models;
using System.Data.SqlClient;

namespace OTEAServer.Services
{
    public class RegionsService
    {
        private static IConfiguration _configuration;
        public RegionsService(IConfiguration configuration)
        {
            _configuration = configuration;
        }
        public List<Region> GetAllByCountry(string idCountry)
        {
            List<Region> RegionsList = new List<Region>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM REGIONS WHERE idCountry=@IDCOUNTRY ORDER BY nameRegion";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDCOUNTRY", idCountry);

                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            RegionsList.Add(new Region(reader.GetInt32(0),idCountry,reader.GetString(2)));
                        }
                    }
                }
            }
            return RegionsList;
        }

        public Region? Get(int idRegion, string idCountry)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM Regions WHERE idRegion=@IDRegion AND idRegion=@IDREGION AND idCountry=@IDCOUNTRY";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDREGION", idRegion);
                    command.Parameters.AddWithValue("@IDCOUNTRY", idCountry);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new Region(idRegion, idCountry, reader.GetString(2));
                        }
                    }
                }
            }
            return null;
        }
    }
}
