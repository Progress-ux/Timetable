package com.example.timetable

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.timetable.data.MyModel
import com.example.timetable.data.logos

@SuppressLint("UnrememberedMutableState")
@Composable
fun Groups(model: MyModel) {
    val groups by model.groups().collectAsState(emptyList())

    BackHandler() { model.home() }

    LazyVerticalGrid(GridCells.Adaptive(120.dp)) {
        items(groups.filter { '#' !in it.Name }) { group ->
            Row(
                modifier = Modifier
                    .clickable { model.groupNumber = group.Key },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(logos[group.Spec.toInt()] ?: R.drawable.logo2015),
                    null,
                    modifier = Modifier
                        .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
                        .size(32.dp)
                )
                Text(group.Name, modifier = Modifier.padding(8.dp).fillMaxWidth())
                VerticalDivider(modifier = Modifier.height(40.dp))
            }
            HorizontalDivider()
        }
    }
}