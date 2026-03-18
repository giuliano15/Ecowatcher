package com.giuldev.ecowatcher.data.remote.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.Charset

/**
 * Custom OkHttp Interceptor para logar as requisições e respostas da API.
 * Formata os detalhes informando a tag padrão "API_MONITOR" no Logcat do Android, facilitando o debug.
 */
class ApiMonitorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        val requestUrl = request.url
        
        Log.d("API_MONITOR", "--> Request: [${request.method}] $requestUrl")
        
        val response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            Log.e("API_MONITOR", "<-- Error: ${e.message} for $requestUrl")
            throw e
        }
        
        val responseBody = response.body
        val source = responseBody?.source()
        source?.request(Long.MAX_VALUE) // Buffer the entire body.
        val buffer = source?.buffer
        
        var responseString = ""
        if (buffer != null) {
            val charset = responseBody?.contentType()?.charset(Charset.forName("UTF-8")) ?: Charset.forName("UTF-8")
            responseString = buffer.clone().readString(charset)
        }
        
        Log.d("API_MONITOR", "<-- Response [${response.code}] $requestUrl")
        Log.d("API_MONITOR", "Body: $responseString")
        
        return response
    }
}
