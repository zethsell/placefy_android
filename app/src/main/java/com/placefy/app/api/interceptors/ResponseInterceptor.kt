package com.placefy.app.api.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build()

        val response = chain.proceed(request)
        var token: String? = null

        Log.i("interceptor-response", "$response")

        if (!response.isSuccessful) {


//        if (response.code() == 403) {
//            //TODO forbidden method
//        }
//
//        if (response.code() == 401 && token !== null) {
//            //TODO unauthorized method
//        }
//
            if (response.code() == 401 && token === null) {

//            val alert = AlertDialog.Builder()
//            alert.setTitle("test")
//            alert.setNeutralButton("ok", DialogInterface.OnClickListener { dialog, id ->
//                // START THE GAME!
//            })
//            alert.create()
//            alert.show()


                Log.i("error", "invalid credentials")
            }
        }

        return response
    }
}