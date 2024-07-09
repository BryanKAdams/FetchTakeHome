package com.bryankeltonadams.fetchtakehometest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bryankeltonadams.fetchtakehometest.ui.screens.ItemListScreen
import com.bryankeltonadams.fetchtakehometest.ui.screens.ItemListScreenViewModel
import kotlinx.serialization.Serializable


// Type Safe Routing
@Serializable
object ItemList

// Compose Navigation is overkill for this project but I wanted to demonstrate it
// and also allow the flexibility to easily implement a detail screen or anything else.
// i.e. it's not overkill, it's just planned architecture to allow the app to scale.

@Composable
fun FetchNavHost(navHostController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navHostController,
        // Compose Navigation Beta allows for Type Safe Routing instead of using strings
        startDestination = ItemList
    ) {
        composable<ItemList> {
            // move to dependency injection if time permits
            val filterViewModel = hiltViewModel<ItemListScreenViewModel>()
            ItemListScreen(filterViewModel)
        }
    }
}
