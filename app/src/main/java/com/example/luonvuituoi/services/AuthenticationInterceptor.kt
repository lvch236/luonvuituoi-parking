package com.example.luonvuituoi.services

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("apiKey", "9af02cf6621e4fa78ef961236262ee53")
            .build()

        val request: Request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
