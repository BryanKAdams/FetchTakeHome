package com.bryankeltonadams.fetchtakehometest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bryankeltonadams.fetchtakehometest.model.Item
import com.bryankeltonadams.fetchtakehometest.ui.screens.ItemListScreen
import kotlinx.serialization.Serializable


// Type Safe Routing
@Serializable
object ItemList

@Composable
fun FetchNavHost(navHostController: NavHostController = rememberNavController()) {
    NavHost(
        navController = rememberNavController(),
        // Compose Navigation Beta allows for Type Safe Routing instead of using strings
        startDestination = ItemList
    ) {
        composable<ItemList> {
            val dummyItems = listOf(
                Item(id = 755, listId = 2, name = ""),
                Item(id = 203, listId = 2, name = ""),
                Item(id = 684, listId = 1, name = "Item 684"),
                Item(id = 276, listId = 1, name = "Item 276"),
                Item(id = 736, listId = 3, name = null),
                Item(id = 926, listId = 4, name = null),
                Item(id = 808, listId = 4, name = "Item 808"),
                Item(id = 599, listId = 1, name = null),
                Item(id = 424, listId = 2, name = null),
                Item(id = 444, listId = 1, name = ""),
                Item(id = 809, listId = 3, name = null),
                Item(id = 293, listId = 2, name = null),
                Item(id = 510, listId = 2, name = null),
                Item(id = 680, listId = 3, name = "Item 680"),
                Item(id = 231, listId = 2, name = null),
                Item(id = 534, listId = 4, name = "Item 534"),
            )
            val sortedItems = dummyItems.sortedBy { it.name }
            val groupedItems = sortedItems.groupBy { it.listId }.toSortedMap(
                compareBy { it }
            )
            ItemListScreen()
        }
    }
}