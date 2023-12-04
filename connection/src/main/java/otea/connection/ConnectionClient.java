package otea.connection;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Connection class that contains the necessary stuff to connect to the server
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class ConnectionClient {

    /**URL of the web app*/
    private static final String BASE_URL = "https://guiaotea.azurewebsites.net/";

    /**OkHttp Client*/
    private OkHttpClient client;

    /**Retrofit instance*/
    private static Retrofit retrofit;

    /**Connection client instance*/
    private static ConnectionClient instance;

    /**Logging interceptor*/
    private static HttpLoggingInterceptor loggingInterceptor;

    /**Class constructor*/
    private ConnectionClient() {
        loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client=new OkHttpClient().newBuilder().build();
        retrofit=new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
    }

    /**
     * Singleton instance builder
     *
     * @return ConnectionClient instance
     * */
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

    /**
     * Method that obtains the retrofit instance
     *
     * @return Retrofit instance
     * */
    public Retrofit getRetrofit(){return retrofit;}



}
