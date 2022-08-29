package com.gst.synccalender.di

import javax.inject.Qualifier


/**
 * Created by gideon on 8/12/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OkHttpDefault

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OkHttpWithAuth

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RestApiWithCache

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitDefault

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitWithOauth