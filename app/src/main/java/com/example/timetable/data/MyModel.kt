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
}