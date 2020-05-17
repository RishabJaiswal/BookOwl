package com.gutenberg.bookowl.data

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.gutenberg.bookowl.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val BASE_URL = "http://skunkworks.ignitesol.com:8000/"

    /**this is used to log the API calls made by the app in debug build*/
    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().run {
        addInterceptor(loggingInterceptor)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        this.build()
    }

    /**here we set the configuration for serialization/deserialization via ObjctMapper
     * add any converters like Jackson, Gson, etc. and other configurations*/
    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder().apply {
        //configuring object mapper
        val objectMapper = ObjectMapper().apply {
            // don't fail on unknown properties
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
        }
        addConverterFactory(JacksonConverterFactory.create(objectMapper))
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        client(okHttpClient)
    }

    //building the retrofit client with baseUrl
    private val retrofitClient: Retrofit = retrofitBuilder
        .baseUrl(BASE_URL)
        .build()


    //building the retrofit client without baseUrl
    private val baselessRetrofitClient: Retrofit by lazy {
        retrofitBuilder
            .build()
    }

    fun <T> createApiService(serviceClass: Class<T>): T {
        return retrofitClient.create(serviceClass)
    }

    fun <T> createBaselessService(serviceClass: Class<T>): T {
        return baselessRetrofitClient.create(serviceClass)
    }
}