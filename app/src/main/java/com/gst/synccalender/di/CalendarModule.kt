package com.gst.synccalender.di

import android.content.Context
import com.gst.synccalender.data.remote.api.ApiCalendar
import com.gst.synccalender.data.remote.api.ApiOauth
import com.gst.synccalender.utils.network.Api.BASE_URL
import com.gst.synccalender.utils.network.Api.BASE_URL_OAUTH
import com.gst.synccalender.utils.network.StringConverterFactory
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Singleton


/**
 * Created by gideon on 8/26/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@Module
@InstallIn(SingletonComponent::class)
object CalendarModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    @OkHttpDefault
    fun providesBasicOkHttpClient(@ApplicationContext ctx: Context): OkHttpClient {
        // Define the OkHttp Client with its cache!
        // Assigning a CacheDirectory
        val myCacheDir = File(ctx.cacheDir, "OkHttpCache")
        // You should create it...
        val cacheSize = 1024 * 1024
        val cacheDir = Cache(myCacheDir, cacheSize.toLong())
        val httpLogInterceptor = HttpLoggingInterceptor()
        httpLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder() //add a cache
            .cache(cacheDir)
            .addInterceptor(httpLogInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @RetrofitWithOauth
    fun provideRetrofitWithOauth(moshi: Moshi, @OkHttpDefault okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(StringConverterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun providesApiOauth(
        @RetrofitWithOauth retrofit: Retrofit,
        @OkHttpDefault okHttpClient: OkHttpClient
    ): ApiOauth {
        return retrofit.newBuilder().client(okHttpClient).build()
            .create(ApiOauth::class.java)
    }
}