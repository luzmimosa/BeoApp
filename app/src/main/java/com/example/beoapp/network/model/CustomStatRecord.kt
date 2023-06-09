package com.example.beoapp.network.model

import java.time.LocalDateTime

data class CustomStatRecord(val dateTime: LocalDateTime, val orbName: String, val map: Map<String, Int>) {
    fun printSelf() {
        println("dateTime: $dateTime, orbName: $orbName")
        map.forEach { (key, value) ->
            println("\t$key: $value")
        }
    }
}
