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
                            evidenceList.Add(new Models.Evidence(reader.GetInt32(0), reader.GetInt32(1), reader.GetString(2), reader.GetString(3), reader.GetInt32(4)));
                        }
                    }
                }
            }
            return evidenceList;

        }

        public List<Models.Evidence> GetAllByIndicator(int idIndicator, string indicatorType)
        {
            List<Models.Evidence> evidenceList = new List<Models.Evidence>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM EVIDENCES WHERE idIndicator=@IDINDICATOR AND indicatorType=@INDICATORTYPE";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDINDICATOR", idIndicator);
                    command.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            evidenceList.Add(new Models.Evidence(reader.GetInt32(0), reader.GetInt32(1), reader.GetString(2), reader.GetString(3), reader.GetInt32(4)));
                        }
                    }
                }
            }
            return evidenceList;

        }

        public Models.Evidence Get(int idEvidence, int idIndicator, string indicatorType)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM EVIDENCE WHERE idEvidence=@IDEVIDENCE AND idIndicator=@IDINDICATOR AND indicatorType=@INDICATORTYPE";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDEVIDENCE", idEvidence);
                    command.Parameters.AddWithValue("@IDINDICATOR", idIndicator);
                    command.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new Models.Evidence(reader.GetInt32(0), reader.GetInt32(1), reader.GetString(2), reader.GetString(3), reader.GetInt32(4));
                        }
                    }
                }
            }
            return null;
        }

        public void Add(int idEvidence, int idIndicator, string indicatorType, string evidenceDescription, int evidenceValue)
        {

            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el command SQL
                string sql = "INSERT INTO EVIDENCES (IDEVIDENCE,IDINDICATOR,INDICATORTYPE,EVIDENCEDESCRIPTION,EVIDENCEVALUE) VALUES (@IDEVIDENCE,@IDINDICATOR,@INDICATORTYPE,@EVIDENCEDESCRIPTION,@EVIDENCEVALUE)";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    command.Parameters.AddWithValue("@IDEVIDENCE", idEvidence);
                    command.Parameters.AddWithValue("@IDINDICATOR", idIndicator);
                    command.Parameters.AddWithValue("@INDICATORTYPE", indicatorType);
                    command.Parameters.AddWithValue("@EVIDENCEDESCRIPTION", evidenceDescription);
                    command.Parameters.AddWithValue("@EVIDENCEVALUE", evidenceValue);
                    // Ejecuta el command
                    command.ExecuteNonQuery();
                }

                // Cierra la conexión a la base de datos
                connection.Close();
            }
        }

        public void Delete(int idEvidence, int idIndicator, string indicatorType)
        {
            if (Get(idEvidence,idIndicator,indicatorType) != null)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "DELETE FROM EVIDENCES WHERE idEvidence=@IDEVIDENCE AND idIndicator=@IDEVIDENCE AND indicatorType=@INDICATORTYPE";
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
            if (evidence != null && Get(evidence.IdEvidence,evidence.IdIndicator,evidence.IndicatorType) == evidence)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "UPDATE EVIDENCES SET EVIDENCEDESCRIPTION=@EVIDENCEDESCRIPTION, EVIDENCEVALUE=@EVIDENCEVALUE WHERE IDEVIDENCE=@IDEVIDENCE AND IDINDICATOR=@IDINDICATOR AND INDICATORTYPE=@INDICATORTYPE";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@IDEVIDENCE", evidence.IdEvidence);
                        command.Parameters.AddWithValue("@IDINDICATOR", evidence.IdIndicator);
                        command.Parameters.AddWithValue("@INDICATORTYPE", evidence.IndicatorType);
                        command.Parameters.AddWithValue("@EVIDENCEDESCRIPTION", evidence.EvidenceDescription);
                        command.Parameters.AddWithValue("@EVIDENCEVALUE", evidence.EvidenceValue);

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
