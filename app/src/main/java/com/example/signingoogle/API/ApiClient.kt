package com.example.signingoogle.API

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor


class ApiClient {

    private var retrofit: Retrofit? = null

    internal enum class Urls {
        DEVELOPMENT, STAGEING, PRODUCTION;

        val baseUrl: String?
            get() {
                when (this) {
                    DEVELOPMENT -> return null
                    STAGEING -> return "https://opentdb.com/"
                    PRODUCTION -> return null
                }

                return null
            }
    }

    private var BASE_URL = Urls.STAGEING


    fun getClient(): Retrofit? {

        val interceptor = HttpLoggingInterceptor()

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL.baseUrl!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit
    }
}