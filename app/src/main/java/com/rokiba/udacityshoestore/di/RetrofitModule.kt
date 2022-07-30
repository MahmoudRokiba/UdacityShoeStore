package com.rokiba.udacityshoestore.di

import android.content.Context
import android.util.Log
import com.rokiba.udacityshoestore.Env
import com.rokiba.udacityshoestore.data.local.MyPreferences
import com.rokiba.udacityshoestore.data.network.ApiServices
import com.rokiba.udacityshoestore.data.network.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

//    val CACHE_SIZE = 10 * 1024 * 1024 //10MB
//    val CACHE_MAX_AGE = 5 //5 seconds
//    val CACHE_MAX_STALE = 60 * 60 * 24 * 7 //1 week

    val CACHE_SIZE = 10 * 1024 * 1024 //10MB
    val CACHE_MAX_AGE = 0
    val CACHE_MAX_STALE = 0

    /**
     * provide Http Interceptor to be used in logging networkinga
     *
     * @return an instance of Http Interceptor with custom logging
     */
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor.Logger { s -> Log.i("MyNetwork", s) }
        val loggingInterceptor = HttpLoggingInterceptor(logger)
        loggingInterceptor.apply { loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS }
        loggingInterceptor.apply { loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }

        return loggingInterceptor
    }

    /**
     * Provides okHttp cache to be used in okHttp client
     *
     * @return the okHttp cache
     */
    @Provides
    @Singleton
    fun provideHttpCache(context: Context) = Cache(context.filesDir, CACHE_SIZE.toLong())

    /**
     * Provide Http Interceptor to update cache if network connected and add Cache-Control to responseBody
     * header if Cache not supported in the server
     *
     * @param context the application context to e used in check the network state
     * @return  Http Interceptor instance
     */
    @Provides
    @Singleton
    @Named("online_interceptor")
    fun provideHttpOnlineInterceptor(context: Context): Interceptor {

        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            val headers = response.header("Cache-Control")
            if (NetworkUtils.isNetworkConnected(context)!! && (headers == null ||
                        headers.contains("no-store") || headers.contains("must-revalidate") ||
                        headers.contains("no-cache") || headers.contains("max-age=0"))
            ) {

                response.newBuilder()
                    .header("Cache-Control", "public, max-age=${CACHE_MAX_AGE}")
                    .build()
            } else
                response
        }
    }

    /**
     * Provide Http Interceptor to get data from cache if network disconnected
     *
     * @param context the application context to e used in check the network state
     * @return  Http Interceptor instance
     */
    @Provides
    @Singleton
    @Named("offline_interceptor")
    fun provideHttpOfflineInterceptor(context: Context): Interceptor {

        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtils.isNetworkConnected(context)!!) {

                request = request.newBuilder()
                    .header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=${CACHE_MAX_STALE}"
                    )
                    .build()
            }
            chain.proceed(request)
        }
    }


    /**
     * Provide Http Interceptor to update cache if network connected and add Cache-Control to responseBody
     * header if Cache not supported in the server
     *
     * @return  Http Interceptor instance
     */
    @Provides
    @Singleton
    @Named("headers_interceptor")
    fun provideHeadersInterceptor(pref: MyPreferences): Interceptor {

        return Interceptor { chain ->
            var request = chain.request()
            //if (pref.getLogin() != null){
            if (false) {
//                val x = pref.getLogin()!!.accessToken
//                request = request.newBuilder()
//                    .header("lang", pref.getLanguage())
//                    .header("authorization", "Bearer $x")
//                    .build()
            } else {
                request = request.newBuilder()
                    .header("lang", pref.getLanguage())
                    .build()
            }

            chain.proceed(request)
        }
    }

    /**
     * Provides the okHttp client for networking
     *
     * @param cache the okHttp cache
     * @param loggingInterceptor the okHttp logging interceptor
     * @param onlineInterceptor the interceptor to be used in case of online network
     * @param offlineInterceptor the interceptor to be used in case of offline network
     * @return okHttp client instance
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache, loggingInterceptor: HttpLoggingInterceptor,
        @Named("online_interceptor") onlineInterceptor: Interceptor,
        @Named("offline_interceptor") offlineInterceptor: Interceptor,
        @Named("headers_interceptor") headersInterceptor: Interceptor
    ): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
            .addNetworkInterceptor(onlineInterceptor)
            .addInterceptor(offlineInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headersInterceptor)
            .addNetworkInterceptor(headersInterceptor)
            .connectTimeout(300, TimeUnit.SECONDS) // connect timeout
            .readTimeout(300, TimeUnit.SECONDS)    // socket timeout

        return client.build()
    }

    /**
     * Provides the Retrofit object.
     *
     * @param httpClient the okHttp client
     * @return the Retrofit object
     */
    @Provides
    @Singleton
    fun provideRetrofitInterface(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Env.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    /**
     * Provides the service implementation.
     *
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the ApiService implementation.
     */
    @Provides
    @Singleton
    fun provideStoreApiService(retrofit: Retrofit) = retrofit.create(ApiServices::class.java)

//    @Provides
//    @Singleton
//    fun provideStoreApiService() : ApiServices {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
//        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
//        return Retrofit.Builder()
//            .baseUrl(Env.API_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//            .create(ApiServices::class.java)
//    }

}