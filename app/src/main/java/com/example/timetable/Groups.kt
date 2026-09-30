package com.example.timetable

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.timetable.data.MyModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun Groups(model: MyModel) {
    val groups by model.groups().collectAsState(emptyList())

    BackHandler() { model.home() }

    LazyColumn() {
        items(groups) { group ->
            Text(group.toString(),
                modifier = Modifier
                    .clickable { model.groupNumber = group.Key }
                    .padding(vertical = 12.dp)
            )
            HorizontalDivider()
        }
    }
}