package com.example.beoapp.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StatsOfLevelService {

    @GET("stats/{level}")
    suspend fun getAvaiableStats(@Path("level") level: String): Response<ResponseBody>

}