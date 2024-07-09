package com.bryankeltonadams.fetchtakehometest.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bryankeltonadams.fetchtakehometest.R
import com.bryankeltonadams.fetchtakehometest.data.model.Item

@Composable
// some code bases name this function ItemListRoute, since it's composable that lives
// within the navigation graph and pulls the data and actions from the view model
// I prefer to just use the same name but a different signature
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
        // I could have put the conditional logic inside the lazy column and created
        // single box items for the progress indicator and the error message
        // but I prefer having the other conditional parts of the ui outside the lazy column
        // this does require having to pass in the scroll state to the boxes though
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
                        /** something quirky I noticed with the Divider implementation is
                        the gap between the Divider and ListItem isn't perfectly flush
                        if you look closely you can see a bit of the items text bleed through
                        I left this here to pinpoint it and show my attention for detail
                        I've resolved this issue by just putting a border on the ListItem
                        which technically does put borders on the sides as well
                        but if I wanted to I could have created a customer modifier that
                        only draws a border on the top or bottom.
                        ^ have to uncomment below code and comment the new  ListItem to
                        see what I mean
                         */

//                        HorizontalDivider()
//                        ListItem(headlineContent = {
//                            Text(
//                                text = "List ${it.key}",
//                                style = MaterialTheme.typography.titleLarge
//                            )
//                        })
//                        HorizontalDivider()

                        ListItem(modifier = Modifier.border(1.dp, Color.Black), headlineContent = {
                            Text(
                                text = "List ${it.key}",
                                style = MaterialTheme.typography.titleLarge
                            )
                        })
                    }
                    // go through the items in the map and create a ListItem for each
                    items(it.value) { item ->
                        ListItem(headlineContent = {
                            item.id?.let { itemId ->
                                Text(
                                    stringResource(
                                        id = R.string.list_item_headline_text, itemId
                                    )
                                )
                            }
                        }, supportingContent = {
                            item.name?.let { itemName ->
                                Text(
                                    stringResource(
                                        id = R.string.list_item_supporting_text, itemName
                                    )
                                )
                            }
                        },
                            // sort of redundant, but for the sake of displaying
                            // all of the data within the item.
                            trailingContent = {
                                Text(
                                    stringResource(
                                        id = R.string.list_item_trailing_text, item.listId
                                    )
                                )
                            })
                    }
                }
            }
        }
    }
}
