package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import cli.organization.data.EvaluatorTeam;
import otea.connection.ConnectionClient;
import otea.connection.api.EvaluatorTeamsApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EvaluatorTeamsCaller {

    private static EvaluatorTeamsApi api;

    private static EvaluatorTeamsCaller instance;

    private EvaluatorTeamsCaller() {
        api = ConnectionClient.getInstance().getRetrofit().create(EvaluatorTeamsApi.class);
    }

    public static EvaluatorTeamsCaller getInstance(){
        if(instance==null){
            synchronized (EvaluatorTeamsCaller.class){
                if(instance==null){
                    instance=new EvaluatorTeamsCaller();
                }
            }
        }
        return instance;
    }



    public static EvaluatorTeam obtainEvaluatorTeam(int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, String illness){
        Call<EvaluatorTeam> call=api.Get(idEvaluatorTeam,idEvaluatorOrganization,orgTypeEvaluator,illness);
        AsyncTask<Void, Void, EvaluatorTeam> asyncTask = new AsyncTask<Void, Void, EvaluatorTeam>() {
            EvaluatorTeam resultEvaluatorTeam = null;
            @Override
            protected EvaluatorTeam doInBackground(Void... voids) {
                try {
                    Response<EvaluatorTeam> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(EvaluatorTeam evaluatorTeam) {
                resultEvaluatorTeam=evaluatorTeam;
            }

        };
        asyncTask.execute();
        try {
            EvaluatorTeam evaluatorTeam=asyncTask.get();
            //Se devuelve el constructor para que obtenga los objetos usuario
            return new EvaluatorTeam(idEvaluatorTeam,evaluatorTeam.getCreationDate(),idEvaluatorOrganization,orgTypeEvaluator, illness, evaluatorTeam.getEmailConsultant(), evaluatorTeam.getEmailProfessional(), evaluatorTeam.getEmailResponsible(), evaluatorTeam.getPatient_name(), evaluatorTeam.getRelative_name());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

}
