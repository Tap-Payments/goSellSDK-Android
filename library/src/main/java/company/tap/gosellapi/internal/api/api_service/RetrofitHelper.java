package company.tap.gosellapi.internal.api.api_service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import company.tap.gosellapi.BuildConfig;
import company.tap.gosellapi.StagingURL;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitHelper {
    private static Retrofit retrofit;
    private static APIService helper;

    @Nullable
    public static APIService getApiHelper() {
        if (retrofit == null) {
            if (AppInfo.getAuthToken() == null) {
                throw new RuntimeException("Auth token was not provided!");
            }

            OkHttpClient okHttpClient = getOkHttpClient();
            retrofit = new Retrofit.Builder()
                    .baseUrl(StagingURL.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                    .client(okHttpClient)
                    .build();
        }

        if (helper == null) {
            helper = retrofit.create(APIService.class);
        }

        return helper;
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader(API_Constants.AUTH_TOKEN_KEY, API_Constants.AUTH_TOKEN_PREFIX + AppInfo.getAuthToken())
                        .addHeader(API_Constants.APPLICATION, AppInfo.getApplicationInfo())
                        .addHeader(API_Constants.CONTENT_TYPE_KEY, API_Constants.CONTENT_TYPE_VALUE).build();
                return chain.proceed(request);
            }
        });
        httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(!BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.NONE : HttpLoggingInterceptor.Level.BODY));

        return httpClientBuilder.build();
    }
}