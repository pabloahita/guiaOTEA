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
            string query = "SELECT * FROM INDICATORS WHERE ISACTIVE=1 ORDER BY INDICATORID";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            indicatorsList.Add(new Indicator(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetString(3), reader.GetString(4), reader.GetInt32(5), reader.GetInt32(6)));
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
            string query = "SELECT * FROM INDICATORS WHERE INDICATORTYPE=@INDICATORTYPE AND ISACTIVE=1 ORDER BY INDICATORID";

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
                            indicatorsList.Add(new Indicator(reader.GetInt32(0), indicatorType, reader.GetString(2), reader.GetString(3), reader.GetString(4), reader.GetInt32(5), reader.GetInt32(6)));
                        }
                    }
                }
            }
            return indicatorsList;
        }

        public Indicator? Get(int idIndicator, string indicatorType, int indicatorVersion)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM INDICATORS WHERE indicatorId=@IDINDICATOR AND indicatorType=@INDICATORTYPE AND indicatorVersion=@INDICATORVERSION ORDER BY INDICATORID";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDINDICATOR", idIndicator);
                    command.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);
                    command.Parameters.AddWithValue("@INDICATORVERSION", indicatorVersion);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new Indicator(idIndicator, indicatorType, reader.GetString(2), reader.GetString(3), reader.GetString(4), indicatorVersion, reader.GetInt32(6));
                        }
                    }
                }
            }
            return null;
        }

        public void Add(int idIndicator, string indicatorType, string descriptionEnglish, string descriptionSpanish, string descriptionFrench, int indicatorPriority, int indicatorVersion) 
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el command SQL
                string sql = "INSERT INTO INDICATORS (INDICATORID,INDICATORTYPE,DESCRIPTIONENGLISH,DESCRIPTIONSPANISH,DESCRIPTIONFRENCH,INDICATORPRIORITY,INDICATORVERSION,ISACTIVE) VALUES (@IDINDICATOR,@INDICATORTYPE,@DESCRIPTIONENGLISH,@DESCRIPTIONSPANISH,@DESCRIPTIONFRENCH,@INDICATORPRIORITY,@INDICATORVERSION,1)";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    command.Parameters.AddWithValue("@IDINDICATOR", idIndicator);
                    command.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);
                    command.Parameters.AddWithValue("@DESCRIPTIONENGLISH", descriptionEnglish);
                    command.Parameters.AddWithValue("@DESCRIPTIONSPANISH", descriptionSpanish);
                    command.Parameters.AddWithValue("@DESCRIPTIONFRENCH", descriptionFrench);
                    command.Parameters.AddWithValue("@INDICATORPRIORITY", indicatorPriority);
                    command.Parameters.AddWithValue("@INDICATORVERSION", indicatorVersion);

                    // Ejecuta el command
                    command.ExecuteNonQuery();
                }

                // Cierra la conexión a la base de datos
                connection.Close();
            }
        }

        //Operación delete

        public void Delete(int idIndicator, string indicatorType, int indicatorVersion)
        {
            if (Get(idIndicator,indicatorType,indicatorVersion) != null)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "DELETE FROM INDICATORS WHERE indicatorId=@IDINDICATOR AND indicatorType=@INDICATORTYPE AND indicatorVersion=@INDICATORVERSION";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@IDINDICATOR", idIndicator);
                        command.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);
                        command.Parameters.AddWithValue("@INDICATORVERSION", indicatorVersion);
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
            if (indicator != null && Get(indicator.indicatorId,indicator.indicatorType,indicator.indicatorVersion-1) != null) //Hay que buscar la versión anterior del indicador
            {

                //Añadir la nueva versión del indicador
                Add(indicator.indicatorId, indicator.indicatorType, indicator.descriptionEnglish, indicator.descriptionSpanish, indicator.descriptionFrench, indicator.indicatorPriority, indicator.indicatorVersion);
                
                //Desactivar versión anterior del indicador
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "UPDATE USERS SET ISACTIVE=0 WHERE INDICATORID=@IDINDICATOR AND INDICATORTYPE=@INDICATORTYPE AND INDICATORVERSION=@INDICATORVERSION"; //Se desactiva la versión anterior del indicador
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL

                        command.Parameters.AddWithValue("@IDINDICATOR", indicator.indicatorId);
                        command.Parameters.AddWithValue("@INDICATORTYPE", indicator.indicatorType);
                        command.Parameters.AddWithValue("@INDICATORVERSION", indicator.indicatorVersion-1);
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
