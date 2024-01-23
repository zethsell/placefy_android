package com.placefy.app.api.interceptors

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.placefy.app.activities.MainActivity
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


        if (!response.isSuccessful) {

//        if (response.code() == 403) {
//            //TODO forbidden method
//        }
//
            if (response.code() == 401 && token === "") {
                redirectMain()
            }

            if (response.code() == 401 && token !== "") {
                redirectMain()
            }
        }

        return response
    }

    private fun redirectMain() {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        ContextCompat.startActivity(context, intent, null)
    }
}