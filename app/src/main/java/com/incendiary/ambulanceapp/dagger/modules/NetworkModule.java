package com.incendiary.ambulanceapp.dagger.modules;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.incendiary.ambulanceapp.dagger.PerApp;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.features.auth.login.LoginAct;
import com.incendiary.ambulanceapp.network.ApiService;
import com.incendiary.androidcommon.android.ContextProvider;
import com.incendiary.androidcommon.android.text.Strings;
import com.incendiary.androidcommon.etc.Logger;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String BASE_URL = "http://app-services001.oganlopian.purwakartakab.go.id/apps_svc.php/";

    private static final int DEFAULT_TIMEOUT = 30;

    @PerApp
    @Provides
    Gson provideGson() {
        return new Gson();
    }

    @PerApp
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @PerApp
    @Provides
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor(message -> Logger.log(Log.DEBUG, message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {

                    Response response = chain.proceed(chain.request());
                    if (response.code() == 401) {
                        LoginAct.startOver(ContextProvider.get());
                    }
                    return response;

                })
                .addInterceptor(chain -> {

                    final String keycode = DataStore.getKeyCode();
                    if (!Strings.isEmpty(keycode)) {
                        final String userId = DataStore.getProfile().toBlocking().first().getUserId();

                        final Request request = chain.request();
                        final HttpUrl httpUrl = request.url().newBuilder()
                                .addEncodedQueryParameter("keycode", DataStore.getKeyCode())
                                .addEncodedQueryParameter("user_id", userId)
                                .build();

                        Request newRequest = request.newBuilder()
                                .url(httpUrl)
                                .build();
                        return chain.proceed(newRequest);
                    }

                    return chain.proceed(chain.request());

                })
                .build();
    }

    @PerApp
    @Provides
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
