package com.placefy.app.api

import android.content.Context
import com.placefy.app.API_URL
import com.placefy.app.VIA_CEP_API_URL
import com.placefy.app.api.httpclients.HttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper(val context: Context) {
    val viaCepApi: Retrofit = Retrofit.Builder()
        .baseUrl(VIA_CEP_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val noAuthApi: Retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(HttpClient.public(context))
        .build()

    val authApi: Retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(HttpClient.private(context))
        .build()
}