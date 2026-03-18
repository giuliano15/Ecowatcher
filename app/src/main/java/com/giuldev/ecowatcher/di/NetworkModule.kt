package com.giuldev.ecowatcher.di

import com.giuldev.ecowatcher.data.remote.api.CoinGeckoApi
import com.giuldev.ecowatcher.data.remote.interceptor.ApiMonitorInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Módulo do Dagger-Hilt responsável por prover dependências relacionadas a Rede (Network).
 * Contém as definições e configurações para Retrofit, OkHttp e serviços da API.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiMonitorInterceptor(): ApiMonitorInterceptor {
        return ApiMonitorInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiMonitorInterceptor: ApiMonitorInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiMonitorInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CoinGeckoApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCoinGeckoApi(retrofit: Retrofit): CoinGeckoApi {
        return retrofit.create(CoinGeckoApi::class.java)
    }
}
