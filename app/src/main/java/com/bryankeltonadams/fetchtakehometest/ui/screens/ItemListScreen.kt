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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bryankeltonadams.fetchtakehometest.data.model.Item
import java.util.SortedMap

@Composable
fun ItemListScreen(itemListScreenViewModel: ItemListScreenViewModel) {
    val uiState by
    itemListScreenViewModel.itemListScreenUiState.collectAsStateWithLifecycle()
    ItemListScreen(itemListScreenUiState = uiState)
}

data class ItemListScreenUiState(
    val sectionedItems: SortedMap<Int, List<Item>>? = null,
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