package com.example.timetable.data

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import androidx.core.content.edit

class MyModel(app: Application): AndroidViewModel(app) {
    private val api = MyClient()

    var groupNumber: Int
        get() = myGroup
        set(value) {
            myGroup = value
            if (value != 0)
                prefs.edit { putInt("group", value) }
        }
    var error: String? by mutableStateOf(null)

    private val prefs = app.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    private var myGroup: Int by mutableIntStateOf(prefs.getInt("group", 0))

    fun home() {
        myGroup = prefs.getInt("group", 0)
    }

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
        emit(api.rooms().associate { it.Key to it.UTF })
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