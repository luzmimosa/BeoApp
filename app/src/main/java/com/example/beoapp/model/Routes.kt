package com.example.beoapp.model

sealed class Routes(val route: String) {
    object HostScreen: Routes("host")
    object AvaiableLevelsScreen: Routes("avaiableLevels")
    object LevelScreen: Routes("level/{levelID}") {
        fun route(levelID: String): String {
            return "level/$levelID"
        }
    }
}
