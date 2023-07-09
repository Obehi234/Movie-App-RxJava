package com.example.onlinestreamingservice_rxjava.api

import com.example.onlinestreamingservice_rxjava.utils.Constants.BASEURL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    fun getInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

}