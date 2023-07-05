using OTEAServer.Models;
using System.Data.SqlClient;
using System.Security.Policy;

namespace OTEAServer.Services
{
    public class IndicatorsEvaluationRegsService
    {

        private static IConfiguration _configuration;
        public IndicatorsEvaluationRegsService(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        public List<IndicatorsEvaluationReg> GetAll()
        {
            List<IndicatorsEvaluationReg> indicatorsEvaluationRegList = new List<IndicatorsEvaluationReg>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM INDICATORSEVALUATIONSREGS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            indicatorsEvaluationRegList.Add(new IndicatorsEvaluationReg(reader.GetInt64(0), reader.GetInt32(1), reader.GetString(2), reader.GetString(3), reader.GetInt32(4), reader.GetInt32(5), reader.GetInt32(6), reader.GetInt32(7)));
                        }
                    }
                }
            }
            return indicatorsEvaluationRegList;

        }

        public List<IndicatorsEvaluationReg> GetAllByIndicatorsEvaluation(long evaluationDate, int idEvaluatedOrganization, string orgType, string illness)
        {
            List<IndicatorsEvaluationReg> indicatorsEvaluationRegList = new List<IndicatorsEvaluationReg>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM INDICATORSEVALUATIONSREGS WHERE EVALUATIONDATE=@EVALUATIONDATE AND IDEVALUATEDORGANIZATION=@IDEVALUATEDORGANIZATION AND ORGTYPEEVALUATED=@ORGTYPEEVALUATED";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@EVALUATIONDATE", evaluationDate);
                    command.Parameters.AddWithValue("@IDEVALUATEDORGANIZATION", idEvaluatedOrganization);
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            indicatorsEvaluationRegList.Add(new IndicatorsEvaluationReg(reader.GetInt64(0), reader.GetInt32(1), reader.GetString(2), reader.GetString(3), reader.GetInt32(4), reader.GetInt32(5), reader.GetInt32(6), reader.GetInt32(7)));
                        }
                    }
                }
            }
            return indicatorsEvaluationRegList;

        }

        public IndicatorsEvaluationReg Get(long evaluationDate, int idEvaluatedOrganization, string orgType, string illness, int indicatorId, int idEvidence, int indicatorVersion)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM INDICATORSEVALUATIONSREGS WHERE evaluationDate=@EVALUATIONDATE AND idEvaluatedOrganization=@IDEVALUATEDORGANIZATION AND orgTypeEvaluated=@ORGTYPE AND illness=@ILLNESS AND indicatorId=@INDICATORID AND idEvidence=@IDEVIDENCE AND indicatorVersion=@INDICATORVERSION";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@EVALUATIONDATE", evaluationDate);
                    command.Parameters.AddWithValue("@IDEVALUATEDORGANIZATION", idEvaluatedOrganization);
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    command.Parameters.AddWithValue("@INDICATORID", indicatorId);
                    command.Parameters.AddWithValue("@IDEVIDENCE", idEvidence);
                    command.Parameters.AddWithValue("@INDICATORVERSION", indicatorVersion);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new IndicatorsEvaluationReg(reader.GetInt64(0), reader.GetInt32(1), reader.GetString(2), reader.GetString(3), reader.GetInt32(4), reader.GetInt32(5), reader.GetInt32(6), reader.GetInt32(7));
                        }
                    }
                }
            }
            return null;
        }

        public void Add(long evaluationDate, int idEvaluatedOrganization, string orgType, string illness, int indicatorId, int idEvidence, int isMarked, int indicatorVersion)
        {

            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el command SQL
                string sql = "INSERT INTO INDICATORSEVALUATIONSREGS (EVALUATIONDATE,IDEVALUATEDORGANIZATION,ORGTYPEEVALUATED,ILLNESS,INDICATORID,IDEVIDENCE,ISMARKED,INDICATORVERSION) VALUES (@EVALUATIONDATE,@IDEVALUATEDORGANIZATION,@ORGTYPEEVALUATED,@ILLNESS,@INDICATORID,@IDEVIDENCE,@ISMARKED,@INDICATORVERSION)";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    command.Parameters.AddWithValue("@EVALUATIONDATE", evaluationDate);
                    command.Parameters.AddWithValue("@IDEVALUATEDORGANIZATION", idEvaluatedOrganization);
                    command.Parameters.AddWithValue("@ORGTYPEEVALUATED", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    command.Parameters.AddWithValue("@INDICATORID", indicatorId);
                    command.Parameters.AddWithValue("@IDEVIDENCE", idEvidence);
                    command.Parameters.AddWithValue("@ISMARKED", isMarked);
                    command.Parameters.AddWithValue("@INDICATORVERSION", indicatorVersion);
                    // Ejecuta el command
                    command.ExecuteNonQuery();
                }

                // Cierra la conexión a la base de datos
                connection.Close();
            }
        }

        public void Delete(long evaluationDate, int idEvaluatedOrganization, string orgType, string illness, int indicatorId, int idEvidence, int indicatorVersion)
        {
            if (Get(evaluationDate, idEvaluatedOrganization, orgType, illness, indicatorId, idEvidence, indicatorVersion) != null)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "DELETE FROM INDICATORSEVALUATIONSREGS WHERE evaluationDate=@EVALUATIONDATE AND idEvaluatedOrganization=@IDEVALUATEDORGANIZATION AND orgTypeEvaluated=@ORGTYPEEVALUATED AND illness=@ILLNESS AND INDICATORID=@INDICATORID AND IDEVIDENCE=@IDEVIDENCE AND INDICATORVERSION=@INDICATORVERSION";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@EVALUATIONDATE", evaluationDate);
                        command.Parameters.AddWithValue("@IDEVALUATEDORGANIZATION", idEvaluatedOrganization);
                        command.Parameters.AddWithValue("@ORGTYPEEVALUATED", orgType);
                        command.Parameters.AddWithValue("@ILLNESS", illness);
                        command.Parameters.AddWithValue("@INDICATORID", indicatorId);
                        command.Parameters.AddWithValue("@IDEVIDENCE", idEvidence);
                        command.Parameters.AddWithValue("@INDICATORVERSION", indicatorVersion);

                        // Ejecuta el command
                        command.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }

        public void Update(IndicatorsEvaluationReg indicatorsEvaluationReg)
        {
            if (indicatorsEvaluationReg != null && Get(indicatorsEvaluationReg.evaluationDate, indicatorsEvaluationReg.idEvaluatedOrganization, indicatorsEvaluationReg.orgTypeEvaluated, indicatorsEvaluationReg.illness, indicatorsEvaluationReg.indicatorId,indicatorsEvaluationReg.idEvidence,indicatorsEvaluationReg.indicatorVersion) == indicatorsEvaluationReg)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "UPDATE INDICATORSEVALUATIONREGS SET ISMARKED=@ISMARKED WHERE EVALUATIONDATE=@EVALUATIONDATE AND IDEVALUATEDORGANIZATION=@IDEVALUATEDORGANIZATION AND ORGTYPEEVALUATED=@ORGTYPEEVALUATED AND ILLNESS=@ILLNESS AND INDICATORID=@INDICATORID AND IDEVIDENCE=@IDEVIDENCE AND INDICATORVERSION=@INDICATORVERSION";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@EVALUATIONDATE", indicatorsEvaluationReg.evaluationDate);
                        command.Parameters.AddWithValue("@IDEVALUATEDORGANIZATION", indicatorsEvaluationReg.idEvaluatedOrganization);
                        command.Parameters.AddWithValue("@ORGTYPEEVALUATED", indicatorsEvaluationReg.orgTypeEvaluated);
                        command.Parameters.AddWithValue("@ILLNESS", indicatorsEvaluationReg.illness);
                        command.Parameters.AddWithValue("@INDICATORID", indicatorsEvaluationReg.illness);
                        command.Parameters.AddWithValue("@IDEVIDENCE", indicatorsEvaluationReg.idEvidence);
                        command.Parameters.AddWithValue("@ISMARKED", indicatorsEvaluationReg.isMarked);
                        command.Parameters.AddWithValue("@INDICATORVERSION", indicatorsEvaluationReg.indicatorVersion);

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
