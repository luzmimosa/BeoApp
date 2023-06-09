package com.example.beoapp.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface AvaiableStatsService {
    @GET("stats")
    suspend fun getAvaiableStats(): Response<ResponseBody>
}