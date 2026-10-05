package com.example.timetable.data

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import androidx.core.content.edit
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.timetable.db.Database
import kotlin.collections.associate

class MyModel(app: Application): AndroidViewModel(app) {
    private val api = MyClient()

    private val driver = AndroidSqliteDriver(Database.Schema, app, "database.db")
    private val database = Database(driver)

    var groupNumber: Long
        get() = myGroup
        set(value) {
            myGroup = value
            if (value != 0L)
                prefs.edit { putLong("group", value) }
        }
    var error: String? by mutableStateOf(null)

    private val prefs = app.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    private var myGroup: Long by mutableLongStateOf(prefs.getLong("group", 0L))

    fun home() {
        myGroup = prefs.getLong("group", 0L)
    }

    fun weeks() = flow {
        emit(api.weeks().map { LocalDate.parse(it) })
    }.flowOn(Dispatchers.IO).catch { error = it.message }

    fun groups() = flow {
        emit(database.groupQueries.all().executeAsList())
        val groups = api.groups()

        database.groupQueries.transaction {
            database.groupQueries.clear()
            groups.forEach {
                database.groupQueries.insert(it.Name, it.Year, it.Spec)
            }
        }
        emit(groups)
    }.flowOn(Dispatchers.IO).catch { error = it.message }

    fun teachers() = flow {
        emit(database.teacherQueries.all().executeAsList().associate { it.Key to it.Teacher })
        val teachers = api.teachers()

        database.teacherQueries.transaction {
            database.groupQueries.clear()
            teachers.forEach {
                database.teacherQueries.insert(it.Teacher)
            }
        }
        emit(teachers.associate { it.Key to it.Teacher })
    }.flowOn(Dispatchers.IO).catch { error = it.message }

    fun rooms() = flow {
        emit(database.roomQueries.all().executeAsList().associate { it.Key to it.UTF})
        val rooms = api.rooms()

        database.roomQueries.transaction {
            database.groupQueries.clear()
            rooms.forEach {
                database.roomQueries.insert(it.UTF)
            }
        }
        emit(rooms.associate { it.Key to it.UTF })
    }.flowOn(Dispatchers.IO).catch { error = it.message }

    fun periods() = flow {
        emit(database.periodQueries.all().executeAsList().associate { it.Key to it })
        val periods = api.periods()

        database.periodQueries.transaction {
            database.periodQueries.clear()
            periods.forEach {
                database.periodQueries.insert(it.Position, it.Begin, it.End)
            }
        }
        emit(periods.associate { it.Key to it })
    }.flowOn(Dispatchers.IO).catch { error = it.message }

    fun subjects(group: Long) = flow {
        emit(database.subjectQueries.all(group).executeAsList().associate { it.Key to it.Subject })
        val subjects = api.subjects(group)

        database.subjectQueries.transaction {
            database.subjectQueries.clear()
            subjects.forEach {
                database.subjectQueries.insert(it.Subject, it.Hours, it.First, group)
            }
        }
        emit(subjects.associate { it.Key to it.Subject })
    }.flowOn(Dispatchers.IO).catch { error = it.message }

    fun lessons(group: Long, week: LocalDate) = flow {
        emit(database.lessonQueries.all(group, week.toEpochDay()).executeAsList())
        val lessons = api.lessons(group, week)

        database.lessonQueries.transaction {
            database.lessonQueries.clear()
            lessons.forEach {
                database.lessonQueries.insert(it.Day, it.Pair, it.Subj, it.Teacher, it.Room, group)
            }
        }
        emit(lessons)
    }.flowOn(Dispatchers.IO).catch { error = it.message }
}