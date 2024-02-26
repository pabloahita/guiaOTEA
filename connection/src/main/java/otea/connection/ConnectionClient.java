package otea.connection;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private static OkHttpClient client;

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

    /**Class constructor with session token param*/
    private ConnectionClient(String token) {
        loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client=new OkHttpClient().newBuilder().addInterceptor(new InterceptorImpl(token)).build();
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

    public static void startSessionToWebApp(String token){
        instance=new ConnectionClient(token);
    }

    public static class InterceptorImpl implements Interceptor{

        private String token;

        public InterceptorImpl(String token){
            this.token="Bearer "+token;
        }

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder builder = original.newBuilder()
                    .header("Authorization", token);

            Request request = builder.build();
            return chain.proceed(request);
        }
    }


}
