package com.placefy.app.api.interceptors

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AuthInterceptor : Authenticator {

    // TODO add token
    override fun authenticate(route: Route?, response: Response): Request? {
        return response.request().newBuilder()
            .addHeader("Authorization", "token")
            .build()
    }
}