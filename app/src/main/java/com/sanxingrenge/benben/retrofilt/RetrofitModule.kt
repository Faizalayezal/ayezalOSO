package com.sanxingrenge.benben.retrofilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    //  val BASE_URL_RETRO = "https://gurutechnolabs.co.in/website/laravel/osomate/api/v1/android/restapi/"
    val BASE_URL_RETRO = "http://apps.osomate.app/api/v1/android/restapi/"
    // val BASE_URL_RETRO = "http://apps.osomate.app/"

    private fun getHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .connectTimeout(160, TimeUnit.SECONDS)
            .writeTimeout(160, TimeUnit.SECONDS)
            .readTimeout(160, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofit(): ApiServiceClass {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_RETRO)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient())
            .build()
            .create(ApiServiceClass::class.java)
    }


    @Singleton
    @Provides
    fun getMainRepo(apiServiceClass: ApiServiceClass): MainRepo {
        return DefaultMainRepo(apiServiceClass)
    }

}