package com.example.timetable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import com.example.timetable.ui.theme.TimetableTheme
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
                    snackbarHost = { SnackbarHost(snack) },
                    floatingActionButton = {
                        if (model.groupNumber != 0) {
                            FloatingActionButton({ model.groupNumber = 0 }) {
                                Icon(Icons.Default.Settings, "")
                            }
                        }
                    }
                ) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        if (model.groupNumber == 0)
                            Groups(model)
                        else
                            Timetable(model)
                    }
                }
            }
        }
    }
}