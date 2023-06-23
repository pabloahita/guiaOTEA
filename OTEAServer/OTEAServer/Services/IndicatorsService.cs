using OTEAServer.Models;
using System.Data.SqlClient;

namespace OTEAServer.Services
{
    public class IndicatorsService
    {
        private static IConfiguration _configuration;
        public IndicatorsService(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        public List<Indicator> GetAll() {
            List<Indicator> indicatorsList = new List<Indicator>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM INDICATORS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            indicatorsList.Add(new Indicator(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetString(3), reader.GetString(4), reader.GetInt32(5)));
                        }
                    }
                }
            }
            return indicatorsList;
        }

        public List<Indicator> GetAllByType(string indicatorType)
        {
            List<Indicator> indicatorsList = new List<Indicator>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM INDICATORS WHERE INDICATORTYPE=@INDICATORTYPE";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            indicatorsList.Add(new Indicator(reader.GetInt32(0), indicatorType, reader.GetString(2), reader.GetString(3), reader.GetString(4), reader.GetInt32(5)));
                        }
                    }
                }
            }
            return indicatorsList;
        }

        public Indicator? Get(int idIndicator, string indicatorType)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM INDICATORS WHERE idIndicator=@IDINDICATOR AND indicatorType=@INDICATORTYPE";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDINDICATOR", idIndicator);
                    command.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new Indicator(idIndicator, indicatorType, reader.GetString(2), reader.GetString(3), reader.GetString(4), reader.GetInt32(5));
                        }
                    }
                }
            }
            return null;
        }

        public void Add(int idIndicator, string indicatorType, string descriptionEnglish, string descriptionSpanish, string descriptionFrench, int indicatorPriority) 
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el command SQL
                string sql = "INSERT INTO INDICATORS (IDINDICATOR,INDICATORTYPE,DESCRIPTIONENGLISH,DESCRIPTIONSPANISH,DESCRIPTIONFRENCH,INDICATORPRIORITY) VALUES (@IDINDICATOR,@INDICATORTYPE,@DESCRIPTIONENGLISH,@DESCRIPTIONSPANISH,@DESCRIPTIONFRENCH,@INDICATORPRIORITY)";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    command.Parameters.AddWithValue("@IDINDICATOR", idIndicator);
                    command.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);
                    command.Parameters.AddWithValue("@DESCRIPTIONENGLISH", descriptionEnglish);
                    command.Parameters.AddWithValue("@DESCRIPTIONSPANISH", descriptionSpanish);
                    command.Parameters.AddWithValue("@DESCRIPTIONFRENCH", descriptionFrench);
                    command.Parameters.AddWithValue("@INDICATORPRIORITY", indicatorPriority);

                    // Ejecuta el command
                    command.ExecuteNonQuery();
                }

                // Cierra la conexión a la base de datos
                connection.Close();
            }
        }

        //Operación delete

        public void Delete(int idIndicator, string indicatorType)
        {
            if (Get(idIndicator,indicatorType) != null)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "DELETE FROM INDICATORS WHERE idIndicator=@IDINDICATOR AND indicatorType=@INDICATORTYPE";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@IDINDICATOR", idIndicator);
                        command.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);
                        // Ejecuta el command
                        command.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }

        public void Update(Indicator indicator)
        {
            if (indicator != null && Get(indicator.indicatorId,indicator.indicatorType) == indicator)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "UPDATE USERS SET DESCRIPTIONENGLISH=@DESCRIPTIONENGLISH, DESCRIPTIONENGLISH=@DESCRIPTIONSPANISH, DESCRIPTIONFRENCH=@DESCRIPTIONFRENCH, INDICATORPRIORITY=@INDICATORPRIORITY WHERE IDINDICATOR=@IDINDICATOR AND INDICATORTYPE=@INDICATORTYPE";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL

                        command.Parameters.AddWithValue("@IDINDICATOR", indicator.indicatorId);
                        command.Parameters.AddWithValue("@INDICATORTYPE", indicator.indicatorType);
                        command.Parameters.AddWithValue("@DESCRIPTIONENGLISH", indicator.descriptionEnglish);
                        command.Parameters.AddWithValue("@DESCRIPTIONSPANISH", indicator.descriptionSpanish);
                        command.Parameters.AddWithValue("@DESCRIPTIONFRENCH", indicator.descriptionFrench);
                        command.Parameters.AddWithValue("@INDICATORPRIORITY", indicator.indicatorPriority);

                        // Ejecuta el command
                        command.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }
    }
}
