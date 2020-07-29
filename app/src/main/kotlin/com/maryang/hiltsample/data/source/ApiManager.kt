package com.maryang.hiltsample.data.source

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ApiManager {

    val gson =
        GsonBuilder()
            .setLenient()
            .create()

    private val okHttpClientBuilder =
        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

    private val githubAdapter by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(okHttpClientBuilder
                .addInterceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                        .header(
                            "Authorization",
                            "token 0162bd7f308926de655d852b82d74a840a4e05aa"
                        )
                    chain.proceed(requestBuilder.build())
                }
                .build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    val githubApi: GithubApi by lazy {
        githubAdapter.create(GithubApi::class.java)
    }
}
