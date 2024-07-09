package com.bryankeltonadams.fetchtakehometest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bryankeltonadams.fetchtakehometest.ui.theme.FetchTakeHomeTestTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchTakeHomeTestTheme {
                // implement a Sticky Header for each group

                // this box is to hold the NavHost and the SnackbarHost
                // it is common to have a single Scaffold which holds the NavHost and SnackbarHost
                // however, I prefer to have each screen have it's own Scaffold so that it can
                // have it's own TopAppBar.
                // I'm comfortable with either implementation though.
                Box(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = rememberNavController(),
                        // Compose Navigation Beta allows for Type Safe Routing instead of using strings
                        startDestination = ScreenA
                    ) {
                        composable<ScreenA> {


                        }
                    }
                }
            }
        }
    }
}

@Serializable
object ScreenA
