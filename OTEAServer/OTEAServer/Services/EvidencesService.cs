using OTEAServer.Models;
using System.Data.SqlClient;
using System.Security.Policy;

namespace OTEAServer.Services
{
    public class EvidencesService
    {
        private static IConfiguration _configuration;
        public EvidencesService(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        public List<Models.Evidence> GetAll()
        {
            List<Models.Evidence> evidenceList = new List<Models.Evidence>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM EVIDENCES";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            evidenceList.Add(new Models.Evidence(reader.GetInt32(0), reader.GetInt32(1), reader.GetString(2), reader.GetString(3), reader.GetString(4), reader.GetString(5), reader.GetInt32(6), reader.GetInt32(7)));
                        }
                    }
                }
            }
            return evidenceList;

        }

        public List<Models.Evidence> GetAllByIndicator(int idIndicator, string indicatorType, int indicatorVersion)
        {
            List<Models.Evidence> evidenceList = new List<Models.Evidence>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM EVIDENCES WHERE idIndicator=@IDINDICATOR AND indicatorType=@INDICATORTYPE AND indicatorVersion=@INDICATORVERSION";

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
                        while (reader.Read())
                        {
                            evidenceList.Add(new Models.Evidence(reader.GetInt32(0), reader.GetInt32(1), reader.GetString(2), reader.GetString(3), reader.GetString(4), reader.GetString(5), reader.GetInt32(6), reader.GetInt32(7)));
                        }
                    }
                }
            }
            return evidenceList;

        }

        public Models.Evidence Get(int idEvidence, int idIndicator, string indicatorType, int indicatorVersion)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM EVIDENCE WHERE idEvidence=@IDEVIDENCE AND idIndicator=@IDINDICATOR AND indicatorType=@INDICATORTYPE AND indicatorVersion=@INDICATORVERSION";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDEVIDENCE", idEvidence);
                    command.Parameters.AddWithValue("@IDINDICATOR", idIndicator);
                    command.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);
                    command.Parameters.AddWithValue("@INDICATORVERSION", indicatorVersion);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new Models.Evidence(reader.GetInt32(0), reader.GetInt32(1), reader.GetString(2), reader.GetString(3), reader.GetString(4), reader.GetString(5), reader.GetInt32(6), reader.GetInt32(7));
                        }
                    }
                }
            }
            return null;
        }

        public void Add(int idEvidence, int idIndicator, string indicatorType, string descriptionEnglish, string descriptionSpanish, string descriptionFrench, int evidenceValue, int indicatorVersion)
        {

            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el command SQL
                string sql = "INSERT INTO EVIDENCES (IDEVIDENCE,IDINDICATOR,INDICATORTYPE,DESCRIPTIONENGLISH,DESCRIPTIONSPANISH,DESCRIPTIONFRENCH,EVIDENCEVALUE,INDICATORVERSION) VALUES (@IDEVIDENCE,@IDINDICATOR,@INDICATORTYPE,@DESCRIPTIONENGLISH,@DESCRIPTIONSPANISH,@DESCRIPTIONFRENCH,@EVIDENCEVALUE,@INDICATORVERSION)";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    command.Parameters.AddWithValue("@IDEVIDENCE", idEvidence);
                    command.Parameters.AddWithValue("@IDINDICATOR", idIndicator);
                    command.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);
                    command.Parameters.AddWithValue("@DESCRIPTIONENGLISH", descriptionEnglish);
                    command.Parameters.AddWithValue("@DESCRIPTIONSPANISH", descriptionSpanish);
                    command.Parameters.AddWithValue("@DESCRIPTIONFRENCH", descriptionFrench);
                    command.Parameters.AddWithValue("@EVIDENCEVALUE", evidenceValue);
                    command.Parameters.AddWithValue("@INDICATORVERSION", indicatorVersion);
                    // Ejecuta el command
                    command.ExecuteNonQuery();
                }

                // Cierra la conexión a la base de datos
                connection.Close();
            }
        }

        public void Delete(int idEvidence, int idIndicator, string indicatorType, int indicatorVersion)
        {
            if (Get(idEvidence,idIndicator,indicatorType, indicatorVersion) != null)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "DELETE FROM EVIDENCES WHERE idEvidence=@IDEVIDENCE AND idIndicator=@IDEVIDENCE AND indicatorType=@INDICATORTYPE AND indicatorVersion=@INDICATORVERSION";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@IDEVIDENCE", idEvidence);
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

        public void Update(Models.Evidence evidence)
        {
            if (evidence != null && Get(evidence.idEvidence,evidence.idIndicator,evidence.indicatorType,evidence.indicatorVersion-1) != null)
            {
                Add(evidence.idEvidence, evidence.idIndicator, evidence.indicatorType, evidence.descriptionEnglish, evidence.descriptionSpanish, evidence.descriptionFrench, evidence.evidenceValue, evidence.indicatorVersion);
            }


        }
    }
}
