package otea.connection.caller;

import otea.connection.ConnectionClient;
import otea.connection.api.IndicatorsEvaluationsApi;

public class IndicatorsEvaluationsCaller {

    private static IndicatorsEvaluationsApi api;

    private static IndicatorsEvaluationsCaller instance;

    private IndicatorsEvaluationsCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(IndicatorsEvaluationsApi.class);
    }

    public static IndicatorsEvaluationsCaller getInstance(){
        if(instance==null){
            synchronized (IndicatorsEvaluationsCaller.class){
                if(instance==null){
                    instance=new IndicatorsEvaluationsCaller();
                }
            }
        }
        return instance;
    }
}
