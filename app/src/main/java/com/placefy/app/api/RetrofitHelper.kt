package com.placefy.app.api

import android.content.Context
import com.placefy.app.api.httpclients.HttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper(val context: Context) {
    val viaCepApi: Retrofit = Retrofit.Builder()
        .baseUrl("https://viacep.com.br/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val noAuthApi: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5556/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(HttpClient.public(context))
        .build()

    val authApi: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5556/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(HttpClient.private(context))
        .build()
}