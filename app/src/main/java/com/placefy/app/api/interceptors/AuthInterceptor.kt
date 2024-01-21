package com.placefy.app.api.interceptors

import android.content.Context
import com.placefy.app.database.dao.AuthDAO
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AuthInterceptor(val context: Context) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val auth = AuthDAO(context).show()

        return response.request().newBuilder()
            .addHeader("Authorization", auth.accessToken.toString())
            .build()
    }
}