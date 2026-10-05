package com.example.timetable.data

import com.example.timetable.db.Groups
import com.example.timetable.db.Lessons
import com.example.timetable.db.Periods
import com.example.timetable.db.Rooms
import com.example.timetable.db.Subjects
import com.example.timetable.db.Teachers
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

    suspend fun groups(): List<Groups> =
        client.get("$url/groups").body()

    suspend fun teachers(): List<Teachers> =
        client.get("$url/teachers").body()

    suspend fun rooms(): List<Rooms> =
        client.get("$url/rooms").body()

    suspend fun periods(): List<Periods> =
        client.get("$url/pairs").body()

    suspend fun subjects(group: Long): List<Subjects> =
        client.get("$url/subjects?group=$group").body()

    suspend fun lessons(group: Long, week: LocalDate): List<Lessons> =
        client.get("$url/timetable?group=$group&week=$week").body()
}