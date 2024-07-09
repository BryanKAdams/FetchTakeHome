package com.bryankeltonadams.fetchtakehometest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bryankeltonadams.fetchtakehometest.ui.theme.FetchTakeHomeTestTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
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
                Surface {
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
                            LazyColumn(
                                modifier = Modifier
                                    .safeDrawingPadding()
                                    .fillMaxSize()
                            ) {
                                groupedItems.forEach {
                                    stickyHeader {
                                        ListItem(headlineContent = {
                                            Text(
                                                text = "List ${it.key}",
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                        })
                                    }
                                    items(it.value) { item ->
                                        ListItem(headlineContent = {
                                            Text("Item ${item.id} - ${item.name}")
                                        }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

data class Item(
    val id: Int? = null,
    val name: String? = null,
    val listId: Int? = null,
)

@Serializable
object ItemList
