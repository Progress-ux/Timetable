package com.example.timetable.data

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate

class MyModel(app: Application): AndroidViewModel(app) {
    private val api = MyClient()

    var error: String? by mutableStateOf(null)

    fun weeks() = flow {
        emit(api.weeks().map { LocalDate.parse(it) })
    }.flowOn(Dispatchers.IO).catch { error = it.message }

    fun groups() = flow {
        emit(api.groups())
    }.flowOn(Dispatchers.IO).catch { error = it.message }

    fun teachers() = flow {
        emit(api.teachers().associate { it.Key to it.Teacher })
    }.flowOn(Dispatchers.IO).catch { error = it.message }

    fun rooms() = flow {
        emit(api.rooms())
    }.flowOn(Dispatchers.IO).catch { error = it.message }

    fun periods() = flow {
        emit(api.periods().associate { it.Key to it })
    }.flowOn(Dispatchers.IO).catch { error = it.message }

    fun subjects(group: Int) = flow {
        emit(api.subjects(group).associate { it.Key to it.Subject })
    }.flowOn(Dispatchers.IO).catch { error = it.message }

    fun lessons(group: Int, week: LocalDate) = flow {
        emit(api.lessons(group, week))
    }.flowOn(Dispatchers.IO).catch { error = it.message }
}