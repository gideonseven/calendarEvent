package com.gst.synccalender.di

import android.content.Context
import com.gst.synccalender.api.ApiCalendar
import com.gst.synccalender.api.ApiOauth
import com.gst.synccalender.utils.network.Api.BASE_URL
import com.gst.synccalender.utils.network.Api.BASE_URL_OAUTH
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
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
    @RetrofitDefault
    fun provideRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    @RetrofitWithOauth
    fun provideRetrofitWithOauth(moshi: Moshi): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL_OAUTH)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    @OkHttpDefault
    fun providesBasicOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .readTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun providesApiCalendar(
        @RetrofitDefault retrofit: Retrofit,
        @OkHttpWithAuth okHttpClient: OkHttpClient
    ): ApiCalendar {
        return retrofit.newBuilder().client(okHttpClient).build()
            .create(ApiCalendar::class.java)
    }

    @Provides
    @Singleton
    fun providesApiOauth(
        @RetrofitWithOauth retrofit: Retrofit,
        @OkHttpDefault okHttpClient: OkHttpClient
    ): ApiOauth {
        return retrofit.newBuilder().client(okHttpClient).build()
            .create(ApiOauth::class.java)
    }

    @Provides
    @Singleton
    @RestApiWithCache
    fun providesApiServiceWithCache(
        @ApplicationContext context: Context,
        @OkHttpDefault okHttpClient: OkHttpClient,
        retrofit: Retrofit
    ): ApiOauth {
        val cacheSize = 1024 * 1024
        val cache = Cache(context.cacheDir, cacheSize.toLong())
        val clientCache = okHttpClient.newBuilder()
            .cache(cache)
            .addInterceptor { chain ->
                // for caching
                val request = chain.request()
                request.newBuilder()
                    .cacheControl(
                        CacheControl.Builder().maxStale(7, TimeUnit.DAYS).build()
                    )
                    .build()
                chain.proceed(request)
            }
            .build()
        return retrofit.newBuilder().client(clientCache).build()
            .create(ApiOauth::class.java)
    }
}