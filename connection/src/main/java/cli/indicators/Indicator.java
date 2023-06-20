package cli.indicators;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import cli.organization.data.Address;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import otea.connection.ConnectionClient;
import otea.connection.EvidencesApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Indicator {

    @SerializedName("idIndicator")
    private int idIndicator;

    @SerializedName("indicatorType")
    private String indicatorType;

    @SerializedName("indicatorDescription")
    private String description;
    private List<Evidence> evidences;

    @SerializedName("indicatorPriority")
    private int priority;

    private static Connection con;

    public Indicator(int idIndicator, String indicatorType, String description, int priority) {
        setIdIndicator(idIndicator);
        setIndicatorType(indicatorType);
        setDescription(description);
        setPriority(priority);
        setEvidences();
    }

    public int getIdIndicator() {
        return idIndicator;
    }

    public void setIdIndicator(int idIndicator) {
        this.idIndicator = idIndicator;
    }

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public void setEvidences() {
        ConnectionClient con=new ConnectionClient();
        Retrofit retrofit=con.getRetrofit();
        EvidencesApi api=retrofit.create(EvidencesApi.class);
        Call<List<Evidence>> call=api.GetAllByIndicator(idIndicator,indicatorType);
        List<Evidence>[] result=new List[1];
        Disposable disposable = Observable.fromCallable(() -> {
                    try {
                        Response<List<Evidence>> response = call.execute();
                        if (response.isSuccessful()) {
                            result[0]=response.body();
                            return result[0];
                        } else {
                            throw new IOException("Error: " + response.code() + " " + response.message());
                        }
                    } catch (IOException e) {
                        throw e;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(evidences-> {
                    System.out.println("Evidences correctly obtained");
                }, error -> {
                    System.out.println(error.toString());
                });
        evidences=result[0];
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public void setDescription(String description) {
        this.description=description;
    }
    public String getDescription() {
        return description;
    }


    public int getIndicatorValue(){
        int value=0;
        for(Evidence e : evidences){
            value+=e.getValue();
        }
        return value;
    }


    public void addEvidence(Evidence evidence){
        this.evidences.add(evidence);
        ConnectionClient con=new ConnectionClient();
        Retrofit retrofit=con.getRetrofit();
        EvidencesApi api=retrofit.create(EvidencesApi.class);
        Call<Evidence> call=api.Create(evidence.getIdEvidence(),evidence.getIdIndicator(),evidence.getIndicatorType(),evidence.getDescription(),evidence.getValue());
        Disposable disposable = Observable.fromCallable(() -> {
                    try {
                        Response<Evidence> response = call.execute();
                        if (response.isSuccessful()) {
                            return response.body();
                        } else {
                            throw new IOException("Error: " + response.code() + " " + response.message());
                        }
                    } catch (IOException e) {
                        throw e;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(evidences-> {
                    System.out.println("Evidence correctly add");
                }, error -> {
                    System.out.println(error.toString());
                });
    }


    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getMultiplicator(int num_evidences_filled){
        if(num_evidences_filled==0||num_evidences_filled==1){return 0;}
        else if (num_evidences_filled==2||num_evidences_filled==3){
            if (this.priority==1){return 1;}
            else if (this.priority==2){return 2;}
            else if (this.priority==3){return 3;}
            else{return 4;}
        }
        else{
            if (this.priority==1){return 2;}
            else if (this.priority==2){return 3;}
            else if (this.priority==3){return 4;}
            else{return 5;}
        }
    }


}
