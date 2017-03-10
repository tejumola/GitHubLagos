package tarrotsystem.com.githublagos.Utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This is a non instantiable immutable class contains all the Generic API Constants to be used
 * It also encapsulate some API related utilities
 * Created by DOTECH on 07/03/2017.
 */

public final class APIConstants {
    //https://api.github.com/search/users?q=location:lagos

    public static final String API_BASE="https://api.github.com";
    private static OkHttpClient okHttpClient = new OkHttpClient();



    /**
     * this endpoint describes search related operations
     *
     */
    public static  final class SearchDeveloper{
        public static final String SEARCH = "/search";
        public static final String USERS = SEARCH + "/users";
    }


    public static <T> T createRetrofitService(final Class<T> clazz) {
        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(APIConstants.API_BASE)
                .addConverterFactory( GsonConverterFactory.create())
                .addCallAdapterFactory( RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        T service = restAdapter.create(clazz);

        return service;
    }
    /**
     * non instantiable constructor
     */
    private APIConstants(){
        throw new AssertionError("cannot instantiate this class");
    }

}
