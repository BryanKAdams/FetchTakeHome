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
