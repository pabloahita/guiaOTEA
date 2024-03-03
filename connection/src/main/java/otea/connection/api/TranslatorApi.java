package otea.connection.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API for translation operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface TranslatorApi {



    /**
     * Translates a text from an origin language to a target language
     *
     * @param text - Text to translate
     * @param origin - Origin language
     * */
    @GET("Translator/translate")
    Call<List<String>> translate(
            @Query("text") String text,
            @Query("origin") String origin
    );


}
