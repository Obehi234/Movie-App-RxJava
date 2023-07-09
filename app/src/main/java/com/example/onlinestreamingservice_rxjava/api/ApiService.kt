package com.example.onlinestreamingservice_rxjava.api

import com.example.onlinestreamingservice_rxjava.model.MovieResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("movie/popular")
    fun getMovies(@Header("Authorization") authorization: String): Observable<MovieResponse>
}