package com.placefy.app.api.interceptors

import android.content.Context
import android.util.Log
import com.placefy.app.activities.ui.dialog.SuccessDialog
import com.placefy.app.database.dao.AuthDAO
import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build()

        val response = chain.proceed(request)

        val token = AuthDAO(context).show().accessToken.toString()



        Log.i("interceptor-response", "$response")

        if (!response.isSuccessful) {


//        if (response.code() == 403) {
//            //TODO forbidden method
//        }
//
            if (response.code() == 401 && token === "") {
//            //TODO unauthorized method
                SuccessDialog("TESTE")
            }
//
            if (response.code() == 401 && token !== "") {

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