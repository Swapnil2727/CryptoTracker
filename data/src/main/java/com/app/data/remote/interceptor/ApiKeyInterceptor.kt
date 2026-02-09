package com.app.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("x-cg-demo-api-key", apiKey)
            .build()
        return chain.proceed(newRequest)
    }
}