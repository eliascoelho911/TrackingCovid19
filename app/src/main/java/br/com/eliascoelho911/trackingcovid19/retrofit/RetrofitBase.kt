package br.com.eliascoelho911.trackingcovid19.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class RetrofitBase<T>(
    baseUrl: String,
    serviceClass: Class<T>
) {
    private val retrofit: Retrofit
    val service: T

    init {
        val client = configuraClient()
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        service = retrofit.create(serviceClass)
    }

    private fun configuraClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}