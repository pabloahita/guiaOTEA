package otea.connection.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TranslatorApi {

    @GET("Translator/translate")
    Call<ResponseBody> translate(
            @Query("text") String text,
            @Query("origin") String origin,
            @Query("target") String target
    );

    @GET("Translator/multiLangTranslate")
    Call<List<String>> multiLangTranslate(
            @Query("text") String text,
            @Query("origin") String origin
    );

}
