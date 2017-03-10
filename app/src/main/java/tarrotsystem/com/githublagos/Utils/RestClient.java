package tarrotsystem.com.githublagos.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tarrotsystem.com.githublagos.model.GithubUser;
import tarrotsystem.com.githublagos.model.Login;

/**
 * Created by DOTECH on 09/03/2017.
 */

public class RestClient {
    private static GitApiInterface gitApiInterface ;
    private static String baseUrl = "https://api.github.com" ;

    public static GitApiInterface getClient() {
        if (gitApiInterface == null) {

            OkHttpClient okClient = new OkHttpClient();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            gitApiInterface = client.create(GitApiInterface.class);
        }
        return gitApiInterface ;
    }

    public interface GitApiInterface {

        @Headers("User-Agent: Retrofit2.0Tutorial-App")
        @GET("/search/users")
        Call<GithubUser> getLagosUsers(@Query("q") String location);

        @GET("/users/{user}" )
        Call<Login> getLogin(@Path("user") String user);
    }
}
