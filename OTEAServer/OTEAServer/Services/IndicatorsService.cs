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
                            indicatorsList.Add(new Indicator(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetInt32(3)));
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
                            indicatorsList.Add(new Indicator(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetInt32(3)));
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
                            return new Indicator(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetInt32(3));
                        }
                    }
                }
            }
            return null;
        }

        public void Add(int idIndicator, string indicatorType, string indicatorDescription, int indicatorPriority) 
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el comando SQL
                string sql = "INSERT INTO INDICATORS (IDINDICATOR,INDICATORTYPE,INDICATORDESCRIPTION,INDICATORPRIORITY) VALUES (@IDINDICATOR,@INDICATORTYPE,@INDICATORDESCRIPTION,@INDICATORPRIORITY)";
                using (SqlCommand comando = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    comando.Parameters.AddWithValue("@IDINDICATOR", idIndicator);
                    comando.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);
                    comando.Parameters.AddWithValue("@INDICATORDESCRIPTION", indicatorDescription);
                    comando.Parameters.AddWithValue("@INDICATORPRIORITY", indicatorPriority);

                    // Ejecuta el comando
                    comando.ExecuteNonQuery();
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

                    // Crea el comando SQL
                    string sql = "DELETE FROM INDICATORS WHERE idIndicator=@IDINDICATOR AND indicatorType=@INDICATORTYPE";
                    using (SqlCommand comando = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        comando.Parameters.AddWithValue("@IDINDICATOR", idIndicator);
                        comando.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);

                        // Ejecuta el comando
                        comando.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }

        public void Update(Indicator indicator)
        {
            if (indicator != null && Get(indicator.IdIndicator,indicator.IndicatorType) == indicator)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el comando SQL
                    string sql = "UPDATE USERS SET INDICATORDESCRIPTION=@INDICATORDESCRIPTION, INDICATORPRIORITY=@INDICATORPRIORITY WHERE IDINDICATOR=@IDINDICATOR AND INDICATORTYPE=@INDICATORTYPE";
                    using (SqlCommand comando = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL

                        comando.Parameters.AddWithValue("@IDINDICATOR", indicator.IdIndicator);
                        comando.Parameters.AddWithValue("@INDICATORTYPE", indicator.IndicatorType);
                        comando.Parameters.AddWithValue("@INDICATORDESCRIPTION", indicator.IndicatorDescription);
                        comando.Parameters.AddWithValue("@INDICATORPRIORITY", indicator.IndicatorPriority);

                        // Ejecuta el comando
                        comando.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }
    }
}
