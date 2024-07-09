package com.bryankeltonadams.fetchtakehometest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.bryankeltonadams.fetchtakehometest.ui.navigation.FetchNavHost
import com.bryankeltonadams.fetchtakehometest.ui.theme.FetchTakeHomeTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchTakeHomeTestTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    FetchNavHost()
                }
            }
        }
    }
}
