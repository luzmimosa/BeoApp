package com.example.beoapp.network

import android.util.Log
import com.example.beoapp.network.model.CustomStatRecord
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.ConnectException
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit


class ServerApi(val serverAddress: String) {


    companion object {

        var currentApi: ServerApi? = null

        fun createApi(serverAddress: String): ServerApi {
            currentApi = ServerApi(serverAddress)
            return currentApi!!;
        }

        val httpClient = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

    }
    init {
        currentApi = this
    }

    private val host = "http://$serverAddress:8080/";

    val avaiableStatsService: AvaiableStatsService by lazy {
        getRetrofit().create(AvaiableStatsService::class.java)
    }

    val statsOfLevelService: StatsOfLevelService by lazy {
        getRetrofit().create(StatsOfLevelService::class.java)
    }

    fun getRetrofit() = Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(httpClient)
        .build()

    /**
     * Get avaiable levels from server
     * @return Array of avaiable levels
     * @throws ConnectException if could not connect to server
     */
    suspend fun getAvaiableLevels(): Array<String> {
        try {
            val response = avaiableStatsService.getAvaiableStats()
            if (response.isSuccessful) {
                val responseBody = response.body() ?: throw ConnectException("Could not connect to server")

                val json = responseBody.string().let {
                    JsonParser.parseString(it)
                }

                Log.i("ServerApi", "getAvaiableLevels: $json")

                if (json != null) {
                    if (json.isJsonArray) {
                        val jsonArray = json.asJsonArray
                        val levels = mutableListOf<String>()
                        for (level in jsonArray) {
                            levels.add(level.asString.replace(".json", ""))
                        }

                        return levels.toTypedArray()
                    }
                }
            }
            throw ConnectException("Could not connect to server")
        } catch (e: Exception) {
            throw ConnectException("Could not connect to server")
        }
    }

    suspend fun getRecordsOf(level: String): Array<CustomStatRecord> {
        try {
            val response = statsOfLevelService.getAvaiableStats(level)

            val json = response.body()?.string()?.let {
                JsonParser.parseString(it)
            }

            val records = mutableListOf<CustomStatRecord>()

            if (json != null) {
                if (json.isJsonArray) {
                    for (record in json.asJsonArray) {
                        val dateTime = LocalDateTime.ofEpochSecond(record.asJsonObject.get("timestamp").asLong, 0, ZoneOffset.UTC);
                        val orbStats = record.asJsonObject.get("orbStats").asJsonArray;

                        val map = mutableMapOf<String, Int>()

                        for (orbStat in orbStats) {
                            val orbName = orbStat.asJsonObject.get("orbName").asString
                            val statMap = orbStat.asJsonObject.get("statMap").asJsonObject

                            statMap.keySet().forEach {
                                map.put(it, statMap.get(it).asInt)
                            }

                            records.add(CustomStatRecord(dateTime, orbName, map))
                        }
                    }
                }
            }

            for (record in records) {
                record.printSelf()
            }

            return records.toTypedArray()
        } catch (e: Exception) {
            e.printStackTrace()
            return arrayOf()
        }
    }

    fun getAddress(): String {
        return serverAddress
    }
}