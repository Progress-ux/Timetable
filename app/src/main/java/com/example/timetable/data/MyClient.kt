package com.example.timetable.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson

class MyClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    private val url = "https://www.lrmk.ru/api/open/"

    suspend fun weeks(): List<String> =
        client.get("$url/weeks").body()
}