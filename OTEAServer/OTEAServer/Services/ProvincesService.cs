using OTEAServer.Models;
using System.Data.SqlClient;

namespace OTEAServer.Services
{
    public class ProvincesService
    {
        private static IConfiguration _configuration;
        public ProvincesService(IConfiguration configuration)
        {
            _configuration = configuration;
        }
        public List<Province> GetAllByRegion(int idRegion, string idCountry)
        {
            List<Province> provincesList = new List<Province>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM PROVINCES WHERE idRegion=@IDREGION AND idCountry=@IDCOUNTRY ORDER BY nameProvince";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDREGION", idRegion);
                    command.Parameters.AddWithValue("@IDCOUNTRY", idCountry);

                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            provincesList.Add(new Province(reader.GetInt32(0),idRegion,idCountry,reader.GetString(3)));
                        }
                    }
                }
            }
            return provincesList;
        }

        public Province? Get(int idProvince, int idRegion, string idCountry)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM PROVINCES WHERE idProvince=@IDPROVINCE AND idRegion=@IDREGION AND idCountry=@IDCOUNTRY";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDPROVINCE", idProvince);
                    command.Parameters.AddWithValue("@IDREGION", idRegion);
                    command.Parameters.AddWithValue("@IDCOUNTRY", idCountry);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new Province(idProvince, idRegion, idCountry, reader.GetString(3));
                        }
                    }
                }
            }
            return null;
        }
    }
}
