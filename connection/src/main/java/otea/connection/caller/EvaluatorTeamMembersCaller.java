package otea.connection.caller;

import otea.connection.ConnectionClient;
import otea.connection.api.EvaluatorTeamMembersApi;

public class EvaluatorTeamMembersCaller {

    private static EvaluatorTeamMembersApi api;

    private static EvaluatorTeamMembersCaller instance;

    private EvaluatorTeamMembersCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(EvaluatorTeamMembersApi.class);
    }

    public static EvaluatorTeamMembersCaller getInstance(){
        if(instance==null){
            synchronized (EvaluatorTeamMembersCaller.class){
                if(instance==null){
                    instance=new EvaluatorTeamMembersCaller();
                }
            }
        }
        return instance;
    }
}
