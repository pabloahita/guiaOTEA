package otea.connection.api;

import java.util.List;

import cli.indicators.IndicatorsEvaluationEvidenceReg;
import cli.indicators.IndicatorsEvaluationSimpleEvidenceReg;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

;

/**
 * API for Indicators evaluations registers operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface IndicatorsEvaluationEvidenceSimpleRegsApi {


    /**
     * Creates a new indicators evaluation register
     *
     * @param regs - Indicators evaluation register
     * */
    @POST("IndicatorsEvaluationsSimpleEvidencesRegs/fromList")
    Call<ResponseBody> CreateRegs(@Body List<IndicatorsEvaluationSimpleEvidenceReg> regs, @Header("Authorization") String Authorization);

}
