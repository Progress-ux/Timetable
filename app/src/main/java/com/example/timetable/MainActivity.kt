package com.example.timetable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.timetable.ui.theme.TimetableTheme
import kotlin.collections.emptyList
import androidx.compose.runtime.*
import com.example.timetable.data.MyModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val model by viewModels<MyModel>()
        setContent {
            val snack = remember { SnackbarHostState() }
            LaunchedEffect(model.error) {
                model.error?.let {
                    snack.showSnackbar(it, withDismissAction = true)
                    model.error = null
                }
            }
            TimetableTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snack) }
                ) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        val weeks by model.weeks().collectAsState(emptyList())
                        for (w in weeks) Text(w.toString())
                    }
                }
            }
        }
    }
}