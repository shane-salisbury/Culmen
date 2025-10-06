package com.weather.core.network.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddAppIdInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        val url: HttpUrl = request.url
            .newBuilder()
            .addQueryParameter("appid", "2df71e3ec3a79b58d8daf6427bcd807b")
            .build()

        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}