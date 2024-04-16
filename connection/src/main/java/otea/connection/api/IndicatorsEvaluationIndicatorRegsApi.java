package otea.connection.api;

import java.util.List;

import cli.indicators.IndicatorsEvaluationIndicatorReg;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IndicatorsEvaluationIndicatorRegsApi {

    /**Gets all registers*/
    @GET("IndicatorsEvaluationsIndicatorsRegs/all")
    Call<List<IndicatorsEvaluationIndicatorReg>> GetAll(@Query("evaluationType") String evaluationType);

    /**
     * Gets all evidence registers of an indicators evaluation
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
    @GET("IndicatorsEvaluationsIndicatorsRegs/indEval")
    Call<List<IndicatorsEvaluationIndicatorReg>> GetAllByIndicatorsEvaluation(@Query("evaluationDate") long evaluationDate, @Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("evaluationType") String evaluationType);

    /**
     * Gets an indicators evaluation evidence register
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
     * @param indicatorVersion - Indicator version
     * */
    @GET("IndicatorsEvaluationsIndicatorsRegs/get")
    Call<IndicatorsEvaluationIndicatorReg> Get(@Query("evaluationDate") long evaluationDate, @Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("idSubSubAmbit") int idSubSubAmbit, @Query("idSubAmbit") int idSubAmbit, @Query("idAmbit") int idAmbit, @Query("idIndicator") int idIndicator, @Query("indicatorVersion") int indicatorVersion, @Query("evaluationType") String evaluationType);

    /**
     * Creates a new indicators evaluation evidence register
     *
     * @param indicatorsEvaluationIndicatorReg - Indicators evaluation register
     * */
    @POST("IndicatorsEvaluationsIndicatorsRegs")
    Call<IndicatorsEvaluationIndicatorReg> Create(@Body IndicatorsEvaluationIndicatorReg indicatorsEvaluationIndicatorReg);

    /**
     * Creates a new indicators evaluation evidence register
     *
     * @param regs - Indicators evaluation register
     * */
    @POST("IndicatorsEvaluationsIndicatorsRegs/fromList")
    Call<ResponseBody> CreateRegs(@Body List<IndicatorsEvaluationIndicatorReg> regs);

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
     * @param indicatorVersion - Indicator version
     * */
    @PUT("IndicatorsEvaluationsIndicatorsRegs")
    Call<IndicatorsEvaluationIndicatorReg> Update(@Query("evaluationDate") long evaluationDate, @Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("idSubSubAmbit") int idSubSubAmbit, @Query("idSubAmbit") int idSubAmbit, @Query("idAmbit") int idAmbit, @Query("idIndicator") int idIndicator, @Query("indicatorVersion") int indicatorVersion, @Query("evaluationType") String evaluationType, @Body IndicatorsEvaluationIndicatorReg indicatorsEvaluationIndicatorReg);

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
     * @param indicatorVersion - Indicator version
     * */
    @DELETE("IndicatorsEvaluationsIndicatorsRegs")
    Call<IndicatorsEvaluationIndicatorReg> Delete(@Query("evaluationDate") long evaluationDate, @Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("idSubSubAmbit") int idSubSubAmbit, @Query("idSubAmbit") int idSubAmbit, @Query("idAmbit") int idAmbit, @Query("idIndicator") int idIndicator, @Query("indicatorVersion") int indicatorVersion, @Query("evaluationType") String evaluationType);

}
