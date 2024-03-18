package otea.connection.api;

;
import java.util.List;

import cli.indicators.IndicatorsEvaluationReg;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * API for Indicators evaluations registers operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface IndicatorsEvaluationRegsApi {

    /**Gets all registers*/
    @GET("IndicatorsEvaluationsRegs/all")
    Call<List<IndicatorsEvaluationReg>> GetAll();

    /**
     * Gets all registers of an indicators evaluation
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * */
    @GET("IndicatorsEvaluationsRegs/indEval")
    Call<List<IndicatorsEvaluationReg>> GetAllByIndicatorsEvaluation(@Query("evaluationDate") long evaluationDate,@Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter);

    /**
     * Gets an indicators evaluation register
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * @param idSubSubAmbit - Second level division of the ambit. It will be -1 if there is no division
     * @param idSubAmbit - First level division of the ambit. It will be -1 if there is no division
     * @param idAmbit - Ambit identifier
     * @param idIndicator - Indicator identifier
     * @param idEvidence - Evidence identifier
     * @param indicatorVersion - Indicator version
     * */
    @GET("IndicatorsEvaluationsRegs/get")
    Call<IndicatorsEvaluationReg> Get(@Query("evaluationDate") long evaluationDate, @Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("idSubSubAmbit") int idSubSubAmbit, @Query("idSubAmbit") int idSubAmbit, @Query("idAmbit") int idAmbit, @Query("idIndicator") int idIndicator, @Query("idEvidence") int idEvidence, @Query("indicatorVersion") int indicatorVersion);

    /**
     * Creates a new indicators evaluation register
     *
     * @param indicatorsEvaluationReg - Indicators evaluation register
     * */
    @POST("IndicatorsEvaluationsRegs")
    Call<IndicatorsEvaluationReg> Create(@Body IndicatorsEvaluationReg indicatorsEvaluationReg);

    /**
     * Creates a new indicators evaluation register
     *
     * @param regs - Indicators evaluation register
     * */
    @POST("IndicatorsEvaluationsRegs/fromList")
    Call<List<IndicatorsEvaluationReg>> CreateRegs(@Body List<IndicatorsEvaluationReg> regs);

    /**
     * Updates an indicators evaluation register
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * @param idSubSubAmbit - Second level division of the ambit. It will be -1 if there is no division
     * @param idSubAmbit - First level division of the ambit. It will be -1 if there is no division
     * @param idAmbit - Ambit identifier
     * @param idIndicator - Indicator identifier
     * @param idEvidence - Evidence identifier
     * @param indicatorVersion - Indicator version
     * */
    @PUT("IndicatorsEvaluationsRegs")
    Call<IndicatorsEvaluationReg> Update(@Query("evaluationDate") long evaluationDate, @Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("idSubSubAmbit") int idSubSubAmbit, @Query("idSubAmbit") int idSubAmbit, @Query("idAmbit") int idAmbit, @Query("idIndicator") int idIndicator, @Query("idEvidence") int idEvidence, @Query("indicatorVersion") int indicatorVersion, @Body IndicatorsEvaluationReg indicatorsEvaluationReg);

    /**
     * Deletes an indicators evaluation registration
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * @param idSubSubAmbit - Second level division of the ambit. It will be -1 if there is no division
     * @param idSubAmbit - First level division of the ambit. It will be -1 if there is no division
     * @param idAmbit - Ambit identifier
     * @param idIndicator - Indicator identifier
     * @param idEvidence - Evidence identifier
     * @param indicatorVersion - Indicator version
     * */
    @DELETE("IndicatorsEvaluationsRegs")
    Call<IndicatorsEvaluationReg> Delete(@Query("evaluationDate") long evaluationDate, @Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("idSubSubAmbit") int idSubSubAmbit, @Query("idSubAmbit") int idSubAmbit, @Query("idAmbit") int idAmbit, @Query("idIndicator") int idIndicator, @Query("idEvidence") int idEvidence, @Query("indicatorVersion") int indicatorVersion);

}
