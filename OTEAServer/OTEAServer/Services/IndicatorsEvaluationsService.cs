using OTEAServer.Models;
using System.Data.SqlClient;
using System.Net.NetworkInformation;
using System.Security.Policy;

namespace OTEAServer.Services
{
    public class IndicatorsEvaluationsService
    {
        private static IConfiguration _configuration;
        public IndicatorsEvaluationsService(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        public List<IndicatorsEvaluation> GetAll()
        {
            List<IndicatorsEvaluation> indicatorsEvaluationList = new List<IndicatorsEvaluation>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM INDICATORSEVALUATIONS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            indicatorsEvaluationList.Add(new IndicatorsEvaluation(reader.GetInt64(0), reader.GetInt32(1), reader.GetString(2), reader.GetInt32(3), reader.GetInt32(4), reader.GetString(5), reader.GetString(6), reader.GetInt32(7), reader.GetInt32(8), reader.GetInt32(9), reader.GetInt32(10), reader.GetInt32(11), reader.GetInt32(12), reader.GetInt32(13)));
                        }
                    }
                }
            }
            return indicatorsEvaluationList;

        }

        public List<IndicatorsEvaluation> GetAllByEvaluatorOrganization(int idEvaluatorOrganization, string orgType, string illness)
        {
            List<IndicatorsEvaluation> indicatorsEvaluationList = new List<IndicatorsEvaluation>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM INDICATORSEVALUATIONS WHERE IDEVALUATORORGANIZATION=@IDEVALUATORORGANIZATION AND ORGTYPEEVALUATOR=@ORGTYPEEVALUATOR AND ILLNESS=@ILLNESS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDEVALUATORORGANIZATION", idEvaluatorOrganization);
                    command.Parameters.AddWithValue("@ORGTYPEEVALUATOR", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            indicatorsEvaluationList.Add(new IndicatorsEvaluation(reader.GetInt64(0), reader.GetInt32(1), reader.GetString(2), reader.GetInt32(3), reader.GetInt32(4), reader.GetString(5), reader.GetString(6), reader.GetInt32(7), reader.GetInt32(8), reader.GetInt32(9), reader.GetInt32(10), reader.GetInt32(11), reader.GetInt32(12), reader.GetInt32(13)));
                        }
                    }
                }
            }
            return indicatorsEvaluationList;

        }

        public List<IndicatorsEvaluation> GetAllByEvaluatedOrganization(int idEvaluatedOrganization, string orgType, string illness)
        {
            List<IndicatorsEvaluation> indicatorsEvaluationList = new List<IndicatorsEvaluation>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM INDICATORSEVALUATIONS WHERE IDEVALUATEDORGANIZATION=@IDEVALUATEDORGANIZATION AND ORGTYPEEVALUATED=@ORGTYPEEVALUATED AND ILLNESS=@ILLNESS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDEVALUATEDORGANIZATION", idEvaluatedOrganization);
                    command.Parameters.AddWithValue("@ORGTYPEEVALUATED", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            indicatorsEvaluationList.Add(new IndicatorsEvaluation(reader.GetInt64(0), reader.GetInt32(1), reader.GetString(2), reader.GetInt32(3), reader.GetInt32(4), reader.GetString(5), reader.GetString(6), reader.GetInt32(7), reader.GetInt32(8), reader.GetInt32(9), reader.GetInt32(10), reader.GetInt32(11), reader.GetInt32(12), reader.GetInt32(13)));
                        }
                    }
                }
            }
            return indicatorsEvaluationList;

        }

        public IndicatorsEvaluation Get(long evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, string illness)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM INDICATORSEVALUATIONS WHERE evaluationDate=@EVALUATIONDATE AND idEvaluatedOrganization=@IDEVALUATEDORGANIZATION AND orgTypeEvaluated=@ORGTYPEEVALUATED AND illness=@ILLNESS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@EVALUATIONDATE", evaluationDate);
                    command.Parameters.AddWithValue("@IDEVALUATEDORGANIZATION", idEvaluatedOrganization);
                    command.Parameters.AddWithValue("@ORGTYPEEVALUATED", orgTypeEvaluated);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new IndicatorsEvaluation(reader.GetInt64(0), reader.GetInt32(1), reader.GetString(2), reader.GetInt32(3), reader.GetInt32(4), reader.GetString(5), reader.GetString(6), reader.GetInt32(7), reader.GetInt32(8), reader.GetInt32(9), reader.GetInt32(10), reader.GetInt32(11), reader.GetInt32(12), reader.GetInt32(13));
                        }
                    }
                }
            }
            return null;
        }

        public void Add(long evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, int idEvaluatorTeam, int idEvaluatorOrganization, string orgTypeEvaluator, string illness, int scoreLevel1, int scoreLevel2, int scoreLevel3, int scoreLevel4, int scoreLevel5, int scoreLevel6, int totalScore)
        {

            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el command SQL
                string sql = "INSERT INTO INDICATORSEVALUATIONS (EVALUATIONDATE,IDEVALUATEDORGANIZATION,ORGTYPEEVALUATED,IDEVALUATORTEAM,IDEVALUATORORGANIZATION,ORGTYPEEVALUATOR,ILLNESS,SCORELEVEL1,SCORELEVEL2,SCORELEVEL3,SCORELEVEL4,SCORELEVEL5,SCORELEVEL6,TOTALSCORE) VALUES (@EVALUATIONDATE,@IDEVALUATEDORGANIZATION,@ORGTYPEEVALUATED,@IDEVALUATORTEAM,@IDEVALUATORORGANIZATION,@ORGTYPEEVALUATOR,@ILLNESS,@SCORELEVEL1,@SCORELEVEL2,@SCORELEVEL3,@SCORELEVEL4,@SCORELEVEL5,@SCORELEVEL6,@TOTALSCORE)";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    command.Parameters.AddWithValue("@EVALUATIONDATE", evaluationDate);
                    command.Parameters.AddWithValue("@IDEVALUATEDORGANIZATION", idEvaluatedOrganization);
                    command.Parameters.AddWithValue("@ORGTYPEEVALUATED", orgTypeEvaluated);
                    command.Parameters.AddWithValue("@IDEVALUATORTEAM", idEvaluatorTeam);
                    command.Parameters.AddWithValue("@IDEVALUATORORGANIZATION", idEvaluatorOrganization);
                    command.Parameters.AddWithValue("@ORGTYPEEVALUATOR", orgTypeEvaluator);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    command.Parameters.AddWithValue("@SCORELEVEL1", scoreLevel1);
                    command.Parameters.AddWithValue("@SCORELEVEL2", scoreLevel2);
                    command.Parameters.AddWithValue("@SCORELEVEL3", scoreLevel3);
                    command.Parameters.AddWithValue("@SCORELEVEL4", scoreLevel4);
                    command.Parameters.AddWithValue("@SCORELEVEL5", scoreLevel5);
                    command.Parameters.AddWithValue("@SCORELEVEL6", scoreLevel6);
                    command.Parameters.AddWithValue("@TOTALSCORE", totalScore);
                    // Ejecuta el command
                    command.ExecuteNonQuery();
                }

                // Cierra la conexión a la base de datos
                connection.Close();
            }
        }

        public void Delete(long evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, string illness)
        {
            if (Get(evaluationDate,idEvaluatedOrganization,orgTypeEvaluated,illness) != null)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "DELETE FROM INDICATORSEVALUATIONS WHERE evaluationDate=@EVALUATIONDATE AND idEvaluatedOrganization=@IDEVALUATEDORGANIZATION AND orgTypeEvaluated=@ORGTYPEEVALUATED AND illness=@ILLNESS";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@EVALUATIONDATE", evaluationDate);
                        command.Parameters.AddWithValue("@IDEVALUATEDORGANIZATION", idEvaluatedOrganization);
                        command.Parameters.AddWithValue("@ORGTYPEEVALUATED", orgTypeEvaluated);
                        command.Parameters.AddWithValue("@ILLNESS", illness);

                        // Ejecuta el command
                        command.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }

        public void Update(long evaluationDate, int idEvaluatedOrganization, string orgTypeEvaluated, string illness, IndicatorsEvaluation indicatorsEvaluation)
        {
            if (indicatorsEvaluation != null && Get(evaluationDate,idEvaluatedOrganization,orgTypeEvaluated,illness) != null && evaluationDate==indicatorsEvaluation.evaluationDate && idEvaluatedOrganization==indicatorsEvaluation.idEvaluatedOrganization && orgTypeEvaluated==indicatorsEvaluation.orgTypeEvaluated && illness==indicatorsEvaluation.illness)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "UPDATE INDICATORSEVALUATIONS SET IDEVALUATORTEAM=@IDEVALUATORTEAM, IDEVALUATORTEAM=@IDEVALUATORORGANIZATION, ORGTYPEEVALUATOR=@ORGTYPEEVALUATOR, SCORELEVEL1=@SCORELEVEL1, SCORELEVEL2=@SCORELEVEL2, SCORELEVEL3=@SCORELEVEL3, SCORELEVEL4=@SCORELEVEL4, SCORELEVEL5=@SCORELEVEL5, SCORELEVEL6=@SCORELEVEL6, TOTALSCORE=@TOTALSCORE WHERE EVALUATIONDATE=@EVALUATIONDATE AND IDEVALUATEDORGANIZATION=@IDEVALUATEDORGANIZATION AND ORGTYPEEVALUATED=@ORGTYPEEVALUATED AND ILLNESS=@ILLNESS";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@EVALUATIONDATE", indicatorsEvaluation.evaluationDate);
                        command.Parameters.AddWithValue("@IDEVALUATEDORGANIZATION", indicatorsEvaluation.idEvaluatedOrganization);
                        command.Parameters.AddWithValue("@ORGTYPEEVALUATED", indicatorsEvaluation.orgTypeEvaluated);
                        command.Parameters.AddWithValue("@IDEVALUATORTEAM", indicatorsEvaluation.idEvaluatorTeam);
                        command.Parameters.AddWithValue("@IDEVALUATORORGANIZATION", indicatorsEvaluation.idEvaluatorOrganization);
                        command.Parameters.AddWithValue("@ORGTYPEEVALUATOR", indicatorsEvaluation.orgTypeEvaluator);
                        command.Parameters.AddWithValue("@ILLNESS", indicatorsEvaluation.illness);
                        command.Parameters.AddWithValue("@SCORELEVEL1", indicatorsEvaluation.scoreLevel1);
                        command.Parameters.AddWithValue("@SCORELEVEL2", indicatorsEvaluation.scoreLevel2);
                        command.Parameters.AddWithValue("@SCORELEVEL3", indicatorsEvaluation.scoreLevel3);
                        command.Parameters.AddWithValue("@SCORELEVEL4", indicatorsEvaluation.scoreLevel4);
                        command.Parameters.AddWithValue("@SCORELEVEL5", indicatorsEvaluation.scoreLevel5);
                        command.Parameters.AddWithValue("@SCORELEVEL6", indicatorsEvaluation.scoreLevel6);
                        command.Parameters.AddWithValue("@TOTALSCORE", indicatorsEvaluation.totalScore);

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
