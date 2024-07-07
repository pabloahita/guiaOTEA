package otea.connection.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.indicators.IndicatorsEvaluationEvidenceReg;
import cli.indicators.IndicatorsEvaluationSimpleEvidenceReg;
import okhttp3.ResponseBody;
import otea.connection.ConnectionClient;
import otea.connection.api.IndicatorsEvaluationEvidenceRegsApi;
import otea.connection.api.IndicatorsEvaluationEvidenceSimpleRegsApi;
import retrofit2.Call;
import retrofit2.Response;
import session.Session;

;

/**
 * Controller for indicators evaluation registers
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class IndicatorsEvaluationEvidenceSimpleRegsController {

    /**Indicators evaluation registers api to connect to the server*/
    private static IndicatorsEvaluationEvidenceSimpleRegsApi api;

    /**Controller instance*/
    private static IndicatorsEvaluationEvidenceSimpleRegsController instance;

    /**Class constructor*/
    private IndicatorsEvaluationEvidenceSimpleRegsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(IndicatorsEvaluationEvidenceSimpleRegsApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
    public static IndicatorsEvaluationEvidenceSimpleRegsController getInstance(){
        if(instance==null){
            synchronized (IndicatorsEvaluationEvidenceSimpleRegsController.class){
                if(instance==null){
                    instance=new IndicatorsEvaluationEvidenceSimpleRegsController();
                }
            }
        }
        return instance;
    }



    /**
     * Method that appends new indicators evaluation register
     *
     * @param regs - Indicators evaluation register
     */
    public static void CreateRegs(IndicatorsEvaluationSimpleEvidenceReg[][] regs){
        List<IndicatorsEvaluationSimpleEvidenceReg> registers=new ArrayList<>();
        for(IndicatorsEvaluationSimpleEvidenceReg[] ind:regs){
            registers.addAll(Arrays.asList(ind));
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<ResponseBody> callable = new Callable<ResponseBody>() {
            @Override
            public ResponseBody call() throws Exception {
                Call<ResponseBody> call = api.CreateRegs(registers,Session.getInstance().getToken());
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<ResponseBody> future = executor.submit(callable);
            ResponseBody result = future.get();
            executor.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
