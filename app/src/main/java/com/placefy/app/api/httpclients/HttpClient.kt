package com.placefy.app.api.httpclients

import android.content.Context
import com.placefy.app.api.interceptors.AuthInterceptor
import com.placefy.app.api.interceptors.ResponseInterceptor
import com.placefy.app.api.interceptors.SupportInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class HttpClient() {
    companion object {
        fun public(): OkHttpClient {
            val builder = OkHttpClient.Builder()
            builder
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(SupportInterceptor())
                .addInterceptor(ResponseInterceptor())
            return builder.build()
        }

        fun private(context: Context): OkHttpClient {
            val builder = OkHttpClient.Builder()
            builder
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(SupportInterceptor())
                .addInterceptor(ResponseInterceptor())
                .authenticator(AuthInterceptor(context))
            return builder.build()
        }
    }
}