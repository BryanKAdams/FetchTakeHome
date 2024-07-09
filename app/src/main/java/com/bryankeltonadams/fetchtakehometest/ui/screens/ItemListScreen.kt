package com.bryankeltonadams.fetchtakehometest.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bryankeltonadams.fetchtakehometest.data.model.Item

@Composable
fun ItemListScreen(itemListScreenViewModel: ItemListScreenViewModel) {
    val uiState by itemListScreenViewModel.itemListScreenUiState.collectAsStateWithLifecycle()
    ItemListScreen(
        itemListScreenUiState = uiState, onRefresh = itemListScreenViewModel::refresh
    )
}

data class ItemListScreenUiState(
    val sectionedItems: Map<Int, List<Item>>? = null,
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val error: String? = null,
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
    itemListScreenUiState: ItemListScreenUiState, onRefresh: () -> Unit
) {
    val sectionedItems = itemListScreenUiState.sectionedItems

    // I had a great discussion with multiple guys in the Kotlin Slack channel
    // about the production implementation of PullToRefresh and how it could use some work
    // and how it was a step back from the material 2 implementation
    // this new PullToRefreshBox on the material3 beta is much better

    PullToRefreshBox(isRefreshing = itemListScreenUiState.isRefreshing, onRefresh = onRefresh) {
        if (itemListScreenUiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else if (itemListScreenUiState.error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center), text = itemListScreenUiState.error
                )
            }

        } else {
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
                        ListItem(
                            headlineContent = {
                                Text("Item ID:  ${item.id}")
                            },
                            supportingContent = {
                                Text("Item Name:  ${item.name}")
                            })
                    }
                }
            }
        }
    }
}