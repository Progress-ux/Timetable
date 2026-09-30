package com.example.timetable.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import java.time.LocalDate

class MyClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    private val url = "https://www.lrmk.ru/api/open/"

    suspend fun weeks(): List<String> =
        client.get("$url/weeks").body()

    suspend fun groups(): List<Group> =
        client.get("$url/groups").body()

    suspend fun teachers(): List<Teacher> =
        client.get("$url/teachers").body()

    suspend fun rooms(): List<Room> =
        client.get("$url/rooms").body()

    suspend fun periods(): List<Period> =
        client.get("$url/pairs").body()

    suspend fun subjects(group: Int): List<Subject> =
        client.get("$url/subjects?group=$group").body()

    suspend fun lessons(group: Int, week: LocalDate): List<Lesson> =
        client.get("$url/timetable?group=$group&week=$week").body()
}