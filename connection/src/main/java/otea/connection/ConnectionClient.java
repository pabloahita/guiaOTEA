package otea.connection;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionClient {

    private static final String BASE_URL = "https://oteawebapp-prueba1.azurewebsites.net"; // Cambiar al enlace definitivo

    private OkHttpClient client;
    private static Retrofit retrofit;

    private static ConnectionClient instance;
    private ConnectionClient() {
        client=new OkHttpClient().newBuilder().build();
        retrofit=new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static ConnectionClient getInstance(){
        if(instance==null){//Si la instancia es nula
            synchronized (ConnectionClient.class){//Llama a la parte síncrona del método para ver si vuelve a ser nula
                if(instance==null){
                    instance=new ConnectionClient();
                }
            }
        }
        return instance;
    }

    public Retrofit getRetrofit(){return retrofit;}



}