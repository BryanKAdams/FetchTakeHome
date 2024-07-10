package com.bryankeltonadams.fetchtakehometest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.bryankeltonadams.fetchtakehometest.ui.components.FetchSnackbarHost
import com.bryankeltonadams.fetchtakehometest.ui.navigation.FetchNavHost
import com.bryankeltonadams.fetchtakehometest.ui.theme.FetchTakeHomeTestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
            val coroutineScope = rememberCoroutineScope()
            FetchTakeHomeTestTheme {
                Scaffold(
                    snackbarHost = {
                        FetchSnackbarHost(snackbarHostState = snackbarHostState)
                    }, modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    FetchNavHost(modifier = Modifier.padding(innerPadding),
                        onShowSnackbar = { message ->
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = message, duration = SnackbarDuration.Short
                                )
                            }
                        })
                }
            }
        }
    }
}
