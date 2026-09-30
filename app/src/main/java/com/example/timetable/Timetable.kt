package com.example.timetable

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.timetable.data.MyModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.collections.emptyMap

@SuppressLint("RememberReturnType")
@Composable
fun Timetable(model: MyModel) {
    val groupNumber = remember { model.groupNumber }
    val today = remember { LocalDate.now() }

    val subjects by remember { model.subjects(groupNumber) }.collectAsState(emptyMap())
    val periods by remember { model.periods() }.collectAsState(emptyMap())
    val teachers by remember { model.teachers() }.collectAsState(emptyMap())
    val rooms by remember { model.rooms() }.collectAsState(emptyMap())
    val weeks by remember { model.weeks() }.collectAsState(emptyList())

    val week = weeks.lastOrNull { it <= today } ?: return

    val timetable by remember {
        model.lessons(groupNumber, week)
    }.collectAsState(emptyList())

    val days = remember(timetable) { timetable.groupBy { it.Day }.toSortedMap() }
    val full = remember { DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL) }

    LazyColumn(modifier = Modifier.padding(12.dp)) {
        days.forEach { (day, lessons) ->
            stickyHeader {
                Text(week.plusDays(day - 1L).format(full))
                HorizontalDivider()
            }
            items(lessons.sortedBy { it.Pair }) { lesson ->
                val formattedTime = periods[lesson.Pair]?.Begin
                    ?.substringBeforeLast(":") ?: ""

                Column() {
                    Row() {
                        Text(formattedTime, Modifier.padding(horizontal = 10.dp))
                        Text(subjects[lesson.Subj] ?: "")
                    }
                    Row() {
                        Text(rooms[lesson.Room] ?: "", Modifier.padding(horizontal = 10.dp))
                        Text(teachers[lesson.Teacher] ?: "")
                    }
                }
                HorizontalDivider()
            }
        }
    }
}