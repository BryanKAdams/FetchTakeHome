package com.bryankeltonadams.fetchtakehometest.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bryankeltonadams.fetchtakehometest.model.Item

@Composable
fun ItemListScreen() {
    ItemListScreen(ItemListScreenUiState())
}

data class ItemListScreenUiState(
    val sectionedItems: Map<Int, List<Item>>? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemListScreen(itemListScreenUiState: ItemListScreenUiState) {
    val sectionedItems = itemListScreenUiState.sectionedItems
    LazyColumn(
        modifier = Modifier
            .safeDrawingPadding()
            .fillMaxSize()
    ) {
        sectionedItems?.forEach {
            // create a sticky header for each listId
            stickyHeader {
                ListItem(headlineContent = {
                    Text(
                        text = "List ${it.key}",
                        style = MaterialTheme.typography.titleLarge
                    )
                })
            }
            // go through the items in the map and create a ListItem for each
            items(it.value) { item ->
                ListItem(headlineContent = {
                    Text("Item ${item.id} - ${item.name}")
                }
                )
            }
        }
    }
}