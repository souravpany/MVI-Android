package com.example.androidmviapp.di

import com.example.androidmviapp.BuildConfig
import com.example.androidmviapp.constant.AppConstants
import com.example.androidmviapp.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @BaseURL
    @Provides
    fun provideBaseUrl(): String = "https://jsonplaceholder.typicode.com/"


    /**
     * provides [Retrofit] client instance
     */
    @Provides
    @Singleton
    fun getRetrofitInstance(
        @BaseURL baseURL: String,
        okHttpClient: OkHttpClient
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    /**
     * provide okhttpClient
     */
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(AppConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(AppConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(AppConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS)
        if (BuildConfig.DEBUG) okHttpClient.addInterceptor(loggingInterceptor)
        return okHttpClient.build()
    }

    /**
     * provide logging interceptor
     */
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    /**
     * Base URL
     */
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BaseURL

}

